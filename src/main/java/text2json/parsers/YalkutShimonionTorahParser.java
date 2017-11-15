package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsUtils.numberToHebrew;

/**
 * Created by Assaf on 08/06/2017.
 */
public class YalkutShimonionTorahParser extends JbsParser {

    private int chapterNum = 0;

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("ילקוט שמעוני על התורה") && line.wordCount() <= 10;
            }
        });


        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Remez ") && line.wordCount() <= 10;
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:
                // No need to create an object for the entire book anymore!
                // It is created manually, outside text2json
                break;

            case BEGIN_PEREK:
//                packagesJsonObjectFlush();
                jsonObjectFlush();
                chapterNum++;
                addUri( getUri());
                addBook( getBookId());
                addPosition(chapterNum);
                String label = "ילקוט שמעוני על התורה " + numberToHebrew(chapterNum);
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
        return "yalkutshimoniontorah";
    }
}
