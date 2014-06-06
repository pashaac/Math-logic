package task5;

import java.util.ArrayList;

/**
 * Created by pavel on 3/18/14.
 */
public class Apostrophe implements Expression {
    private Expression expression;

    public Apostrophe(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public boolean evaluate(ArrayList<String> trueVariables) {
        return trueVariables.contains(expression);
    }

    @Override
    public ArrayList<Variable> allVariables() {
        return this.getExpression().allVariables(); // ???
    }

    @Override
    public ArrayList<Variable> freeVariables(ArrayList<Variable> busyVariables) {
        ArrayList<Variable> temporaryVariables = new ArrayList<Variable>();
        temporaryVariables.addAll(this.getExpression().freeVariables(busyVariables));
        return temporaryVariables;
    }

    @Override
    public ArrayList<Variable> busyVariables() {
        return this.getExpression().busyVariables();
    }

    @Override
    public Pair<Expression,Expression> differenceExpression(Expression expression) {
        if (expression instanceof Apostrophe)
            return this.getExpression().differenceExpression(((Apostrophe) expression).getExpression());
        return new Pair(this, expression);
    }

    @Override
    public String toString() {
        return expression.toString() + "'";
    }

    @Override
    public  boolean equals(Object object) {
        if (object instanceof Apostrophe) {
            return ((Apostrophe) object).getExpression().equals(expression);
        }
        return false;
    }
    @Override
    public Expression doChange(Expression instead, Expression substitution) {
        if (this.equals(instead))
            return substitution;
        return new Apostrophe(expression.doChange(instead,substitution));
    }
}
