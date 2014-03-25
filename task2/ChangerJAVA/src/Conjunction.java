/**
 * Created by ПАВЕЛ on 15.02.14.
 */
public class Conjunction extends BinaryOp {

    public Conjunction(Expression left, Expression right) {
        super(left, right);
    }

    protected boolean calc(boolean left, boolean right) {
        return left & right;
    }

    public String toString() {
        String lefStr = left.toString();
        String rightStr = right.toString();

        if (left instanceof Implication || left instanceof Disjunction || left instanceof Conjunction)
            lefStr = "(" + lefStr + ")";
        if (right instanceof Implication || right instanceof Disjunction || right instanceof Conjunction)
            rightStr = "(" + rightStr + ")";

        return lefStr + "&" + rightStr;
    }

    public boolean equal(Expression expression) {
        if (expression instanceof Conjunction)
            return (this.getLeft().equal(((Conjunction) expression).getLeft()) && this.getRight().equal(((Conjunction) expression).getRight()));
        else
            return false;
    }
}

