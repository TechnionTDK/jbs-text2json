package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.JBO_INTERPRETS;
import static text2json.JbsUtils.HEB_LETTERS_INDEX;
import static text2json.JbsUtils.PARASHOT_HE;

/**
 * Created by Assaf on 08/06/2017.
 */
public class SefatEmetParser extends JbsParser {

    private int parashaNum = 0,position=0,packagePosition=1;
    private int seferNum = 0;
    private int seifNum = 0;
    private String label = "";

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
                label = line.getLine();
                addPackageUri( "tanach-"+getBookId()+"-" + seferNum);
                addPosition(packagesJsonObject(),packagePosition);
                packagePosition++;
                addLabel(packagesJsonObject(),  "שפת אמת " + label);
                packagesJsonObjectFlush();
                break;

            case BEGIN_PARASHA:
                jsonObjectFlush();
                seifNum=0;
                parashaNum++;
                addPackageUri( "tanach-"+getBookId()+"-" + seferNum +"-" + parashaNum);
                addPosition(packagesJsonObject(),packagePosition);
                packagePosition++;
                addLabel(packagesJsonObject(),  "שפת אמת " + PARASHOT_HE[parashaNum-1]);
                packagesJsonObjectFlush();
                break;

            case BEGIN_SAIF:
                jsonObjectFlush();
                seifNum++;
                position++;
                String parashaName1 = PARASHOT_HE[parashaNum-1];
                String seifName = HEB_LETTERS_INDEX[seifNum-1];
                addTextUri( getUri());
                jsonObject().add(JBO_INTERPRETS, parashaName1);
                addPosition( position);
                addBook( getBookId());
                addWithin( "tanach-"+getBookId()+"-" + seferNum);
                addWithin( "tanach-"+getBookId()+"-" + seferNum +"-" + parashaNum);
                String label = "שפת אמת" + " " + parashaName1 + " " + seifName;
                addLabel(label);

                break;

            case NO_MATCH:
                appendText( line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  "tanach-"+getBookId()+"-" + seferNum +"-" + parashaNum + "-"+ seifNum;    }

    @Override
    protected String getBookId() {
        return "sefatemet";
    }
}
