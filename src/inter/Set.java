package inter;
import symbols.Type;
/**
 * Id = expr 赋值语句
 *
 */
public class Set extends Stmt {

   public Id id; public Expr expr;

   public Set(Id i, Expr x) {
      id = i; expr = x;
      if ( check(id.type, expr.type) == null ) error("type error");
   }
   /**
    * 类型检查
    * @param p1 左部标识符的类型
    * @param p2 右部表达式的类型
    * @return 合法类型或者null
    */
   public Type check(Type p1, Type p2) {
      if ( Type.numeric(p1) && Type.numeric(p2) ) return p2;
      else if ( p1 == Type.Bool && p2 == Type.Bool ) return p2;
      else return null;
   }
   /**
    * 生成赋值语句的三地址并打印。形如：<br>
    * id = expr<br>
    */
   public void gen(int b, int a) {
      emit( id.toString() + " = " + expr.gen().toString() );
   }
}
