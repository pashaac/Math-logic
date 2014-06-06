package task2;

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
            FileWriter comments = new FileWriter("comments.txt");

            try {
                int ind = 0;
                DeductionWork deductionWork = new DeductionWork(input.readLine());

                // perfomance
                comments.write("0) " + deductionWork.getString() + "\n");
                for (int i = 0; i < deductionWork.getString().length() + 3; ++i)
                    comments.write("*");
                comments.write("\n");

                // main work
                String s;
                while ((s = input.readLine()) != null) {
                    if (s.length() == 0) continue;
                    ++ind;
                    Parser parser = new Parser(s);
                    if (deductionWork.add(parser.parse())) {
                        continue;
                    } else {
                        output.write("Исходный вывод некорректен. Строка № " + ind);
                        ArrayList<String> inputComments = deductionWork.getInputComments();
                        for (int i = 0; i < inputComments.size(); ++i)
                            comments.write(inputComments.get(i));
                        ind = -1;
                        break;
                    }
                }

                if (ind != -1) {
                    ArrayList<Expression> mainProof = deductionWork.getMainProof();
                    for (int i = 0; i < mainProof.size(); ++i)
                        output.write(mainProof.get(i).toString() + "\n");
                    ArrayList<String> inputComments = deductionWork.getInputComments();
                    for (int i = 0; i < inputComments.size(); ++i)
                        comments.write(inputComments.get(i));
                }
            } finally {
                input.close();
                output.close();
                comments.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
    }
}
