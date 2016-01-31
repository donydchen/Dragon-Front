package inter;
/**
 * Break语句
 */
public class Break extends Stmt {

   Stmt stmt;
   /**
    * 初始化Break语句。
    * 判断是否为嵌套语句，若是，设置其跳出语句。
    */
   public Break() {
      if( Stmt.Enclosing == Stmt.Null ) error("unenclosed break");
      stmt = Stmt.Enclosing;
   }
   /**
    * 生成Break语句三地址码并打印。形如：<br>
    * goto Lafter
    */
   public void gen(int b, int a) {
      emit( "goto L" + stmt.after);
   }
}
