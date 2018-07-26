package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.JBO_TEXT;
import static text2json.JbsUtils.numberToHebrew;

/**
 * Created by Assaf on 08/06/2017.
 */
public class BeitYosefParser extends JbsParser {

    protected static final String BEGIN_TUR = "begin_tur";
    protected static final String BEGIN_SEIF = "begin_seif";
    protected static final String BEGIN_SIMAN = "begin_siman";

    private int turNum = 0,position =1,packagePosition =1;
    private int seifNum = 0;
    private int simanNum = 0;
    private String turName ="";

    public BeitYosefParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("בית יוסף") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_TUR;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("טור ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEIF;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Seif ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SIMAN;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Siman ") && line.wordCount() <= 10;
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:

                break;

            case BEGIN_TUR:
                seifNum=0;
                simanNum=0;
                turName = line.getLine().replace("טור ","");
                turNum++;
                addBook(packagesJsonObject(), getBookId());
                addPosition(packagesJsonObject(),packagePosition);
                packagePosition++;
                addPackageUri( getBookId() + "-"+ turNum);
                addLabel(packagesJsonObject(),"בית יוסף " + turName);
                packagesJsonObjectFlush();
                break;

            case BEGIN_SIMAN :
                seifNum=0;
                simanNum++;
                addBook(packagesJsonObject(), getBookId());
                addPosition(packagesJsonObject(), packagePosition);
                packagePosition++;
                addPackageUri( getBookId() + "-"+ turNum + "-" + simanNum);
                String label = "בית יוסף " + turName + " " + numberToHebrew(simanNum);
                addLabel(packagesJsonObject(), label);
                packagesJsonObjectFlush();

                break;

            case BEGIN_SEIF:
                if (jsonObject().hasKey(JBO_TEXT)){
                    jsonObjectFlush();
                    position++;
                }
                else{
                    jsonObject().clear();
                }

                seifNum++;
                addTextUri( getUri());
                addPosition( position);
                addBook( getBookId());
                addWithin( getBookId() + "-" + turNum);
                addWithin( getBookId() + "-" + turNum +"-" + simanNum);
                String label1 = "בית יוסף " + turName  + " " + numberToHebrew(simanNum) + " " + numberToHebrew(seifNum);
                addLabel(label1);
                packagesJsonObjectFlush();

                break;


            case NO_MATCH:
                appendText( line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  getBookId() + "-" + turNum +"-"  + simanNum + "-" + seifNum;   }

    @Override
    protected String getBookId() {
        return "beityosef";
    }


}
