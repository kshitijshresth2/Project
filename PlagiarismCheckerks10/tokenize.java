import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class tokenize {
    public static void main(String[] args) {
        
        String[] fileNames = {
            "/home/ks10/Documents/test1.txt",
            "/home/ks10/Documents/test2.txt",
            "/home/ks10/Documents/test3.txt",
            "/home/ks10/Documents/test4.txt",
            "/home/ks10/Documents/test5.txt"
        };
        
        
        
        String outputFileName = "Documents/tokenized.html";
        
      
        File outputFile = new File(outputFileName);
        File outputDirectory = outputFile.getParentFile();
        if (outputDirectory != null && !outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }
        
        try {
        
            FileWriter writer = new FileWriter(outputFile);
        
            writer.write("<html>\n<head>\n<title>Tokenized Words</title>\n</head>\n<body>\n"); //html header
            
            
            for (String fileName : fileNames) {
                
                File file = new File(fileName); //create file
                
           
                Scanner scanner = new Scanner(file); //read
                
              
                Map<String, Integer> wordCount = new HashMap<>();
                
                
                while (scanner.hasNext()) {
                    String word = scanner.next().toLowerCase().replaceAll("[^a-zA-Z]", "");
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
                
       
                scanner.close();
                
                
                writer.write("<table>\n");
                writer.write("<tr><th colspan=\"2\">" + fileName + "</th></tr>\n");
                for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
                    String word = entry.getKey();
                    int count = entry.getValue();
                    writer.write("<tr><td>" + word + "</td><td>" + count + "</td></tr>\n");
                }
                writer.write("</table>\n");
            }
            
        
            writer.write("</body>\n</html>");
            
      
            writer.close();
            
            System.out.println("Successfully tokenized words and wrote to " + outputFileName);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
