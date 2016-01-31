package inter;
import symbols.*;
/**
 * Do stmt while (expr)语句
 *
 */
public class Do extends Stmt {

   Expr expr; Stmt stmt;

   public Do() { expr = null; stmt = null; }

   public void init(Stmt s, Expr x) {
      expr = x; stmt = s;
      if( expr.type != Type.Bool ) expr.error("boolean required in do");
   }
   /**
    * 生成Do语句的三地址并打印。形如：<br>
    * Lb:	stmt<br>
    * Llabel:	if expr goto Lb<br>
    * La:<br>
    */
   public void gen(int b, int a) {
      after = a;
      int label = newlabel();   // label for expr
      stmt.gen(b,label);
      emitlabel(label);
      expr.jumping(b,0);
   }
}
