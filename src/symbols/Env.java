package symbols;
import java.util.*; import lexer.*; import inter.*;
/**
 * 标识符的作用域，采用链接符号表的方式实现。
 *
 */
public class Env {
	/**
	 * 符号表。从Token对应到符号表条目
	 */
	private Hashtable<Token, Id> table;
	/**
	 * 链接到下一个环境。
	 */
	protected Env prev;
	/**
	 * 初始化环境。创建一个当前作用域的符号表table，并将当前环境链接到另一个环境n上。
	 * @param n 另一个环境Env
	 */
	public Env(Env n) { table = new Hashtable<Token, Id>(); prev = n; }
	/**
	 * 将词法单元加入当前符号表table中。
	 * @param w 词法单元。
	 * @param i 标识符条目。
	 */
	public void put(Token w, Id i) { table.put(w, i); }
	/**
	 * 在可见作用域中查找词法单元
	 * @param w 待查找的词法单元
	 * @return Id 查找到的标识符条目或者null
	 */
	public Id get(Token w) {
		for( Env e = this; e != null; e = e.prev ) {
			Id found = (Id)(e.table.get(w));
			if( found != null ) return found;
		}
		return null;
	}
}
