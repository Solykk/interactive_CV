import control.CVApplication;
import model.ENFromFile;
import model.RUFromFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Solyk on 28.04.2017.
 */
public class Main extends CVApplication{
    public static void main(String[] args) {

        String cv = getStringBuilder("CV_EN.txt");
        String cvInfo = getStringBuilder("CVInfo_EN.txt");
        String more = getStringBuilder("More_EN.txt");
        String oldJob = getStringBuilder("OldJob_EN.txt");
        String why = getStringBuilder("Why_EN.txt");

        new ENFromFile(cv, cvInfo, more, oldJob, why);

        String cvR = getStringBuilder("CV_RU.txt");
        String cvInfoR = getStringBuilder("CVInfo_RU.txt");
        String moreR = getStringBuilder("More_RU.txt");
        String oldJobR = getStringBuilder("OldJob_RU.txt");
        String whyR = getStringBuilder("Why_RU.txt");

        new RUFromFile(cvR, cvInfoR, moreR, oldJobR, whyR);

        CVApplication.launch(args);
    }

    private static String getStringBuilder(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = Main.class.getResourceAsStream(fileName);
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            int c;
            while ((c = br.read()) != -1) {
                stringBuilder.append((char) c);
            }
            return stringBuilder.toString();
        } catch (IOException e){
            return "Problems with file reading " + e.getMessage();
        }
    }
}
