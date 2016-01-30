package inter;
import lexer.*; import symbols.*;
/**
 * 单目运算符表达式。
 * 这里只处理负号。
 *
 */
public class Unary extends Op {
   
   public Expr expr;
   /**
    * 初始化Unary。
    * 调用super来初始化，但类型type先使用null作为占位符，后面再确定。
    * type必须是“数字”形的，若expr类型大于int则返回，否则将Unary的type设置为int。通过max函数实现
    * @param tok 表达式的运算符。
    * @param x 表达式。
    */
   public Unary(Token tok, Expr x) {    // handles minus, for ! see Not
      super(tok, null);  expr = x;
      type = Type.max(Type.Int, expr.type);
      if (type == null ) error("type error");
   }
   /**
    * 创建一个单目运算符节点。
    */
   public Expr gen() { return new Unary(op, expr.reduce()); }
   /**
    * @return 单目运算符表达式。形式为“- expr”。
    */
   public String toString() { return op.toString()+" "+expr.toString(); }
}
