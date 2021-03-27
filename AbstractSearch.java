import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class AbstractSearch {
    
    protected String[] lineResult;
    public String refernece, lookUp, foreignKey;
    protected BufferedReader br;

    public AbstractSearch(){}

    public abstract void searchFileRefrence(String refrence) throws IOException;

    public void getSearchRefernece() throws InputMismatchException, IOException {
        Scanner scan = new Scanner(System.in);

        System.out.println(referenceString());
        refernece = scan.nextLine().toLowerCase();

        System.out.println("What is your Sreach (CASE SENSITIVE!!!!)");
        lookUp = scan.nextLine();

        if (checkRefernece(refernece)) {
            searchFileRefrenceHelper(lookUp);
        } else
            searchFileRefrence(refernece);
    }

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
                APT #,
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

}
