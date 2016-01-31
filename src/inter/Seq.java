package inter;
/**
 * 语句序列
 *
 */
public class Seq extends Stmt {

   Stmt stmt1; Stmt stmt2;

   public Seq(Stmt s1, Stmt s2) { stmt1 = s1; stmt2 = s2; }
   /**
    * 生成序列语句的三地址并打印。形如：<br>
    * Lb:	stmt1<br>
    * Llabel:	stmt2<br>
    * La:
    */
   public void gen(int b, int a) {
      if ( stmt1 == Stmt.Null ) stmt2.gen(b, a);
      else if ( stmt2 == Stmt.Null ) stmt1.gen(b, a);
      else {
         int label = newlabel();
         stmt1.gen(b,label);
         emitlabel(label);
         stmt2.gen(label,a);
      }
   }
}
