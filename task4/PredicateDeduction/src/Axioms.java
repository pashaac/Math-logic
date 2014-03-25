import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
        if (axiom11(expression)) return 11;
        if (axiom12(expression)) return 12;
        return -1;
    }

    private static boolean axiom1(Expression expression) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom1.txt"));
            try {
                String s = input.readLine();
                if (expression instanceof Implication && ((Implication) expression).getRight() instanceof Implication) {
                    s = s.replaceAll("#", "(" + ((Implication) expression).getLeft().toString() + ")");
                    s = s.replaceAll("/", "(" + ((Implication) ((Implication) expression).getRight()).getLeft().toString() + ")");
                    return expression.equals(new Parser(s).parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean axiom2(Expression expression) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom2.txt"));
            try {
                String s = input.readLine();
                if (expression instanceof Implication && ((Implication) expression).getRight() instanceof Implication) {
                    Expression expressionLeft = ((Implication) expression).getLeft();
                    Expression expressionMiddle = ((Implication) ((Implication) expression).getRight()).getLeft();
                    Expression expressionRight = ((Implication) ((Implication) expression).getRight()).getRight();
                    if (expressionLeft instanceof Implication && expressionRight instanceof Implication) {
                        s = s.replaceAll("#", "(" + ((Implication) expressionLeft).getLeft().toString() + ")");
                        s = s.replaceAll("/", "(" + ((Implication) expressionLeft).getRight().toString() + ")");
                        s = s.replaceAll("%", "(" + ((Implication) expressionRight).getRight().toString() + ")");
                        return expression.equals(new Parser(s).parse());
                    }
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean axiom3(Expression expression) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom3.txt"));
            try {
                String s = input.readLine();
                if (expression instanceof Implication && ((Implication) expression).getRight() instanceof Implication) {
                    s = s.replaceAll("#", "(" + ((Implication) expression).getLeft().toString() + ")");
                    s = s.replaceAll("/", "(" + ((Implication) ((Implication) expression).getRight()).getLeft().toString() + ")");
                    return expression.equals(new Parser(s).parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean axiom4(Expression expression) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom4.txt"));
            try {
                String s = input.readLine();
                if (expression instanceof Implication && ((Implication) expression).getLeft() instanceof Conjunction) {
                    s = s.replaceAll("#", "(" + ((Implication) expression).getRight().toString() + ")");
                    s = s.replaceAll("/", "(" + ((Conjunction) ((Implication) expression).getLeft()).getRight().toString() + ")");
                    return expression.equals(new Parser(s).parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean axiom5(Expression expression) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom5.txt"));
            try {
                String s = input.readLine();
                if (expression instanceof Implication && ((Implication) expression).getLeft() instanceof Conjunction) {
                    s = s.replaceAll("#", "(" + ((Conjunction) ((Implication) expression).getLeft()).getLeft().toString() + ")");
                    s = s.replaceAll("/", "(" + ((Implication) expression).getRight().toString() + ")");
                    return expression.equals(new Parser(s).parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean axiom6(Expression expression) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom6.txt"));
            try {
                String s = input.readLine();
                if (expression instanceof Implication && ((Implication) expression).getRight() instanceof Disjunction) {
                    s = s.replaceAll("#", "(" + ((Implication) expression).getLeft().toString() + ")");
                    s = s.replaceAll("/", "(" + ((Disjunction) ((Implication) expression).getRight()).getRight().toString() + ")");
                    return expression.equals(new Parser(s).parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean axiom7(Expression expression) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom7.txt"));
            try {
                String s = input.readLine();
                if (expression instanceof Implication && ((Implication) expression).getRight() instanceof Disjunction) {
                    s = s.replaceAll("#", "(" + ((Disjunction) ((Implication) expression).getRight()).getLeft().toString() + ")");
                    s = s.replaceAll("/", "(" + ((Implication) expression).getLeft().toString() + ")");
                    return expression.equals(new Parser(s).parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean axiom8(Expression expression) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom8.txt"));
            try {
                String s = input.readLine();
                if (expression instanceof Implication && ((Implication) expression).getRight() instanceof Implication) {
                    Expression expressionLeft = ((Implication) expression).getLeft();
                    Expression expressionMiddle = ((Implication) ((Implication) expression).getRight()).getLeft();
                    Expression expressionRight = ((Implication) ((Implication) expression).getRight()).getRight();
                    if (expressionLeft instanceof Implication && expressionMiddle instanceof Implication) {
                        s = s.replaceAll("#", "(" + ((Implication) expressionLeft).getLeft().toString() + ")");
                        s = s.replaceAll("/", "(" + ((Implication) expressionMiddle).getLeft().toString() + ")");
                        s = s.replaceAll("%", "(" + ((Implication) expressionLeft).getRight().toString() + ")");
                        return expression.equals(new Parser(s).parse());
                    }
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean axiom9(Expression expression) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom9.txt"));
            try {
                String s = input.readLine();
                if (expression instanceof Implication && ((Implication) expression).getLeft() instanceof Implication) {
                    s = s.replaceAll("#", "(" + ((Implication) ((Implication) expression).getLeft()).getLeft().toString() + ")");
                    s = s.replaceAll("/", "(" + ((Implication) ((Implication) expression).getLeft()).getRight().toString() + ")");
                    return expression.equals(new Parser(s).parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean axiom10(Expression expression) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom10.txt"));
            try {
                String s = input.readLine();
                if (expression instanceof Implication) {
                    s = s.replaceAll("#", "(" + ((Implication) expression).getRight().toString() + ")");
                    return expression.equals(new Parser(s).parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean axiom11(Expression expression) {
        if (expression instanceof Implication) {
            Implication implication = (Implication) expression;
            if (implication.getLeft() instanceof Universal) {
                Pair<Expression, Expression> difference = ((Universal) implication.getLeft()).getExpression().differenceExpression(implication.getRight());
                if (difference == null)
                    return true;
                if (difference.getFirst().equals(((Universal) implication.getLeft()).getVariable())) {
                    Expression afterSub = ((Universal) implication.getLeft()).getExpression().doChange(difference.getFirst(), difference.getSecond());
                    return afterSub.equals(implication.getRight());
                }
                //variableRight = null;
                //variableLeft = ((Universal) implication.getLeft()).getVariable();
                //return isAlmostIdentical(((Universal) implication.getLeft()).getExpression(), implication.getRight());
            }
        }
        return false;
    }

    private static boolean axiom12(Expression expression) {
        if (expression instanceof Implication) {
            Implication implication = (Implication) expression;
            if (implication.getRight() instanceof Existential) {
                Pair<Expression, Expression> difference = ((Existential) implication.getRight()).getExpression().differenceExpression(implication.getLeft());
                if (difference == null)
                    return true;
                if (difference.getFirst().equals(((Existential) implication.getRight()).getVariable())) {
                    Expression afterSub = ((Existential) implication.getRight()).getExpression().doChange(difference.getFirst(), difference.getSecond());
                    return afterSub.equals(implication.getLeft());
                }
                //variableRight = null;
                //variableLeft = ((Existential) implication.getRight()).getVariable();
                //return isAlmostIdentical(((Existential)implication.getRight()).getExpression(), implication.getLeft());
            }
        }
        return false;
    }

    private static Variable variableLeft;
    private static Variable variableRight;
    private static boolean isAlmostIdentical(Expression left, Expression right) {
        if (left instanceof Variable && right instanceof Variable) {
            if (left.equals(right))
                return true;
            if (variableRight == null) {
                variableRight = (Variable) right;
                return left.equals(variableLeft);
            }
            return left.equals(variableLeft) && right.equals(variableRight);
        }
        if (left instanceof BinaryOp && right instanceof BinaryOp) {
            BinaryOp leftOp = (BinaryOp) left;
            BinaryOp rightOp = (BinaryOp) right;
            return leftOp.getLeft().equals(rightOp.getLeft()) && leftOp.getRight().equals(rightOp.getRight());
        }
        if (left instanceof Quantifier && right instanceof Quantifier) {
            Quantifier leftQuantifier = (Quantifier) left;
            Quantifier rightQuantifier = (Quantifier) right;
            return leftQuantifier.getVariable().equals(rightQuantifier.getVariable()) &&
                      leftQuantifier.getExpression().equals(rightQuantifier.getExpression());
        }
        if (left instanceof Predicate && right instanceof Predicate) {
            Predicate leftPredicate = (Predicate) left;
            Predicate rightPredicate = (Predicate) right;
            ArrayList<Expression> leftTerms = leftPredicate.getTerms();
            ArrayList<Expression> rightTerms = rightPredicate.getTerms();
            if (leftTerms.size() != rightTerms.size())
                return false;
            boolean result = true;
            for (int i = 0; i < leftTerms.size(); ++i) {
                result &= isAlmostIdentical(leftTerms.get(i), rightTerms.get(i));
            }
            return result;
        }
        if (left instanceof Negative && right instanceof Negative) {
            Negative leftNegative = (Negative) left;
            Negative rightNegative = (Negative) right;
            return isAlmostIdentical(leftNegative.getExpression(), rightNegative.getExpression());
        }
        return false;
    }
}