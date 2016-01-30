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

	public Id(Word id, Type p, int b) { super(id, p); offset = b; }

//	public String toString() {return "" + op.toString() + offset;}
}
