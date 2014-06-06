package task5;
import java.util.ArrayList;

/**
 * Created by pavel on 3/16/14.
 */

public class Predicate implements Expression {

    private String name;
    private ArrayList<Expression> terms;

    public Predicate(String name, ArrayList<Expression> terms) {
        this.name = name;
        this.terms = terms;
    }

    public boolean evaluate(ArrayList<String> trueVariables) {
        return true;
    }

    public String toString() {
        String s = name;
        if (terms != null && terms.size() != 0) {
            s += "(";
        }
        if (terms != null) {
            for (int i = 0; i < terms.size(); ++i) {
                s += terms.get(i);
                if (i < terms.size() - 1) {
                    s += ",";
                }
            }
        }
        if (terms != null && terms.size() != 0) {
            s += ")";
        }
        return s;
    }

    @Override
    public ArrayList<Variable> freeVariables(ArrayList<Variable> busyVariables) {
        ArrayList<Variable> temporaryArrayList = new ArrayList<Variable>();
        for (Expression term : terms) {
            temporaryArrayList.addAll(term.freeVariables(busyVariables));
        }
        return temporaryArrayList;
    }

    @Override
    public ArrayList<Variable> busyVariables() {
        ArrayList<Variable> temporaryArrayList = new ArrayList<Variable>();
        for (Expression term : terms) {
            temporaryArrayList.addAll(term.busyVariables());
        }
        return temporaryArrayList;
    }

    @Override
    public ArrayList<Variable> allVariables() {
        ArrayList<Variable> temporaryArrayList = new ArrayList<Variable>();
        for (Expression term : terms) {
            temporaryArrayList.addAll(term.allVariables());
        }
        return temporaryArrayList;
    }

    @Override
    public Pair<Expression,Expression> differenceExpression(Expression expression) {
        if (expression instanceof Predicate) {
            if (name.equals(((Predicate) expression).name) == false)
                new Pair(this, expression);

            ArrayList<Expression> expressionTerms = ((Predicate) expression).getTerms();
            if (terms.size() != expressionTerms.size())
                return new Pair(this, expression);
            for (int i = 0; i < terms.size(); ++i) {
                Pair<Expression, Expression> difference = terms.get(i).differenceExpression(expressionTerms.get(i));
                if (difference != null)
                    return difference;
            }
            return null;
        }
        return new Pair(this, expression);
    }

    @Override
    public Expression doChange(Expression instead, Expression newExpr) {
        if (this.equals(instead))
            return newExpr;
        ArrayList<Expression> expressionTerms = new ArrayList<>();
        for (Expression term : terms) {
            expressionTerms.add(term.doChange(instead, newExpr));
        }
        return new Predicate(name, expressionTerms);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Predicate) {
            Predicate predicate = (Predicate) object;
            boolean flag = name.equals(predicate.getName()) && (terms.size() == predicate.getTerms().size());
            for (int i = 0; i < terms.size() && flag; ++i) {
                if (!terms.get(i).equals(predicate.getTerms().get(i))) {
                    flag = false;
                }
            }
            return flag;
        }
        return false;
    }

    public ArrayList<Expression> getTerms() {
        return terms;
    }

    public String getName() {
        return name;
    }
}