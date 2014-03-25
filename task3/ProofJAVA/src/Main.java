import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ПАВЕЛ on 15.02.14.
 */

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("input.txt"));
            FileWriter output = new FileWriter("output.txt");

            try {
                DoingProof doingProof = new DoingProof(input.readLine());
                String result = IsTrueExpression.getTrueFalseResult(doingProof.getString());
                if (result.equals("True expression.")) {
                    ArrayList<Expression> answer = doingProof.getResult();
                    for (int i = 0; i < answer.size(); ++i)
                        output.write(answer.get(i).toString() + "\n");
                }
                else {
                    output.write(result + "\n");
                }
            } finally {
                input.close();
                output.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
    }
}
