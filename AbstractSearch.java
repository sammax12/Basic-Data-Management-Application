import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class AbstractSearch {

    protected String[] lineResult;
    public String refernece, lookUp, foreignKey;
    protected BufferedReader br;

    public AbstractSearch() {
    }

    // Search function other classes implement for thier search functionality needs.
    public abstract void searchFileRefrence(String refrence) throws IOException;

    public void getSearchRefernece() throws InputMismatchException, IOException {
        Scanner scan = new Scanner(System.in);

        System.out.println(referenceString());
        refernece = scan.nextLine().toLowerCase();

        if (checkReferneceAll(refernece)) {
            System.out.println("\nYour search was not recognized, Try Again");
            return;
        }

        System.out.println("What is your Search (CASE SENSITIVE!!!!)");
        lookUp = scan.nextLine();

        if (checkRefernece(refernece)) {
            searchFileRefrenceHelper(lookUp);
        } else
            searchFileRefrence(refernece);
    }

    // If refernece is not ID, Firstname, Lastname, or gender, this method is
    // invoked to help assist to search functionality.
    private void searchFileRefrenceHelper(String localLookUp) throws IOException {
        String line;
        BufferedReader br;

        if ((refernece.equals("phone") || refernece.equals("phone number"))) {
            br = new BufferedReader(new FileReader(DBFile.fileArray[2]));
        } else {
            br = new BufferedReader(new FileReader(DBFile.fileArray[1]));
        }

        while ((line = br.readLine()) != null) {
            lineResult = line.trim().split("[,]");

            if (lineResult[DBFile.collumMap.get(refernece)].trim().equals(localLookUp)) {
                foreignKey = lineResult[1].trim();
                lookUp = foreignKey;
                searchFileRefrence("id");
            }
        }
    }

    public void setLookUp(String lookUp) {
        this.lookUp = lookUp;
    }

    private String referenceString() {
        return """

                Search by:
                ID,
                Firstname,
                Lasrname,
                Gender,
                Address,
                City,
                State,
                Country,
                Phone,
                Phone Number
                """;
    }

    private boolean checkRefernece(String refernece) {
        return (!((refernece.equals("id")) || (refernece.equals("firstname")) || (refernece.equals("lastname"))
                || (refernece.equals("gender"))));
    }

    private boolean checkReferneceAll(String refernece) {
        return (!((refernece.equals("id")) || (refernece.equals("firstname")) || (refernece.equals("lastname"))
                || (refernece.equals("gender")) || (refernece.equals("address")) || (refernece.equals("city"))
                || (refernece.equals("state")) || (refernece.equals("country")) || (refernece.equals("phone"))
                || (refernece.equals("phone number"))));
    }
}
