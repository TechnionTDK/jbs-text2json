package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.*;
import static text2json.JbsUtils.*;

/**
 * Created by omishali on 17/01/2017.
 */
public class ShemonaKevatzimParser extends JbsParser {
    private static final String BEGIN_KOVETZ = "begin_kovetz";
    private static final String BEGIN_SAIF = "begin_saif";
    
    private int kovetzNum = 0;
    private int saifNum = 0;
    private int position = 1;

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
                addTextUri(getUri());
                addLabel("שמונה קבצים " + numberToHebrew(kovetzNum) + " " + numberToHebrew(saifNum));
                addBook(getBookId());
                addPosition(position++);
                break;
            case NO_MATCH:
                appendText( line.getLine());
                break;
        }

    }

    @Override
    protected String getUri() {
        return getBookId()+"-" + kovetzNum + "-" + saifNum;
    }

    @Override
    protected String getBookId() {
        return "shemonakevatzim";
    }
}
