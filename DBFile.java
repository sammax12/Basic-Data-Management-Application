import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class DBFile {

    public final static File[] fileArray = new File[] {new File("./textFiles/Contact.txt"),
            new File("./textFiles/Address.txt"), new File("./textFiles/Phone.txt") };
    public final static Map<String, Integer> collumMap = Map.of("id", 0, "firstname", 1, "lastname", 2, "gender", 3,
            "address", 2, "city", 4, "state", 5, "country", 6, "phone", 2, "phone number", 3);

    //public static AtomicInteger uniquePrimaryKey = new AtomicInteger();

    private DBFile() {
    }

    // Write to the specifc file.
    public static void writeToFile(ArrayList<String> dataList, File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }

        PrintWriter pw = new PrintWriter(new FileWriter(file, true));

        for (String data : dataList)
            pw.write(data);

        pw.println();
        pw.flush();
        pw.close();
    }

    public static int getPrimaryKey(String primaryKeyType) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(new File("./textFiles/PrimaryKeyPersistence.txt")));
        String[] primaryKeyData= br.readLine().split(",");

        if(primaryKeyType.equals("Contact")){
            return Integer.parseInt(primaryKeyData[0]);
        } 
        else if(primaryKeyType.equals("Address")){
            return Integer.parseInt(primaryKeyData[1]);
        }
        else if (primaryKeyType.equals("Phone")){
            return Integer.parseInt(primaryKeyData[2]);
        }
        
        return -1;
    }

    // public static void setPrimaryKey(String primaryKeyType) throws IOException{);
    //     PrintWriter pw = new PrintWriter(new FileWriter(new File("./textFiles/PrimaryKeyPersistence.txt"));

    //     pw.write(s);

    // }

}