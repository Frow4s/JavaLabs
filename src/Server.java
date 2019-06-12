import Objects.Gryadka;

import javax.mail.MessagingException;
import java.io.*;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;


public class Server {

    static String enter_user;

    static LocalDateTime time=LocalDateTime.now();
    public static DatagramSocket socket; //создаём сокет с портом по которому будем принимать команды

    public static SocketAddress client;

    private static ConcurrentLinkedDeque<Gryadka> gryadkas = new ConcurrentLinkedDeque<>(); //Создаём потокобезопасную очередь
    public static String  play(String user) throws Exception {
        DataBaseHandler db=new DataBaseHandler();
        Output story = new Output();
        gryadkas = Parse_xml_Scanner.main();
        return(story.tellTheStory(db.gryadki(user)));

    }

    public static void main(String[] args) throws Exception {
        socket = new DatagramSocket(2016);
        //check();
        System.err.println("Прием данных…");
        while (true){

            try { // прием файла
                parse_line(acceptFile(socket), enter_user); //парсим строку которую получили

        } catch (IOException e) {

            e.printStackTrace(); //обрабатываем ошибку в теории можно убрать команду

        }
        }
    }


    private static String acceptFile(DatagramSocket s) throws IOException { //метод для принятия датаграммы

        byte data[] = new byte[10000]; //создаём байтовый массив для данных

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
    private static void parse_line(String line, String user) throws Exception {
        Func theFunc = new Func();
        String[] words = line.split(" ");
        try {
            if (words[0].equals("add")) {
                write(theFunc.add(line, enter_user));
            } else if (words[0].equals("remove")) {
                write(theFunc.remove(line,enter_user));
            } else if (words[0].equals("remove_lower")) {
                write(theFunc.remove_lower(line,enter_user));
            } else if (line.equals("show")) {
                write(theFunc.show(enter_user));
            } else if (line.equals("play")) {
                write(play(enter_user));
            } else if (line.equals("clear")) {
                write(theFunc.clear(enter_user));
            } else if (line.equals("stop")) {
                stop();
            } else if (words[0].equals("import")) {
                impor((words[1]));
            } else if (words[0].equals("info")) {
                info();
            } else if (line.equals("check")) {
                check();
            } else if (words[0].equals("sign_up")) {
                write(theFunc.sign_up(line));
            } else if (words[0].equals("login")) {
                write(theFunc.login(line));
                if (theFunc.login(line).equals("*Неправильный логин или пароль*")){
                    enter_user = null;
                } else
                    enter_user = theFunc.getLogin();
            } else
                write("Команды не существует");
        } catch (Exception e){
            write("Неверный формат команды");
        }

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
        write("Тип:ArrayDequeue"+"\n"+"Пользователь:"+enter_user);
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
        write("Сервер закрыт");
        System.exit(0);
    }

    /**
     * Добавляет в коллекцию грядку с введёнными параметрами
     * @param word1-названием грядки
     * @param word2-названием грядки
     * @param word3-количество плодов
     * @throws FileNotFoundException
     */


    /*public static void add(String word1,String word2,String word3) throws IOException {
        String name = word1 + " " + word2;
        int count = Integer.parseInt(word3);
        Gryadka gryadka = new Gryadka(count, name);
        gryadkas.addLast(new Gryadka(gryadka.getCount(), gryadka.getType()));
        gryadkas.getLast().setCreateTime(LocalDateTime.now());
        To_xml_file.to_xml_add(gryadka);
        write("Команда получена");
    }*/

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
            String answer = acceptFile(socket);
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
                write("Данные востановлены.");
            } else {
                write("Данные не востановлены.");
            }
        } else {
            write("Программа была завершена корректно");
        }
        gryadkas = Parse_xml_Scanner.main();
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
