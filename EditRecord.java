import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class EditRecord extends AbstractSearch {

    private List<List<String>> record = new ArrayList<>();
    private String replace, toReplace, search, setLookUP;
    private DeleteRecord deleteRecordBeforeEdit = new DeleteRecord(true);

    public EditRecord() throws IOException {
        editLineHelper();
    }

    // Get user input of what they want to change about a specfic contact/
    private void editLineHelper() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Serach By ID, Firstname,Lastname");
        search = scan.nextLine().toLowerCase();
        System.out.println("Whats the contacts " + search + " you'll like to edit? (CASE SENSITIVE)");
        setLookUP = scan.nextLine();

        setLookUp(setLookUP);
        searchFileRefrence(search);

        if (record.isEmpty()) {
            System.out.println("Contact Was Not Found!!");
            scan.close();
            return;
        }

        System.out.println("\n=====Full Contact Data For Edit======");
        printContactData();

        System.out.println("\nEnter the data that you'll like to change? (ONLY ONE AT A TIME)");
        replace = scan.nextLine();
        System.out.println("What will you like " + replace + " change too?");
        toReplace = scan.nextLine();

        deleteRecordBeforeEdit.setContactPrimaryKey(foreignKey);
        deleteRecordBeforeEdit.deleteRecord();

        editLine();
    }

    // Edit data that user wants to change too
    private void editLine() throws IOException {
        List<String> index = new ArrayList<>();

        for (int i = 0; i < record.size(); i++) {
            index = record.get(i);

            if (checkInput(index)) {
                index.set(index.indexOf(replace), toReplace);
            } else {
                System.out.println("\nYour change was not allowed, try again");
                break;
            }
        }

        wirteToFileHelper();
    }

    private boolean checkInput(List<String> index) {
        return ((index.contains(replace)) && !((index.indexOf(replace) == 0) || (index.indexOf(replace) == 1))
                && !((replace.equals("endOfContact")) || (replace.equals("endOfAddress"))
                        || (replace.equals("endOfPhone"))));
    }

    // Writing the contacts data back to thier repected files.
    private void wirteToFileHelper() throws IOException {
        String writeToFileType;
        File fileType = null;

        for (List<String> line : record) {
            writeToFileType = line.get(line.size() - 1);

            if (writeToFileType.equals("endOfContact"))
                fileType = DBFile.fileArray[0];
            else if (writeToFileType.equals("endOfAddress"))
                fileType = DBFile.fileArray[1];
            else if (writeToFileType.equals("endOfPhone"))
                fileType = DBFile.fileArray[2];

            DBFile.writeToFile(line.subList(0, line.size() - 1), fileType);
        }
    }

    // Search for specfic contact the user inputed in editLineHelper method
    @Override
    public void searchFileRefrence(String refrence) {
        String line, addressLine;
        List<String> temp;
        try {
            BufferedReader fileOne = new BufferedReader(new FileReader(DBFile.fileArray[0]));

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
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void printContactData() {
        record.stream().map(printString -> String.join(" | ", printString).replaceAll(",", " "))
                .forEach(System.out::println);
    }
}
