/**
 * Created by ПАВЕЛ on 15.02.14.
 */
public class Disjunction extends BinaryOp {

    public Disjunction(Expression left, Expression right) {
        super(left, right);
    }

    protected boolean calc(boolean left, boolean right) {
        return left | right;
    }

    public String toString() {
        String lefStr = left.toString();
        String rightStr = right.toString();

        if (left instanceof Implication || left instanceof Disjunction)
            lefStr = "(" + lefStr + ")";
        if (right instanceof Implication || right instanceof Disjunction)
            rightStr = "(" + rightStr + ")";

        return lefStr + "|" + rightStr;
    }

    public boolean equal(Expression expression) {
        if (expression instanceof Disjunction)
            return (this.getLeft().equal(((Disjunction) expression).getLeft()) && this.getRight().equal(((Disjunction) expression).getRight()));
        else
            return false;
    }
}
