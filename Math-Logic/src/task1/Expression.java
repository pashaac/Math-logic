package task1;

import java.util.ArrayList;

public interface Expression {
    public abstract boolean evaluate(ArrayList<String> trueVariables);
    public String toString();
    public boolean equal(Expression expression);
}
