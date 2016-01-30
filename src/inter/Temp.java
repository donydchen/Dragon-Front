package inter;
import lexer.*; import symbols.*;
/**
 * 临时变量
 *
 */
public class Temp extends Expr {
   /**
    * 已创建的临时变量的总数
    */
   static int count = 0;
   /**
    * 临时变量的id
    */
   int number = 0;
   /**
    * 初始化临时变量。
    * 通过count的自增来设定当前临时变量的ID，即number
    * @param p 类型
    */
   public Temp(Type p) { super(Word.temp, p); number = ++count; }
   /**
    * @return 临时变量名称，形如“tn”
    */
   public String toString() { return "t" + number; }
}
