package inter;
import symbols.Array;
import symbols.Type;
/**
 * a[i] = expr数组元素的赋值语句
 *
 */
public class SetElem extends Stmt {

   public Id array; public Expr index; public Expr expr;

   public SetElem(Access x, Expr y) {
      array = x.array; index = x.index; expr = y;
      if ( check(x.type, expr.type) == null ) error("type error");
   }

   public Type check(Type p1, Type p2) {
      if ( p1 instanceof Array || p2 instanceof Array ) return null;
      else if ( p1 == p2 ) return p2;
      else if ( Type.numeric(p1) && Type.numeric(p2) ) return p2;
      else return null;
   }
   /**
    * 生成数组元素的赋值语句并打印。形如：<br>
    * a[i] = expr<br>
    */
   public void gen(int b, int a) {
      String s1 = index.reduce().toString();
      String s2 = expr.reduce().toString();
      emit(array.toString() + " [ " + s1 + " ] = " + s2);
   }
}
