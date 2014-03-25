import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

        if (a1(expression)) return 101;
        if (a2(expression)) return 102;
        if (a3(expression)) return 103;
        if (a4(expression)) return 104;
        if (a5(expression)) return 105;
        if (a6(expression)) return 106;
        if (a7(expression)) return 107;
        if (a8(expression)) return 108;
        if (a9(expression)) return 109;
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
                    Expression ololo = ((Universal) implication.getLeft()).getExpression();
                    Expression afterSub = ((Universal) implication.getLeft()).getExpression().doChange(difference.getFirst(), difference.getSecond());
                    return afterSub.equals(implication.getRight());
                }
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
            }
        }
        return false;
    }

    private static boolean a1(Expression expression) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("A1.txt"));
            try {
                String s = input.readLine();
                if (expression instanceof Implication) {
                    Implication implication = (Implication) expression;
                    if (implication.getLeft() instanceof Equal) {
                        s = s.replaceAll("#", ((Equal) implication.getLeft()).getLeft().toString());
                        s = s.replaceAll("%", ((Equal) implication.getLeft()).getRight().toString());
                        return new Parser(s).parse().equals(expression);
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

    private static boolean a2(Expression expression) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("A2.txt"));
            try {
                String s = input.readLine();
                if (expression instanceof Implication) {
                    Implication implication = (Implication) expression;
                    if (implication.getRight() instanceof Implication) {
                        Implication implicationRight = (Implication) implication.getRight();
                        if (implicationRight.getLeft() instanceof Equal && implicationRight.getRight() instanceof Equal) {
                            s = s.replaceAll("#", ((Equal) implicationRight.getLeft()).getLeft().toString());
                            s = s.replaceAll("%", ((Equal) implicationRight.getRight()).getLeft().toString());
                            s = s.replaceAll("/", ((Equal) implicationRight.getLeft()).getRight().toString());
                            return new Parser(s).parse().equals(expression);
                        }
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

    private static boolean a3(Expression expression) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("A3.txt"));
            try {
                String s = input.readLine();
                if (expression instanceof Implication) {
                    Implication implication = (Implication) expression;
                    if (implication.getRight() instanceof Equal) {
                        s = s.replaceAll("#", ((Equal) implication.getRight()).getLeft().toString());
                        s = s.replaceAll("%", ((Equal) implication.getRight()).getRight().toString());
                        return new Parser(s).parse().equals(expression);
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

    private static boolean a4(Expression expression) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("A4.txt"));
            try {
                String s = input.readLine();
                if (expression instanceof Equal) {
                    Expression expressionLeft = (Expression) ((Equal) expression).getLeft();
                    if (expressionLeft instanceof Apostrophe) {
                        if (((Apostrophe) expressionLeft).getExpression() instanceof Negative) {
                            s = s.replaceAll("#", ((Negative) ((Apostrophe) expressionLeft).getExpression()).getExpression().toString());
                            return new Parser(s).parse().equals(expression);
                        }
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

    private static boolean a5(Expression expression) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("A5.txt"));
            try {
                String s = input.readLine();
                if (expression instanceof Equal) {
                    Expression expressionLeft = ((Equal) expression).getLeft();
                    if (expressionLeft instanceof Sum) {
                        if (((Sum) expressionLeft).getRight() instanceof Apostrophe) {
                            s = s.replaceAll("#", ((Sum) expressionLeft).getLeft().toString());
                            s = s.replaceAll("%", ((Apostrophe) ((Sum) expressionLeft).getRight()).getExpression().toString());
                            return new Parser(s).parse().equals(expression);
                        }
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

    private static boolean a6(Expression expression) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("A6.txt"));
            try {
                String s = input.readLine();
                if (expression instanceof Equal) {
                    s = s.replaceAll("#", ((Equal) expression).getRight().toString());
                    return new Parser(s).parse().equals(expression);
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean a7(Expression expression) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("A7.txt"));
            try {
                String s = input.readLine();
                if (expression instanceof Equal) {
                    if (((Equal) expression).getLeft() instanceof Mul) {
                        s = s.replaceAll("#", ((Mul) ((Equal) expression).getLeft()).getLeft().toString());
                        return new Parser(s).parse().equals(expression);
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

    private static boolean a8(Expression expression) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("A8.txt"));
            try {
                String s = input.readLine();
                if (expression instanceof Equal) {
                    if (((Equal) expression).getLeft() instanceof Mul) {
                        if (((Mul) ((Equal) expression).getLeft()).getRight() instanceof Apostrophe) {
                            s = s.replaceAll("#", ((Mul) ((Equal) expression).getLeft()).getLeft().toString());
                            s = s.replaceAll("%", ((Apostrophe) ((Mul) ((Equal) expression).getLeft()).getRight()).getExpression().toString());
                            return new Parser(s).parse().equals(expression);
                        }
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

    private static boolean a9(Expression expression) {
        if (expression instanceof Implication) {
            Implication implication = (Implication) expression;
            if (implication.getLeft() instanceof Conjunction) {
                Conjunction conjunction = (Conjunction) implication.getLeft();
                if (conjunction.getRight() instanceof Universal) {
                    Universal universal = (Universal) conjunction.getRight();
                    Variable x = universal.getVariable();
                    if (universal.getExpression() instanceof Implication) {
                        Implication universalImplication = (Implication) universal.getExpression();
                        //Expression expressionSubst = conjunction.getLeft().doChange(new Zero(), x);
                        boolean flag1 = conjunction.getLeft().equals(implication.getRight().doChange(x, new Zero()));
                        boolean flag2 = implication.getRight().equals(universalImplication.getLeft());
                        boolean flag3 = universalImplication.getRight().equals(implication.getRight().doChange(x, new Apostrophe(x)));
                        return flag1 && flag2 && flag3;
                    }
                }
            }
        }
        return false;
    }
}