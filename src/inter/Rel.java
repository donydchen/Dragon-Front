package inter;
import lexer.*; import symbols.*;
/**
 * compare operation like {@literal ("<", "==")} ,etc
 * compare operation like {@literal <}, {@literal ==}, etc
 *
 */
public class Rel extends Logical {
   /**
    * 初始化比较运算
    * @param tok 运算符
    * @param x1 第一个运算数
    * @param x2 第二个运算数
    */
   public Rel(Token tok, Expr x1, Expr x2) { super(tok, x1, x2); }
   /**
    * 判断两个运算数的类型
    * @param p1 第一个运算数的类型
    * @param p2 第二个运算数的类型
    * @return 如果两个运算数的类型相同并且都不是数组元素，返回Bool，否则返回null
    */
   public Type check(Type p1, Type p2) {
      if ( p1 instanceof Array || p2 instanceof Array ) return null;
      else if( p1 == p2 ) return Type.Bool;
      else return null;
   }
   /**
    * 生成关系运算式的三地址跳转代码并打印。主要包括以下三个过程：<br>
    * <ul>
    * <li>将expr1规约为一个单一地址a，并打印规约过程三地址码</li>
    * <li>将expr2规约为一个单一地址b，并打印规约过程三地址码</li>
    * <li>构造表达式"a op b",然后再打印最后的跳转代码</li>
    * </ul>
    * @param t true出口
    * @param f false出口
    */
   public void jumping(int t, int f) {
      Expr a = expr1.reduce();
      Expr b = expr2.reduce();
      String test = a.toString() + " " + op.toString() + " " + b.toString();
      emitjumps(test, t, f);
   }
}
