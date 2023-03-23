package utilities;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class leaderboardManager {
    static File currentDir = new File("textFiles/");
    public final static String path = currentDir.getAbsolutePath()+"\\";
    private static final String FILENAME = "leaderboard.txt";
    public static void addToFile(String content) {
        System.out.println(path + FILENAME);
        try (FileWriter fw = new FileWriter(path + FILENAME, true)) {
            fw.write(content);
            fw.write(System.lineSeparator());
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String[] getLines() {
        List<Integer> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path + FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(Integer.parseInt(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(lines);
        String[] result = new String[lines.size()];


        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.toString(lines.get(lines.size()-1-i));
        }

        return result;
    }

}
