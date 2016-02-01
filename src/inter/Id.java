package inter;
import lexer.*; import symbols.*;
/**
 * 标识符
 *
 */
public class Id extends Expr {
	/**
	 * 相对地址。
	 */
	public int offset;     // relative address
	/**
	 * 初始化id
	 * @param id 标识符
	 * @param p 类型
	 * @param b 偏移地址，offset。
	 */
	public Id(Word id, Type p, int b) { super(id, p); offset = b; }

	//public String toString() {return op.toString() + " " + type.toString() ;}
}
