package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.*;
import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 08/06/2017.
 */
public class ChovotHalevavotParser extends JbsParser {

    protected static final String BEGIN_SHAAR = "begin_gate";
    protected static final String BEGIN_HAKDAMA = "begin_hakdama";
    protected static final String BEGIN_FIRST_HAKDAMA = "begin_hakdama1";


    private int shaarNum = 0, position =0, packagePosition=0;
    private int perekNum = 0;
    private String shaarName ="";
    private String perekName = "";
    public ChovotHalevavotParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("חובות הלבבות") && line.wordCount() <= 10;
            }
        });


        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SHAAR;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("שער ") && line.wordCount() <= 10;
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
            public String type() { return BEGIN_FIRST_HAKDAMA;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("הקדמת המחבר") && line.wordCount() <= 10;
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

            case BEGIN_FIRST_HAKDAMA:
                jsonObjectFlush();
                perekNum=0;
                position++;
                addUri( getUri());
                addPosition( position);
                addRdfs("חובות הלבבות הקדמת המחבר");
                addBook( "chovothalevavot");

                addPackageUri ("chovothalevavot-0-0");
                addPosition(packagesJsonObject(),packagePosition);
                packagePosition++;
                addRdfs(packagesJsonObject(),"חובות הלבבות הקדמת המחבר");
                packagesJsonObjectFlush();
                break;

            case BEGIN_SHAAR:
                jsonObjectFlush();
                perekNum=0;
                shaarName = line.getLine().replace(" - "," ");
                shaarNum++;
                addPackageUri("chovothalevavot-" + shaarNum);
                addPosition(packagesJsonObject(),packagePosition);
                packagePosition++;
                addRdfs(packagesJsonObject(),"חובות הלבבות " + shaarName );
                packagesJsonObjectFlush();
                break;


            case BEGIN_HAKDAMA:
                jsonObjectFlush();
                perekNum=0;
                position++;
                addUri( getUri());
                addPosition( position);
                addBook( "chovothalevavot");
                addWithin( "chovothalevavot-" + shaarNum);
                String rdfs = "חובות הלבבות " + shaarName + "הקדמה";
                addRdfs(rdfs);
                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                position++;
                perekName = HEB_LETTERS_INDEX[perekNum - 1];
                addUri( getUri());
                addBook( "chovothalevavot");
                addPosition( position);
                addWithin( "chovothalevavot-" + shaarNum);
                String rdfs1 = "חובות הלבבות " + shaarName + " " + perekName;
                addRdfs(rdfs1);
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  "chovothalevavot-" + shaarNum + "-" + perekNum;   }
    protected String getcorpus() { return JBR + "chovothalevavot";    }

}
