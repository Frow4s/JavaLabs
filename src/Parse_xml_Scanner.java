import Objects.Gryadka;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Scanner;

public class Parse_xml_Scanner {
    private static ArrayDeque<Gryadka> gryadkas = new ArrayDeque<>();

    public static ArrayDeque<Gryadka> main() throws FileNotFoundException {
        File file = new File(System.getenv("INPUT"));
        Scanner scan = new Scanner(file);
        while (scan.hasNext()) {
            String line = scan.nextLine(); //проходимся по всем линиям xml файла

            if (line.startsWith("    <gryadka")){ //находим нужные нам элементы
                String line2 = line.substring(line.indexOf("\"")+1,line.lastIndexOf("\""));
                String[] parse_line = line2.split("\" count=\""); //парсим имя грядки и кол-во плодов

                String name = parse_line[0];
                int count = Integer.parseInt(parse_line[1]);
                gryadkas.addLast(new Gryadka(count, name)); //пихаем в коллекцию
            }
        }
        scan.close();

        return gryadkas;
    }
}
