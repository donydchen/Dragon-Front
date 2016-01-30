package inter;
import lexer.*; import symbols.*;
/**
 * 表达式构造类
 *
 */
public class Expr extends Node {
   /**
    * 表达式节点上的运算符。
    */
   public Token op;
   /**
    * 表达式节点的类型。
    */
   public Type type;
   /**
    * 初始化表达式节点
    * @param tok 表达式的运算符单元
    * @param p 表达式的类型
    */
   Expr(Token tok, Type p) { op = tok; type = p; }
   /**
    * 生成表达式。
    * @return 三地址码的右部
    */
   public Expr gen() { return this; }
   /**
    * 将表达式规约为一个单一的地址。
    * @return 表达式对应的单一的值的地址。
    */
   public Expr reduce() { return this; }
   /**
    * 调用emitjumps()，为布尔表达式生成跳转代码并打印。
    * @param t true情况对应的label。
    * @param f false情况对应的label。
    */
   public void jumping(int t, int f) { emitjumps(toString(), t, f); }

   public void emitjumps(String test, int t, int f) {
      if( t != 0 && f != 0 ) {
         emit("if " + test + " goto L" + t);
         emit("goto L" + f);
      }
      else if( t != 0 ) emit("if " + test + " goto L" + t);
      else if( f != 0 ) emit("iffalse " + test + " goto L" + f);
      else ; // nothing since both t and f fall through
   }
   public String toString() { return op.toString(); }
}
