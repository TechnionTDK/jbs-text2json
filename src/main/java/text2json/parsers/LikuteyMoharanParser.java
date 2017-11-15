package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.*;

/**
 * Created by USER on 16-Mar-17.
 */
public class LikuteyMoharanParser extends JbsParser{
    protected static final String BEGIN_CHELEK = "begin_chelek";
    protected static final String BEGIN_TEXT = "begin_text";

    private int chelekNum = 0;
    private int saifNum = 0;
    private int positionInSefer = 0;
    private String chelekHebIdx;
    private String saifTitle;
    private String saifHebIdx;

    public LikuteyMoharanParser() { createPackagesJson(); }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_SEFER;
            }
            @Override
            public boolean match(Line line) {
                return line.beginsWith("ליקוטי מוהר''ן") && line.wordCount() == 2;
            }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_CHELEK;
            }
            @Override
            public boolean match(Line line) {
                return line.beginsWith("ליקוטי מוהר''ן - חלק ") && line.wordCount() == 5;
            }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_SAIF;
            }
            @Override
            public boolean match(Line line) {
                if (!line.contains(" -")) return false;
                String hebIdx = line.extract(" ", " - ");
                return !hebIdx.contains(".") && hebIdx.length() <= 3 && line.wordCount() <= 15;
            }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_TEXT;
            }
            @Override
            public boolean match(Line line) {
                return false;
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch (type) {
            case BEGIN_SEFER:
                break;

            case BEGIN_CHELEK:
                jsonObjectFlush();
                saifNum = 0;
                chelekNum++;
                chelekHebIdx = line.extract("ליקוטי מוהר''ן - חלק ", " ");
                packagesJsonObject().add(URI, JBR_SECTION + getBookId()+"-" + chelekNum);
                packagesJsonObject().add(RDFS_LABEL, "ליקוטי מוהר''ן - חלק " + chelekHebIdx);
                packagesJsonObject().add(JBO_POSITION, chelekNum);
                packagesJsonObject().add(JBO_BOOK, JBR_BOOK + getBookId());
                packagesJsonObjectFlush();
                break;

            case BEGIN_SAIF:
                jsonObjectFlush();
                saifNum++;
                if ((chelekNum == 1 && saifNum == 215) || (chelekNum == 2 && saifNum == 102)) saifNum++;
                if (chelekNum == 1 && saifNum == 270) saifNum+=2;
                positionInSefer++;
                saifHebIdx = line.getFirstWord();
                saifTitle = line.extract(" - ", " ");
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_BOOK, JBR_BOOK + getBookId());
                jsonObject().addToArray(JBO_WITHIN, JBR_SECTION + getBookId()+"-" + chelekNum);
                jsonObject().add(JBO_POSITION, positionInSefer);
                jsonObject().add(RDFS_LABEL, "ליקוטי מוהר''ן " + chelekHebIdx + " " + saifHebIdx);
                jsonObject().add(JBO_NAME, saifTitle);
                break;

            case BEGIN_TEXT:
                appendText( line.getLine());
                break;

            case NO_MATCH:
                onLineMatch(BEGIN_TEXT, line);
                break;
        }
    }

    @Override
    protected String getUri() {
        return JBR_TEXT + getBookId()+"-" + chelekNum + "-" + saifNum;
    }

    @Override
    protected String getBookId() {
        return "likuteymoharan";
    }

}
