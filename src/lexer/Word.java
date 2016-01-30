package lexer;
/**
 * 保留字，标识符以及复合符号的词法单元
 *
 */
public class Word extends Token {
   /**
    * 词法单元的词素。
    */
   public String lexeme = "";
   /**
    * 初始化Word词法单元。
    * @param s 为词素lexeme赋值。
    * @param tag 为Word的tag赋值，tag来自于Tag中定义的保留字。
    */
   public Word(String s, int tag) { super(tag); lexeme = s; }
   /**
    * @return String 词法单元的词素lexeme
    */
   public String toString() { return lexeme; }

   public static final Word

      and = new Word( "&&", Tag.AND ),  or = new Word( "||", Tag.OR ),
      eq  = new Word( "==", Tag.EQ  ),  ne = new Word( "!=", Tag.NE ),
      le  = new Word( "<=", Tag.LE  ),  ge = new Word( ">=", Tag.GE ),

      minus  = new Word( "minus", Tag.MINUS ),
      True   = new Word( "true",  Tag.TRUE  ),
      False  = new Word( "false", Tag.FALSE ),
      temp   = new Word( "t",     Tag.TEMP  );
}
