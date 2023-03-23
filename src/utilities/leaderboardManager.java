package utilities;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class leaderboardManager {
    private static final String FILENAME = "leaderboard.txt";
    public static void addToFile(String content) {
        try (FileWriter fw = new FileWriter(FILENAME, true)) {
            fw.write(content);
            fw.write(System.lineSeparator());
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String[] getLines() {
        List<Integer> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
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
