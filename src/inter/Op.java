package inter;
import lexer.*; import symbols.*;
/**
 * 运算符表达式的父类
 *
 */
public class Op extends Expr {
   /**
    * 初始化表达式。
    * @param tok 表达式的词法单元
    * @param p 表达式的类型
    */
   public Op(Token tok, Type p)  { super(tok, p); }
   /**
    * 将运算符表达式规约为一个单一的值tn。
    * 并打印一个形如“tn = x”的表达式。
    * @return 临时变量tn
    */
   public Expr reduce() {
      Expr x = gen();
      Temp t = new Temp(type);
      emit( t.toString() + " = " + x.toString() );
      return t;
   }
}
