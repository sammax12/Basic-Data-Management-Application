import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DeleteRecord {

    private String serchContact, reference, contactPrimaryKey;

    public DeleteRecord(boolean deleteEditRecord) throws IOException {
        if (!deleteEditRecord) {
            deleteRecordHelper();
            System.out.println("Delete Contact successful");
        }
    }

    // Get user inputs about what contact to delete.
    private void deleteRecordHelper() throws IOException {
        Scanner scan = new Scanner(System.in);

        System.out.println("Serach ID, Firstname, or Lastname");
        reference = scan.nextLine().toLowerCase();

        System.out.println(reference);
        System.out.println(checkReference());
        if (checkReference()) {
            System.out.println("\nReference Input is not ID, Firstname, or Lastname. Exited Delete Operation");
            return;
        }

        System.out.println("Whats the Contacts " + reference + " you'll like to delete? (CASE SENSITIVE)");
        serchContact = scan.nextLine();

        contactPrimaryKey = getPrimaryKey();

        deleteRecord();
    }

    // Deleteing user inputed contact
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
                if (!line.contains(contactPrimaryKey)) {
                    pw.write(line);
                    pw.println();
                }
            }

            if (!file.delete())
                System.out.println("Unsuccsfull Delete");

            if (!tempFile.renameTo(orgFilePath))
                System.out.println("Unsuccsfull");

            pw.flush();
            pw.close();
            br.close();
        }
    }

    // Get primary key for the contact being deleted
    private String getPrimaryKey() {
        String line;
        String[] lineResult;

        try {

            BufferedReader br = new BufferedReader(new FileReader(DBFile.fileArray[0]));
            while ((line = br.readLine()) != null) {
                lineResult = line.trim().split("[,]");

                if (lineResult[DBFile.collumMap.get(reference)].trim().equals(serchContact))
                    return lineResult[0].trim();
            }

            br.close();

        } catch (IOException e) {
            System.err.println(e.getStackTrace());
        }

        return null;
    }

    public void setContactPrimaryKey(String contactPrimaryKey) {
        this.contactPrimaryKey = contactPrimaryKey;
    }

    private boolean checkReference() {
        return (!(reference.equals("id")) || (!(reference.equals("firstname"))) || (!(reference.equals("lastname"))));
    }
}
