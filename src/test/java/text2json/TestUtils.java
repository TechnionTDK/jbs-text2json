package text2json;

import com.google.gson.Gson;

import java.io.*;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static text2json.JbsOntology.*;

/**
 * Created by omishali on 26/12/2016.
 */
public class TestUtils {
    private static final String TEXT_DIR = "/jbs-raw/";
    private static final String JSON_DIR = "json/";


    public static SubjectsJson setupParser(Parser parser, String bookId) {
        createOutputFolderIfNotExists(bookId);
        BufferedReader reader = null;
        try {
            reader = getText(bookId +"/" +bookId + ".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            parser.parse(reader, "json/" + bookId + "/" + bookId + ".json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return getJson("json/" + bookId + "/" + bookId + ".json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





    public static BufferedReader getText(String s) throws FileNotFoundException {
        return new BufferedReader(getFileReader(TEXT_DIR + s));
    }

    public static void createOutputFolderIfNotExists(String name) {
        File folder = new File(JSON_DIR + name);
        if (!folder.exists())
            folder.mkdir();
    }

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
        String filePath = new File("").getAbsolutePath();
        return new FileReader(filePath.concat(s));
    }


    static FileReader getJsonFileReader(String s) throws FileNotFoundException {
        return getFileReader(JSON_DIR + s);
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


    protected static void assertTextUriProperty(Map<String, String> object, String uriID) { assertEquals(JBR_TEXT + uriID, object.get(URI)); }
    protected static void assertBookProperty(Map<String, String> object, String bookId) { assertEquals(JBR_BOOK + bookId, object.get(JBO_BOOK)); }
    protected static void assertPositionProperty(Map<String, String> object, String position) { assertEquals(position, object.get(JBO_POSITION)); }
    protected static void assertLabelProperty(Map<String, String> object, String rdfs) {  assertEquals(rdfs, object.get(RDFS_LABEL)); }

}
