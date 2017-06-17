package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;

import java.io.IOException;

import static text2json.JbsOntology.*;
import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 08/06/2017.
 */
public class ChovotHalevavotParser extends Parser {

    protected static final String BEGIN_SHAAR = "begin_gate";
    protected static final String BEGIN_PATIAH = "begin_ptiha";
    protected static final String BEGIN_HAKDAMA = "begin_hakdama";
    protected static final String BEGIN_FIRST_HAKDAMA = "begin_hakdama1";


    private int shaarNum = 0;
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
                packagesJsonObject().add(URI, getcorpus());
                packagesJsonObject().add(RDFS_LABEL, "חובות הלבבות");
                packagesJsonObjectFlush();
                break;

            case BEGIN_FIRST_HAKDAMA:
                jsonObjectFlush();
                perekNum=0;
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_POSITION, "0");
                jsonObject().add(RDFS_LABEL,"חובות הלבבות - הקדמת המחבר");
                packagesJsonObject().add(URI, "chovothalevavot - hakdamat hamehaber");
                packagesJsonObject().add(RDFS_LABEL,"חובות הלבבות - הקדמת המחבר");
                packagesJsonObjectFlush();
                break;

            case BEGIN_SHAAR:
                jsonObjectFlush();
                perekNum=0;
                shaarName = line.getLine();
                shaarNum++;
                packagesJsonObject().add(URI, "chovothalevavot - " + shaarNum);
                packagesJsonObject().add(RDFS_LABEL,"חובות הלבבות - " + shaarName );
                packagesJsonObjectFlush();
                break;


            case BEGIN_HAKDAMA:
                jsonObjectFlush();
                perekNum=0;
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_POSITION, perekNum);
                jsonObject().addToArray(JBO_WITHIN, getcorpus());
                jsonObject().addToArray(JBO_WITHIN, shaarName);
                String rdfs = "חובות הלבבות - " + shaarName + "הקדמה";
                jsonObject().add(RDFS_LABEL,rdfs);
                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                perekName = "פרק " + HEB_LETTERS_INDEX[perekNum - 1];
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_POSITION, perekNum);
                jsonObject().addToArray(JBO_WITHIN, getcorpus());
                jsonObject().addToArray(JBO_WITHIN, shaarName);
                String rdfs1 = "חובות הלבבות - " + shaarName + " - " + perekName;
                jsonObject().add(RDFS_LABEL,rdfs1);
                packagesJsonObject().add(URI, "chovothalevavot - " + shaarNum + " - " + perekNum);
                packagesJsonObject().add(RDFS_LABEL,"חובות הלבבות - " + shaarName + " - " + perekName );
                packagesJsonObjectFlush();
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return JBR + "chovothalevavot-" + shaarNum + "-" + perekNum;   }
    protected String getcorpus() { return JBR + "chovothalevavot";    }

}
