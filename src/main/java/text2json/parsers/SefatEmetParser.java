package text2json.parsers;
import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;
import java.io.IOException;

import static text2json.JbsOntology.*;
import static text2json.JbsUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class SefatEmetParser extends Parser {

    private int parashaNum = 0,position=0;
    private int seferNum = 0;
    private int seifNum = 0;

    public SefatEmetParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PERUSH;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("שפת אמת") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("ספר ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PARASHA;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("פרשת ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SAIF;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Section") && line.wordCount() <= 10;
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_PERUSH:
                // No need to create an object for the entire book anymore!
                // It is created outside text2json
                break;

            case BEGIN_SEFER:
                jsonObjectFlush();
                seferNum++;
                break;

            case BEGIN_PARASHA:
                jsonObjectFlush();
                seifNum=0;
                parashaNum++;
                packagesJsonObject().add(URI, JBR_SECTION + "tanach-sefatemet-" + seferNum +"-" + parashaNum);
                packagesJsonObject().add(RDFS_LABEL,  "שפת אמת " + PARASHOT_HE[parashaNum-1]);
                packagesJsonObjectFlush();
                break;

            case BEGIN_SAIF:
                jsonObjectFlush();
                seifNum++;
                position++;
                String parashaName1 = PARASHOT_HE[parashaNum-1];
                String seifName = HEB_LETTERS_INDEX[seifNum-1];
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_INTERPRETS, parashaName1);
                jsonObject().add(JBO_POSITION, position);
                jsonObject().add(JBO_BOOK, JBR_BOOK + "sefatemet");
                jsonObject().addToArray(JBO_WITHIN, JBR_SECTION + "tanach-sefatemet-" + seferNum);
                jsonObject().addToArray(JBO_WITHIN, JBR_SECTION + "tanach-sefatemet-" + seferNum +"-" + parashaNum);
                String rdfs = "שפת אמת" + " " + parashaName1 + " " + seifName;
                jsonObject().add(RDFS_LABEL,rdfs);

                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return JBR_TEXT + "tanach-sefatemet-" + seferNum +"-" + parashaNum + "-"+ seifNum;    }
}
