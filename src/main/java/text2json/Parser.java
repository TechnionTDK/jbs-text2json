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
    protected static final String NO_MATCH = "no_match";
    private JsonObject jsonObject;

    protected ParserOutput jsonFile;

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
        jsonObject.write();
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
        // TODO
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
            ParserOutput json = (ParserOutput) parse.invoke(parser, raw, configFile.getOutputBaseDir() + '/' + configParser.getOutput());


        }
    }

    // TODO
    // should be changed to return an object representing the
    // output json file.
    protected ParserOutput parse(BufferedReader reader) throws IOException {
        //create json
        jsonFile = new ParserOutput(fileName);
        //create json main object
        jsonFile.createMainObject();


        String line;
        while((line = reader.readLine()) != null) {
            Line l = new Line(line);
            // checks whether there is a match
            // and trigger onLineMatch appropriately.
            for (LineMatcher matcher : matchers) {
                if (matcher.match(l)) {
                    onLineMatch(matcher.type(), l);
                    l.lineMatched();
                }
            }
            if (!l.isLineMatched()){ onNoMatch(l);}
        }
        onEOF();
        //close json
        jsonFile.closeMainObject();


        return jsonFile;
    }

    protected void addJsonSubject(List<JsonObject> subject) throws IOException {
        jsonFile.addJsonSubject(subject);
    }

    /**
     * Adds key-value to the current json object.
     * @param key
     * @param value
     */
    public void jsonObjectAdd(String key, String value) {
    }

    /**
     * Writes the content of the current json object to ParserOutput
     * and clears the contents of the current json object (creates a new one).
     */
    public void jsonObjectFlush() {
    }

    /**
     * Appends the given value to the value of an existing key of the
     * current json object. If key does not exist, it is created.
     * @param key
     * @param value
     */
    public void jsonObjectAppend(String key, String value) {
    }
}
