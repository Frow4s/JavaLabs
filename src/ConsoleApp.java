import java.util.Scanner;

public class ConsoleApp {

    public static void main(String args[]){

        /*ConsoleApp cp = new ConsoleApp();
        System.out.println(cp.szAppName);*/

        //use java.util.Scanner
        Scanner scanner = new Scanner(System.in);
        System.out.print("Грядку с каким плодом вы хотите добавить: ");
        String str = scanner.nextLine();
        System.out.println("Грядка " + str);

    }
}
