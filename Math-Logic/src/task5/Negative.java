package task5;
import java.util.ArrayList;

/**
 * Created by ПАВЕЛ on 15.02.14.
 */
public class Negative implements Expression {
    private Expression expr;

    public Negative(Expression expr) {
        this.expr = expr;
    }

    public Expression getExpression() {
        return expr;
    }

    public boolean evaluate(ArrayList<String> trueVariables) {
        return !expr.evaluate(trueVariables);
    }

    public String toString() {
        if (expr instanceof Variable || expr instanceof Existential || expr instanceof  Universal || expr instanceof Predicate)
            return "!" + expr.toString();
        else
            return "!(" + expr.toString() + ")";
    }

    @Override //
    public ArrayList<Variable> freeVariables(ArrayList<Variable> busyVariables) {
        ArrayList<Variable> temporaryArrayList = new ArrayList<Variable>();
        temporaryArrayList.addAll(expr.freeVariables(busyVariables));
        return temporaryArrayList;
    }

    @Override
    public ArrayList<Variable> busyVariables() {
        ArrayList<Variable> temporaryArrayList = new ArrayList<Variable>();
        temporaryArrayList.addAll(expr.busyVariables());
        return temporaryArrayList;
    }

    @Override
    public ArrayList<Variable> allVariables() {
        ArrayList<Variable> temporaryArrayList = new ArrayList<Variable>();
        temporaryArrayList.addAll(expr.allVariables());
        return temporaryArrayList;
    }

    @Override
    public Pair<Expression,Expression> differenceExpression(Expression expression) {
        if (expression instanceof Negative) {
            return this.getExpression().differenceExpression(((Negative) expression).getExpression());
        }
        return new Pair(this, expression);
    }

    @Override
    public Expression doChange(Expression instead, Expression newExpr) {
        if (this.equals(instead))
            return newExpr;
        return new Negative(expr.doChange(instead, newExpr));
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Negative)
            return this.getExpression().equals(((Negative) object).getExpression());
        else
            return false;
    }
}
