import java.util.ArrayList;

/**
 * Created by pavel on 3/16/14.
 */
public abstract class Quantifier implements Expression {
    protected Variable variable;
    protected Expression expression;

    protected Quantifier(Variable variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return variable.toString() + "(" + expression.toString() + ")";
    }

    public Variable getVariable() { return variable; }

    public Expression getExpression() { return expression; }

    @Override
    public ArrayList<Variable> freeVariables(ArrayList<Variable> busyVariables) {
        ArrayList<Variable> temporaryArrayList = new ArrayList<Variable>();
        boolean isBusy = busyVariables.contains(variable);
        if (!isBusy)
            busyVariables.add(variable);
        temporaryArrayList.addAll(expression.freeVariables(busyVariables));
        if (!isBusy)
            busyVariables.remove(variable);
        return temporaryArrayList;
    }

    @Override
    public ArrayList<Variable> busyVariables() {
        ArrayList<Variable> temporaryArrayList = new ArrayList<Variable>();
        temporaryArrayList.add(variable);
        return temporaryArrayList;
    }

    @Override
    public ArrayList<Variable> allVariables() {
        ArrayList<Variable> temporaryArrayList = new ArrayList<Variable>();
        temporaryArrayList.add(variable);
        temporaryArrayList.addAll(expression.allVariables());
        return temporaryArrayList;
    }

    @Override
    public Pair<Expression,Expression> differenceExpression(Expression expression) {
        if (expression instanceof Quantifier) {
            Pair<Expression, Expression> difference = this.getVariable().differenceExpression(((Quantifier) expression).getVariable());
            if (difference != null)
                return difference;
            return this.getExpression().differenceExpression(((Quantifier) expression).getExpression());
        }
        return new Pair(this, expression);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Quantifier) {
            Quantifier q = (Quantifier) object;
            return expression.equals(q.getExpression()) && variable.equals(q.getVariable());
        }
        return false;
    }
}
