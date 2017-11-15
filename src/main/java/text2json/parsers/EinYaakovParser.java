package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.JBO_TEXT_NIKUD;

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
                break;

            case BEGIN_MASECHET:
                jsonObjectFlush();
                perekNum=0;
                masechetNum++;
                addBook(packagesJsonObject(), getBookId());
                addPosition(packagesJsonObject(),packagePosition);
                packagePosition++;
                label1=line.getLine().replace("מסכת ","");
                addPackageUri( getBookId()+"-" + masechetNum);
                addLabel(packagesJsonObject(),"עין יעקב " + label1);
                packagesJsonObjectFlush();
                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                position++;
                addUri( getUri());
                addPosition( position);
                addBook( getBookId());
                addWithin( getBookId()+"-" + masechetNum);
                label2 = line.getLine().replace("heb","");
                String label = "עין יעקב" + label2;
                addLabel(label);
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT_NIKUD, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  getBookId()+"-" + masechetNum + "-"+  perekNum;    }

    @Override
    protected String getBookId() {
        return "einyaakov";
    }
}
