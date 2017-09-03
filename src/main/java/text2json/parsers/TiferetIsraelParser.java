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
public class TiferetIsraelParser extends JbsParser {

    private int chapterNum = -1;


//    public TiferetIsraelParser() {
//        createPackagesJson();
//    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("תפארת ישראל") && line.wordCount() <= 10;
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
                // No need to create an object for the entire book anymore!
                // It is created outside text2json
                break;

            case BEGIN_HAKDAMA:
                jsonObjectFlush();
                chapterNum++;
                addUri( getUri());
                addPosition( chapterNum+1);
                addRdfs("תפארת ישראל - הקדמה");
                addBook( "tiferetisrael");
                break;


            case BEGIN_PEREK:
//                packagesJsonObjectFlush();
                jsonObjectFlush();
                chapterNum++;
                String chapterName =HEB_LETTERS_INDEX[chapterNum-1];
                addUri( getUri());
                addPosition( chapterNum+1);
                addBook( "tiferetisrael");
                String rdfs = "תפארת ישראל " + chapterName;
                addRdfs(rdfs);
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  "tiferetisrael-" + chapterNum ;    }
}
