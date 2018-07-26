package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.JBO_BOOK;
import static text2json.JbsOntology.JBR_BOOK;
import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 08/06/2017.
 */
public class MoreNevochimParser extends JbsParser {

    protected static final String BEGIN_PART = "begin_part";
    protected static final String BEGIN_PATIAH = "begin_ptiha";
    protected static final String BEGIN_HAKDAMA = "begin_hakdama";
    protected static final String BEGIN_WRITERS_HAKDAMA = "begin_hakdama1";


    private int partNum = 0,position=0, packagePosition =1;
    private int perekNum = 0;
    private String ptihaName ="";
    private String perekName ="";

    public MoreNevochimParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("מורה נבוכים") && line.wordCount() <= 10;
            }
        });


        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PART;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("חלק ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PATIAH;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("פתיחת ") && line.wordCount() <= 10;
            }
        });


        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_HAKDAMA;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("הקדמה") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_WRITERS_HAKDAMA;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("הקדמות המתרגמים") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Chapter") && line.wordCount() <= 10;
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:
                // No need to create an object for the entire book anymore!
                // It is created outside text2json
                break;

            case BEGIN_WRITERS_HAKDAMA:
                jsonObjectFlush();
                perekNum=0;
                break;

            case BEGIN_PATIAH:
                jsonObjectFlush();
                perekNum++;
                position++;
                ptihaName = line.getLine();
                addTextUri( getUri());
                addBook( getBookId());
                addPosition( position);
                addLabel("מורה נבוכים הקדמות המתרגמים " + ptihaName);
                addPackageUri( getBookId()+"-" + partNum +"-"+ perekNum);
                addPosition(packagesJsonObject(),packagePosition);
                packagePosition++;
                addLabel(packagesJsonObject(),"מורה נבוכים - הקדמות המתרגמים " + ptihaName );
                packagesJsonObjectFlush();
                break;

            case BEGIN_PART:
                jsonObjectFlush();
                perekNum=1;
                partNum++;
                addPackageUri( getBookId()+"-" + partNum);
                packagesJsonObject().add(JBO_BOOK, JBR_BOOK + getBookId());
                addPosition(packagesJsonObject(),packagePosition);
                packagePosition++;
                addLabel(packagesJsonObject(),"מורה נבוכים " + HEB_LETTERS_INDEX[partNum - 1] );
                packagesJsonObjectFlush();
                break;


            case BEGIN_HAKDAMA:
                jsonObjectFlush();
                perekNum=0;
                position++;
                addTextUri( getUri());
                addPosition( position);
                addBook( getBookId());
                addWithin( getBookId()+"-" + partNum);
                String label = "מורה נבוכים - חלק " + HEB_LETTERS_INDEX[partNum - 1] + " הקדמה";
               addLabel(label);
                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                position++;
                perekName = HEB_LETTERS_INDEX[perekNum - 1];
                addTextUri( getUri());
                addPosition( position);
                addBook( getBookId());
                addWithin( getBookId()+"-" + partNum);
                String label1 = "מורה נבוכים " + HEB_LETTERS_INDEX[partNum - 1] + " "  + perekName;
                addLabel(label1);
                break;

            case NO_MATCH:
                appendText( line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  getBookId()+"-" + partNum + "-" + perekNum;   }

    @Override
    protected String getBookId() {
        return "morenevochim";
    }
}
