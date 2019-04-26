import Objects.Gryadka;

import java.io.*;

import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;


public class Server {
    static Date time= new Date();
    private static ConcurrentLinkedDeque<Gryadka> gryadkas = new ConcurrentLinkedDeque<>();
    public static void play() throws IOException {
        Output story = new Output();
        story.tellTheStory(gryadkas);
        gryadkas = Parse_xml_Scanner.main();
    }

    public static void main(String[] args) throws Exception {

        DatagramSocket s = new DatagramSocket(8033);
        System.out.println("Прием данных…");
        while (true){

            try { // прием файла
                parse_line(acceptFile( 8033, 1000,s));


        } catch (IOException e) {

            e.printStackTrace();

        }
        }

    }


    private static String acceptFile( int port, int pacSize,DatagramSocket s) throws IOException {

        byte data[] = new byte[pacSize];

        DatagramPacket pac = new DatagramPacket(data, data.length);

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {


            s.receive(pac); //получаем пакет

            os.write(data); //записываем в виде последовательности байтов

            os.close(); //закрываем

            return(os.toString().trim()); //преобразовыаем в строку

        }
        catch (Exception e){
            return ("Ошибка приёма команды");
        }

    }
    private static void parse_line(String line) throws IOException {
        String[] words = line.split(" ");
        try {
            if (words[0].equals("add")) {
                add(words[1], words[2], words[3]);
            } else if (words[0].equals("remove")) {
                remove(words[1], words[2], words[3]);
            } else if (words[0].equals("remove_lower")) {
                remove_lower(words[1], words[2], words[3]);
            } else if (line.equals("show")) {
                show();
            } else if (line.equals("play")) {
                play();
            } else if (line.equals("clear")) {
                clear();
            } else if (line.equals("stop")) {
                stop();
            } else if (words[0].equals("import")) {
                impor((words[1]));
            } else if (words[0].equals("info")) {
                info();
            } else
                System.out.println("Команды не существует");
        } catch (Exception e){
            System.out.println("Неверный формат команды");
        }

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

    /**
     * Убирает из коллекции все элементы меньше заданного
     * @param word1-значение элемента коллекции
     * @param word2-значение элемента коллекции
     * @param word3-значение элемента коллекции
     * @throws FileNotFoundException
     */
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

    /**
     * Добавляет в коллекцию элемент из json-файла
     * @param path-путь к json-файлу
     * @throws FileNotFoundException
     */
    public static void impor(String path) throws FileNotFoundException {
        gryadkas.addLast(importGson.importJson(path));
        To_xml_file.to_xml_add(importGson.importJson(path));
    }

    /**
     *Выводит информацию о коллекции в стандартный поток вывода
     */
    public static void info(){
        System.out.println("Тип:ArrayDequeue");
        System.out.println("Размер очереди:"+gryadkas.size());
        System.out.println("Дата инициализации:"+time);
    }

    /**
     *Останавливает работу консольного приложения
     */
    public static void stop() throws IOException {
        //перезапись input.xml
        File file = new File(System.getenv("INPUT"));
        File edit = new File("src/edit.xml");
        FileWriter fw = new FileWriter(file);
        Scanner scanner = new Scanner(edit);
        Scanner scanner2 = new Scanner(file);
        while (scanner.hasNext()){
            String linee = scanner.nextLine();
            if (!scanner2.hasNext()){
                fw.write(linee + "\n");
            }
        }
        fw.close();
        scanner.close();
        scanner2.close();
        edit.delete();
        System.exit(0);
    }

    /**
     * Добавляет в коллекцию грядку с введёнными параметрами
     * @param word1-названием грядки
     * @param word2-названием грядки
     * @param word3-количество плодов
     * @throws FileNotFoundException
     */
    public static void add(String word1,String word2,String word3) throws FileNotFoundException{
        String name = word1 + " " + word2;
        int count = Integer.parseInt(word3);
        Date current_date=new Date();
        Gryadka gryadka = new Gryadka(count, name);
        gryadkas.addLast(new Gryadka(gryadka.getCount(), gryadka.getType()));
        gryadkas.getLast().setCreateTime(current_date);
        To_xml_file.to_xml_add(gryadka);
    }

    /**
     *Показывает все элементы коллекции в строковом прелставлении
     */
    public static void show(){
        if (gryadkas.isEmpty())
            System.out.println("Коллекция пуста!"); //можно прикрутить exception
        for (Gryadka gryadka : gryadkas) {
            System.out.println(gryadka.getName() + " " + gryadka.getCount());
        }
    }

    /**
     *Очищает коллекцию
     */
    public static void clear() throws IOException {
        File sourceFile = new File("src/edit.xml");
        File outputFile = new File("src/edit2.xml");

        FileWriter fw = new FileWriter(outputFile);
        String importatntLine = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        fw.write(importatntLine);
        fw.close();
        sourceFile.delete();
        outputFile.renameTo(sourceFile);
        gryadkas.clear();
    }
    public static void check() throws IOException{
        if(new File("src/edit.xml").exists()) {
            System.out.println("Программа была завершена некорректно,восстановить изменения?");
            Scanner sc = new Scanner(System.in);
            String answer = sc.nextLine();
            if (answer.equals("yes")) {
                //перезапись input.xml
                File file = new File(System.getenv("INPUT"));
                File edit = new File("src/edit.xml");
                FileWriter fw = new FileWriter(file);
                Scanner scanner = new Scanner(edit);
                Scanner scanner2 = new Scanner(file);
                while (scanner.hasNext()){
                    String linee = scanner.nextLine();
                    if (!scanner2.hasNext()){
                        fw.write(linee + "\n");
                    }
                }
                fw.close();
                scanner.close();
                scanner2.close();
                edit.delete();
            }
        }
    }

}
