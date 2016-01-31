package inter;
import symbols.*;
/**
 * if (Expr) S 语句
 *
 */
public class If extends Stmt {

   Expr expr; Stmt stmt;

   public If(Expr x, Stmt s) {
      expr = x;  stmt = s;
      if( expr.type != Type.Bool ) expr.error("boolean required in if");
   }
   /**
    * 生成if语句三地址码并打印，形如:<br>
    * iffalse expr goto La<br>
    * label:	stmt<br>
    * 
    */
   public void gen(int b, int a) {
      int label = newlabel(); // label for the code for stmt
      expr.jumping(0, a);     // fall through on true, goto a on false
      emitlabel(label); stmt.gen(label, a);
   }
}
