package task5;
import java.util.ArrayList;

/**
 * Created by ПАВЕЛ on 17.02.14.
 */

public class ProofChecker {
    private static final String OK = "OK";
    private static final String NEXT = "next";

    private String s;
    private ArrayList<Expression> inputExpressions;

    private int inputSize;

    public ProofChecker(String s) {
        this.s = s;
        inputSize = 0;
        inputExpressions = new ArrayList<>();
    }

    public String getString() {
        return s;
    }

    public String add(Expression expression) {
        ++inputSize;
        inputExpressions.add(expression);

        if (isAxiom(expression) != -1) {
            return axiomWork(expression);
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
                                "терм " + term.toString() + " не свободен для подстановки в формулу " +
                                universal.getExpression().toString() + " вместо переменной " + universal.getVariable().toString();
                    }
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
                                "терм " + term.toString() + " не свободен для подстановки в формулу " +
                                existential.getExpression().toString() + " вместо переменной " + existential.getVariable().toString();
                    }
                }
            }
        }
        if (axiom == 109) {
            Implication implication = (Implication) expression;
            Conjunction conjunction = (Conjunction) implication.getLeft();
            Universal universal = (Universal) conjunction.getRight();
            Variable variable = universal.getVariable();
            ArrayList<Variable> variables = implication.getRight().busyVariables();
            if (variables.contains(variable)){
                return "Вывод некорректен начиная с формулы " + inputSize + ": " +
                        "терм " + (new Apostrophe(variable)) + " не свободен для подстановки в формулу " +
                        implication.getRight() + " вместо переменной " + variable;
            }
        }
        return OK;
    }

    private boolean mainProofModusPonens(Expression expression) {
        for (int i = inputExpressions.size() - 1; i >= 0; --i) {
            if (inputExpressions.get(i) instanceof Implication) {
                Implication implication = (Implication) inputExpressions.get(i);
                if (implication.getRight().equals(expression)) {
                    for (int j = inputExpressions.size() - 1; j >= 0; --j) {
                        Expression left = inputExpressions.get(j);
                        if (left.equals(implication.getLeft())) {
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
                            if (expressionImplication.getLeft().allVariables().contains(universal.getVariable()) &&
                                    expressionImplication.getLeft().freeVariables(new ArrayList<Variable>()).contains(universal.getVariable())) {
                                return "Вывод некорректен начиная с формулы " + inputSize + ": "
                                        + "переменная " + universal.getVariable().toString() + " входит свободно в формулу " +
                                        expressionImplication.getLeft().toString();
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
                for (int j = inputSize - 2; j >= 0; --j) {
                    if (inputExpressions.get(j) instanceof Implication) {
                        Implication impl2 = (Implication) inputExpressions.get(j);
                        if (expressionImplication.getRight().equals(impl2.getRight()) && exists.getExpression().equals(impl2.getLeft())) {
                            if (expressionImplication.getRight().allVariables().contains(exists.getVariable())
                                    && expressionImplication.getRight().freeVariables(new ArrayList<Variable>()).contains(exists.getVariable())) {
                                return "Вывод некорректен начиная с формулы " + inputSize + ": " + " переменная " +
                                        exists.getVariable().toString() + " входит свободно в формулу " + expressionImplication.getRight().toString();
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
}