import java.util.ArrayList;

/**
 * Created by ПАВЕЛ on 15.02.14.
 */
public class Parser {
    private String expressionStr;
    public Parser(String expr) {
        this.expressionStr = expr;
    }

    public Expression parse() {
        return realParse(0, expressionStr.length() - 1);
    }

    private Expression realParse(int l, int r)
    {
        // ->
        int brackets = 0;
        for (int i = l; i <= r; ++i)
        {
            if (expressionStr.charAt(i) == '>' && brackets == 0)
                return new Implication(realParse(l, i - 2), realParse(i + 1, r));
            if (expressionStr.charAt(i) == ')')
                ++brackets;
            if (expressionStr.charAt(i) == '(')
                --brackets;
        }

        // |
        brackets = 0;
        for (int i = l; i <= r; ++i)
        {
            if (expressionStr.charAt(i) == '|' && brackets == 0)
                return new Disjunction(realParse(l, i - 1), realParse(i + 1, r));
            if (expressionStr.charAt(i) == ')')
                ++brackets;
            if (expressionStr.charAt(i) == '(')
                --brackets;
        }

        // &
        brackets = 0;
        for (int i = l; i <= r; ++i)
        {
            if (expressionStr.charAt(i) == '&' && brackets == 0)
                return new Conjunction(realParse(l, i - 1), realParse(i + 1, r));
            if (expressionStr.charAt(i) == ')')
                ++brackets;
            if (expressionStr.charAt(i) == '(')
                --brackets;
        }

        // !
        if (expressionStr.charAt(l) == '!')
            return new Negative(realParse(l + 1, r));

        // get round bad situations +  miscarry (, )

        if (expressionStr.charAt(l) == '(' && expressionStr.charAt(r) == ')') {
            boolean flag = true;
            int balance = 0;
            for (int i = l + 1; i < r; ++i) {
                if (expressionStr.charAt(i) == '(') {
                    ++balance;
                }
                if (expressionStr.charAt(i) == ')') {
                    --balance;
                }
                if (balance == -1)
                    flag = false;
            }
            if (flag && balance == 0)
                return realParse(l + 1, r - 1);
        }

        // quantifiers E, \/
        if (expressionStr.charAt(l) == '@') {
            int index = l + 1;
            while (index <= r && (Character.isDigit(expressionStr.charAt(index))) || (Character.isAlphabetic(expressionStr.charAt(index)) &&
                    Character.isLowerCase(expressionStr.charAt(index)))) {
                ++index;
            }
            Variable variable = new Variable(expressionStr.substring(l + 1, index));
            return new Universal(variable, realParse(l + variable.toString().length() + 1, r));
        }
        if (expressionStr.charAt(l) == '?') {
            int index = l + 1;
            while (index <= r && (Character.isDigit(expressionStr.charAt(index))) || (Character.isAlphabetic(expressionStr.charAt(index)) &&
                    Character.isLowerCase(expressionStr.charAt(index)))) {
                ++index;
            }
            Variable variable = new Variable(expressionStr.substring(l + 1, index));
            return new Existential(variable, realParse(l + variable.toString().length() + 1, r));
        }

        // predicate
        if (Character.isAlphabetic(expressionStr.charAt(l))) {
            int index = l;
            while (index <= r && (Character.isDigit(expressionStr.charAt(index)) || ((Character.isAlphabetic(expressionStr.charAt(index)))))) {
                ++index;
            }
            if (index > r)
                return new Variable(expressionStr.substring(l, index));

            String predicateName = expressionStr.substring(l, index);
            ArrayList<Expression> terms = new ArrayList<Expression>();
            int lastPosAfterComma = index + 1;
            brackets = 0;
            for (int i = index + 1; i < r; ++i) {
                if (expressionStr.charAt(i) == '(') ++brackets;
                if (expressionStr.charAt(i) == ')') --brackets;
                if (expressionStr.charAt(i) == ',' && brackets == 0) {
                    terms.add(realParse(lastPosAfterComma, i - 1));
                    lastPosAfterComma = i + 1;
                }
            }
            terms.add(realParse(lastPosAfterComma, r - 1));
            return new Predicate(predicateName, terms);
        }
        return null;
    }
}