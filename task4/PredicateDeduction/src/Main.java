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
            boolean isGood = true;
            try {
                DeductionWork deductionWork = new DeductionWork(input.readLine());
                String s;
                while ((s = input.readLine()) != null) {
                    if (s.length() == 0) continue;
                    String result = deductionWork.add(new Parser(s).parse());
                    if (result.equals("OK"))
                        continue;

                    output.write(result + "\n");
                    isGood = false;
                    break;
                }
                if (isGood) {
                    ArrayList<Expression> mainProof = deductionWork.getMainProof();
                    for (int i = 0; i < mainProof.size(); ++i)
                        output.write(mainProof.get(i).toString() + "\n");
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
