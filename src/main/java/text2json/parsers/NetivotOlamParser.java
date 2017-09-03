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
public class NetivotOlamParser extends JbsParser {

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
                // No need to create an object for the entire book anymore!
                // It is created outside text2json
                break;

            case BEGIN_HAKDAMA:
                jsonObjectFlush();
                position++;
                addUri("netivotolam-" + perekNum);
                addBook( "netivotolam");
                addPosition( position);
                addRdfs("נתיבות עולם - הקדמה");

                break;

            case BEGIN_NATIV:
                jsonObjectFlush();
                perekNum=0;
                nativNum++;
                nativName = line.getLine();
                addBook(packagesJsonObject() ,"netivotolam");
                addPackageUri( "netivotolam-" + nativNum);
                addPosition(packagesJsonObject(), nativNum);
                addRdfs(packagesJsonObject(), nativName);
                packagesJsonObjectFlush();
                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                position++;
                String perekName = HEB_LETTERS_INDEX[perekNum-1];
                addUri( getUri());
                addPosition( position);
                addBook( "netivotolam");
                addWithin( "netivotolam-" + nativNum);
                String rdfs = "נתיבות עולם - " + nativName +" " + perekName;
                addRdfs(rdfs);

                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  "netivotolam-" + nativNum + "-"+ perekNum;    }
}
