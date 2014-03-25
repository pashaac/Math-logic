import java.util.ArrayList;

/**
 * Created by ПАВЕЛ on 17.02.14.
 */
public class DeductionWork {
    private static final String INPUT_EXPRESSION_INPUT_EXPRESSION = "input_expression_input_expression";
    private static final String INPUT_EXPRESSION_ALPHA = "input_expression_alpha";
    private static final String INPUT_EXPRESSION_G = "input_expression_g";
    private static final String G_G = "g_g";
    private static final String ALPHA_G = "alpha_g";

    private String s;
    private ArrayList<Expression> G;
    private ArrayList<Expression> mainProof;
    private ArrayList<Expression> inputExpression;
    private ArrayList<String> inputComments;

    private Expression alpha = null;
    private Expression betta = null;
    private int inputSize;

    public DeductionWork(String s) {
        this.s = s;
        inputSize = 0;
        DeductionExpressionParse();
    }

    public Expression getBetta() { return betta; }

    public Expression getAlpha() { return alpha; }

    public String getString() {
        return s;
    }

    public ArrayList<Expression> getMainProof() {
        return mainProof;
    }

    public ArrayList<String> getInputComments() {
        return inputComments;
    }

    public boolean add(Expression expression) {
        ++inputSize;
        inputExpression.add(expression);

        if (alpha == null) {
            mainProof.add(expression);
            return true;
        }

        if (isAxiom(expression) != -1) {
            inputComments.add(inputSize + ") " + expression.toString() + "   // axiom № " + isAxiom(expression) + "\n");

            mainProof.add(expression);
            mainProof.add(new Implication(expression, new Implication(alpha, expression)));
            mainProof.add(new Implication(alpha, expression));

            return true;
        }

        if (isG(expression)) {
            return true;
        }

        if (expression.equal(alpha))  {
            inputComments.add(inputSize + ") " + expression.toString() + "   // is alpha" + "\n");

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

        if (mainProofModusPonensCheck(expression)) {
            return true;
        }

        if (mainProofAlphaGModusPonensCheck(expression)) {
            return true;
        }

        if (alphaGandGModusPonensCheck(expression)) {
            return true;
        }

        inputComments.add(inputSize + ") " + expression.toString() + "   // некорректное выражение" + "\n");
        return false;
    }

    private boolean alphaGandGModusPonensCheck(Expression expression) {
        for (int i = 0; i < G.size(); ++i) {
            if (G.get(i) instanceof Implication && ((Implication) G.get(i)).getRight().equal(expression)) {
                for (int j = 0; j < G.size(); ++j)
                    if (G.get(j).equal(((Implication) G.get(i)).getLeft())) {
                        makeChange(expression, G.get(i), G.get(j), i, j, G_G);
                        return true;
                    }
                if (alpha.equal(((Implication) G.get(i)).getLeft())) {
                    makeChange(expression, G.get(i), alpha, i, -1, ALPHA_G);
                    return true;
                }
            }
            if (G.get(i) instanceof Implication && ((Implication) G.get(i)).getLeft().equal(expression)) {
                for (int j = 0; j < G.size(); ++j)
                    if (G.get(j).equal(((Implication) G.get(i)).getRight())) {
                        makeChange(expression, G.get(j), G.get(j), i, j, G_G);
                        return true;
                    }
                if (alpha.equal(((Implication) G.get(i)).getRight())) {
                    makeChange(expression, alpha, G.get(i), i, -1, ALPHA_G);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean mainProofAlphaGModusPonensCheck(Expression expression) {
        for (int i = inputExpression.size() - 1; i >= 0; --i) {
            if (inputExpression.get(i) instanceof Implication && ((Implication) inputExpression.get(i)).getRight().equal(expression)) {
                for (int j = 0; j < G.size(); ++j)
                    if (((Implication) inputExpression.get(i)).getLeft().equal(G.get(j))) {
                        makeChange(expression, inputExpression.get(i), G.get(j), i, j, INPUT_EXPRESSION_G);
                        return true;
                    }
                if (((Implication) inputExpression.get(i)).getLeft().equal(alpha)) {
                    makeChange(expression, inputExpression.get(i), alpha, i, -1, INPUT_EXPRESSION_ALPHA);
                    return true;
                }
            }

            if (inputExpression.get(i) instanceof Implication && ((Implication) inputExpression.get(i)).getLeft().equal(expression)) {
                for (int j = 0; j < G.size(); ++j)
                    if (((Implication) inputExpression.get(i)).getRight().equal(G.get(j))) {
                        makeChange(expression, G.get(j), inputExpression.get(i), i, j, INPUT_EXPRESSION_G);
                        return true;
                    }
                if (((Implication) inputExpression.get(i)).getRight().equal(alpha)) {
                    makeChange(expression, alpha, inputExpression.get(i), i, -1, INPUT_EXPRESSION_ALPHA);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isG(Expression expression) {
        for (int i = 0; i < G.size(); ++i)
            if (expression.equal(G.get(i))) {
                inputComments.add(inputSize + ") " + expression.toString() + "   // expression from G № " + (i + 1) + "\n");

                mainProof.add(expression);
                mainProof.add(new Implication(expression, new Implication(alpha, expression)));
                mainProof.add(new Implication(alpha, expression));

                return true;
            }
        return false;
    }

    private boolean mainProofModusPonensCheck(Expression expression) {
        for (int i = inputExpression.size() - 1; i >= 0; --i) {
            if (inputExpression.get(i) instanceof Implication && ((Implication) inputExpression.get(i)).getRight().equal(expression))
                for (int j = inputExpression.size() - 1; j >= 0; --j)
                    if (((Implication) inputExpression.get(i)).getLeft().equal(inputExpression.get(j))) {
                        makeChange(expression, inputExpression.get(i), inputExpression.get(j), i, j, INPUT_EXPRESSION_INPUT_EXPRESSION);
                        return true;
                    }
            if (inputExpression.get(i) instanceof Implication && ((Implication) inputExpression.get(i)).getLeft().equal(expression))
                for (int j = inputExpression.size() - 1; j >= 0; --j)
                    if (((Implication) inputExpression.get(i)).getRight().equal(inputExpression.get(j))) {
                        makeChange(expression, inputExpression.get(j), inputExpression.get(i), j, i, INPUT_EXPRESSION_INPUT_EXPRESSION);
                        return true;
                    }
        }
        return false;
    }

    private void makeChange(Expression expression, Expression exprK, Expression exprJ, int i, int j, String comment){
        Expression left = new Implication(alpha, exprJ);
        Expression middle = new Implication(alpha, new Implication(exprJ, expression));
        Expression right = new Implication(alpha, expression);

        mainProof.add(new Implication(left, new Implication(middle, right)));
        mainProof.add(new Implication(middle, right));
        mainProof.add(right);

        if (comment.equals(INPUT_EXPRESSION_INPUT_EXPRESSION)) {
            inputComments.add(inputSize + ") " + expression.toString() + "   // modus ponens input expressions №" + (i + 1) + " and " + (j + 1) + "\n");
        }

        if (comment.equals(INPUT_EXPRESSION_G)) {
            inputComments.add(inputSize + ") " + expression.toString() + "   // modus ponens input expression № " + (i + 1) + " and expression from G № " + (j + 1) + "\n");
        }

        if (comment.equals(INPUT_EXPRESSION_ALPHA)) {
            inputComments.add(inputSize + ") " + expression.toString() + "   // modus ponens input expression №" + (i + 1) + " and alpha" + "\n");
        }

        if (comment.equals(G_G)) {
            inputComments.add(inputSize + ") " + expression.toString() + "   // modus ponens expressions from G №" + (i + 1) + " and " +  (j + 1) + "\n");
        }

        if (comment.equals(ALPHA_G)) {
            inputComments.add(inputSize + ") " + expression.toString() + "   // modus ponens expression from G №" + (i + 1) + " and alpha"  + "\n");
        }
    }

    private int isAxiom(Expression expression) {
        return Axioms.isAxiom(expression);
    }

    private void DeductionExpressionParse() {
        G = new ArrayList<Expression>();
        mainProof = new ArrayList<Expression>();
        inputComments = new ArrayList<String>();
        inputExpression = new ArrayList<Expression>();
        Parser parser;
        while (s.indexOf(",") >= 0) {
            String temporary = s.substring(0, s.indexOf(","));
            parser = new Parser(temporary);
            G.add(parser.parse());
            s = s.substring(s.indexOf(",") + 1, s.length());
        }
        if (s.indexOf("|-") == 0) {
            alpha = null;
            return;
        }
        String temporary = s.substring(0, s.indexOf("|-"));
        parser = new Parser(temporary);
        alpha = parser.parse();
        temporary = s.substring(s.indexOf("|-") + 2, s.length());
        parser = new Parser(temporary);
        betta = parser.parse();
    }
}
