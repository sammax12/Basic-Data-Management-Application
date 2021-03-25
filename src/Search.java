package src;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Search {

    private String[] lineResult;
    private ArrayList<String> record = new ArrayList<String>();

    String refernece, lookUp, foreignKey = new String();
    BufferedReader br;

    public Search() throws IOException {
        getSearchRefernece();
    }

    private void getSearchRefernece() throws InputMismatchException, IOException {
        Scanner scan = new Scanner(System.in);

        System.out.println(referenceString());
        refernece = scan.nextLine().toLowerCase();

        System.out.println("What is your Sreach (CASE SENSITIVE)");
        lookUp = scan.nextLine();

        if (checkRefernece(refernece)) {
            searchFileRefrenceHelper(lookUp);
        } else
            searchFileRefrence(refernece);
    }

    public void searchFileRefrence(String refrence) throws IOException {
        BufferedReader fileOne = new BufferedReader(new FileReader(DBFileManager.fileArray[0]));
        String line, addressLine;

        while ((line = fileOne.readLine()) != null) {
            if(line.isEmpty()){
                continue;
            }

            lineResult = line.trim().split("[,]");



            if (lineResult[DBFileManager.collumMap.get(refrence)].trim().equals(lookUp)) {
                record.add(line.substring(5));
                foreignKey = lineResult[0].trim();

                for (int fileType = 1; fileType < DBFileManager.fileArray.length; fileType++) {
                    br = new BufferedReader(new FileReader(DBFileManager.fileArray[fileType]));
                    if (fileType == 1)
                        record.add("\nAddress:");
                    else
                        record.add("\nPhone:");

                    while ((addressLine = br.readLine()) != null) {
                        if(addressLine.isEmpty()){
                            continue;
                        }

                        lineResult = addressLine.trim().split(",");
                        if (lineResult[1].trim().equals(foreignKey)) {
                            record.add(addressLine.substring(9));
                        }
                    }
                }
                br.close();
            }

            if (!record.isEmpty()) {
                // System.out.print("\n==========Contact==============" + this);
                System.out.println("Contact: " + this);
                record.clear();
            }
        }
        fileOne.close();
    }

    private void searchFileRefrenceHelper(String localLookUp) throws IOException {
        String line;
        BufferedReader br;

        if ((refernece.equals("phone") || refernece.equals("phone number"))) {
            br = new BufferedReader(new FileReader(DBFileManager.fileArray[2]));
        } else {
            br = new BufferedReader(new FileReader(DBFileManager.fileArray[1]));
        }

        while ((line = br.readLine()) != null) {
            lineResult = line.trim().split("[,]");

            if (lineResult[DBFileManager.collumMap.get(refernece)].trim().equals(localLookUp)) {
                foreignKey = lineResult[1].trim();
                lookUp = foreignKey;
                searchFileRefrence("id");
            }
        }
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

    @Override
    public String toString() {
        return String.join(" ", record).replaceAll(",", " "); //String.format("%-20s", String.join(" ", record).replaceAll(",", "\n"));
    }

}
