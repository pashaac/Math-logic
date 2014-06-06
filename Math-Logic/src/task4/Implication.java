package task4;
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

    @Override
    public Expression doChange(Expression instead, Expression newExpr) {
        if (this.equals(instead))
            return newExpr;
        return new Implication(left.doChange(instead, newExpr), right.doChange(instead, newExpr));
    }


    @Override
    public boolean equals(Object object) {
        if (object instanceof Implication)
            return (this.getLeft().equals(((Implication) object).getLeft()) && this.getRight().equals(((Implication) object).getRight()));
        else
            return false;
    }
}
