package sg.edu.nus.iss.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class App 
{
    public static void main( String[] args ) throws IOException
    {

        for (String a: args) {
            System.out.printf(">>>%s\n", a);
        }

        Path p = Paths.get(args[0]);
        File f = p.toFile();

        Map<String, Integer> wordFreq = new HashMap<>();

        //open a file as input stream
        InputStream is = new FileInputStream(f);

        //codes to read 
        InputStreamReader isr = new InputStreamReader(is);

        //read whole lines
        BufferedReader br = new BufferedReader(isr);

        String line;
        int numWords = 0;

        while((line = br.readLine()) != null) {
            System.out.printf("> %s\n", line.toUpperCase());
            String[] words = line.split(" ");
            numWords += words.length;
        

            for (String w: words) {
                String t = w.toLowerCase();
                if (!wordFreq.containsKey(t)) {
                    wordFreq.put(t, 1);
                    
                } else {
                    //if word is in map, increment the count
                    int c = wordFreq.get(t);
                    wordFreq.put(t, c + 1);
                }
            }
            
        }

        Set<String> words = wordFreq.keySet();
        for (String w: words) {
            int count = wordFreq.get(w);
            System.out.printf(": %s - %d\n", w, count);
        }

        System.out.printf("Number of words: %d\n", numWords);
        System.out.printf("Number of unique words: %d\n", words.size());


        //to create new file, write and save the above map values in the main working folder
        String saveLocation = p.getParent() + File.separator + "result.txt";
        File saveFile = new File(saveLocation);
        
        System.out.println(saveLocation);
                try{
                    Path path = p.getParent();
                    Files.createDirectory(path);
                }catch(FileAlreadyExistsException e){
                    System.err.println("File directory already exists");
                }
            saveFile.createNewFile();

        try {
            FileWriter writer = new FileWriter(saveFile);
            Set<Map.Entry<String, Integer>> entries = wordFreq.entrySet();

            for (Map.Entry<String, Integer> entry : entries) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                writer.write(key + ": " + value + "\n");
               
            } 
            writer.close();
        }
        catch (IOException e) {
                e.printStackTrace();
            }     
        
        // OutputStream os = new FileOutputStream(saveLocation);
    
        is.close();
        isr.close();
        br.close();
    }
}
