import Objects.Gryadka;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Xml_remove {
    static void xml_remove(Gryadka gryadka) throws FileNotFoundException {
        String[] lines = new String[40];
        File file = new File(System.getenv("INPUT"));
        File edit = new File("src/edit.xml"); //файл, который мы изменяем

        Scanner scan = new Scanner(edit);
        int i = 0;
        while (scan.hasNext()) {
            String line = scan.nextLine();
            lines[i] = line;
            i++;
        }

        PrintWriter pw = new PrintWriter(edit);
        String to_remove="";
        for (String str : lines) {
            if (str != null) {
                to_remove = "    <gryadka name=\"" + gryadka.getType();
                if (!str.startsWith(to_remove))
                    pw.println(str);
            }
        }
        pw.close();
    }
}
