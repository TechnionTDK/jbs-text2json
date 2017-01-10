package text2json;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;


/**
 * Created by omishali on 12/12/2016.
 */
public abstract class Parser {
    private static final String TEXT_DIR = "/../../text/";
    private static final String JSON_DIR = "/json/";
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
    public abstract String getId();
    protected void registerMatcher(LineMatcher matcher) {
        matchers.add(matcher);
    }
    /**
     * Reads the PARSER_CONFIG_FILE and parses all input
     * texts into output directory.
     */
    public void parse() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException,
                                NoSuchMethodException, InvocationTargetException {
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(new FileReader("../resources/configParser"));
        ConfigFile configFile = gson.fromJson(br, ConfigFile.class);

        List<ConfigParser> Parsers = configFile.getAllParsers();

        for (ConfigParser configParser : Parsers){
            BufferedReader raw = new BufferedReader(new FileReader(configFile.getInputBaseDir() + '/' + configParser.getInput()));

            //get class from string
            Class parserType = Class.forName(configParser.getParser());
            //create new object of that class
            Object parser = parserType.newInstance();
            //get object function
            Method parse = parserType.getMethod("parse", BufferedReader.class);
            //invoke function
            JsonFile json = (JsonFile) parse.invoke(parser, raw, configFile.getOutputBaseDir() + '/' + configParser.getOutput());
        }
    }

    protected JsonFile parse(BufferedReader reader) throws IOException {
        //create json
        jsonFile = new JsonFile("." + JSON_DIR + getId() + ".json");
        jsonObject = new JsonObject();
        //create json main object
        jsonFile.createMainObject();

        int lineNum = 0;
        String line;
        while((line = reader.readLine()) != null) {
            Line l = new Line(line);
            lineNum++;
            System.out.println("lineNum = " + lineNum);
            if(l.getLine() == "\n"){ continue;}
            // checks whether there is a match
            // and trigger onLineMatch appropriately.
            for (LineMatcher matcher : matchers) {
                if (matcher.match(l)) {
                    //System.out.print("  matched " + l.getLine() + "\n");
                    //System.out.print("  matcherType = " + matcher.type() + "\n");
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

    /**
     * Writes the content of the current json object to JsonFile
     * and clears the contents of the current json object (creates a new one).
     */
    public void jsonObjectFlush(/*JsonFile jsonFile, JsonObject jsonObject*/) throws IOException {
        jsonFile.write(jsonObject);
        jsonObject = new JsonObject();
    }

    /**
     * Appends the given value to the value of an existing key of the
     * current json object. If key does not exist, it is created.
     * @param key
     * @param value
     */
    public void jsonObjectAppend(/*JsonObject jsonObject,*/ String key, String value) {
        jsonObject.append(key,value);
    }

    public void jsonObjectAddArray(/*JsonObject jsonObject,*/ String arrayKey){
        jsonObject.addArray(arrayKey);
    }

    public void jsonObjectAddObjectToArray(/*JsonObject jsonObject,*/){jsonObject.addObjectToArray();}

    public void jsonObjectAddToArrayObject(/*JsonObject jsonObject,*/ String key, String value){
        jsonObject.addToArrayObject(key, value);
    }
}
