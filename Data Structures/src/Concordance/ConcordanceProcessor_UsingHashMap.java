package Concordance;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

public class ConcordanceProcessor_UsingHashMap {
    public static boolean autoOpenOutput = true;
    private final MyHashMap myHashMap;
    private final String outputPath;

    public ConcordanceProcessor_UsingHashMap(File file, String _outputPath) throws IOException {
        long start = System.currentTimeMillis();

        //O(n) when n is the number of lines
        String[] lines = Files.lines(file.toPath()).toArray(String[]::new);

        /*
            Regex and Split operations - O(num of chars in line) per line -> O(num of chars in file) in total
            We check with regex and leave only letters and whitespaces, then split by whitespaces
         */

        String[][] words = new String[lines.length][];

        // O(n) when n is the number of words in the file
        Arrays.setAll(
                words,
                i -> lines[i]
                        .replaceAll("-", " ")
                        .replaceAll("[^\\p{Alpha}\\s]", "")
                        .trim()
                        .toLowerCase()
                        .split("[\\s]+")
        );

        myHashMap = new MyHashMap(words.length);

        // O(n) when n is the number of words in the file
        for (int l = 0; l < words.length; l++) {
            for (int w = 0; w < words[l].length; w++) {
                if (!words[l][w].isEmpty() && checkForOneLetterWords(words[l][w])) {
                    myHashMap.insert(words[l][w], l + 1);
                }
            }
        }
        System.out.println(
                "File Processed In " + (System.currentTimeMillis() - start) + "ms (" + lines.length + " lines)"
        );

        outputPath = _outputPath;
    }

    private boolean checkForOneLetterWords(String word) {
        return word.length() != 1 || "aioAIO".contains(word);
    }

    //O(n) when n is the number of words in the text file
    public void printToFile() throws IOException {
        printToFile(outputPath);
    }

    public void printToFile(String filePath) throws IOException {
        long start = System.currentTimeMillis();
        File outFile = new File(filePath);
        PrintWriter printWriter = new PrintWriter(outFile);
        printWriter.println(myHashMap);
        printWriter.close();
        System.out.println(
                "Output File Created In " + (System.currentTimeMillis() - start) + "ms, Open It At <" +
                        filePath + "> For The Results"
        );
        if (autoOpenOutput) Desktop.getDesktop().open(outFile);
    }

    /*
        // O(1)
     */
    public void search(String word) {
        long start = System.currentTimeMillis();
        System.out.print(myHashMap.search(word));
        System.out.println("Search Finished In " + (System.currentTimeMillis() - start) + "ms");
    }

    /*
        O(nlgn) when n is the number of words in the text file
        It's because the output is sorted alphabetically.
     */
    public String toString() {
        return myHashMap.toString();
    }

}
