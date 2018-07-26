package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 08/06/2017.
 */
public class MidbarShurParser extends JbsParser {

    private int chapterNum = 0;

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("מדבר שור") && line.wordCount() <= 10;
            }
        });


        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Drush ") && line.wordCount() <= 10;
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:
                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                chapterNum++;
                String chapterName = HEB_LETTERS_INDEX[chapterNum-1];
                addTextUri( getUri());
                addBook( getBookId());
                addPosition(chapterNum);
                String label = "מדבר שור " + chapterName;
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
        return "midbarshur";
    }
}
