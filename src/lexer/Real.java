package lexer;
/**
 * 实数词法单元。
 *
 */
public class Real extends Token {
	/**
	 * 实数数值。
	 */
	public final float value;
    //use the super keyword to invoke a superclass's constructor
	/**
	 * 初始化实数词法单元。
	 * 设置其tag为Tag.REAL， 用输入参数v为value赋值。
	 * @param v 实数的数值
	 */
	public Real(float v) { super(Tag.REAL); value = v; }
	/**
	 * @return String 实数数值。
	 */
	public String toString() { return "" + value; } //""是为了将value转换为String的策略。
}
