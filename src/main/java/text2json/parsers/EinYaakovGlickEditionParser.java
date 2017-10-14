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
public class EinYaakovGlickEditionParser extends JbsParser {

    protected static final String BEGIN_TRACTATE = "begin_tractate";
//    protected static final String BEGIN_HEB = "begin_label";
    private int tractateNum = 0,position=1 ,packagePosition=1;
    private int perekNum = 0;
    private String label1 = "";
    private String label2 = "";

    public EinYaakovGlickEditionParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("עין יעקב (מאת שמואל צבי גליק)") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_TRACTATE;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("מסכת ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Chapter ") && line.wordCount() <= 10;
            }
        });

//        registerMatcher(new LineMatcher() {
//            @Override
//            public String type() { return BEGIN_HEB;}
//
//            @Override
//            public boolean match(Line line) {
//                return line.beginsWith("heb ") && line.wordCount() <= 10;
//            }
//        });

    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:
                // No need to create an object for the entire book anymore!
                // It is created manually, outside text2json
                break;

            case BEGIN_TRACTATE:
                packagesJsonObjectFlush();
                tractateNum++;
                perekNum=0;
                addBook(packagesJsonObject(),"einyaakovglickedition");
                addPackageUri("einyaakovglickedition-"+tractateNum);
                addPosition(packagesJsonObject(),packagePosition);
                packagePosition++;
                label2 = line.getLine();
                addRdfs(packagesJsonObject(), "עין יעקב (מאת שמואל צבי גליק) - " + label2);
                // No need to create an object for the entire book anymore!
                // It is created manually, outside text2json
                break;

//            case BEGIN_HEB:
//                label1 = line.getLine().replace("heb ","");
//                addRdfs(label1);
//                // No need to create an object for the entire book anymore!
//                // It is created manually, outside text2json
//                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                addBook("einyaakovglickedition");
                addUri(getUri());
                addWithin("einyaakovglickedition");
                addWithin("einyaakovglickedition-" + tractateNum);
                addPosition(position);
                position++;
                addRdfs("עין יעקב (מאת שמואל צבי גליק) " + label2  + " פרק " + HEB_LETTERS_INDEX[perekNum-1] );
                packagesJsonObjectFlush();
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  "einyaakovglickedition-" + tractateNum + "-"+ perekNum;    }
}
