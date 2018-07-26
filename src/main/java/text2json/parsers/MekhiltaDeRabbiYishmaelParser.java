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

                break;

            case BEGIN_PEREK:
                packagesJsonObjectFlush();
                perekNum++;
                pasukNum=0;
                addBook(packagesJsonObject(),getBookId());
                addPackageUri(getBookId()+"-"+perekNum);
                addPosition(packagesJsonObject(),packagePosition);
                packagePosition++;
                addLabel(packagesJsonObject(),"מכילתא דרבי ישמעאל " + HEB_LETTERS_INDEX[perekNum-1]);
                break;


            case BEGIN_PASUK:
                if (jsonObject().hasKey(JBO_TEXT)){
                    jsonObjectFlush();
                    position++;}
                else{
                    jsonObject().clear();
                }
                pasukNum++;
                addBook(getBookId());
                addTextUri(getUri());
                addWithin(getBookId()+"-" + pasukNum);
                addPosition(position);
                addLabel("מכילתא דרבי ישמעאל " + HEB_LETTERS_INDEX[perekNum-1] + " " + HEB_LETTERS_INDEX[pasukNum-1]);
                packagesJsonObjectFlush();
                break;

            case NO_MATCH:
                appendText( line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  getBookId()+"-" + perekNum + "-"+ pasukNum;    }

    @Override
    protected String getBookId() {
        return "mekhiltaderabbiyishmael";
    }
}
