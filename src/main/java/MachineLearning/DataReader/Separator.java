package MachineLearning.DataReader;
import java.io.File;

public class Separator {
    // Metodo para hacer el codigo independiente de la plataforma en que se ejecute.
    public static String pathConverter(String path){
        return path.replace("/" , System.getProperty("file.separator"));
    }
}
