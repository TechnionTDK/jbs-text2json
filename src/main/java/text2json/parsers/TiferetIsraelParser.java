package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 08/06/2017.
 */
public class TiferetIsraelParser extends JbsParser {

    private int chapterNum = -1;


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
                addTextUri( getUri());
                addPosition( chapterNum+1);
                addLabel("תפארת ישראל - הקדמה");
                addBook( getBookId());
                break;


            case BEGIN_PEREK:
                jsonObjectFlush();
                chapterNum++;
                String chapterName =HEB_LETTERS_INDEX[chapterNum-1];
                addTextUri( getUri());
                addPosition( chapterNum+1);
                addBook( getBookId());
                String label = "תפארת ישראל " + chapterName;
                addLabel(label);
                break;

            case NO_MATCH:
                appendText( line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  getBookId()+"-" + chapterNum ;    }

    @Override
    protected String getBookId() {
        return "tiferetisrael";
    }
}
