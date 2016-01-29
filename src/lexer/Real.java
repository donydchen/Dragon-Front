package lexer;
public class Real extends Token {

	public final float value;
    //use the super keyword to invoke a superclass's constructor
	public Real(float v) { super(Tag.REAL); value = v; }
	public String toString() { return "" + value; } //why ""
}
