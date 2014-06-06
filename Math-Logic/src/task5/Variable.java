package task5;
import java.util.ArrayList;

/**
 * Created by ПАВЕЛ on 15.02.14.
 */
public class Variable implements Expression {
    private String variableName;

    public Variable(String variableName) {
        this.variableName = variableName;
    }

    public boolean evaluate(ArrayList<String> trueVariables) {
        return trueVariables.contains(variableName);
    }

    public String toString() {
        return variableName;
    }

    @Override
    public ArrayList<Variable> freeVariables(ArrayList<Variable> busyVariables) {
        ArrayList<Variable> temporaryArrayList = new ArrayList<Variable>();
        if (!busyVariables.contains(this)) {
            temporaryArrayList.add(this);
        }
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
        temporaryArrayList.add(this);
        return temporaryArrayList;
    }

    @Override
    public Pair<Expression, Expression> differenceExpression(Expression expression) {
        if (expression.equals(this))
            return null;
        return new Pair(this, expression);
    }

    @Override
    public Expression doChange(Expression instead, Expression newExpr) {
        if (this.equals(instead))
            return newExpr;
        else
            return this;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Variable) {
            return this.toString().equals(object.toString());
        }
        return false;
    }
}
