import java.util.ArrayList;

/**
 * Created by ПАВЕЛ on 15.02.14.
 */
public interface Expression {
    public abstract boolean evaluate(ArrayList<String> trueVariables);
    public String toString();
    public boolean equals(Object object);
    public ArrayList<Variable> freeVariables(ArrayList<Variable> busyVariables);
    public ArrayList<Variable> busyVariables();
    public ArrayList<Variable> allVariables();
    public Pair<Expression, Expression> differenceExpression(Expression expression); // return different expressions or null if this are equals
    public Expression doChange(Expression instead, Expression newExpr);
}
