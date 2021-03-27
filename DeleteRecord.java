import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class DeleteRecord {

    private String serchContact, reference, foreignKey;

    public DeleteRecord(boolean deleteEditRecord) throws IOException {
       if(!deleteEditRecord)
            deleteRecordHelper();
    }

    private void deleteRecordHelper() throws IOException {
        Scanner scan = new Scanner(System.in);

        System.out.println("Serach By Firstname or Lastname");
        reference = scan.nextLine().toLowerCase();

        System.out.println("Whats the Contacts + " + reference + " you'll like to delete? (CASE SENSITIVE)");
        serchContact = scan.nextLine();

        foreignKey = getForeignKey();

        deleteRecord();
    }

    public void deleteRecord() throws IOException {
        BufferedReader br;
        PrintWriter pw;
        File tempFile, orgFilePath;
        String line;

        for (File file : DBFile.fileArray) {
            orgFilePath = file;
            tempFile = new File("./textFiles/tempFile");
            pw = new PrintWriter(tempFile);
            br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if (!line.contains(foreignKey)) {
                    pw.write(line);
                    pw.println();
                }
            }

            if (!file.delete())
                System.out.println("Unsuccsfull Delete");

            if (!tempFile.renameTo(orgFilePath))
                System.out.println("Unsuccsfull");

            System.out.println(tempFile.getPath());

            pw.flush();
            pw.close();
            br.close();
        }
    }

    private String getForeignKey() throws IOException {
        String line;
        String[] lineResult;
        BufferedReader br = new BufferedReader(new FileReader(DBFile.fileArray[0]));

        while ((line = br.readLine()) != null) {
            lineResult = line.trim().split("[,]");

            System.out.println(Arrays.toString(lineResult));

            if (lineResult[DBFile.collumMap.get(reference)].trim().equals(serchContact))
                return lineResult[0].trim();
        }
        br.close();
        return null;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }
}
