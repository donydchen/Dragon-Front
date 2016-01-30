package inter;
import lexer.*; import symbols.*;
/**
 * 逻辑运算表达式，做为AND，OR，NOT的父类，提供公用的功能
 *
 */
public class Logical extends Expr {

   public Expr expr1, expr2;
   /**
    * 初始化逻辑运算表达式节点。
    * @param tok 运算符单元
    * @param x1 第一个运算数
    * @param x2 第二个运算数
    * @see #check(Type, Type)
    */
   Logical(Token tok, Expr x1, Expr x2) {
      super(tok, null);                      // null type to start
      expr1 = x1; expr2 = x2;
      type = check(expr1.type, expr2.type);
      if (type == null ) error("type error");
   }
   /**
    * 判断运算数的类型。
    * @param p1 第一个运算数的类型
    * @param p2 第二个运算数的类型
    * @return 若两个运算数都是bool，则返回Bool，否则返回null
    */
   public Type check(Type p1, Type p2) {
      if ( p1 == Type.Bool && p2 == Type.Bool ) return Type.Bool;
      else return null;
   }
   /**
    * 生成布尔表达式并打印。形如<br>
    * iffalse Expr goto Lf<br>
    * tn = true<br>
    * goto La<br>
    * Lf:	tn = false<br>
    * La:
    * @return 临时变量tn
    */
   public Expr gen() {
      int f = newlabel(); int a = newlabel();
      Temp temp = new Temp(type);
      this.jumping(0,f);
      emit(temp.toString() + " = true");
      emit("goto L" + a);
      emitlabel(f); emit(temp.toString() + " = false");
      emitlabel(a);
      return temp;
   }
   /**
    * @return 逻辑运算式式子，形如“x1 op x2”.
    */
   public String toString() {
      return expr1.toString()+" "+op.toString()+" "+expr2.toString();
   }
}
