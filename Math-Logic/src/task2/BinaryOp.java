package task2;

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
    public boolean evaluate(ArrayList<String> trueVariables) {
        return calc(left.evaluate(trueVariables), right.evaluate(trueVariables));
    }

    protected abstract boolean calc(boolean left, boolean right);
}
