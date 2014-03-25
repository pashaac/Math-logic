import java.util.ArrayList;

/**
 * Created by ПАВЕЛ on 16.02.14.
 */

// bust of all variables(True/False).
public class IsTrueExpression {
    public static String getTrueFalseResult(String s) {
        Parser parser = new Parser(s);
        String result = "";
        Expression inputExpression = parser.parse();
        ArrayList<String> allVariables = getAllVariables(s);
        for (int i = 0; i < (1<<allVariables.size()); ++i) {
            String code = getCode(i, allVariables.size());
            ArrayList<String> trueVariables = new ArrayList<String>();
            for (int j = 0; j < code.length(); ++j)
                if (code.charAt(j) == '1') {
                    trueVariables.add(allVariables.get(j));
                }
            if (inputExpression.evaluate(trueVariables) == false) {
                result += "Высказывание ложно при ";
                for (int j = 0; j < code.length() - 1; ++j)
                    if (code.charAt(j) == '1') {
                        result += allVariables.get(j) + "=И, ";
                    }
                    else {
                        result += allVariables.get(j) + "=Л, ";
                    }
                if (code.charAt(code.length() - 1) == '1') {
                    result += allVariables.get(code.length() - 1) + "=И.";
                }
                else {
                    result += allVariables.get(code.length() - 1) + "=Л.";
                }
                return result;
            }
        }
        return "True expression.";
    }

    // get binary code of bust
    public static String getCode(int n, int len) {
        if (len == 0)
            return "";
        if (n % 2 == 1)
            return getCode(n / 2, len - 1) + "1";
        else
            return getCode(n / 2, len - 1) + "0";
    }

    // all variables from string s
    public static ArrayList<String> getAllVariables(String s) {
        ArrayList<String> result = new ArrayList<String>();
        String variable = "";
        s = s + "@";
        for (int i = 0; i < s.length(); ++i)
            if (Character.isDigit(s.charAt(i)) || Character.isLetter(s.charAt(i))) {
                variable += s.charAt(i);
            } else {
                if (variable.equals("") == false && result.contains(variable) == false) {
                    result.add(variable);
                }
                variable = "";
            }
        return result;
    }
}
