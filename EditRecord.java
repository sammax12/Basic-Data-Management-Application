import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class EditRecord extends AbstractSearch {

    private ArrayList<String> resultLine = new ArrayList<String>();
    private List<List<String>> record = new ArrayList<>();
    private String replace, toReplace, search, setLookUP;

    public EditRecord() throws IOException {
        editLineHelper();
        // System.out.println(record);
        // System.out.println(record.size());
    }

    private void editLineHelper() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Serach By Firstname or Lastname");
        search = scan.nextLine().toLowerCase();
        System.out.println("Whats the contacts " + search + " you'll like to edit? (CASE SENSITIVE)");
        setLookUP = scan.nextLine();

        setLookUp(setLookUP);
        searchFileRefrence(search);
        System.out.println(record);

        System.out.println("What are you change?");
        replace = scan.nextLine();
        System.out.println("What are you change " + replace + " too?");
        toReplace = scan.nextLine();

        // DeleteRecord deleteRecordBeforeEdit = new DeleteRecord(true);
        // deleteRecordBeforeEdit.setForeignKey(foreignKey);
        // deleteRecordBeforeEdit.deleteRecord();

        editLine();
    }

    private void editLine() throws IOException {
        List<String> index;

        // Delete and then write to file.
        System.out.println(record.size());

        for (int i = 0; i < record.size(); i++) {
            index = record.get(i);

            if (index.contains(replace))
                index.set(index.indexOf(replace), toReplace);

            System.out.println(index);
        }

        writeToFile();
    }

    private void writeToFile()
    {
        for(List<String> line: record)
        {
            System.out.println((line.get(line.size()-1)));
        }
    }

    @Override
    public void searchFileRefrence(String refrence) throws IOException {
        BufferedReader fileOne = new BufferedReader(new FileReader(DBFile.fileArray[0]));
        String line, addressLine;
        List<String> temp;

        while ((line = fileOne.readLine()) != null) {
            if (line.isEmpty()) {
                continue;
            }

            lineResult = line.trim().split("[,]");
            if (lineResult[DBFile.collumMap.get(refrence)].trim().equals(lookUp)) {
                temp = new ArrayList<>(Arrays.asList(lineResult));
                temp.add("endOfContact");

                record.add(temp);

                foreignKey = lineResult[0].trim();

                for (int fileType = 1; fileType < DBFile.fileArray.length; fileType++) {
                    br = new BufferedReader(new FileReader(DBFile.fileArray[fileType]));

                    while ((addressLine = br.readLine()) != null) {
                        if (addressLine.isEmpty()) {
                            continue;
                        }

                        lineResult = addressLine.trim().split(",");
                        if (lineResult[1].trim().equals(foreignKey)) {
                            temp = new ArrayList<>(Arrays.asList(lineResult));

                            if (fileType == 1)
                                temp.add("endOfAddress");
                            else
                                temp.add("endOfPhone");

                            record.add(temp);
                        }
                    }
                }
                br.close();
            }
        }
        fileOne.close();
    }

    // if (lineResult.contains(replace)) {
    // lineResult = lineResult.replaceAll(replace, toReplace);
    // //line = String.join("", lineResult).replaceAll(",", "");
    // System.out.println(lineResult);
    // }
    // for (File file : DBFile.fileArray) {
    // br = new BufferedReader(new FileReader(file));
    // while ((line = br.readLine()) != null) {
    // lineResult = line.trim();
    // // System.out.println(lineResult);

    // if (lineResult.contains(replace)) {
    // // lineResult = lineResult.replaceAll(replace, toReplace);
    // System.out.println("TRUE\n" + lineResult);
    // lol.add(lineResult);
    // }

    // // lol = new ArrayList<>(Arrays.asList(lineResult.split("[,]")));
    // // DBFile.writeToFile(lol, DBFile.fileArray[1]);
    // }
    // System.out.println("\n");
    // br.close();
    // }
    // System.out.println(this);

    // @Override
    public String toString(String resultLine) {
        return String.join("", resultLine).replaceAll(",", " "); // String.format("%-20s", String.join(" ",
                                                                 // record).replaceAll(",", "\n"));
    }
}
