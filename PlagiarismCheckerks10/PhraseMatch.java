import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PhraseMatch {
    public static void main(String[] args) {
        int phraseLength = 1; 


        List<Path> filePaths = new ArrayList<>();
        filePaths.add(Paths.get("/home/ks10/Documents/test1.txt"));
        filePaths.add(Paths.get("/home/ks10/Documents/test2.txt"));
        filePaths.add(Paths.get("/home/ks10/Documents/test3.txt"));
        filePaths.add(Paths.get("/home/ks10/Documents/test4.txt"));
        filePaths.add(Paths.get("/home/ks10/Documents/test5.txt"));

        List<List<String>> filePhraseList = new ArrayList<>();

     
        for (Path filePath : filePaths) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
                List<String> phrases = new ArrayList<>();
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String[] words = sb.toString().split("\\W+");
                for (int i = 0; i < words.length - phraseLength + 1; i++) {
                    StringBuilder phraseBuilder = new StringBuilder();
                    for (int j = 0; j < phraseLength; j++) {
                        phraseBuilder.append(words[i + j]).append(" ");
                    }
                    phrases.add(phraseBuilder.toString().trim());
                }
                filePhraseList.add(phrases);
            } catch (IOException e) {
                System.err.format("IOException: %s%n", e);
            }
        }

       
        Path outputFile = Paths.get("/home/ks10/Documents/results.html");
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile.toFile()))) {
          
            writer.println("<html><head><title>Phrase Match Results</title></head><body>");
            writer.println("<table border=\"1\"><tr><th>Phrase</th><th>File</th><th>Match Percentage</th></tr>");

     
            for (int i = 0; i < filePhraseList.size(); i++) {
                List<String> phrases1 = filePhraseList.get(i);
                for (int j = i + 1; j < filePhraseList.size(); j++) {
                    List<String> phrases2 = filePhraseList.get(j);

                 
                    int matchCount = 0;
                    for (String phrase : phrases1) {
                        if (phrases2.contains(phrase)) {
                            matchCount++;
                        }
                    }
                    String phrase = "Phrase " + (i + 1) + "-" + (j + 1);
                    double matchPercentage = (double) matchCount / phrases1.size() * 100;


                
                    writer.printf("<tr><td>%s</td><td>%s, %s</td><td>%.2f%%</td></tr>\n", phrase,
                            filePaths.get(i).getFileName(), filePaths.get(j).getFileName(), matchPercentage);
                }
            }

          
            writer.println("</table></body></html>");
            System.out.println("Results written to " + outputFile);
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}
