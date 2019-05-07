import java.io.*;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;
import java.util.stream.*;


public class Client implements Serializable{

    public static void main(String[] args) throws IOException {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите команду: ");
            String phrase = sc.nextLine();

            try {
                DatagramChannel client = DatagramChannel.open();

                client.bind(null); //связываем с localhost

                ByteBuffer buffer = ByteBuffer.allocate(1000); //Выделяет новый буфер
                buffer.put(phrase.getBytes()); //положили строку в буфер
                buffer.flip();

                InetSocketAddress serverAddr = new InetSocketAddress("localhost", 2012);
                client.send(buffer, serverAddr); //отправляем поток байт на сервер
                System.out.println("Комманда отправлена");

                //прием от сервера
                buffer.clear();
                client.receive(buffer);
                buffer.flip();

                String response = ByteBufferToString(buffer);

                System.out.println("Ответ от сервера:");
                System.out.println(response);


            } catch (Exception e){
                System.out.println("Ошибка.Попробуйте ещё раз.");
            }










            /*try {

                byte[] data = new byte[1000]; //создаем массив байтов для создания пакета

                DatagramSocket s = new DatagramSocket(); //сокеты бла бла

                InetAddress addr = InetAddress.getLocalHost(); //адрес локального хоста, можно null или localhost

                InputStream fr = new ByteArrayInputStream(phrase.getBytes());//превращаем строку в байтовый stream чтобы отправить

                while (fr.read(data) != -1) { //записываем

                    //создание пакета данных

                    DatagramPacket pac = new DatagramPacket(data, data.length, addr, 2012);

                    s.send(pac);//отправление пакета

                }

                fr.close();//закрываем поток

                System.out.println("Комманда отправлена");

            } catch (Exception e) {
                System.out.println("Ошибка.Попробуйте ещё раз.");

            }*/

        }

    }

    private static String ByteBufferToString(ByteBuffer buffer){
        int limit = buffer.limit();
        byte bytes[] = new byte[limit];
        buffer.get(bytes,0,limit);
        String s = new String(bytes);
        return s;
    }
}