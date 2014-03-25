import java.util.ArrayList;

/**
 * Created by ПАВЕЛ on 15.02.14.
 */
public interface Expression {
    public abstract boolean evaluate(ArrayList<String> trueVariables);
    public String toString();
    public boolean equal(Expression expression);
}
