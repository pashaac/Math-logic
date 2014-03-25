/**
 * Created by ПАВЕЛ on 16.02.14.
 */
public class Axioms {
    static int isAxiom(Expression expression) {
        if (axiom1(expression)) return 1;
        if (axiom2(expression)) return 2;
        if (axiom3(expression)) return 3;
        if (axiom4(expression)) return 4;
        if (axiom5(expression)) return 5;
        if (axiom6(expression)) return 6;
        if (axiom7(expression)) return 7;
        if (axiom8(expression)) return 8;
        if (axiom9(expression)) return 9;
        if (axiom10(expression)) return 10;
        return -1;
    }

    private static boolean axiom1(Expression expression) {
        if (expression instanceof Implication)
            if (((Implication) expression).getRight() instanceof Implication)
                if (((Implication) expression).getLeft().equal(((Implication) ((Implication) expression).getRight()).getRight()))
                    return true;
        return false;
    }

    private static boolean axiom2(Expression expression) {
        if (expression instanceof Implication) {
            Expression leftExpression = ((Implication) expression).getLeft();
            Expression rightExpression = ((Implication) expression).getRight();
            if (leftExpression instanceof Implication)
                if (rightExpression instanceof Implication) {
                    Expression rightRightExpression = ((Implication) rightExpression).getRight();
                    Expression rightLeftExpression = ((Implication) rightExpression).getLeft();
                    if (rightRightExpression instanceof Implication)
                        if (rightLeftExpression instanceof Implication)
                            if (((Implication) rightLeftExpression).getRight() instanceof Implication)
                                if (((Implication) leftExpression).getLeft().equal(((Implication) rightLeftExpression).getLeft()) &&
                                        ((Implication) leftExpression).getLeft().equal(((Implication) rightRightExpression).getLeft()) &&
                                        ((Implication) leftExpression).getRight().equal(((Implication) ((Implication) rightLeftExpression).getRight()).getLeft()) &&
                                        ((Implication) ((Implication) rightLeftExpression).getRight()).getRight().equal(((Implication) rightRightExpression).getRight()))
                                    return true;
                }
        }
        return false;
    }

    private static boolean axiom3(Expression expression) {
        if (expression instanceof Implication)
            if (((Implication) expression).getRight() instanceof  Implication)
                if (((Implication) ((Implication) expression).getRight()).getRight() instanceof Conjunction)
                    if (((Implication) expression).getLeft().equal(((Conjunction) ((Implication) ((Implication) expression).getRight()).getRight()).getLeft()) && ((Implication) ((Implication) expression).getRight()).getLeft().equal(((Conjunction) ((Implication) ((Implication) expression).getRight()).getRight()).getRight()))
                        return true;
        return false;
    }

    private static boolean axiom4(Expression expression) {
        if (expression instanceof Implication)
            if (((Implication) expression).getLeft() instanceof Conjunction)
                if (((Conjunction) ((Implication) expression).getLeft()).getLeft().equal(((Implication) expression).getRight()))
                    return true;
        return false;
    }

    private static boolean axiom5(Expression expression) {
        if (expression instanceof  Implication)
            if (((Implication) expression).getLeft() instanceof Conjunction)
                if (((Conjunction) ((Implication) expression).getLeft()).getRight().equal(((Implication) expression).getRight()))
                    return true;
        return false;
    }

    private static boolean axiom6(Expression expression) {
        if (expression instanceof Implication)
            if (((Implication) expression).getRight() instanceof Disjunction)
                if (((Implication) expression).getLeft().equal(((Disjunction) ((Implication) expression).getRight()).getLeft()))
                    return true;
        return false;
    }

    private static boolean axiom7(Expression expression) {
        if (expression instanceof Implication)
            if (((Implication) expression).getRight() instanceof Disjunction)
                if (((Implication) expression).getLeft().equal(((Disjunction) ((Implication) expression).getRight()).getRight()))
                    return true;
        return false;
    }

    private static boolean axiom8(Expression expression) {
        if (expression instanceof Implication) {
            Expression leftExpression = ((Implication) expression).getLeft();
            Expression rightExpression = ((Implication) expression).getRight();
            if (leftExpression instanceof Implication)
                if (rightExpression instanceof Implication)
                    if (((Implication) rightExpression).getLeft() instanceof Implication)
                        if (((Implication) rightExpression).getRight() instanceof Implication)
                            if (((Implication) ((Implication) rightExpression).getRight()).getLeft() instanceof Disjunction)
                                if (((Implication) leftExpression).getLeft().equal(((Disjunction) ((Implication) ((Implication) rightExpression).getRight()).getLeft()).getLeft()) &&
                                        ((Implication) leftExpression).getRight().equal(((Implication) ((Implication) rightExpression).getLeft()).getRight()) &&
                                        ((Implication) leftExpression).getRight().equal(((Implication) ((Implication) rightExpression).getRight()).getRight()) &&
                                        ((Implication) ((Implication) rightExpression).getLeft()).getLeft().equal(((Disjunction) ((Implication) ((Implication) rightExpression).getRight()).getLeft()).getRight()))
                                    return true;
        }
        return false;
    }

    private static boolean axiom9(Expression expression) {
        if (expression instanceof Implication) {
            Expression leftExpression = ((Implication) expression).getLeft();
            Expression rightExpression = ((Implication) expression).getRight();
            if (leftExpression instanceof Implication)
                if (rightExpression instanceof Implication)
                    if (((Implication) rightExpression).getRight() instanceof Negative)
                        if (((Implication) rightExpression).getLeft() instanceof Implication)
                            if (((Implication) ((Implication) rightExpression).getLeft()).getRight() instanceof Negative)
                                if (((Implication) leftExpression).getLeft().equal(((Implication) ((Implication) rightExpression).getLeft()).getLeft()) &&
                                        ((Implication) leftExpression).getLeft().equal(((Negative) ((Implication) rightExpression).getRight()).getExpression()) &&
                                        ((Implication) leftExpression).getRight().equal(((Negative) ((Implication) ((Implication) rightExpression).getLeft()).getRight()).getExpression()))
                                    return true;
        }
        return false;
    }

    private static boolean axiom10(Expression expression) {
        if (expression instanceof Implication)
            if (((Implication) expression).getLeft() instanceof Negative)
                if (((Negative) ((Implication) expression).getLeft()).getExpression() instanceof Negative)
                    if (((Negative) ((Negative) ((Implication) expression).getLeft()).getExpression()).getExpression().equal(((Implication) expression).getRight()))
                        return true;
        return false;
    }
}
