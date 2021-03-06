import java.io.*;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.stream.*;


public class Client implements Serializable{

    public static void main(String[] args) throws IOException {
        send("check");
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите команду: ");
            String phrase = sc.nextLine();
            send(phrase);
        }

    }

    private static String ByteBufferToString(ByteBuffer buffer){
        int limit = buffer.limit();
        byte bytes[] = new byte[limit];
        buffer.get(bytes,0,limit);
        String s = new String(bytes);
        return s;
    }
    private static void send(String phrase){

        try {
            DatagramChannel client = DatagramChannel.open();

            client.bind(null); //связываем с localhost

            ByteBuffer buffer = ByteBuffer.allocate(10000); //Выделяет новый буфер
            buffer.put(phrase.getBytes()); //положили строку в буфер
            buffer.flip();

            InetSocketAddress serverAddr = new InetSocketAddress("localhost", 2016);
            client.send(buffer, serverAddr); //отправляем поток байт на сервер
            System.out.println("Комманда отправлена");

            //прием от сервера
            buffer.clear();
            Runnable check=()->{
                Long curtime=System.currentTimeMillis();
                Thread ThisThread=Thread.currentThread();
                try {
                    while (System.currentTimeMillis() < curtime + 8000) {
                    }
                    System.out.println("ШОТА НЕ ТО С СЕРВЕРОМ");
                    client.close();
                } catch (Exception e){
                    System.out.println("OOPs");
                }

            };
            Thread thread1 = new Thread(check);
            thread1.start();
            client.receive(buffer);
            thread1.stop();
            buffer.flip();
            String response = ByteBufferToString(buffer);
            System.out.println("Ответ от сервера:");
            System.out.println(response);


        } catch (Exception e){
            System.out.println("Ошибка.Попробуйте ещё раз.");
        }
    }
}