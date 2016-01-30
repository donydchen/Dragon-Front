package inter;
import lexer.Token;
/**
 * 非运算
 *
 */
public class Not extends Logical {

   public Not(Token tok, Expr x2) { super(tok, x2, x2); }
   /**
    * 生成非运算的跳转代码并打印。
    * 只需要将表达式的true和false出口对调就可以了。
    * @param t true出口
    * @param f false出口
    */
   public void jumping(int t, int f) { expr2.jumping(f, t); }
   /**
    * @return 非运算表达式，形如“! expr”。
    */
   public String toString() { return op.toString()+" "+expr2.toString(); }
}
