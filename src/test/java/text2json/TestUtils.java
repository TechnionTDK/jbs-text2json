package text2json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by omishali on 26/12/2016.
 */
public class TestUtils {
    public static BufferedReader getBufferedReader(String s) throws FileNotFoundException {
        return new BufferedReader(getFileReader(s));
    }

    public static FileReader getFileReader(String s) throws FileNotFoundException {
        String filePath = new File("").getAbsolutePath();
        return new FileReader(filePath.concat(s));
    }


    public static void toJson(JsonFile output) {
    }
}
