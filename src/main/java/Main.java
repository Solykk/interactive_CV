import control.CVApplication;
import model.ENFromFile;
import model.RUFromFile;

/**
 * Created by Solyk on 28.04.2017.
 */
public class Main extends CVApplication{
    public static void main(String[] args) {
        new ENFromFile();
        new RUFromFile();
        CVApplication.launch(args);
    }
}
