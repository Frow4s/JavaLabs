import Objects.Gryadka;

import java.io.*;
import java.util.Scanner;

public class To_xml_file {

    public static void to_xml(Gryadka gryadka) throws FileNotFoundException{

        String[] lines = new String[20];

        File file = new File("src/input.xml");
        Scanner scan = new Scanner(file);
        int i = 0;
        while (scan.hasNext()){
            String line = scan.nextLine();
            lines[i] = line;
            i++;
        }

        PrintWriter pw = new PrintWriter("src/input.xml");
        for (String str : lines){
            if (str != null)
                pw.println(str);
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
