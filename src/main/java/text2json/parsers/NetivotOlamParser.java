package text2json.parsers;
import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;
import java.io.IOException;

import static text2json.JbsOntology.*;
import static text2json.JbsUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class NetivotOlamParser extends Parser {

    protected static final String BEGIN_NATIV = "begin_nativ";
    private int nativNum = 0,position=0;
    private int perekNum = 0;
    private String nativName = "";

    public NetivotOlamParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("נתיבות עולם") && line.wordCount() <= 10;
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
            public String type() { return BEGIN_NATIV;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("נתיב ") && line.wordCount() <= 10;
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
                packagesJsonObject().add(RDFS_LABEL, "נתיבות עולם");
                packagesJsonObjectFlush();
                break;

            case BEGIN_HAKDAMA:
                jsonObjectFlush();
                position++;
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_BOOK, JBR + "netivotolam");
                jsonObject().add(JBO_POSITION, position);
                jsonObject().add(RDFS_LABEL,"נתיבות עולם - הקדמה");

                break;

            case BEGIN_NATIV:
                jsonObjectFlush();
                perekNum=0;
                nativNum++;
                nativName = line.getLine();
                packagesJsonObject().add(JBO_BOOK, JBR + "netivotolam");
                packagesJsonObject().add(URI, JBR + "netivotolam-" + nativNum);
                packagesJsonObject().add(JBO_POSITION, nativNum);
                packagesJsonObject().add(RDFS_LABEL, nativName);
                packagesJsonObjectFlush();
                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                position++;
                String perekName = HEB_LETTERS_INDEX[perekNum-1];
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_POSITION, position);
                jsonObject().add(JBO_BOOK, JBR + "netivotolam");
                jsonObject().addToArray(JBO_WITHIN, JBR + "netivotolam-" + nativNum);
                String rdfs = "נתיבות עולם - " + nativName +" " + perekName;
                jsonObject().add(RDFS_LABEL,rdfs);
//                packagesJsonObject().add(URI, getUri());
//                packagesJsonObject().add(RDFS_LABEL, rdfs);
//                packagesJsonObjectFlush();

                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return JBR + "netivotolam-" + nativNum + "-"+ perekNum;    }
    protected String getcorpus() { return JBR + "netivotolam";    }


}
