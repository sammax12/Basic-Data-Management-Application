import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class DBFile {

    public final static File[] fileArray = new File[] { new File("./textFiles/Contact.txt"),
            new File("./textFiles/Address.txt"), new File("./textFiles/Phone.txt") };
    public final static Map<String, Integer> collumMap = Map.of("id", 0, "firstname", 1, "lastname", 2, "gender", 3,
            "address", 2, "city", 4, "state", 5, "country", 6, "phone", 2, "phone number", 3);

    private DBFile() {
    }

    // Write to the specifc file.
    public static void writeToFile(List<String> dataList, File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }

        PrintWriter pw = new PrintWriter(new FileWriter(file, true));

        for (String data : dataList)
            pw.write(data + ",");

        pw.println();
        pw.flush();
        pw.close();
    }

    public static int getPrimaryKey(String primaryKeyType) throws IOException {
        File file = new File("./textFiles/PrimaryKeyPersistence.txt");
        BufferedReader br;
        String[] primaryKeyData;

        if (!file.exists()) {
            file.createNewFile();
            setPrimaryKey("allPrimaryKeys");
        }
        br = new BufferedReader(new FileReader(file));

        primaryKeyData = br.readLine().split(",");
        br.close();

        if (primaryKeyType.equals("Contact")) {
            return Integer.parseInt(primaryKeyData[0]);
        } else if (primaryKeyType.equals("Address")) {
            return Integer.parseInt(primaryKeyData[1]);
        } else if (primaryKeyType.equals("Phone")) {
            return Integer.parseInt(primaryKeyData[2]);
        } else if (primaryKeyType.equals("AdressTable")) {
            return Integer.parseInt(primaryKeyData[3]);
        } else if (primaryKeyType.equals("PhoneTable")) {
            return Integer.parseInt(primaryKeyData[4]);
        }

        return -1;
    }

    public static void setPrimaryKey(String primaryKeyType) throws IOException {
        File file = new File("./textFiles/PrimaryKeyPersistence.txt");

            File tempFile, orgFilePath;
            BufferedReader br;
            PrintWriter pw;
            String[] primaryKeyData;

            orgFilePath = file;
            tempFile = new File("./textFiles/tempFile");
            pw = new PrintWriter(new FileWriter(tempFile, true));

            if (primaryKeyType.equals("allPrimaryKeys")) {
                String[] input = { "0", "0", "0", "0", "0" };
                primaryKeyData = input.clone();
            } else {
                br = new BufferedReader(new FileReader(file));
                primaryKeyData = br.readLine().split(",");
                br.close();

                if (primaryKeyType.equals("Contact")) {
                    primaryKeyData[0] = Integer.toString(getPrimaryKey("Contact") + 1);
                } else if (primaryKeyType.equals("Address")) {
                    primaryKeyData[1] = Integer.toString(getPrimaryKey("Address") + 1);
                } else if (primaryKeyType.equals("Phone")) {
                    primaryKeyData[2] = Integer.toString(getPrimaryKey("Phone") + 1);
                }
            }

            pw.write(String.join(",", primaryKeyData));
            pw.println();

            if (!file.delete())
                System.out.println("Unsuccsfull Delete");

            if (!tempFile.renameTo(orgFilePath))
                System.out.println("Unsuccsfull");

            pw.flush();
            pw.close();
        }

    public static void cheackIFPrimaryKeyFileExists() throws IOException
    {
        File file = new File("./textFiles/PrimaryKeyPersistence.txt");

        if (!file.exists()) {
            file.createNewFile();
            setPrimaryKey("allPrimaryKeys");
        } 
    }
}