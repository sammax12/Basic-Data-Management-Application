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
    private DeleteRecord  deleteRecordBeforeEdit = new DeleteRecord(true);

    public EditRecord() throws IOException {
        editLineHelper();
    }

    private void editLineHelper() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Serach By Firstname or Lastname");
        search = scan.nextLine().toLowerCase();
        System.out.println("Whats the contacts " + search + " you'll like to edit? (CASE SENSITIVE)");
        setLookUP = scan.nextLine();

        setLookUp(setLookUP);
        searchFileRefrence(search);
        
        System.out.println("=====Full Contact Data For Edit======");
        printContactData();

        // System.out.println("What are you change?");
        // replace = scan.nextLine();
        // System.out.println("What are you change " + replace + " too?");
        // toReplace = scan.nextLine();

        // deleteRecordBeforeEdit.setForeignKey(foreignKey);
        // deleteRecordBeforeEdit.deleteRecord();

        // editLine();
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

        wirteToFileHelper();
    }

    private void wirteToFileHelper() throws IOException
    {
        String writeToFileType;
        File fileType = null;
        
        for(List<String> line: record)
        {
            writeToFileType = line.get(line.size() - 1);
            System.out.println(writeToFileType);
            if(writeToFileType.equals("endOfContact"))
                fileType = DBFile.fileArray[0];
            else if(writeToFileType.equals("endOfAddress"))
                fileType = DBFile.fileArray[1];
            else if(writeToFileType.equals("endOfPhone"))
                fileType = DBFile.fileArray[2];

            DBFile.writeToFile(line.subList(0, line.size() - 1), fileType);
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

    public void printContactData() {
         record.stream().map(printString -> String.join(" ", printString).replaceAll(",", " ")).forEach(System.out::println);
    }
}
