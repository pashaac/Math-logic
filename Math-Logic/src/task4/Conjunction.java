package task4;
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

    @Override
    public Expression doChange(Expression instead, Expression newExpr) {
        if (this.equals(instead))
            return newExpr;
        return new Conjunction(left.doChange(instead, newExpr), right.doChange(instead, newExpr));
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Conjunction)
            return (this.getLeft().equals(((Conjunction) object).getLeft()) && this.getRight().equals(((Conjunction) object).getRight()));
        else
            return false;
    }
}

