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
public class EinYaakovParser extends JbsParser {

    protected static final String BEGIN_MASECHET = "begin_masechet";
    protected static final String BEGIN_PEREK2 = "begin_perek2";
    private int  masechetNum = 0,position=0 ,packagePosition=1;
    private int perekNum = 0;
    private String label1 = "",label2 = "";

    public EinYaakovParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("עין יעקב") && line.wordCount() <= 10;
            }
        });


        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("heb ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK2;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Chapter ") && line.wordCount() <= 10;
            }
        });


        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_MASECHET;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("מסכת ") && line.wordCount() <= 10;
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

            case BEGIN_MASECHET:
                jsonObjectFlush();
                perekNum=0;
                masechetNum++;
                addBook(packagesJsonObject(), "einyaakov");
                addPosition(packagesJsonObject(),packagePosition);
                packagePosition++;
                label1=line.getLine().replace("מסכת ","");
                addPackageUri( "einyaakov-" + masechetNum);
                addRdfs(packagesJsonObject(),"עין יעקב " + label1);
                packagesJsonObjectFlush();
                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                position++;
                addUri( getUri());
                addPosition( position);
                addBook( "einyaakov");
                addWithin( "einyaakov-" + masechetNum);
                label2 = line.getLine().replace("heb","");
                String rdfs = "עין יעקב" + label2;
                addRdfs(rdfs);
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  "einyaakov-" + masechetNum + "-"+  perekNum;    }
}
