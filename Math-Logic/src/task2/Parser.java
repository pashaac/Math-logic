package task2;

/**
 * Created by ПАВЕЛ on 15.02.14.
 */
public class Parser {
    private String expr;
    public Parser(String expr) {
        this.expr = expr;
    }

    Expression parse() {
        return realParse(0, expr.length() - 1);
    }

    Expression realParse(int l, int r)
    {
        // ->
        int brackets = 0;
        for (int i = l; i <= r; ++i)
        {
            if (expr.charAt(i) == '>' && brackets == 0)
                return new Implication(realParse(l, i - 2), realParse(i + 1, r));
            if (expr.charAt(i) == ')')
                ++brackets;
            if (expr.charAt(i) == '(')
                --brackets;
        }

        // \/
        brackets = 0;
        for (int i = r; i >= l; --i)
        {
            if (expr.charAt(i) == '|' && brackets == 0)
                return new Disjunction(realParse(l, i - 1), realParse(i + 1, r));
            if (expr.charAt(i) == ')')
                ++brackets;
            if (expr.charAt(i) == '(')
                --brackets;
        }

        // &
        brackets = 0;
        for (int i = r; i >= l; --i)
        {
            if (expr.charAt(i) == '&' && brackets == 0)
                return new Conjunction(realParse(l, i - 1), realParse(i + 1, r));
            if (expr.charAt(i) == ')')
                ++brackets;
            if (expr.charAt(i) == '(')
                --brackets;
        }

        if (expr.charAt(l) == '!')
            return new Negative(realParse(l + 1, r));

        if (expr.charAt(l) == '(')
            return realParse(l + 1, r - 1);

        return new Variable(expr.substring(l, r + 1));
    };
}
