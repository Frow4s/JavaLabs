import Objects.Gryadka;

import java.io.*;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;


public class Server {
    static Date time= new Date();
    public static DatagramSocket socket; //создаём сокет с портом по которому будем принимать команды

    public static SocketAddress client;

    private static ConcurrentLinkedDeque<Gryadka> gryadkas = new ConcurrentLinkedDeque<>(); //Создаём потокобезопасную очередь
    public static void play() throws IOException {
        Output story = new Output();
        story.tellTheStory(gryadkas);
        gryadkas = Parse_xml_Scanner.main();
    }

    public static void main(String[] args) throws Exception {
        socket = new DatagramSocket(2015);
        System.out.println("Прием данных…");
        while (true){

            try { // прием файла
                parse_line(acceptFile(socket)); //парсим строку которую получили

        } catch (IOException e) {

            e.printStackTrace(); //обрабатываем ошибку в теории можно убрать команду

        }
        }
    }


    private static String acceptFile(DatagramSocket s) throws IOException { //метод для принятия датаграммы

        byte data[] = new byte[1000]; //создаём байтовый массив для данных

        DatagramPacket pac = new DatagramPacket(data, data.length); //формируем пакет через который судя по всему происходит запись

        ByteArrayOutputStream os = new ByteArrayOutputStream(); //создаём байтовый поток

        try {

            s.receive(pac); //получаем пакет по сокету и записываем

            client = pac.getSocketAddress();

            os.write(data);

            os.close();

            return(os.toString().trim()); //преобразовыаем в строку из потока байтов и обрезаем пробелы в конце строки

        }
        catch (Exception e){
            return ("Ошибка приёма команды");
        }

    }


    //тут все команды из консольного приложения кроме play (он в начале)
    private static void parse_line(String line) throws IOException {
        String[] words = line.split(" ");
        //try {
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
                write("Команды не существует");
        /*} catch (Exception e){
            write("Неверный формат команды");
        }*/

    }
    public static void remove(String word1,String word2,String word3) throws IOException {
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
        write("Команда получена");
    }

    /**
     * Убирает из коллекции все элементы меньше заданного
     * @param word1-значение элемента коллекции
     * @param word2-значение элемента коллекции
     * @param word3-значение элемента коллекции
     * @throws FileNotFoundException
     */
    public static void remove_lower(String word1,String word2,String word3) throws IOException {
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
        write("Команда получена");
    }

    /**
     * Добавляет в коллекцию элемент из json-файла
     * @param path-путь к json-файлу
     * @throws FileNotFoundException
     */
    public static void impor(String path) throws IOException {
        gryadkas.addLast(importGson.importJson(path));
        To_xml_file.to_xml_add(importGson.importJson(path));
        write("Команда получена");
    }

    /**
     *Выводит информацию о коллекции в стандартный поток вывода
     */
    public static void info() throws IOException {
        write("Тип:ArrayDequeue");
        write("Размер очереди:"+gryadkas.size());
        write("Дата инициализации:"+time);
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
        write("Команда получена");
    }

    /**
     * Добавляет в коллекцию грядку с введёнными параметрами
     * @param word1-названием грядки
     * @param word2-названием грядки
     * @param word3-количество плодов
     * @throws FileNotFoundException
     */


    public static void add(String word1,String word2,String word3) throws IOException {
        String name = word1 + " " + word2;
        int count = Integer.parseInt(word3);
        Date current_date=new Date();
        Gryadka gryadka = new Gryadka(count, name);
        gryadkas.addLast(new Gryadka(gryadka.getCount(), gryadka.getType()));
        gryadkas.getLast().setCreateTime(current_date);
        To_xml_file.to_xml_add(gryadka);
        write("Команда получена");
    }

    /**
     *Показывает все элементы коллекции в строковом прелставлении
     */
    public static void show() throws IOException {
        String str="";
        String toClient[] = new String[gryadkas.size()];
        int i = 0;
        if (gryadkas.isEmpty())
            write("Коллекция пуста!"); //можно прикрутить exception
        else {
            for (Gryadka gryadka : gryadkas) {
                toClient[i] = gryadka.getName() + " " + gryadka.getCount();
                i++;
            }
            Arrays.sort(toClient, new Comparator<String>() { //заменить на лямбда-выражение
                public int compare(String o1, String o2) {
                    return o1.toString().compareTo(o2.toString());
                }
            });
            for (String s:toClient){
                if (s.equals(toClient[gryadkas.size()-1]))
                    str += s;
                else
                    str += s + "\n";
            }
            write(str);
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
        write("Команда получена");
    }
    public static void check() throws IOException{
        if(new File("src/edit.xml").exists()) {
            write("Программа была завершена некорректно,восстановить изменения?");
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

    public static void writeObject(Object obj){

        Thread thread = new Thread(() -> {  //создаем отдельный поток
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(obj);
                oos.flush();
                byte[] response = bos.toByteArray();
                DatagramPacket resp = new DatagramPacket(response, response.length, client);
                socket.send(resp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public static void write(String s) throws IOException {

        Thread thread = new Thread(() -> {  //создаем отдельный поток
            byte response[] = s.getBytes();

            DatagramPacket resp = new DatagramPacket(response, response.length, client);

            try {
                socket.send(resp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

}
