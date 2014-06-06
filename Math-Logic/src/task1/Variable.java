package task1;

import java.util.ArrayList;

public class Variable implements Expression {
    private String variableName;

    public Variable(String variableName) {
        this.variableName = variableName;
    }

    public boolean evaluate(ArrayList<String> trueVariables) {
        return trueVariables.contains(variableName);
    }

    public String toString() {
        return variableName;
    }

    public boolean equal(Expression expression) {
        return this.toString().equals(expression.toString());
    }
}
