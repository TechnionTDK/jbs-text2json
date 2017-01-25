package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;
import static text2json.JbsOntology.*;
import java.io.IOException;

/**
 * Created by USER on 20-Jan-17.
 */
public class TalmudBavliParser extends Parser {
    private static final String BEGIN_PEREK = "begin_perek";
    private static final String BEGIN_DAF = "begin_daf";
    private static final String BEGIN_AMUD = "begin_amud";
    private static final String BEGIN_PERUSH = "begin_perush";
    private static final String BEGIN_DAF_TEXT = "begin_daf_text";
    
    private int masechetNum = 0;
    private String masechetTitle;
    private int perekNum = 0;
    private String perekTitle;
    private int dafNum = 1;
    private String dafTitle;
    private String dafText;
    private int positionInMasechet = 0;



    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_PEREK;
            }
            @Override
            public boolean match(Line line) {
                return line.beginsWith("פרק ") && line.wordCount() <= 7
                        && line.contains(" - ");
            }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_DAF;
            }
            @Override
            public boolean match(Line line) {
                return line.beginsWith("דף ") && line.wordCount() <= 5
                        && line.contains(" - ");
            }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_PERUSH;
            }
            @Override
            public boolean match(Line line) {
                return line.beginsWith("רש\"י");
            }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_DAF_TEXT;
            }
            @Override
            public boolean match(Line line) {
                return !line.extract("{", "}").isEmpty();
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type){
            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                perekTitle = line.extract(" - ", " ");
                break;
            case BEGIN_DAF:
                jsonObjectFlush();
                dafNum++;
                positionInMasechet++;
                dafTitle = line.extract("דף ", " ");
                break;
            case BEGIN_DAF_TEXT:
                dafText = line.extract(" ", " ");
                break;
            case BEGIN_PERUSH:
                jsonObjectFlush();
                break;
        }
    }

    @Override
    protected String getUri() {
        return null;
    }
}
