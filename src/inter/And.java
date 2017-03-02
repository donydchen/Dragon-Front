package inter;
import lexer.Token;
/**
 * 和运算
 *
 */
public class And extends Logical {

   public And(Token tok, Expr x1, Expr x2) { super(tok, x1, x2); }
   /**
    * 生成B1 &amp;&amp; B2运算的三地址码并打印。B1和B2只要有一个为假，整个表达式就是假。形如：<br>
    * iffalse expr1 goto label<br>
    * if expr2 goto Lt<br>
    * goto Lf<br>
    * label:<br>
    * label确保了expr1的false出口一定是跟在整个块的结尾处。
    * @param t true出口
    * @param f false出口
    */
   public void jumping(int t, int f) {
      int label = f != 0 ? f : newlabel();
      expr1.jumping(0, label);
      expr2.jumping(t,f);
      if( f == 0 ) emitlabel(label);
   }
}
