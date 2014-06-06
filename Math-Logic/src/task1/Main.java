package task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    static ArrayList<task1.Expression> arrayList = new ArrayList<task1.Expression>();

    public static void main(String[] args) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("input.txt"));
            FileWriter output = new FileWriter("output.txt");
            FileWriter comments = new FileWriter("comments.txt");

            try {
                String s;
                int ind = 0;
                while ((s = input.readLine()) != null) {
                    ++ind;
                    Parser parser = new Parser(s);
                    arrayList.add(parser.parse());
                    String outString = getInformation(arrayList.get(arrayList.size() - 1));
                    comments.write(ind + ") " + arrayList.get(arrayList.size() - 1).toString() + "    | " + outString + "\n");
                    if (outString.equals("Доказательство некорректно на текущем высказывании.")) {
                        output.write("Доказательство некорректно начиная с высказывания № " + ind);
                        ind = -1;
                        break;
                    }
                }
                if (ind != -1)
                    output.write("Доказательство корректно.");
            } finally {
                input.close();
                output.close();
                comments.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
    }

    private static String getInformation(task1.Expression expression) {
        int axiom = Axioms.isAxiom(expression);
        if (axiom != -1)
            return "axiom № " + axiom;
        return modusPonensCheck(expression);
    }

    private static String modusPonensCheck(task1.Expression expression) {
        for (int i = arrayList.size() - 1; i >= 0; --i) {
            if (arrayList.get(i) instanceof Implication && ((Implication) arrayList.get(i)).getRight().equal(expression)) {
                for (int j = arrayList.size() - 1; j >= 0 ; --j)
                    if (((Implication) arrayList.get(i)).getLeft().equal(arrayList.get(j)))
                        return "Modus Ponens strings " + (i + 1) + " and " + (j + 1);
            }
        }
        return "Доказательство некорректно на текущем высказывании.";
    }
}
