package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;

import java.io.IOException;

import static text2json.JbsOntology.*;

/**
 * Created by USER on 23-Dec-16.
 */
public class MesilatYesharimParser extends Parser {
    private static final String BEGIN_HAKDAMA = "begin_hakdama";
    private static final String BEGIN_HATIMA = "begin_hatima";

    private int perekNum = 0;

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            public String type() { return BEGIN_HAKDAMA;}
            public boolean match(Line line) {
                return line.beginsWith("הקדמת הרב") && line.wordCount() <= 10;
            }
        });
        registerMatcher(new LineMatcher() {
            public String type() { return BEGIN_PEREK;}
            public boolean match(Line line) {
                return line.beginsWith("פרק") && line.wordCount() <= 10;
            }
        });
        registerMatcher(new LineMatcher() {
            public String type() { return BEGIN_HATIMA; }
            public boolean match(Line line) {
                return line.is("חתימה");
            }
        });
    }


    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_HAKDAMA:
                jsonObject().add(URI, getUri());
                jsonObject().add(RDFS_LABEL, line.getLine());
                jsonObject().addToArray(JBO_WITHIN, JBR + "mesilatyesharim");
                break;
            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                jsonObject().add(URI, getUri());
                if(perekNum <= 20) {
                    jsonObject().add(RDFS_LABEL, line.extract(" - ", " "));
                }
                else {
                    jsonObject().add(RDFS_LABEL, line.extract(" – ", " "));
                }
                jsonObject().addToArray(JBO_WITHIN, JBR + "mesilatyesharim");
                break;
            case BEGIN_HATIMA:
                jsonObjectFlush();
                perekNum++;
                jsonObject().add(URI, getUri());
                jsonObject().add(RDFS_LABEL, line.getLine());
                jsonObject().addToArray(JBO_WITHIN, JBR + "mesilatyesharim");
                break;
            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return JBR + "mesilatyesharim" + "-" + perekNum;
    }

}