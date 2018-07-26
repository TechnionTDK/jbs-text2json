package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 08/06/2017.
 */
public class NetzachIsraelParser extends JbsParser {

    private int chapterNum = -1, first =0;
    

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("נצח ישראל") && line.wordCount() <= 10;
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
                break;


            case BEGIN_PEREK:
                jsonObjectFlush();
                chapterNum++;
                first = 1;
                if (chapterNum>0) {
                    String chapterName = HEB_LETTERS_INDEX[chapterNum - 1];
                    addTextUri( getUri());
                    addPosition( chapterNum+1);
                    addBook( getBookId());
                    String label = "נצח ישראל " + chapterName;
                    addLabel( label);
                }
                else{
                    addTextUri( getUri());
                    addPosition( chapterNum+1);
                    addBook( getBookId());
                    String label = "נצח ישראל הקדמה";
                    addLabel( label);
                }
                break;

            case NO_MATCH:
                if(first == 0)
                    appendText( line.getLine());
                else
                    first = 0;
                break;
        }
    }

    @Override
    protected String getUri() {
        return  getBookId()+"-" + chapterNum ;    }

    @Override
    protected String getBookId() {
        return "netzachisrael";
    }
}
