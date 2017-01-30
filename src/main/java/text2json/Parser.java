package text2json;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by omishali on 12/12/2016.
 */
public abstract class Parser {
    protected static final String NO_MATCH = "no_match";

    private JsonObject defaultJsonObject;
    private JsonFile defaultJsonFile;

    List<LineMatcher> matchers = new ArrayList<LineMatcher>();

    protected JsonObject jsonObject() {
        return defaultJsonObject;
    }

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
        jsonObjectFlush(/*defaultJsonFile, defaultJsonObject*/);
    }
    protected void registerMatcher(LineMatcher matcher) {
        matchers.add(matcher);
    }

    public JsonFile parse(BufferedReader reader, String outputJson) throws IOException {
        //create json
        defaultJsonFile = new JsonFile(outputJson);
        //defaultJsonFile = new JsonFile("." + JSON_DIR + getId() + ".json");
        defaultJsonObject = new JsonObject();
        //create json main object
        defaultJsonFile.createMainObject();

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
        defaultJsonFile.closeMainObject();
        return defaultJsonFile;
    }

    /**
     * Writes the content of the current json object to JsonFile
     * and clears the contents of the current json object (creates a new one).
     */
    public void jsonObjectFlush() throws IOException {
        defaultJsonFile.write(defaultJsonObject);
        defaultJsonObject = new JsonObject();
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
