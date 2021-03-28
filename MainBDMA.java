
import java.io.IOException;
import java.util.Scanner;

public class MainBDMA {
    public static void main(String[] args) throws Exception {
        menu();
    }

    public static void menu() throws IOException {
        Scanner scan = new Scanner(System.in);
        int pick;

        try {
            while (true) {
                System.out.print(menuString() + "\nUSER INPUT(PICK NUMBER 1 - 5): ");

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
                            new SearchContact();
                            break;
                        case 4:
                            new DeleteRecord(false);
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
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
        } finally {
            scan.close();
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
