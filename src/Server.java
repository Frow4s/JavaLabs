import java.io.*;

import java.net.*;


public class Server {

    public static void main(String[] args) {

        byte phrase=111; //мб вообще не нужно( делал ночью, умираю)

        System.out.println("Прием данных…");

        try { // прием файла

            acceptFile(phrase, 8033, 1000);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    private static void acceptFile(byte file, int port, int pacSize) throws IOException {

        byte data[] = new byte[pacSize];

        DatagramPacket pac = new DatagramPacket(data, data.length);

        DatagramSocket s = new DatagramSocket(port);

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {

            while (true) {

                s.receive(pac); //получаем пакет

                os.write(data); //записываем в виде последовательности байтов

                System.out.println(os.toString()); //преобразовыаем в строку

                os.reset(); // *очищаем* stream, иначе команды накапливаются (123 , 123+43242 и т.д.)

                os.close(); //закрываем

            }

        } catch (SocketTimeoutException e) {
            

            os.close();

            System.out.println(

                    "Истекло время ожидания, прием данных закончен");

        }

    }

}
