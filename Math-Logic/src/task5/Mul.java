package task5;
/**
 * Created by pavel on 3/18/14.
 */
public class Mul extends BinaryOp {

    public Mul(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected boolean calc(boolean a, boolean b) {
        return true;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "*" + right.toString() + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Mul) {
           return (((Mul) object).getLeft().equals(left)) && ((Mul) object).getRight().equals(right);
        }

        return false;
    }

    @Override
    public Expression doChange(Expression instead, Expression substitution) {
        if (this.equals(instead))
            return substitution;
        return new Mul(left.doChange(instead, substitution), right.doChange(instead, substitution));
    }
}
