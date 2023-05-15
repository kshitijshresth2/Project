import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class lookup {

    public static void main(String[] args) throws IOException {
       
        String[] fileNames = {
                "/home/ks10/Documents/test1.txt",
                "/home/ks10/Documents/test2.txt",
                "/home/ks10/Documents/test3.txt",
                "/home/ks10/Documents/test4.txt",
                "/home/ks10/Documents/test5.txt"
        };

        // read each file and store the words in a map
        Map<String, Integer>[] wordCounters = new HashMap[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            wordCounters[i] = new HashMap<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileNames[i]))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        wordCounters[i].merge(word, 1, Integer::sum);
                    }
                }
            }
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a word to lookup:");
        String wordToLookup = scanner.nextLine();

      
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("lookup.html"))) {
            writer.write("<html><body><table><tr><th>File</th><th>Frequency</th><th>Highlighted Text</th></tr>");
            for (int i = 0; i < fileNames.length; i++) {
                int frequency = wordCounters[i].getOrDefault(wordToLookup, 0);
                String highlightedText = getHighlightedText(fileNames[i], wordToLookup);
                writer.write(String.format("<tr><td>%s</td><td>%d</td><td>%s</td></tr>", fileNames[i], frequency, highlightedText));
            }
            writer.write("</table></body></html>");
        }
    }

    
    private static String getHighlightedText(String fileName, String word) throws IOException {
        StringBuilder x = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String w : words) {
                    if (w.equals(word)) {
                        x.append("<mark>").append(w).append("</mark>").append(" ");
                    } else {
                        x.append(w).append(" ");
                    }
                }
                x.append("<br>");
            }
        }
        return x.toString();
    }
}
