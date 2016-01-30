package inter;
import lexer.Token;
/**
 * 或运算
 */
public class Or extends Logical {
   
   public Or(Token tok, Expr x1, Expr x2) { super(tok, x1, x2); }
   /**
    * 生成B1 || B2运算的三地址码并打印。B1和B2只要有一个为真，整个表达式就是真。形如：<br>
    * if expr1 goto label<br>
    * if expr2 goto Lt<br>
    * goto Lf<br>
    * label:<br>
    * label确保了expr1的true出口一定是跟在整个块的结尾处。
    * @param t true出口
    * @param f false出口
    */
   public void jumping(int t, int f) {
      int label = t != 0 ? t : newlabel(); 
      expr1.jumping(label, 0);
      expr2.jumping(t,f);
      if( t == 0 ) emitlabel(label);
   }
}
