import Objects.Gryadka;

import java.io.*;
import java.util.Scanner;

public class To_xml_file {

    public static void to_xml(Gryadka gryadka) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter("src/input1.xml"); //я хочу еще сделать так,
        File file = new File("src/input.xml");  // чтобы эти файлы были под одним именем
        Scanner scan = new Scanner(file);
        while (scan.hasNext()){
            String line = scan.nextLine();
            pw.println(line);
        }

        String intro = "<gryadkas>";
        String name = gryadka.getType();
        int count = gryadka.getCount();
        String atr = "    <gryadka name=\"" + name + "\" count=\"" + count + "\"/>";
        String close = "</gryadkas>";
        pw.println(intro);
        pw.println(atr);
        pw.println(close);
        pw.close();
    }
}
