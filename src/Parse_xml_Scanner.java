import Objects.Gryadka;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Parse_xml_Scanner {
    private static ConcurrentLinkedDeque <Gryadka> gryadkas = new ConcurrentLinkedDeque<>();

    public static ConcurrentLinkedDeque<Gryadka> main() throws IOException {
        File file = new File(System.getenv("INPUT"));
        File edit = new File("src/edit.xml"); //файл, который мы изменяем


        Scanner scan = new Scanner(file);
        FileWriter fw = new FileWriter(edit);
        while (scan.hasNext()) {
            String line = scan.nextLine(); //проходимся по всем линиям xml файла

            fw.write(line + "\n"); //записываем все в файл edit

            if (line.startsWith("    <gryadka")){ //находим нужные нам элементы
                String line2 = line.substring(line.indexOf("\"")+1,line.lastIndexOf("\""));
                String[] parse_line = line2.split("\" count=\""); //парсим имя грядки и кол-во плодов

                String name = parse_line[0];
                int count = Integer.parseInt(parse_line[1]);
                gryadkas.addLast(new Gryadka(count, name)); //пихаем в коллекцию
            }
        }
        scan.close();
        fw.close();

        return gryadkas;
    }
}
