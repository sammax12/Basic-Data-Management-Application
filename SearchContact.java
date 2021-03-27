import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SearchContact extends AbstractSearch {

    private String[] lineResult;
    private ArrayList<String> record;

    public SearchContact() throws IOException {
        super();
        record = new ArrayList<String>();
        getSearchRefernece();
        System.out.println(this);
    }

    public void searchFileRefrence(String refrence) throws IOException {
        BufferedReader fileOne = new BufferedReader(new FileReader(DBFile.fileArray[0]));
        String line, addressLine;

        while ((line = fileOne.readLine()) != null) {
            if (line.isEmpty()) {
                continue;
            }

            lineResult = line.trim().split("[,]");
            if (lineResult[DBFile.collumMap.get(refrence)].trim().equals(lookUp)) {
                    record.add("Contact: ");

                record.add(line.substring(5));
                foreignKey = lineResult[0].trim();

                for (int fileType = 1; fileType < DBFile.fileArray.length; fileType++) {
                    br = new BufferedReader(new FileReader(DBFile.fileArray[fileType]));

                    if (fileType == 1)
                        record.add("\nAddress: ");
                    else 
                        record.add("\nPhone: ");

                    while ((addressLine = br.readLine()) != null) {
                        if (addressLine.isEmpty()) {
                            continue;
                        }

                        lineResult = addressLine.trim().split(",");
                        if (lineResult[1].trim().equals(foreignKey)) {
                            record.add(addressLine.substring(10));
                        }
                    }
                }
                br.close();
            }
        }
        fileOne.close();
    }

    @Override
    public String toString() {
        return String.join("", record).replaceAll(",", " ");
        // String.format("%-20s", String.join(" ", record).replaceAll(",", "\n"));
    }
}
