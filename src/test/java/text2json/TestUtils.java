package text2json;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by omishali on 26/12/2016.
 */
public class TestUtils {
    private static final String TEXT_DIR = "/../../text/";
    private static final String JSON_DIR = "/json/";

    public static BufferedReader getText(String s) throws FileNotFoundException {
        return new BufferedReader(getFileReader(TEXT_DIR + s));
    }

    static FileReader getFileReader(String s) throws FileNotFoundException {
        String filePath = new File("").getAbsolutePath();
        return new FileReader(filePath.concat(s));
    }

    static SubjectsJson getJson(String json) throws Exception {
        Gson gson = new Gson();
        return gson.fromJson(new BufferedReader(new FileReader(json)), SubjectsJson.class);
    }

    public static String stripVowels(String rawString){
        String newString = "";
        for(int j=0; j<rawString.length(); j++) {
            if(rawString.codePointAt(j)<1425 || rawString.codePointAt(j)>1479)
                { newString = newString + rawString.charAt(j); }
        }
        return(newString);
    }

}
