/**
 * Created by ПАВЕЛ on 15.02.14.
 */
public class Implication extends BinaryOp {

    public Implication(Expression left, Expression right) {
        super(left, right);
    }

    protected boolean calc(boolean left, boolean right) {
        return !left | right;
    }

    public String toString() {
        String leftStr = left.toString();
        String rightStr = right.toString();

        if (left instanceof Implication)
            leftStr = "(" + leftStr + ")";
        if (right instanceof Implication)
            rightStr = "(" + rightStr + ")";
        return leftStr + "->" + rightStr;
    }

    public boolean equal(Expression expression) {
        if (expression instanceof Implication)
            return (this.getLeft().equal(((Implication) expression).getLeft()) && this.getRight().equal(((Implication) expression).getRight()));
        else
            return false;
    }
}
