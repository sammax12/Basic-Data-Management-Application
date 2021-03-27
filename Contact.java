
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Contact {

    private final String[] infoGrab = new String[] { "Whats Your First Name?", "Whats Your Last Name?",
            "Whats Your Gender?", "endContactQuestion", "Whats Your Address?", "Whats Your Apartment (APT) Number?",
            "Whats City You Live In?", "Whats State You Live In?", "What Contury You Live In?", "endAddressQuestion",
            "What Type Of Phone You Have?", "Whats Your Phone Number?", "endPhoneQuestion" };

    List<String> list = new ArrayList<String>();

    public Contact() throws InterruptedException, IOException {
        createContact();
    }

    private void createContact() throws IOException {
        Scanner scan = new Scanner(System.in);
        String readIn, data;

        DBFile.cheackIFPrimaryKeyFileExists();

        for (int infoGrabPostion = 0; infoGrabPostion < infoGrab.length; infoGrabPostion++) {
            data = infoGrab[infoGrabPostion];

            if (data.equals("endContactQuestion")) {
                wirteToFileHelper(0, "endContactQuestion");
                continue;

            } else if (data.equals("endAddressQuestion")) {
                System.out.println("Would you like to add another Address? Enter Yes or No");
                if ((readIn = scan.nextLine()).toLowerCase().equals("yes"))
                    infoGrabPostion = 3;

                wirteToFileHelper(1, "endAddressQuestion");
                continue;

            } else if (data.equals("endPhoneQuestion")) {
                System.out.println("Would you like to add another Phone? Enter Yes or No");
                if ((readIn = scan.nextLine()).toLowerCase().equals("yes"))
                    infoGrabPostion = 9;

                wirteToFileHelper(2, "endPhoneQuestion");
                continue;
            }

            System.out.println(data
                    + " (If you have no data to input, then enter 'None' or skip to the next question by hitting enter)");

            System.out.print("INPUT: ");
            readIn = scan.nextLine();

            if (readIn.isEmpty()) {
                readIn = "null";
            }

            list.add(readIn);
        }
    }

    private void wirteToFileHelper(int fileType, String endofQuestion) throws IOException {
        if (endofQuestion.equals("endContactQuestion")) {
            DBFile.setPrimaryKey("Contact");
            list.add(0, Integer.toString((DBFile.getPrimaryKey("Contact"))));
        } else if (endofQuestion.equals("endAddressQuestion")){
            DBFile.setPrimaryKey("AddressTableID");
            list.add(0, Integer.toString((DBFile.getPrimaryKey("AddressTableID"))));
            list.add(1, Integer.toString((DBFile.getPrimaryKey("Contact"))));
        } else if (endofQuestion.equals("endPhoneQuestion")){
            DBFile.setPrimaryKey("PhoneTableID");
            list.add(0, Integer.toString((DBFile.getPrimaryKey("PhoneTableID"))));
            list.add(1, Integer.toString((DBFile.getPrimaryKey("Contact"))));
        }

        DBFile.writeToFile(list, DBFile.fileArray[fileType]);
        list.clear();
    }
}
