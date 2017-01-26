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
    private static final String TEXT_DIR = "/jbs-raw/";
    private static final String JSON_DIR = "/json/";

    public static BufferedReader getText(String s) throws FileNotFoundException {
        return new BufferedReader(getFileReader(TEXT_DIR + s));
    }

<<<<<<< HEAD
    public static int countMtches(String str, String findStr){
        int lastIndex = 0;
        int count = 0;
        while(lastIndex != -1){

            lastIndex = str.indexOf(findStr,lastIndex);

            if(lastIndex != -1){
                count ++;
                lastIndex += findStr.length();
            }
        }
        return count;
    }

    static FileReader getFileReader(String s) throws FileNotFoundException {
=======
    public static FileReader getFileReader(String s) throws FileNotFoundException {
>>>>>>> 5af4bd5878a89455fba0ffd414b3a5bbcd09f62a
        String filePath = new File("").getAbsolutePath();
        return new FileReader(filePath.concat(s));
    }

<<<<<<< HEAD
    static FileReader getJsonFileReader(String s) throws FileNotFoundException {
        return getFileReader(JSON_DIR + s);
    }

    static SubjectsJson getJson(String json) throws Exception {
=======
    public static SubjectsJson getJson(String json) throws Exception {
>>>>>>> 5af4bd5878a89455fba0ffd414b3a5bbcd09f62a
        Gson gson = new Gson();
        return gson.fromJson(new BufferedReader(new FileReader(json)), SubjectsJson.class);
    }

<<<<<<< HEAD
=======
    public static String stripVowels(String rawString){
        String newString = "";
        for(int j=0; j<rawString.length(); j++) {
            if(rawString.codePointAt(j)<1425
                    || rawString.codePointAt(j)>1479)
            { newString = newString + rawString.charAt(j); }
        }
        return(newString);
    }
>>>>>>> 5af4bd5878a89455fba0ffd414b3a5bbcd09f62a
}
