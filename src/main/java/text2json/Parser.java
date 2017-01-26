package text2json;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by omishali on 12/12/2016.
 */
public abstract class Parser {
<<<<<<< HEAD
    private static final String TEXT_DIR = "/../../jbs-raw/";
    //private static final String JSON_DIR = "/json/";
=======
>>>>>>> 5af4bd5878a89455fba0ffd414b3a5bbcd09f62a
    protected static final String NO_MATCH = "no_match";

    private JsonObject jsonObject;
    protected JsonFile jsonFile;

    List<LineMatcher> matchers = new ArrayList<LineMatcher>();

    protected Parser() {
        registerMatchers();
    }
    /**
     * In this method you should create LineMatchers and
     * register them using registerMatcher.
     */
    protected abstract void registerMatchers();
    protected abstract void onLineMatch(String type, Line line) throws IOException;
    protected abstract String getUri();
    protected void onEOF() throws IOException {
        jsonObjectFlush(/*jsonFile, jsonObject*/);
    }
    protected void registerMatcher(LineMatcher matcher) {
        matchers.add(matcher);
    }

    public JsonFile parse(BufferedReader reader, String outputJson) throws IOException {
        //create json
        jsonFile = new JsonFile(outputJson);
        //jsonFile = new JsonFile("." + JSON_DIR + getId() + ".json");
        jsonObject = new JsonObject();
        //create json main object
        jsonFile.createMainObject();

        int lineNum = 0;
        String line;
        while((line = reader.readLine()) != null) {
            Line l = new Line(line);
            lineNum++;
            //System.out.println("lineNum = " + lineNum);
            if(l.getLine().equals("\n")){ continue;}
            if(l.getLine().equals("")){ continue;}

            //System.out.println("    line = " + l.getLine());
            // checks whether there is a match
            // and trigger onLineMatch appropriately.
            for (LineMatcher matcher : matchers) {
                if (matcher.match(l)) {
                    //System.out.println("  matched " + l.getLine());
                    //System.out.println("  matcherType = " + matcher.type());
                    onLineMatch(matcher.type(), l);
                    l.lineMatched();
                    continue;
                }
            }
            if (!l.isLineMatched()){
                onLineMatch(NO_MATCH, l);
            }
        }
        onEOF();
        //close json
        jsonFile.closeMainObject();
        return jsonFile;
    }

    /**
     * Adds key-value to the current json object.
     * @param key
     * @param value
     */
    public void jsonObjectAdd(/*JsonObject jsonObject,*/ String key, String value) {
        jsonObject.addObject(key, value);
    }

    public void jsonObjectAdd(/*JsonObject jsonObject,*/ String key, int value) {
        jsonObject.addObject(key, value);
    }

    /**
     * Writes the content of the current json object to JsonFile
     * and clears the contents of the current json object (creates a new one).
     */
    public void jsonObjectFlush(/*JsonFile jsonFile*/) throws IOException {
        jsonFile.write(jsonObject);
        jsonObject = new JsonObject();
    }

    /**
     * Appends the given value to the value of an existing key of the
     * current json object. If key does not exist, it is created.
     * @param key
     * @param value
     */
    public void jsonObjectAppend(/*JsonFile jsonFile*/ String key, String value) {
        jsonObject.append(key,value);
    }

    public void jsonObjectOpenObject(String objectKey){ jsonObject.openObject(objectKey);}
    public void jsonObjectOpenObject(){jsonObject.openObject();}
    public void jsonObjectCloseObject(){jsonObject.closeObject();}
    public void jsonObjectOpenArray(/*JsonFile jsonFile*/ String arrayKey){
        jsonObject.openArray(arrayKey);
    }
    public void jsonObjectOpenArray(/*JsonFile jsonFile*/){
        jsonObject.openArray();
    }
    public void jsonObjectCloseArray() {
        jsonObject.closeArray();
    }

    public String stripVowels(String rawString){
        String newString = "";
        for(int j=0; j<rawString.length(); j++) {
            if(rawString.codePointAt(j)<1425
                    || rawString.codePointAt(j)>1479)
            { newString = newString + rawString.charAt(j); }
        }
        return(newString);
    }

}
