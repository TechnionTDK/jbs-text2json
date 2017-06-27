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
public class MoreNevochimParser extends Parser {

    protected static final String BEGIN_PART = "begin_part";
    protected static final String BEGIN_PATIAH = "begin_ptiha";
    protected static final String BEGIN_HAKDAMA = "begin_hakdama";
    protected static final String BEGIN_WRITERS_HAKDAMA = "begin_hakdama1";


    private int partNum = 0,position=0;
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
                packagesJsonObject().add(URI, getcorpus());
                packagesJsonObject().add(RDFS_LABEL, "מורה נבוכים");
                packagesJsonObjectFlush();
                break;

            case BEGIN_WRITERS_HAKDAMA:
                jsonObjectFlush();
                perekNum=0;
//                jsonObject().add(URI, getUri());
//                jsonObject().add(JBO_POSITION, "0");
//                jsonObject().add(JBO_BOOK, JBR + "morenevochim");
//                jsonObject().add(RDFS_LABEL,"מורה נבוכים - הקדמות המתרגמים");
//                packagesJsonObject().add(URI, "morenevochim - writers prolog");
//                packagesJsonObject().add(RDFS_LABEL,"מורה נבוכים - הקדמות המתרגמים");
//                packagesJsonObjectFlush();
                break;

            case BEGIN_PATIAH:
                jsonObjectFlush();
                perekNum++;
                position++;
                ptihaName = line.getLine();
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_BOOK, JBR + "morenevochim");
                jsonObject().add(JBO_POSITION, position);
                jsonObject().add(RDFS_LABEL,"מורה נבוכים הקדמות המתרגמים " + ptihaName);
                packagesJsonObject().add(URI, "morenevochim " + partNum + " " + perekNum);
                packagesJsonObject().add(RDFS_LABEL,"מורה נבוכים - הקדמות המתרגמים " + ptihaName );
                packagesJsonObjectFlush();
                break;

            case BEGIN_PART:
                jsonObjectFlush();
                perekNum=1;
                partNum++;
                packagesJsonObject().add(URI, "morenevochim - " + partNum);
                packagesJsonObject().add(JBO_BOOK, JBR + "morenevochim");
                packagesJsonObject().add(RDFS_LABEL,"מורה נבוכים " + HEB_LETTERS_INDEX[partNum - 1] );
                packagesJsonObjectFlush();
                break;


            case BEGIN_HAKDAMA:
                jsonObjectFlush();
                perekNum=0;
                position++;
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_POSITION, position);
                jsonObject().add(JBO_BOOK, JBR + "morenevochim");
                jsonObject().addToArray(JBO_WITHIN,JBR + "morenevochim-" + partNum);
                String rdfs = "מורה נבוכים - חלק " + HEB_LETTERS_INDEX[partNum - 1] + " הקדמה";
                jsonObject().add(RDFS_LABEL,rdfs);
                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                position++;
                perekName = HEB_LETTERS_INDEX[perekNum - 1];
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_POSITION, position);
                jsonObject().add(JBO_BOOK, JBR + "morenevochim");
                jsonObject().addToArray(JBO_WITHIN, JBR + "morenevochim-" + partNum);
                String rdfs1 = "מורה נבוכים " + HEB_LETTERS_INDEX[partNum - 1] + " "  + perekName;
                jsonObject().add(RDFS_LABEL,rdfs1);
//                packagesJsonObject().add(URI, getUri());
//                packagesJsonObject().add(RDFS_LABEL,rdfs1 );
//                packagesJsonObjectFlush();
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return JBR + "morenevochim-" + partNum + "-" + perekNum;   }
    protected String getcorpus() { return JBR + "morenevochim";    }

}
