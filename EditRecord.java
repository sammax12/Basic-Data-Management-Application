import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class EditRecord {

    ArrayList<String> lol = new ArrayList<String>();

    public EditRecord() throws IOException {
        editLine();
    }

    private void editLine() throws IOException {
        String replace, toReplace;
        Scanner scan = new Scanner(System.in);
        System.out.println("What are you change?");
        replace = scan.nextLine();
        System.out.println("What are you change " + replace + " too?");
        toReplace = scan.nextLine();

        BufferedReader br;
        String line;
        String newText;
        String replacedText;
        String lineResult;

        // br.readLine(); // Skips First Line in file

        for (File file : DBFileManager.fileArray) {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                lineResult = line.trim();
                //System.out.println(lineResult);

                if (lineResult.contains(replace)) {
                    //lineResult = lineResult.replaceAll(replace, toReplace);
                    System.out.println("TRUE\n" + lineResult);
                    lol.add(lineResult);
                }

                // lol = new ArrayList<>(Arrays.asList(lineResult.split("[,]")));
                // DBFileManager.writeToFile(lol, DBFileManager.fileArray[1]);
            }
            System.out.println("\n");
            br.close();
        }
        System.out.println(this);
    }


    @Override
    public String toString() {
        return String.join(" ", lol).replaceAll(",", " ");
    }
}
