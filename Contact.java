import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Contact {

    private final String[] infoGrab = new String[] { "Whats Your First Name?", "Whats Your Last Name?",
            "Whats Your Gender?", "endContactQuestion", "Whats Your Address?", "Whats Your Apartment (APT) Number?",
            "Whats City You Live In?", "Whats State You Live In?", "What Contury You Live In?", "endAddressQuestion",
            "What Type Of Phone You Have?", "Whats Your Phone Number?", "endPhoneQuestion" };

    private final String ID = randIDCreate();
    ArrayList<String> list = new ArrayList<String>();

    public Contact() throws InterruptedException, IOException {
        createContact();
    }

    private void createContact() throws IOException {
        Scanner scan = new Scanner(System.in);
        String readIn, data;

        for (int infoGrabPostion = 0; infoGrabPostion < infoGrab.length; infoGrabPostion++) {
            data = infoGrab[infoGrabPostion];

            if (data.equals("endContactQuestion")) {
                wirteToFileHelper(0, true);
                continue;

            } else if (data.equals("endAddressQuestion")) {
                System.out.println("Would you like to add another Address? Enter Yes or No");
                if ((readIn = scan.nextLine()).toLowerCase().equals("yes"))
                    infoGrabPostion = 3;

                wirteToFileHelper(1, false);
                continue;

            } else if (data.equals("endPhoneQuestion")) {
                System.out.println("Would you like to add another Phone? Enter Yes or No");
                if ((readIn = scan.nextLine()).toLowerCase().equals("yes"))
                    infoGrabPostion = 9;

                wirteToFileHelper(2, false);
                continue;
            }

            System.out.println(data
                    + " (If you have no data to input, then enter 'None' or skip to the next question by hitting enter)");

            System.out.print("INPUT: ");
            readIn = scan.nextLine();

            if (readIn.isEmpty()) {
                readIn = "null";
            }

            list.add(readIn + ",");
        }
    }

    private void wirteToFileHelper(int fileType, boolean ifEndofContactQuestion) throws IOException {
        if (ifEndofContactQuestion)
            list.add(0, ID + ",");
        else {
            list.add(0, randIDCreate() + ",");
            list.add(1, ID + ",");
        }

        DBFileManager.writeToFile(list, DBFileManager.fileArray[fileType]);
        list.clear();
    }

    private String randIDCreate() {
        return Integer.toString(((int) (Math.random() * (8999)) + 1000));
    }
}
