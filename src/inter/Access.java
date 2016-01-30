package inter;
import lexer.*; import symbols.*;
/**
 * 数组运算
 *
 */
public class Access extends Op {

   public Id array;
   public Expr index;

   public Access(Id a, Expr i, Type p) {    // p is element type after
      super(new Word("[]", Tag.INDEX), p);  // flattening the array
      array = a; index = i;
   }
   /**
    * 生成正常代码
    */
   public Expr gen() { return new Access(array, index.reduce(), type); }
   /**
    * 生成跳转代码
    */
   public void jumping(int t,int f) { emitjumps(reduce().toString(),t,f); }
   /**
    * @return 数组运算表达式,形如“a[i]”
    */
   public String toString() {
      return array.toString() + " [ " + index.toString() + " ]";
   }
}
