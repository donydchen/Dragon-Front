package inter;
import lexer.*; import symbols.*;
/**
 * 布尔常量True和False以及数值常量
 *
 */
public class Constant extends Expr {
   /**
    * 初始化布尔值常量
    * @param tok 词法单元。保留字True和False。
    * @param p 类型。Bool
    */
   public Constant(Token tok, Type p) { super(tok, p); }
   /**
    * 初始化数值常量。类型设置为Int。
    * @param i 数值
    */
   public Constant(int i) { super(new Num(i), Type.Int); }

   public static final Constant
      True  = new Constant(Word.True,  Type.Bool),
      False = new Constant(Word.False, Type.Bool);
   /**
    * 打印跳转代码， 形如“goto Ln”。
    * @param t true出口
    * @param f false出口
    */
   public void jumping(int t, int f) {
      if ( this == True && t != 0 ) emit("goto L" + t);
      else if ( this == False && f != 0) emit("goto L" + f);
   }
}
