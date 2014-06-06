package task3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ПАВЕЛ on 16.02.14.
 */
public class Proofs {
    private static ArrayList<Expression> arrayList;
    public static ArrayList<Expression> doingProof(Expression expression, ArrayList<String> trueVariables) {
        arrayList = new ArrayList<Expression>();
        recursionStart(expression, trueVariables);
        return arrayList;
    }

    private static boolean recursionStart(Expression expression, ArrayList<String> trueVariables) {
        if (expression instanceof Implication) {
            boolean flagLeft = recursionStart(((Implication) expression).getLeft(), trueVariables);
            boolean flagRight = recursionStart(((Implication) expression).getRight(), trueVariables);
            if (flagLeft == true && flagRight == true) {
                add(expression5(((Implication) expression).getLeft(), ((Implication) expression).getRight()));
                return true;
            } else
            if (flagLeft == true && flagRight == false) {
                add(expression6(((Implication) expression).getLeft(), new Negative(((Implication) expression).getRight())));
                return false;
            } else
            if (flagLeft == false && flagRight == true) {
                add(expression7(new Negative(((Implication) expression).getLeft()), ((Implication) expression).getRight()));
                return true;
            } else {
                add(expression8(new Negative(((Implication) expression).getLeft()), new Negative(((Implication) expression).getRight())));
                return true;
            }
        }
        if (expression instanceof Disjunction) {
            boolean flagLeft = recursionStart(((Disjunction) expression).getLeft(), trueVariables);
            boolean flagRight = recursionStart(((Disjunction) expression).getRight(), trueVariables);
            if (flagLeft == true && flagRight == true) {
                add(expression9(((Disjunction) expression).getLeft(),((Disjunction) expression).getRight()));
                return true;
            } else
            if (flagLeft == true && flagRight == false) {
                add(expression10(((Disjunction) expression).getLeft(), new Negative(((Disjunction) expression).getRight())));
                return true;
            } else
            if (flagLeft == false && flagRight == true) {
                add(expression11(new Negative(((Disjunction) expression).getLeft()), ((Disjunction) expression).getRight()));
                return true;
            } else {
                add(expression12(new Negative(((Disjunction) expression).getLeft()), new Negative(((Disjunction) expression).getRight())));
                return false;
            }
        }
        if (expression instanceof Conjunction) {
            boolean flagLeft = recursionStart(((Conjunction) expression).getLeft(), trueVariables);
            boolean flagRight = recursionStart(((Conjunction) expression).getRight(), trueVariables);
            if (flagLeft == true && flagRight == true) {
                add(expression1(((Conjunction) expression).getLeft(), ((Conjunction) expression).getRight()));
                return true;
            } else
            if (flagLeft == true && flagRight == false) {
                add(expression2(((Conjunction) expression).getLeft(), new Negative(((Conjunction) expression).getRight())));
                return false;
            } else
            if (flagLeft == false && flagRight == true) {
                add(expression3(new Negative(((Conjunction) expression).getLeft()), ((Conjunction) expression).getRight()));
                return false;
            }  else {
                add(expression4(new Negative(((Conjunction) expression).getLeft()), new Negative(((Conjunction) expression).getRight())));
                return false;
            }
        }

        if (expression instanceof Negative) {
            boolean flag = recursionStart(((Negative) expression).getExpression(), trueVariables);
            if (flag == true) {
                add(expression13(((Negative) expression).getExpression()));
                //add(expression14(((Negative) expression)));

                return false;
            } else {
                //add(expression13((expression)));
                add(expression14(new Negative(((Negative) expression).getExpression())));

                if (((Negative) expression).getExpression() instanceof Negative) {
                    add(expression15(((Negative) expression)));
                }
                return true;
            }
        }

        if (expression instanceof Variable) {
            if (trueVariables.contains(expression.toString())) {
                add(expression13(expression));
                return true;
            }
            else
                return false;
        }

        return true;
    }

    private static void add(ArrayList<Expression> tmp) {
        for (int i = 0; i < tmp.size(); ++i)
            arrayList.add(tmp.get(i));
       // arrayList.add(new Variable("xxxxxxxxxxxxxxxxxxxxx"));
    }

    // P, Q |- (P&Q)
    static ArrayList<Expression> expression1(Expression exprP, Expression exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression1.txt"));
            try {
                input = new BufferedReader(new FileReader("expression1.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll("@", "(" + exprP.toString() + ")");
                    s = s.replaceAll("#", "(" + exprQ.toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // P, !Q |- !(P&Q)
    static ArrayList<Expression> expression2(Expression exprP, Negative exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression2.txt"));
            try {
                input = new BufferedReader(new FileReader("expression2.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll("@", "(" + exprP.toString() + ")");
                    s = s.replaceAll("#", "(" + exprQ.getExpression().toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // !P, Q |- !(P&Q)
    static ArrayList<Expression> expression3(Negative exprP, Expression exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression3.txt"));
            try {
                input = new BufferedReader(new FileReader("expression3.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll("@", "(" + exprP.getExpression().toString() + ")");
                    s = s.replaceAll("#", "(" + exprQ.toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // !P, !Q |- !(P&Q)
    static ArrayList<Expression> expression4(Negative exprP, Negative exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression4.txt"));
            try {
                input = new BufferedReader(new FileReader("expression4.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll("@", "(" + exprP.getExpression().toString() + ")");
                    s = s.replaceAll("#", "(" + exprQ.getExpression().toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // P, Q |- (P->Q)
    static ArrayList<Expression> expression5(Expression exprP, Expression exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression5.txt"));
            try {
                input = new BufferedReader(new FileReader("expression5.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll("@", "(" + exprP.toString() + ")");
                    s = s.replaceAll("#", "(" + exprQ.toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // P, !Q |- !(P->Q)
    static ArrayList<Expression> expression6(Expression exprP, Negative exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression6.txt"));
            try {
                input = new BufferedReader(new FileReader("expression6.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll("@", "(" + exprP.toString() + ")");
                    s = s.replaceAll("#", "(" + exprQ.getExpression().toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // !P, Q |- (P->Q) ???
    static ArrayList<Expression> expression7(Negative exprP, Expression exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression7.txt"));
            try {
                input = new BufferedReader(new FileReader("expression7.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll("@", "(" + exprP.getExpression().toString() + ")");
                    s = s.replaceAll("#", "(" + exprQ.toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // !P, !Q |- (P->Q)
    static ArrayList<Expression> expression8(Negative exprP, Negative exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression8.txt"));
            try {
                input = new BufferedReader(new FileReader("expression8.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll("@", "(" + exprP.getExpression().toString() + ")");
                    s = s.replaceAll("#", "(" + exprQ.getExpression().toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // P, Q |- (P|Q)
    static ArrayList<Expression> expression9(Expression exprP, Expression exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression9.txt"));
            try {
                input = new BufferedReader(new FileReader("expression9.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll("@", "(" + exprP.toString() + ")");
                    s = s.replaceAll("#", "(" + exprQ.toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // P, !Q |- (P|Q)
    static ArrayList<Expression> expression10(Expression exprP, Negative exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression10.txt"));
            try {
                input = new BufferedReader(new FileReader("expression10.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll("@", "(" + exprP.toString() + ")");
                    s = s.replaceAll("#", "(" + exprQ.getExpression().toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // !P, Q |- (P|Q)
    static ArrayList<Expression> expression11(Negative exprP, Expression exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression11.txt"));
            try {
                input = new BufferedReader(new FileReader("expression11.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll("@", "(" + exprP.getExpression().toString() + ")");
                    s = s.replaceAll("#", "(" + exprQ.toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // !P, !Q |- !(P|Q)
    static ArrayList<Expression> expression12(Negative exprP, Negative exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression12.txt"));
            try {
                input = new BufferedReader(new FileReader("expression12.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll("@", "(" + exprP.getExpression().toString() + ")");
                    s = s.replaceAll("#", "(" + exprQ.getExpression().toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // P |- !(!P)
    static ArrayList<Expression> expression13(Expression exprP) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression13.txt"));
            try {
                input = new BufferedReader(new FileReader("expression13.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll("@", "(" + exprP.toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // !P |- !P
    static ArrayList<Expression> expression14(Negative exprP) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression14.txt"));
            try {
                input = new BufferedReader(new FileReader("expression14.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll("@", "(" + exprP.getExpression().toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // !!P |- P
    static ArrayList<Expression> expression15(Negative exprP) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression15.txt"));
            try {
                input = new BufferedReader(new FileReader("expression15.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    if (exprP.getExpression() instanceof Negative) {
                        s = s.replaceAll("@", "(" + ((Negative) exprP.getExpression()).getExpression().toString() + ")");
                        Parser parser = new Parser(s);
                        result.add(parser.parse());
                    }
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    public static ArrayList<Expression> expression16(Expression exprP) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression16.txt"));
            try {
                input = new BufferedReader(new FileReader("expression16.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll("@", "(" + exprP.toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }
}








