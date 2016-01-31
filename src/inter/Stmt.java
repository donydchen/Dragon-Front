package inter;
/**
 * 语句构造的父类
 *
 */
public class Stmt extends Node {

   public Stmt() { }
   /**
    * 空语句
    */
   public static Stmt Null = new Stmt();
   /**
    * 生成语句的三地址码
    * @param b 开始语句的label
    * @param a 结束语句的label
    */
   public void gen(int b, int a) {} // called with labels begin and after
   /**
    * 循环语句后跳出来的执行语句label。
    */
   int after = 0;                   // saves label after
   /**
    * 记录语句块的外部语句，用于break语句的判断以及跳出后的执行位置。
    */
   public static Stmt Enclosing = Stmt.Null;  // used for break stmts
}
