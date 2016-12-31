package text2json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 23-Dec-16.
 */
public class MesilatYesharimParser extends Parser {
    private static final String BEGIN_HAKDAMA = "begin_hakdama";
    private static final String BEGIN_PEREK = "begin_perek";
    private static final String BEGIN_HATIMA = "begin_hatima";
    private static final String MESILAT_YESHARIM_PARSER_ID = "parser.mesilatYesharim";

    private int perekNum = 0;

    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            public String type() { return BEGIN_HAKDAMA;}
            public boolean match(Line line) {
                //System.out.println("In BEGIN_HAKDAMA\nline is: " + line.getLine() + " return value is: " + line.beginsWith("הקדמת הרב"));
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
                jsonObjectAdd("uri", getUri());
                jsonObjectAdd("title", line.getLine());
                jsonObjectAdd("sefer", "mesilatyesharim");
                jsonObjectAdd("text", "");
                break;
            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                jsonObjectAdd("uri", getUri());
                if(perekNum <= 20) {
                    jsonObjectAdd("title", line.extract(" - ", " "));
                }
                else {
                    jsonObjectAdd("title", line.extract(" – ", " "));
                }
                jsonObjectAdd("sefer", "mesilatyesharim");
                jsonObjectAdd("text", "");
                break;
            case BEGIN_HATIMA:
                jsonObjectFlush();
                perekNum++;
                jsonObjectAdd("uri", getUri());
                jsonObjectAdd("title", line.getLine());
                jsonObjectAdd("sefer", "mesilatyesharim");
                jsonObjectAdd("text", "");
            case NO_MATCH:
                if(line.getLine() != "\n"){
                    super.jsonObjectAppend("text", line.getLine());
                }
                break;
        }
    }

    @Override
    protected String getUri() {
        return "mesilatyesharim" + "-" + perekNum;
    }

    @Override
    public String getId() {
        return MESILAT_YESHARIM_PARSER_ID;
    }

}
