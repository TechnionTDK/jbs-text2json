package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 08/06/2017.
 */
public class EinYaakovGlickEditionParser extends JbsParser {

    protected static final String BEGIN_TRACTATE = "begin_tractate";
    private int tractateNum = 0,position=1 ,packagePosition=1;
    private int perekNum = 0;
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

    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:
                break;

            case BEGIN_TRACTATE:
                packagesJsonObjectFlush();
                tractateNum++;
                perekNum=0;
                addBook(packagesJsonObject(),getBookId());
                addPackageUri(getBookId()+"-"+tractateNum);
                addPosition(packagesJsonObject(),packagePosition);
                packagePosition++;
                label2 = line.getLine().replace("מסכת ","");
                addLabel(packagesJsonObject(), "עין יעקב (מאת שמואל צבי גליק) - " + label2);
  
                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                addBook(getBookId());
                addUri(getUri());
                addWithin(getBookId()+"-" + tractateNum);
                addPosition(position);
                position++;
                addLabel("עין יעקב (מאת שמואל צבי גליק) " + label2  + " פרק " + HEB_LETTERS_INDEX[perekNum-1] );
                packagesJsonObjectFlush();
                break;

            case NO_MATCH:
                appendText( line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  getBookId()+"-" + tractateNum + "-"+ perekNum;    }

    @Override
    protected String getBookId() {
        return "einyaakovglickedition";
    }
}
