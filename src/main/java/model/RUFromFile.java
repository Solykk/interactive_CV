package model;

import java.io.IOException;

/**
 * Created by Solyk on 04.05.2017.
 */
public class RUFromFile {

    static String cv = getTextFromFile("CV_RU.txt");
    static String cvInfo = getTextFromFile("CVInfo_RU.txt");
    static String more = getTextFromFile("More_RU.txt");
    static String oldJob = getTextFromFile("OldJob_RU.txt");
    static String why = getTextFromFile("Why_RU.txt");

    private static String getTextFromFile(String url) {
        try {
            return ENFromFile.getString(url);
        } catch (IOException e) {
            return "Проблемы с загрузкой файла " + e.getMessage();
        }
    }
}
