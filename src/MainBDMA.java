package src;
import java.io.IOException;
import java.util.Scanner;

public class MainBDMA {
 public static void main(String[] args) throws Exception {
   
    menu();
    }

    static void menu() throws InterruptedException, IOException {
        Scanner scan = new Scanner(System.in);
        int pick;

        while (true) {
            System.out.println(menuString());

            if (scan.hasNextInt()) { // Error handling if user input not int.
                pick = Integer.parseInt(scan.nextLine());
                if ((pick < 1) || (pick > 5)) {
                    System.out.println("Retry! Enter input 1 - 5");
                } else {
                    switch (pick) {
                    case 1:
                        new Contact();
                        break;
                    case 2:
                        new EditRecord();
                        break;
                    case 3:
                        new Search();
                        break;
                    case 4:
                        new DeleteRecord();
                        break;
                    case 5:
                        System.out.println("Exit \n");
                        scan.close();
                        return;
                    }

                }
            } else {
                System.out.println("Retry! Enter input 1 - 5");
                scan.nextLine();
            }
        }
    }

    private static String menuString() {
        return """

                1. Create Contact
                2. Edit Contact
                3. Search Contact
                4. Delete Contact
                5. Exit
                """;
    }
}
