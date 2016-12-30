package text2json;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by omishali on 26/12/2016.
 */
public class TestUtils {
    public static BufferedReader getText(String s) throws FileNotFoundException {
        return new BufferedReader(new FileReader(s));
    }

    public static void toJson(JsonFile output) {
    }
}
