package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;

import java.io.IOException;
import static text2json.JbsOntology.*;

/**
 * Created by omishali on 17/01/2017.
 */
public class ShmonaKvatzimParser extends Parser {
    private static final String BEGIN_KOVETZ = "begin_kovetz";
    private static final String BEGIN_SAIF = "begin_saif";

    private String kovetz;
    private String saif;
    private int kovetzNum = 0;
    private int saifNum = 0;

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_KOVETZ;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("[קובץ");
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_SAIF;
            }

            @Override
            public boolean match(Line line) {
                return line.contains("[עריכה]");
            }
        });

    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_KOVETZ:
                kovetzNum++;
                saifNum = 0;
                break;
            case BEGIN_SAIF:
                jsonObjectFlush();
                saifNum++;
                jsonObjectAdd(URI, getUri());

                break;
            case NO_MATCH:
                jsonObjectAdd(JBO_TEXT, line.getLine());
                break;
        }

    }

    @Override
    protected String getUri() {
        return JBR + "shmonakvatzim-" + kovetzNum + "-" + saifNum;
    }
}
