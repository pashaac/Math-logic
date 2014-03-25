import java.util.ArrayList;

/**
 * Created by pavel on 3/18/14.
 */
public class Zero implements Expression {
    public Zero() {

    }

    public boolean evaluate(ArrayList<String> trueVariables) {
        return false;
    }

    @Override
    public ArrayList<Variable> freeVariables(ArrayList<Variable> busyVariables) {
        ArrayList<Variable> temporaryArrayList = new ArrayList<Variable>();
        return temporaryArrayList;
    }

    @Override
    public ArrayList<Variable> busyVariables() {
        ArrayList<Variable> temporaryArrayList = new ArrayList<Variable>();
        return temporaryArrayList;
    }

    @Override
    public ArrayList<Variable> allVariables() {
        ArrayList<Variable> temporaryArrayList = new ArrayList<Variable>();
        return temporaryArrayList;
    }

    @Override
    public Pair<Expression,Expression> differenceExpression(Expression expression) {
        if (expression.equals(this))
            return null;
        return new Pair(this, expression);
    }

    @Override
    public String toString() {
        return "0";
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof Zero);
    }

    @Override
    public Expression doChange(Expression instead, Expression substitution) {
        if (this.equals(instead))
            return substitution;
        return this;
    }
}
