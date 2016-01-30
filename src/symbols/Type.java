package symbols;
import lexer.*;
/**
 * 基本数据类型。
 * 其中，初始化了如下几个基本类型：
 * <ul>
 * <li>float， 宽度8</li>
 * <li>int, 宽度4</li>
 * <li>char， 宽度1</li>
 * <li>bool， 宽度1</li>
 * </ul>
 *
 */
public class Type extends Word {
   /**
    * 类型所占用的空间，用于内存分配
    */
   public int width = 0;          // width is used for storage allocation
   /**
    * 初始化类型。
    * @param s 类型所对应的词素
    * @param tag 类型所对应的tag
    * @param w 类型所占用的空间，为width赋值
    */
   public Type(String s, int tag, int w) { super(s, tag); width = w; }

   public static final Type
      Int   = new Type( "int",   Tag.BASIC, 4 ),
      Float = new Type( "float", Tag.BASIC, 8 ),
      Char  = new Type( "char",  Tag.BASIC, 1 ),
      Bool  = new Type( "bool",  Tag.BASIC, 1 );
   /**
    * 判断类型是否为“数字”类型，包括int，float，char。
    * “数字”类型的值可进行类型转换。
    * @param p 词法单元对应的类型
    * @return 布尔值。若是数字类型，则返回true，否则返回false
    */
   public static boolean numeric(Type p) {
      if (p == Type.Char || p == Type.Int || p == Type.Float) return true;
      else return false;
   }
   /**
    * 返回两个数字形变量中，占用空间较大的类型，用于类型转换。
    * 类似于java中的拓宽（widening）转换。
    * @param p1 词法单元1的类型
    * @param p2 词法单元2的类型
    * @return 两个参数中，占用空间较大的类型。
    */
   public static Type max(Type p1, Type p2 ) {
      if ( ! numeric(p1) || ! numeric(p2) ) return null;
      else if ( p1 == Type.Float || p2 == Type.Float ) return Type.Float;
      else if ( p1 == Type.Int   || p2 == Type.Int   ) return Type.Int;
      else return Type.Char;
   }
}
