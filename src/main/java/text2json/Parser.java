package text2json;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by omishali on 12/12/2016.
 */
public abstract class Parser {
    List<LineMatcher> matchers = new ArrayList<LineMatcher>();

    protected Parser() {
        registerMatchers();
    }
    /**
     * In this method you should create LineMatchers and
     * register them using registerMatcher.
     */
    protected abstract void registerMatchers();
    protected abstract void onLineMatch(String type, Line line);
    public abstract String getId();

    /**
     * Reads the PARSER_CONFIG_FILE and parses all input
     * texts into output directory.
     */
    public void parse() {
        // TODO
    }
    protected void registerMatcher(LineMatcher matcher) {
        matchers.add(matcher);
    }


    // TODO
    // should be changed to return an object representing the
    // output json file.
    protected ParserOutput parse(BufferedReader reader) throws IOException {
        String line;
        while((line = reader.readLine()) != null) {
            Line l = new Line(line);
            // should check whether there is a match
            // and trigger onLineMatch appropriately.
            for (LineMatcher matcher : matchers) {
                if (matcher.match(l)) onLineMatch(matcher.type(), l);
            }
        }
        return null;
    }
}
