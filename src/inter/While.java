package inter;
import symbols.*;
/**
 * while(expr) stmt 语句
 *
 */
public class While extends Stmt {

   Expr expr; Stmt stmt;

   public While() { expr = null; stmt = null; }
   /**
    * 初始化while语句并对expr做类型检查。
    * @param x expr
    * @param s stmt
    */
   public void init(Expr x, Stmt s) {
      expr = x;  stmt = s;
      if( expr.type != Type.Bool ) expr.error("boolean required in while");
   }
   /**
    * 生成while语句三地址并打印。形如：<br>
    * iffalse expr goto La<br>
    * Llabel:	stmt<br>
    * goto Lb<br>
    */
   public void gen(int b, int a) {
      after = a;                // save label a
      expr.jumping(0, a);
      int label = newlabel();   // label for stmt
      emitlabel(label); stmt.gen(label, b);
      emit("goto L" + b);
   }
}
