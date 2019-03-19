import Objects.Gryadka;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Scanner;

public class ConsoleApp {
    static Date time= new Date();
    private static ArrayDeque<Gryadka> gryadkas = new ArrayDeque<>();

    public static void main(String args[]) throws FileNotFoundException {
        gryadkas = Parse_xml_Scanner.main();
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите команду: ");
            String phrase = sc.nextLine();
            //парсер строки на команду и остальное
            parse_line(phrase);
            //System.out.println(phrase);

        }
    }

    public static void play() throws FileNotFoundException{
        Output story = new Output();
        story.tellTheStory(gryadkas);
        gryadkas = Parse_xml_Scanner.main();
    }

    private static void parse_line(String line) throws FileNotFoundException{
        String[] words = line.split(" ");


        if (words[0].equals("add")) {
            String name = words[1] + " " + words[2];
            int count = Integer.parseInt(words[3]);
            Gryadka gryadka = new Gryadka(count, name);
            add(gryadka);
        } else
            if (words[0].equals("remove")){
                remove(words[1],words[2],words[3]);
            } else
                if (words[0].equals("remove_lower")){
                    remove_lower(words[1],words[2],words[3]);
                } else
                    if (line.equals("show")){
                        show();
                    } else
                        if (line.equals("play")){
                            play();
                        } else
                            if (line.equals("clear")) {
                                clear();
                            } else
                                if (line.equals("stop")) {
                                    stop();
                                } else
                                    if (words[0].equals("import")){
                                    impor((words[1]));
                                    } else
                                        if (words[0].equals("info")){
                                            info();
                                        }else
                                            System.out.println("Команды не существует"); //можно прикрутить exception
    }
    public static void remove(String word1,String word2,String word3)throws FileNotFoundException{
        String name = word1 + " " + word2;
        int count = Integer.parseInt(word3);
        Gryadka gr = new Gryadka(count,name);
        ArrayDeque<Gryadka> toRemove = new ArrayDeque<>();
        for (Gryadka gryadk : gryadkas) {
            if (gryadk.getName().equals(gr.getName()) && (gryadk.getCount() == gr.getCount())) {
                toRemove.add(gryadk);
            }
        }
        gryadkas.removeAll(toRemove);  //решение против ошибки ConcurrentModificationException
        for (Gryadka grr : toRemove) {
            Xml_remove.xml_remove(grr);
        }
    }
    public static void remove_lower(String word1,String word2,String word3)throws FileNotFoundException{
        ArrayDeque<Gryadka> toRemove = new ArrayDeque<>();
        String name = word1 + " " + word2;
        int count = Integer.parseInt(word3);
        for (Gryadka gryadk : gryadkas) {
            if (gryadk.getCount() <= count) {
                toRemove.add(gryadk);
            }
        }
        gryadkas.removeAll(toRemove);
        for (Gryadka grr : toRemove) {
            Xml_remove.xml_remove(grr);
        }
    }
    public static void impor(String path) throws FileNotFoundException {
        add(importGson.importJson(path));
    }
    public static void info(){
        System.out.println("Тип:ArrayDequeue");
        System.out.println("Размер очереди:"+gryadkas.size());
        System.out.println("Дата инициализации:"+time);
    }
    public static void stop(){
        System.exit(0);
    }
    public static void add(Gryadka gryadka) throws FileNotFoundException{
        gryadkas.addLast(new Gryadka(gryadka.getCount(), gryadka.getType()));
        To_xml_file.to_xml_add(gryadka);
    }

    public static void show(){
        if (gryadkas.isEmpty())
            System.out.println("Коллекция пуста!"); //можно прикрутить exception
        for (Gryadka gryadka : gryadkas) {
            System.out.println(gryadka.getName() + " " + gryadka.getCount());
        }
    }


    public static void clear(){
        gryadkas.clear();
    }

}

