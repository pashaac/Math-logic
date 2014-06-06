package task5;
/**
 * Created by pavel on 3/18/14.
 */
public class Equal extends BinaryOp {

    public Equal(Expression left, Expression right) {
        super(left, right);
    }

    public boolean calc(boolean a, boolean b) {
        return a == b;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "=" + right.toString() + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Equal) {
            return (((Equal) object).getLeft().equals(left)) && (((Equal) object).getRight().equals(right));
        }
        return false;
    }

    @Override
    public Expression doChange(Expression instead, Expression substitution) {
        if (this.equals(instead))
            return substitution;
        return new Equal(left.doChange(instead,substitution), right.doChange(instead,substitution));
    }
}
