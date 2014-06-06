package task4;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ПАВЕЛ on 17.02.14.
 */

public class DeductionWork {
    private static final String OK = "OK";
    private static final String NEXT = "next";

    private String s;
    private ArrayList<Expression> G;
    private ArrayList<Expression> mainProof;
    private ArrayList<Expression> inputExpressions;
    private ArrayList<Expression> existentialInferences;
    private ArrayList<Expression> universalInferences;
    private ArrayList<ArrayList<Variable>> freeVariables;

    private Expression alpha = null;
    private Expression betta = null;
    private int inputSize;

    public DeductionWork(String s) {
        this.s = s;
        inputSize = 0;
        G = new ArrayList<Expression>();
        mainProof = new ArrayList<Expression>();
        inputExpressions = new ArrayList<Expression>();
        existentialInferences = readFromFile("MPExistential.in");
        universalInferences = readFromFile("MPUniversal.in");
        freeVariables = new ArrayList<ArrayList<Variable>>();
        DeductionExpressionParse();
    }

    public String getString() {
        return s;
    }

    public ArrayList<Expression> getMainProof() {
        return mainProof;
    }

    private ArrayList<Expression> readFromFile(final String fileName) {
        ArrayList<Expression> arrayList = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            try {
                String line;
                while ((line = input.readLine()) != null) {
                    arrayList.add(new Parser(line).parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return arrayList;
    }

    public String add(Expression expression) {
        ++inputSize;
        inputExpressions.add(expression);

        if (isAxiom(expression) != -1) {
            return axiomWork(expression);
        }

        if (isG(expression)) {
            return OK;
        }

        if (isAlpha(expression)) {
            return OK;
        }

        if (mainProofModusPonens(expression)) {
            return OK;
        }

        String universalRuleResult = isUniversalRule(expression);
        if (!universalRuleResult.equals(NEXT)) {
            return universalRuleResult;
        }

        String existentialRuleResult = isExistentialRule(expression);
        if (!existentialRuleResult.equals(NEXT)) {
            return existentialRuleResult;
        }

        return "Вывод некорректен начиная с формулы " + inputSize;
    }

    private String axiomWork(Expression expression) {
        int axiom = isAxiom(expression);
        if (axiom == 11) {
            Universal universal = (Universal) ((Implication) expression).getLeft();
            Expression term = ((Implication) expression).getRight();
            Pair<Expression, Expression> difference = universal.getExpression().differenceExpression(term);
            if (difference == null)
                term = null;
            else
                term = difference.getSecond();
            ArrayList<Variable> busyVariables = universal.getExpression().busyVariables();
            ArrayList<Variable> termVariables = (term != null) ? term.allVariables() : null;

            if (term != null && !universal.getVariable().equals(term)) { // maybe bug: several different expressions in term. We know only about one.
                for (Variable variable : termVariables) {
                    if (busyVariables.contains(variable)) {
                        return "Вывод некорректен начиная с формулы " + inputSize + ": " +
                                "терм " + term.toString() + "не свободен для подстановки в формулу " +
                                universal.getExpression().toString() + " вместо переменной " + universal.getVariable().toString();
                    }
                }
            }

            for (int i = 0; i < freeVariables.size(); ++i) {
                if (freeVariables.get(i).contains(universal.getVariable())) {
                    Expression e = alpha;
                    if (i < G.size())
                        e = G.get(i);
                    return "Вывод некорректен начиная с формулы " + inputSize + ": " +
                            "используется схема аксиом с квантором по переменной " + universal.getVariable().toString() +
                            ", входящей свободно в допущение " + e.toString();
                }
            }
        }

        if (axiom == 12) {
            Existential existential = (Existential) ((Implication) expression).getRight();
            Expression term = ((Implication) expression).getLeft();
            Pair<Expression, Expression> difference = existential.getExpression().differenceExpression(term);
            if (difference == null)
                term = null;
            else
                term = difference.getSecond();

            ArrayList<Variable> busyVariables = existential.getExpression().busyVariables();
            busyVariables.add(existential.getVariable());
            ArrayList<Variable> variables = (term != null) ? term.allVariables() : null;

            if (term != null && !existential.getVariable().equals(term)) {
                for (Variable x : variables) {
                    if (busyVariables.contains(x)) {
                        return "Вывод некорректен начиная с формулы " + inputSize + ": " +
                                "терм " + term.toString() + "не свободен для подстановки в формулу " +
                                existential.getExpression().toString() + " вместо переменной " + existential.getVariable().toString();
                    }
                }
            }

            for (int i = 0; i < freeVariables.size(); ++i) {
                if (freeVariables.get(i).contains(existential.getVariable())) {
                    Expression e = alpha;
                    if (i < G.size())
                        e = G.get(i);
                    return "Вывод некорректен начиная с формулы " + inputSize + ": " +
                            "используется схема аксиом с квантором по переменной " + existential.getVariable().toString() +
                            ", входящей свободно в допущение " + e.toString();
                }
            }

        }
        mainProof.add(expression);
        mainProof.add(new Implication(expression, new Implication(alpha, expression)));
        mainProof.add(new Implication(alpha, expression));
        return OK;
    }

    private boolean isAlpha(Expression expression) {
        if (expression.equals(alpha)) {
            Expression left = new Implication(alpha, new Implication(alpha, alpha));
            Expression middle = new Implication(alpha, new Implication(new Implication(alpha, alpha), alpha));
            Expression right = new Implication(alpha, alpha);
            mainProof.add(new Implication(alpha, new Implication(alpha, alpha)));
            mainProof.add(new Implication(left, new Implication(middle, right)));
            mainProof.add(new Implication(middle, right));
            mainProof.add(middle);
            mainProof.add(new Implication(alpha, alpha));
            return true;
        }
        return false;
    }

    private boolean isG(Expression expression) {
        for (int i = 0; i < G.size(); ++i)
            if (expression.equals(G.get(i))) {
                mainProof.add(expression);
                mainProof.add(new Implication(expression, new Implication(alpha, expression)));
                mainProof.add(new Implication(alpha, expression));
                return true;
            }
        return false;
    }

    private boolean mainProofModusPonens(Expression expression) {
        for (int i = inputExpressions.size() - 1; i >= 0; --i) {
            if (inputExpressions.get(i) instanceof Implication) {
                Implication implication = (Implication) inputExpressions.get(i);
                if (implication.getRight().equals(expression)) {
                    for (int j = inputExpressions.size() - 1; j >= 0; --j) {
                        Expression left = inputExpressions.get(j);
                        if (left.equals(implication.getLeft())) {
                            mainProof.add(new Implication(new Implication(alpha, left), new Implication(new
                                    Implication(alpha, new Implication(left, expression)), new Implication(alpha, expression))));
                            mainProof.add(new Implication(new Implication(alpha, new Implication(left, expression)),
                                    new Implication(alpha, expression)));
                            mainProof.add(new Implication(alpha, expression));
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private String isUniversalRule(Expression expression) {
        if (expression instanceof Implication) {
            Implication expressionImplication = (Implication) expression;
            if (expressionImplication.getRight() instanceof Universal) {
                Universal universal = (Universal) expressionImplication.getRight();
                for (int j = inputSize - 1; j >= 0; --j) {
                    if (inputExpressions.get(j) instanceof Implication) {
                        Implication inputImplication = (Implication) inputExpressions.get(j);
                        if (expressionImplication.getLeft().equals(inputImplication.getLeft()) &&
                                universal.getExpression().equals(inputImplication.getRight())) {
                            if (expressionImplication.getLeft().allVariables().contains(universal.getVariable())
                                    && expressionImplication.getLeft().freeVariables(new ArrayList<Variable>()).contains(universal.getVariable())) {
                                return "Вывод некорректен начиная с формулы " + inputSize + ": "
                                        + "переменная " + universal.getVariable().toString() + " входит свободно в формулу " +
                                        expressionImplication.getLeft().toString();
                            }
                            for (int i = 0; i < freeVariables.size(); ++i) {
                                if (freeVariables.get(i).contains(universal.getVariable())) {
                                    Expression e = alpha;
                                    if (i < G.size())
                                        e = G.get(i);
                                    return "Вывод некорректен начиная с формулы " + inputSize + ": " +
                                            "используется правило с квантором по переменной " + universal.getVariable().toString() +
                                            ", входящей свободно в допущение " + e.toString();
                                }
                            }
                            for (Expression expressionFromUniversal : universalInferences) {
                                mainProof.add(predicatesPack(expressionFromUniversal, alpha, universal.getExpression(), expressionImplication.getLeft(), universal.getVariable()));
                            }
                            return OK;
                        }
                    }
                }
            }
        }
        return NEXT;
    }

    private String isExistentialRule(Expression expression) {
        if (expression instanceof Implication) {
            Implication expressionImplication = (Implication) expression;
            if (expressionImplication.getLeft() instanceof Existential) {
                Existential exists = (Existential) expressionImplication.getLeft();
                for (int j = inputSize - 1; j >= 0; --j) {
                    if (inputExpressions.get(j) instanceof Implication) {
                        Implication impl2 = (Implication) inputExpressions.get(j);
                        if (expressionImplication.getRight().equals(impl2.getRight()) && exists.getExpression().equals(impl2.getLeft())) {
                            if (expressionImplication.getRight().allVariables().contains(exists.getVariable())
                                    && expressionImplication.getRight().freeVariables(new ArrayList<Variable>()).contains(exists.getVariable())) {
                                return "Вывод некорректен начиная с формулы " + inputSize + ": " + "переменная " +
                                        exists.getVariable().toString() + " входит свободно в формулу " + expressionImplication.getRight().toString();
                            }

                            for (int i = 0; i < freeVariables.size(); ++i) {
                                if (freeVariables.get(i).contains(exists.getVariable())) {
                                    Expression e = alpha;
                                    if (i < G.size())
                                        e = G.get(i);
                                    return "Вывод некорректен начиная с формулы " + inputSize + ": " +
                                            "используется правило с квантором по переменной " + exists.getVariable().toString() +
                                            ", входящей свободно в допущение " + e.toString();
                                }
                            }

                            for (Expression expressionFromExistential : existentialInferences) {
                                mainProof.add(predicatesPack(expressionFromExistential, alpha, exists.getExpression(), expressionImplication.getRight(), exists.getVariable()));
                            }
                            return OK;
                        }
                    }
                }
            }
        }
        return NEXT;
    }

    private int isAxiom(Expression expression) {
        return Axioms.isAxiom(expression);
    }

    private static Expression predicatesPack(Expression expression, Expression a, Expression b, Expression c, Variable v) {
        if (expression instanceof Variable) {
            if ("A".equals(expression.toString()))
                return a;
            if ("B".equals(expression.toString()))
                return b;
            if ("C".equals(expression.toString()))
                return c;
        }

        if (expression instanceof Predicate) {
            Predicate predicate = (Predicate) expression;
            if ("A".equals(predicate.getName()))
                return a;
            if ("B".equals(predicate.getName()))
                return b;
            if ("C".equals(predicate.getName()))
                return c;
        }


        if (expression instanceof Conjunction) {
            Conjunction conj = (Conjunction) expression;
            return new Conjunction(predicatesPack(conj.getLeft(), a, b, c, v), predicatesPack(conj.getRight(), a, b, c, v));
        }

        if (expression instanceof Disjunction) {
            Disjunction disj = (Disjunction) expression;
            return new Disjunction(predicatesPack(disj.getLeft(), a, b, c, v), predicatesPack(disj.getRight(), a, b, c, v));
        }

        if (expression instanceof Implication) {
            Implication impl = (Implication) expression;
            return new Implication(predicatesPack(impl.getLeft(), a, b, c, v), predicatesPack(impl.getRight(), a, b, c, v));
        }

        if (expression instanceof Negative) {
            Negative neg = (Negative) expression;
            return new Negative(predicatesPack(neg.getExpression(), a, b, c, v));
        }

        if (expression instanceof Existential) {
            Existential Existential = (Existential) expression;
            return new Existential(v, predicatesPack(Existential.getExpression(), a, b, c, v));
        }

        if (expression instanceof Universal) {
            Universal Universal = (Universal) expression;
            Universal = new Universal(v, predicatesPack(Universal.getExpression(), a, b, c, v));
            return Universal;
        }
        return null;
    }

    private void DeductionExpressionParse() {
        int brackets = 0;
        int afterLastComma = 0;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '(') ++brackets;
            if (s.charAt(i) == ')') --brackets;
            if (s.charAt(i) == ',' && brackets == 0) {
                G.add(new Parser(s.substring(afterLastComma, i)).parse());
                freeVariables.add((G.get(G.size() - 1)).freeVariables(new ArrayList<Variable>()));
                afterLastComma = i + 1;
            }
        }
        alpha = new Parser(s.substring(afterLastComma, s.indexOf("|-"))).parse();
        freeVariables.add(alpha.freeVariables(new ArrayList<Variable>()));
        betta = new Parser(s.substring(s.indexOf("|-") + 2, s.length())).parse();
    }
}