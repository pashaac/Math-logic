import java.util.ArrayList;

/**
 * Created by pavel on 3/16/14.
 */
public class Existential extends Quantifier {

    public Existential(Variable variable, Expression expression) {
        super(variable, expression);
    }

    @Override
    public String toString() {
        return "?" + super.toString();
    }

    public boolean evaluate(ArrayList<String> trueExpressions) {
        return trueExpressions.contains(this.toString());
    }

    @Override
    public Expression doChange(Expression instead, Expression newExpr) {
        if (this.equals(instead))
            return newExpr;
        return new Existential((Variable) variable.doChange(instead, newExpr), expression.doChange(instead, newExpr));
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof Existential) && super.equals(object);
    }
}
