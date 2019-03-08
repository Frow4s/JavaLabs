import Objects.Gryadka;

import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Scanner;

public class ConsoleApp {
    String[] Commands = new String[7];
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

    private static void play() throws FileNotFoundException{
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
                String name = words[1] + " " + words[2];
                int count = Integer.parseInt(words[3]);
                Gryadka gr = new Gryadka(count,name);
                ArrayDeque<Gryadka> toRemove = new ArrayDeque<>();
                for (Gryadka gryadk : gryadkas) {
                    if (gryadk.getName().equals(gr.getName()) && (gryadk.getCount() == gr.getCount())) {
                        toRemove.add(gryadk);
                    }
                }
                gryadkas.removeAll(toRemove);  //решение против ошибки ConcurrentModificationException
            } else
                if (words[0].equals("remove_lower")){
                    ArrayDeque<Gryadka> toRemove = new ArrayDeque<>();
                    String name = words[1] + " " + words[2];
                    int count = Integer.parseInt(words[3]);
                    for (Gryadka gryadk : gryadkas) {
                        if (gryadk.getCount() <= count) {
                          toRemove.add(gryadk);
                        }
                    }
                gryadkas.removeAll(toRemove);
                } else
                    if (line.equals("show")){
                        show();
                    } else
                        if (line.equals("play")){
                            play();
                        } else
                            if (line.equals("clear")){
                                clear();
                            } else
                                System.out.println("Команды не существует"); //можно прикрутить exception
    }

    private static void add(Gryadka gryadka){
        gryadkas.addLast(new Gryadka(gryadka.getCount(), gryadka.getType()));
    }

    private static void show(){
        if (gryadkas.isEmpty())
            System.out.println("Коллекция пуста!"); //можно прикрутить exception
        for (Gryadka gryadka : gryadkas) {
            System.out.println(gryadka.getName() + " " + gryadka.getCount());
        }
    }

    private static void clear(){
        gryadkas.clear();
    }

}

