package task5;
/**
 * Created by pavel on 3/18/14.
 */

public class Sum extends BinaryOp {
    public Sum(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected boolean calc(boolean a, boolean b) {
        return true;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "+" + right.toString() + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Sum) {
            return (((Sum) object).getLeft().equals(left)) && ((Sum) object).getRight().equals(right);
        }
        return false;
    }

    @Override
    public Expression doChange(Expression instead, Expression substitution) {
        if (this.equals(instead))
            return substitution;
        return new Sum(left.doChange(instead, substitution), right.doChange(instead,substitution));
    }
}
