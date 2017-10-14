package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.JBO_TEXT;
import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 08/06/2017.
 */
public class MekhiltaDeRabbiYishmaelParser extends JbsParser {

    protected static final String BEGIN_PASUK = "begin_pasuk";
    private int pasukNum = 0,position=1 ,packagePosition=1;
    private int perekNum = 0;
    private String label1 = "";
    private String label2 = "";

    public MekhiltaDeRabbiYishmaelParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("מכילתא דרבי ישמעאל") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Perek ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PASUK;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Pasuk ") && line.wordCount() <= 10;
            }
        });

    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:
                // No need to create an object for the entire book anymore!
                // It is created manually, outside text2json
                break;

            case BEGIN_PEREK:
                packagesJsonObjectFlush();
                perekNum++;
                pasukNum=0;
                addBook(packagesJsonObject(),"mekhiltaderabbiyishmael");
                addPackageUri("mekhiltaderabbiyishmael-"+perekNum);
                addPosition(packagesJsonObject(),packagePosition);
                packagePosition++;
                addRdfs(packagesJsonObject(),"מכילתא דרבי ישמעאל פרק " + HEB_LETTERS_INDEX[perekNum-1]);
                // No need to create an object for the entire book anymore!
                // It is created manually, outside text2json
                break;


            case BEGIN_PASUK:
                jsonObjectFlush();
                pasukNum++;
                addBook("mekhiltaderabbiyishmael");
                addUri(getUri());
                addWithin("mekhiltaderabbiyishmael");
                addWithin("mekhiltaderabbiyishmael-" + pasukNum);
                addPosition(position);
                position++;
                addRdfs("מכילתא דרבי ישמעאל פרק " + HEB_LETTERS_INDEX[perekNum-1] + " פסוק " + HEB_LETTERS_INDEX[pasukNum-1]);

                packagesJsonObjectFlush();
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  "mekhiltaderabbiyishmael-" + perekNum + "-"+ pasukNum;    }
}
