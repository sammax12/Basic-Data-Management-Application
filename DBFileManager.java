import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class DBFileManager {

    public final static File[] fileArray = new File[] {new File("./textFiles/Contact.txt"),
            new File("./textFiles/Address.txt"), new File("./textFiles/Phone.txt") };
    public final static Map<String, Integer> collumMap = Map.of("id", 0, "firstname", 1, "lastname", 2, "gender", 3,
            "address", 2, "city", 4, "state", 5, "country", 6, "phone", 2, "phone number", 3);

    private DBFileManager() {
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

}