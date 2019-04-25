import java.io.*;

import java.net.*;
import java.util.Scanner;
import java.util.stream.*;


public class Client {

    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите команду: ");
            String phrase = sc.nextLine();

            try {

                byte[] data = new byte[1000]; //создаем массив байтов для создания пакета

                DatagramSocket s = new DatagramSocket(); //сокеты бла бла

                InetAddress addr = InetAddress.getLocalHost(); //адрес локального хоста, можно null или localhost

                InputStream fr = new ByteArrayInputStream(phrase.getBytes()); //превращаем строку в байтовый stream чтобы отправить


                while (fr.read(data) != -1) { //записываем

                    //создание пакета данных

                    DatagramPacket pac= new DatagramPacket(data, data.length, addr, 8033);

                    s.send(pac);//отправление пакета

                }

                fr.close();//закрываем поток

                System.out.println("Файл отправлен");

            } catch (Exception e) {
                System.out.println("Ошибка.Попробуйте ещё раз.");

            }

        }

    }
}