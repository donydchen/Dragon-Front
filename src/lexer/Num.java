package lexer;
/**
 * 整数词法单元。
 *
 */
public class Num extends Token {
	/**
	 * 保存整数的数值。
	 */
	public final int value;
	/**
	 * 初始化整数词法单元。
	 * 设置其tag为Tag.NUM，用输入参数v为value赋值。
	 * @param v 整数数值，给value赋值
	 */
	public Num(int v) { super(Tag.NUM); value = v; }
	/**
	 * @return String 整数的数值。
	 */
	public String toString() { return "" + value; }
}
