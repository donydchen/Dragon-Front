package inter;
import symbols.*;
/**
 * if (expr) stmt1 else stmt2语句
 *
 */
public class Else extends Stmt {

   Expr expr; Stmt stmt1, stmt2;
   /**
    * 初始化else语句并对expr做类型检查
    * @param x 判断语句
    * @param s1 stmt1
    * @param s2 stmt2
    */
   public Else(Expr x, Stmt s1, Stmt s2) {
      expr = x; stmt1 = s1; stmt2 = s2;
      if( expr.type != Type.Bool ) expr.error("boolean required in if");
   }
   /**
    * 生成else语句的三地址并打印，形如:<br>
    * iffalse expr goto Llabel2<br>
    * label1:	stmt1 goto La<br>
    * label2:	stmt2<br>
    */
   public void gen(int b, int a) {
      int label1 = newlabel();   // label1 for stmt1
      int label2 = newlabel();   // label2 for stmt2
      expr.jumping(0,label2);    // fall through to stmt1 on true
      emitlabel(label1); stmt1.gen(label1, a); emit("goto L" + a);
      emitlabel(label2); stmt2.gen(label2, a);
   }
}
