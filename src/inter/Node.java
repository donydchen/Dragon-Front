package inter;
import lexer.*;
/**
 * 抽象语法树的节点
 *
 */
public class Node {
   /**
    * 当前节点在代码中对应的行码
    */
   int lexline = 0;
   /**
    * 初始化节点。从词法分析器中获取行码
    */
   Node() { lexline = Lexer.line; }
   /**
    * 打印错误信息。
    * @param s 待打印的错误信息。
    */
   void error(String s) { throw new Error("near line "+lexline+": "+s); }
   /**
    * 三地址码的label。
    */
   static int labels = 0;
   /**
    * 创建一个新的label。通过label自增来实现
    * @return 新的label的值。
    */
   public int newlabel() { return ++labels; }
   /**
    * 打印label。形式为“Ln:”。
    * @param i
    */
   public void emitlabel(int i) { System.out.print("L" + i + ":"); }
   /**
    * 打印字符串。形式为“\t”。
    * @param s 待打印的字符串。
    */
   public void emit(String s) { System.out.println("\t" + s); }
}
