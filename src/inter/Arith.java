package inter;
import lexer.*; import symbols.*;
/**
 * 双目运算符表达式节点。
 *
 */
public class Arith extends Op {

   public Expr expr1, expr2;
   /**
    * 初始化双目运算符表达式节点 。
    * 两个运算数都必须是数字形。该节点对应的类型是两个运算数中类型较大的类型。
    * @param tok 符号单元
    * @param x1 第一个运算数
    * @param x2 第二个运算数
    */
   public Arith(Token tok, Expr x1, Expr x2)  {
      super(tok, null); expr1 = x1; expr2 = x2;
      type = Type.max(expr1.type, expr2.type);
      if (type == null ) error("type error");
   }
   /**
    * 生成双目运算符节点。
    */
   public Expr gen() {
      return new Arith(op, expr1.reduce(), expr2.reduce());
   }
   /**
    * @return 双目运算符表达式，形式为“x1 op x2”
    */
   public String toString() {
      return expr1.toString()+" "+op.toString()+" "+expr2.toString();
   }
}
