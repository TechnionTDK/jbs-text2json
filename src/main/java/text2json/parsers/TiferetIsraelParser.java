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
public class TiferetIsraelParser extends Parser {

    private int chapterNum = -1;


    public TiferetIsraelParser() {
        createPackagesJson();
    }

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
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_POSITION, chapterNum+1);
                jsonObject().add(RDFS_LABEL,"תפארת ישראל - הקדמה");
                jsonObject().add(JBO_BOOK, JBR_BOOK + "tiferetisrael");
                break;


            case BEGIN_PEREK:
                packagesJsonObjectFlush();
                jsonObjectFlush();
                chapterNum++;
                String chapterName =HEB_LETTERS_INDEX[chapterNum-1];
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_POSITION, chapterNum+1);
                jsonObject().add(JBO_BOOK, JBR_BOOK + "tiferetisrael");
                String rdfs = "תפארת ישראל " + chapterName;
                jsonObject().add(RDFS_LABEL,rdfs);
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return JBR_TEXT + "tiferetisrael-" + chapterNum ;    }
}
