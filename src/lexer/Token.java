package lexer;
/**
 * 所有词法单元的父类。
 *
 */
public class Token {
	
	/**
	 * 词法单元的tag，对应于Tag类中的终结符常量。
	 */
	public final int tag;
	public Token(int t) { tag = t; }
	/**
	 * @return String 词法单元的tag
	 */
	public String toString() {return "" + (char)tag;}
}
