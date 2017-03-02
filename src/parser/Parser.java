package parser;
import java.io.*; import lexer.*; import symbols.*; import inter.*;
/**
 * 语法生成器，采用递归下降方法。
 *
 */
public class Parser {
   /**
    * lexical analyzer for this parser
    */
   private Lexer lex;    
   /**
    * lookahead token
    */
   private Token look;   
   /**
    * current or top symbol table
    */
   Env top = null;       
   /**
    * storage used for declarations
    */
   int used = 0;         

   public Parser(Lexer l) throws IOException { lex = l; move(); }
   /**
    * 读取下一个词法单元，保存到look中。
    * @throws IOException
    */
   void move() throws IOException { look = lex.scan(); }
   /**
    * 打印错误信息。
    * @param s 出错语句。
    */
   void error(String s) { throw new Error("near line "+Lexer.line+": "+s); }
   /**
    * 判断读取的词法单元的tag是否与预期相同，相同则继续读取下一个，否则报语法错误
    * @param t 预期词法单元tag
    * @throws IOException 语法错误
    */
   void match(int t) throws IOException {
      if( look.tag == t ) move();
      else error("syntax error");
   }
   /**
    * program {@literal ->} block<br>
    * 先构建整棵抽象语法树，然后打印三地址码。
    * @throws IOException
    */
   public void program() throws IOException {  
      Stmt s = block();
      int begin = s.newlabel();  int after = s.newlabel();
      s.emitlabel(begin);  s.gen(begin, after);  s.emitlabel(after);
   }
   /**
    * block -> { decls stmts }<br>
    * 在进入block前，先保存环境变量，然后创建新的环境变量，退出时恢复环境变量。
    * @return stmt地址
    * @throws IOException
    */
   Stmt block() throws IOException { 
      match('{');  Env savedEnv = top;  top = new Env(top);
      decls(); Stmt s = stmts();
      match('}');  top = savedEnv;
      return s;
   }

   void decls() throws IOException {

      while( look.tag == Tag.BASIC ) {   // D -> type ID ;
         Type p = type(); Token tok = look; match(Tag.ID); match(';');
         Id id = new Id((Word)tok, p, used);
         top.put( tok, id );
         used = used + p.width;
      }
   }
   /**
    * 判断基本类型，通过match实现。
    * @return 类型
    * @throws IOException
    */
   Type type() throws IOException {

      Type p = (Type)look;            // expect look.tag == Tag.BASIC 
      match(Tag.BASIC);
      if( look.tag != '[' ) return p; // T -> basic
      else return dims(p);            // return array type
   }
   /**
    * 数组类型
    * @param p 数组基本类型
    * @return 类型
    * @throws IOException
    */
   Type dims(Type p) throws IOException {
      match('[');  Token tok = look;  match(Tag.NUM);  match(']');
      if( look.tag == '[' )
    	  p = dims(p);
      return new Array(((Num)tok).value, p);
   }

   Stmt stmts() throws IOException {
      if ( look.tag == '}' ) return Stmt.Null;
      else return new Seq(stmt(), stmts());
   }

   Stmt stmt() throws IOException {
      Expr x;  Stmt s1, s2;
      Stmt savedStmt;         // save enclosing loop for breaks

      switch( look.tag ) {
      //此处有问题。作者为了实现if(bool);语句而设计的，但原文法并不允许，而且这将导致if(bool);else;等语句都合法，不太合理。
      case ';':
         move();
         return Stmt.Null;

      case Tag.IF:
         match(Tag.IF); match('('); x = bool(); match(')');
         s1 = stmt();
         if( look.tag != Tag.ELSE ) return new If(x, s1);
         match(Tag.ELSE);
         s2 = stmt();
         return new Else(x, s1, s2);

      case Tag.WHILE:
         While whilenode = new While();
         savedStmt = Stmt.Enclosing; Stmt.Enclosing = whilenode;
         match(Tag.WHILE); match('('); x = bool(); match(')');
         s1 = stmt();
         whilenode.init(x, s1);
         Stmt.Enclosing = savedStmt;  // reset Stmt.Enclosing
         return whilenode;

      case Tag.DO:
         Do donode = new Do();
         savedStmt = Stmt.Enclosing; Stmt.Enclosing = donode;
         match(Tag.DO);
         s1 = stmt();
         match(Tag.WHILE); match('('); x = bool(); match(')'); match(';');
         donode.init(s1, x);
         Stmt.Enclosing = savedStmt;  // reset Stmt.Enclosing
         return donode;

      case Tag.BREAK:
         match(Tag.BREAK); match(';');
         return new Break();

      case '{':
         return block();

      default:
         return assign();
      }
   }
   /**
    * 赋值语句，包括基本的赋值和数组元素赋值。
    * @return 语句地址。
    * @throws IOException
    */
   Stmt assign() throws IOException {
      Stmt stmt;  Token t = look;
      match(Tag.ID);
      Id id = top.get(t);
      if( id == null ) error(t.toString() + " undeclared");

      if( look.tag == '=' ) {       // S -> id = E ;
         move();  stmt = new Set(id, bool());
      }
      else {                        // S -> L = E ;
         Access x = offset(id);
         match('=');  stmt = new SetElem(x, bool());
      }
      match(';');
      return stmt;
   }
   /**
    * 布尔运算。<br>
    * 先做和运算，再做与运算。
    * @return expr
    * @throws IOException
    * @see {@link #join()}
    */
   Expr bool() throws IOException {
      Expr x = join();
      while( look.tag == Tag.OR ) {
         Token tok = look;  move();  x = new Or(tok, x, join());
      }
      return x;
   }
   /**
    * 和运算。<br>
    * 先做等价运算，再做和运算。
    * @return expr
    * @throws IOExceptio 
    * @see {@link #equality()}
    */
   Expr join() throws IOException {
      Expr x = equality();
      while( look.tag == Tag.AND ) {
         Token tok = look;  move();  x = new And(tok, x, equality());
      }
      return x;
   }
   /**
    * 等价运算。<br>
    * 先做关系运算，再做等价运算。
    * @return expr
    * @throws IOException
    * @see {@link #rel()}
    */
   Expr equality() throws IOException {
      Expr x = rel();
      while( look.tag == Tag.EQ || look.tag == Tag.NE ) {
         Token tok = look;  move();  x = new Rel(tok, x, rel());
      }
      return x;
   }
   /**
    * 关系运算。<br>
    * 先做加减运算，再做关系运算。
    * @return expr
    * @throws IOException
    * @see {@link #equality()}
    */
   Expr rel() throws IOException {
      Expr x = expr();
      switch( look.tag ) {
      case '<': case Tag.LE: case Tag.GE: case '>':
         Token tok = look;  move();  return new Rel(tok, x, expr());
      default:
         return x;
      }
   }
   /**
    * 加减运算。<br>
    * 先做乘除运算，再做加减运算。
    * @return expr
    * @throws IOException
    * @see {@link #term()}
    */
   Expr expr() throws IOException {
      Expr x = term();
      while( look.tag == '+' || look.tag == '-' ) {
         Token tok = look;  move();  x = new Arith(tok, x, term());
      }
      return x;
   }
   /**
    * 乘除运算。<br>
    * 先做单目运算，再做乘除运算。
    * @return expr
    * @throws IOException
    * @see {@link #unary()}
    */
   Expr term() throws IOException {
      Expr x = unary();
      while(look.tag == '*' || look.tag == '/' ) {
         Token tok = look;  move();   x = new Arith(tok, x, unary());
      }
      return x;
   }
   /**
    * 单目运算。
    * @return expr
    * @throws IOException
    */
   Expr unary() throws IOException {
      if( look.tag == '-' ) {
         move();  return new Unary(Word.minus, unary());
      }
      else if( look.tag == '!' ) {
         Token tok = look;  move();  return new Not(tok, unary());
      }
      else return factor();
   }
   /**
    * 处理表达式因子
    * @return expr
    * @throws IOException
    */
   Expr factor() throws IOException {
      Expr x = null;
      switch( look.tag ) {
      case '(':
         move(); x = bool(); match(')');
         return x;
      case Tag.NUM:
         x = new Constant(look, Type.Int);    move(); return x;
      case Tag.REAL:
         x = new Constant(look, Type.Float);  move(); return x;
      case Tag.TRUE:
         x = Constant.True;                   move(); return x;
      case Tag.FALSE:
         x = Constant.False;                  move(); return x;
      default:
         error("syntax error");
         return x;
      case Tag.ID:
         Id id = top.get(look);
         if( id == null ) error(look.toString() + " undeclared");
         move();
         if( look.tag != '[' ) return id;
         else return offset(id);
      }
   }
   /**
    * 数组元素偏移值计算
    * @param a 数组id
    * @return 数组地址
    * @throws IOException
    */
   Access offset(Id a) throws IOException {   // I -> [E] | [E] I
      Expr i; Expr w; Expr t1, t2; Expr loc;  // inherit id

      Type type = a.type;
      match('['); i = bool(); match(']');     // first index, I -> [ E ]
      type = ((Array)type).of;
      w = new Constant(type.width);
      t1 = new Arith(new Token('*'), i, w);
      loc = t1;
      while( look.tag == '[' ) {      // multi-dimensional I -> [ E ] I
         match('['); i = bool(); match(']');
         type = ((Array)type).of;
         w = new Constant(type.width);
         t1 = new Arith(new Token('*'), i, w);
         t2 = new Arith(new Token('+'), loc, t1);
         loc = t2;
      }

      return new Access(a, loc, type);
   }
}
