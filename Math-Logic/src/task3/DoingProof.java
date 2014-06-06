package task3;

import java.util.ArrayList;

/**
 * Created by ПАВЕЛ on 18.02.14.
 */
public class DoingProof {
    private String s;
    private ArrayList<String> allVariables;
    private ArrayList<Expression>[] allProofs;
    private Expression inputExpression;


    public DoingProof(String s) {
        this.s = s;
    }

    public String getString() {
        return s;
    }

    public ArrayList<Expression> getResult() {
        allVariables = IsTrueExpression.getAllVariables(s);
        allProofs = new ArrayList[(1<<allVariables.size())];
        inputExpression = new Parser(s).parse();
        setAllProofs();
        return getAnswer(1);
    }

    private ArrayList<Expression> getAnswer(int n) {
        if (n >= (1<<allVariables.size())) {
            return allProofs[n - (1<<allVariables.size())];
        } else {
            return merge(getAnswer(2*n), getAnswer(2*n+1), 2*n);
        }
    }

    private ArrayList<Expression> merge(ArrayList<Expression> left, ArrayList<Expression> right, int n) {
        int len = 0, k = n;
        while (k > 0) { k /= 2; ++len; }
        --len;

        // for n = 2*k
        String request = getRequest(IsTrueExpression.getCode(n, len), len, left.get(left.size() - 1));
        DeductionWork deductionWork = new DeductionWork(request);
        for (int i = 0; i < left.size(); ++i)
            if (deductionWork.add(left.get(i))) { }
        ArrayList<Expression> result = deductionWork.getMainProof();

        // for n = 2*k + 1
        request = getRequest(IsTrueExpression.getCode(n + 1, len), len, right.get(right.size() - 1));
        deductionWork = new DeductionWork(request);
        for (int i = 0; i < right.size(); ++i)
            if (deductionWork.add(right.get(i))) { }
        ArrayList<Expression> temporaryResult = deductionWork.getMainProof();

        // union
        for (int i = 0; i < temporaryResult.size(); ++i)
            result.add(temporaryResult.get(i));
        Expression betta = deductionWork.getBetta();
        Expression alpha = deductionWork.getAlpha();

        if (alpha instanceof Negative)
            alpha = ((Negative) alpha).getExpression();
        ArrayList<Expression> alphaNotAlpha = Proofs.expression16(alpha);
        for (int i = 0; i < alphaNotAlpha.size(); ++i)
            result.add(alphaNotAlpha.get(i));

        Expression left_ = new Implication(alpha, betta);
        Expression midle_ = new Implication(new Negative(alpha), betta);
        Expression right_ = new Implication(new Disjunction(alpha, new Negative(alpha)), betta);

        result.add(new Implication(left_, new Implication(midle_, right_)));
        result.add(new Implication(midle_, right_));
        result.add(right_);
        result.add(betta);

        return result;
    }

    private void setAllProofs() {
        for (int i = 0; i < (1<<allVariables.size()); ++i) {
            ArrayList<String> trueVariable = getTrueVariables(i);
            allProofs[i] = new Proofs().doingProof(inputExpression, trueVariable);
        }
    }

    private String getRequest(String code, int len, Expression expression) {
        String request = "";
        for (int i = 0; i < len - 1; ++i)
            if (code.charAt(i) == '1')
                request += allVariables.get(i) + ",";
            else
                request += "!" + allVariables.get(i) + ",";

        if (code.charAt(len - 1) == '1')
            request += allVariables.get(len - 1) + "|-";
        else
            request += "!" + allVariables.get(len - 1) + "|-";
        request += expression.toString();
        return request;
    }

    private ArrayList<String> getTrueVariables(int n) {
        ArrayList<String> trueVariable = new ArrayList<String>();
        String str = IsTrueExpression.getCode(n, allVariables.size());
        for (int j = 0; j < str.length(); ++j)
            if (str.charAt(j) == '1')
                trueVariable.add(allVariables.get(j));
            else
                trueVariable.add("!" + allVariables.get(j));
        return trueVariable;
    }
}
