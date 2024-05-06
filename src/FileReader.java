package src;

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class FileReader {
    public static ArrayList<String> readFile(String fname) {
        ArrayList<String> result = new ArrayList<>();
        try {
            File file = new File(fname);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.add(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }
}
