package symbols;
import lexer.*;
/**
 * 构造数据类型——数组。
 *
 */
public class Array extends Type {
   /**
    * 数组的基本类型。
    */
   public Type of;                  // array *of* type
   /**
    * 数组的大小。
    */
   public int size = 1;             // number of elements
   public Array(int sz, Type p) {
      super("[]", Tag.INDEX, sz*p.width); size = sz;  of = p;
   }
   public String toString() { return "[" + size + "] " + of.toString(); }
}
