package lexer;
import java.io.*; import java.util.*; import symbols.*;
/**
 * 词法分析器
 *
 */
public class Lexer {
   /**
    * 当前代码所对应的行码，第一行行码为1.
    */
   public static int line = 1;
   /**
    * 当前读取的字符。
    */
   char peek = ' ';
   /**
    * String到Word词法单元的对应表，用于管理保留字以及已经识别到的标识符。
    * 如果识别到的String是保留字或者标识符，那么就直接返回该Word词法单元而不创建新的Word。
    */
   Hashtable<String, Word> words = new Hashtable<String, Word>();
   /**
    * 将Word压入Hashtable中。
    * @param w Word词法单元。
    */
   void reserve(Word w) { words.put(w.lexeme, w); }
   /**
    * 初始化Lexer类，将保留字，布尔值以及基本类型的词法单元压入Hashtable表中。
    */
   public Lexer() {

      reserve( new Word("if",    Tag.IF)    );
      reserve( new Word("else",  Tag.ELSE)  );
      reserve( new Word("while", Tag.WHILE) );
      reserve( new Word("do",    Tag.DO)    );
      reserve( new Word("break", Tag.BREAK) );

      reserve( Word.True );  reserve( Word.False );

      reserve( Type.Int  );  reserve( Type.Char  );
      reserve( Type.Bool );  reserve( Type.Float );
   }
   /**
    * 读取一个字符，保存在{@link #peek}中。
    * @throws IOException
    * @see #peek
    */
   void readch() throws IOException { peek = (char)System.in.read(); }
   /**
    * 判断读取的字符是否与输入参数c相同。
    * 起到预读字符的作用，用于判断组合符号，如“&&”等。
    * @param c 期望的字符
    * @return boolean 相同则返回true，否则返回false。
    * @throws IOException
    */
   boolean readch(char c) throws IOException {
      readch();
      if( peek != c ) return false;
      peek = ' '; //初始化peek，在下一轮调用scan时，可以跳过并顺利读取下一个字符。
      return true;
   }
   /**
    * 获取一个词法单元。<br>
    * 主要包含以下几个过程：
    * <ul>
    * <li>跳过空白字符</li>
    * <li>读取组合符号</li>
    * <li>读取整数或者浮点数</li>
    * <li>读取保留字，标识符等</li>
    * <li>读取其他符号或者非法字符</li>
    * </ul>
    * @return Token 读取到的词法单元。
    * @throws IOException
    */
   public Token scan() throws IOException {
	  //跳过空格，tab以及换行，如果遇到换行，将line加1。 
      for( ; ; readch() ) {
         if( peek == ' ' || peek == '\t' ) continue;
         else if( peek == '\n' ) line = line + 1;
         else break;
      }
      //读取“&&”等组合符号，调用readch()函数来预读下一个字符。
      switch( peek ) {
      case '&': 
         if( readch('&') ) return Word.and;  else return new Token('&');
      case '|':
         if( readch('|') ) return Word.or;   else return new Token('|');
      case '=':
         if( readch('=') ) return Word.eq;   else return new Token('=');
      case '!':
         if( readch('=') ) return Word.ne;   else return new Token('!');
      case '<':
         if( readch('=') ) return Word.le;   else return new Token('<');
      case '>':
         if( readch('=') ) return Word.ge;   else return new Token('>');
      }
      //读取整数或者浮点数，通过小数点来区分。
      if( Character.isDigit(peek) ) {
         int v = 0;
         do {
            v = 10*v + Character.digit(peek, 10); readch();
         } while( Character.isDigit(peek) );
         if( peek != '.' ) return new Num(v);
         float x = v; float d = 10;
         for(;;) {
            readch();
            if( ! Character.isDigit(peek) ) break;
            x = x + Character.digit(peek, 10) / d; d = d*10;
         }
         return new Real(x);
      }
      //读取合法字符串。如果是Hashtable中已存在的word，直接返回。否则创建词法单元并压入Hashtable中。
      if( Character.isLetter(peek) ) {
         StringBuffer b = new StringBuffer();
         do {
            b.append(peek); readch();
         } while( Character.isLetterOrDigit(peek) );
         String s = b.toString();
         Word w = (Word)words.get(s);
         if( w != null ) return w;
         w = new Word(s, Tag.ID);
         words.put(s, w);
         return w;
      } 
      //返回其他字符，包括其他“+”号等其他运算符号以及非法字符如“$"等。
      Token tok = new Token(peek); peek = ' ';
      return tok;
   }
}
