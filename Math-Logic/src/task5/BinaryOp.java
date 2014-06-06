package task5;
import java.util.ArrayList;

/**
 * Created by ПАВЕЛ on 15.02.14.
 */
public abstract class BinaryOp implements Expression {
    protected Expression left;
    protected Expression right;

    protected BinaryOp(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    protected Expression getLeft() {
        return left;
    }

    protected Expression getRight() {
        return right;
    }

    @Override
    public ArrayList<Variable> freeVariables(ArrayList<Variable> busyVariables) {
        ArrayList<Variable> temporaryArrayList = new ArrayList<Variable>();
        temporaryArrayList.addAll(left.freeVariables(busyVariables));
        temporaryArrayList.addAll(right.freeVariables(busyVariables));
        return temporaryArrayList;
    }

    @Override
    public ArrayList<Variable> busyVariables() {
        ArrayList<Variable> temporaryArrayList = new ArrayList<Variable>();
        temporaryArrayList.addAll(left.busyVariables());
        temporaryArrayList.addAll(right.busyVariables());
        return temporaryArrayList;
    }

    @Override
    public ArrayList<Variable> allVariables() {
        ArrayList<Variable> temporaryArrayList = new ArrayList<Variable>();
        temporaryArrayList.addAll(left.allVariables());
        temporaryArrayList.addAll(right.allVariables());
        return temporaryArrayList;
    }

    @Override
    public Pair<Expression, Expression> differenceExpression(Expression expression) {
        if (expression instanceof BinaryOp) {
            Pair<Expression, Expression> difference = this.getLeft().differenceExpression(((BinaryOp) expression).getLeft());
            if (difference != null)
                return difference;
            return this.getRight().differenceExpression(((BinaryOp) expression).getRight());
        }
        return new Pair(this, expression);
    }

    @Override
    public boolean evaluate(ArrayList<String> trueVariables) {
        return calc(left.evaluate(trueVariables), right.evaluate(trueVariables));
    }

    protected abstract boolean calc(boolean left, boolean right);
}
