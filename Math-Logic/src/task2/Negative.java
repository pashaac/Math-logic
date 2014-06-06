package task2;

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
        if (expr instanceof Variable)
            return "!" + expr.toString();
        else
            return "!(" + expr.toString() + ")";
    }

    public boolean equal(Expression expression) {
        if (expression instanceof Negative)
            return this.getExpression().equal(((Negative) expression).getExpression()); // ????
        else
            return false;
    }
}
