package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;
import static text2json.JbsOntology.*;
import java.io.IOException;
import static text2json.JbsUtils.*;

/**
 * Created by USER on 16-Mar-17.
 */
public class LikuteyMoharanParser extends Parser{
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
                packagesJsonObject().add(URI, "jbr:likuteymoharan");
                packagesJsonObject().add(RDFS_LABEL, "ליקוטי מוהר''ן");
                packagesJsonObjectFlush();
                break;

            case BEGIN_CHELEK:
                jsonObjectFlush();
                saifNum = 0;
                chelekNum++;
                chelekHebIdx = line.extract("ליקוטי מוהר''ן - חלק ", " ");
                //add chelek to package json
                packagesJsonObject().add(URI, "jbr:likuteymoharan-" + chelekNum);
                packagesJsonObject().add(RDFS_LABEL, "ליקוטי מוהר''ן - חלק " + chelekHebIdx);
                packagesJsonObject().add(JBO_POSITION, chelekNum);
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
                jsonObject().addToArray(JBO_WITHIN, "jbr:likuteymoharan");
                jsonObject().addToArray(JBO_WITHIN, "jbr:likuteymoharan-" + chelekNum);
                jsonObject().add(JBO_POSITION, positionInSefer);
                jsonObject().add(RDFS_LABEL, "ליקוטי מוהר''ן " + chelekHebIdx + " " + saifHebIdx);
                jsonObject().add(JBO_NAME, saifTitle);
                break;

            case BEGIN_TEXT:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;

            case NO_MATCH:
                onLineMatch(BEGIN_TEXT, line);
                break;
        }
    }

    @Override
    protected String getUri() {
        return "jbr:likuteymoharan-" + chelekNum + "-" + saifNum;
    }

}
