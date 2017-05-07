package model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Solyk on 04.05.2017.
 */
class ENFromFile {

    static String cv = getTextFromFile("CV_EN.txt");
    static String cvInfo = getTextFromFile("CVInfo_EN.txt");
    static String more = getTextFromFile("More_EN.txt");
    static String oldJob = getTextFromFile("OldJob_EN.txt");
    static String why = getTextFromFile("Why_EN.txt");

    private static String getTextFromFile(String url) {
        try {
            return getString(url);
        } catch (IOException e) {
            return "Problems with file reading " + e.getMessage();
        }
    }

     static String getString(String url) throws IOException {
         File file = new File(url);
         try (FileReader fis = new FileReader("src/main/resources/" + file)) {
             int c;
             StringBuilder stringBuilder = new StringBuilder();
             while((c = fis.read()) != -1){
                 stringBuilder.append((char) c);
             }
             return stringBuilder.toString();
        }
    }
}
