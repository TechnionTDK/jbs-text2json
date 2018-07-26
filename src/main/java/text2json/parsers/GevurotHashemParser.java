package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 08/06/2017.
 */
public class GevurotHashemParser extends JbsParser {

    private int chapterNum = 0;
    private int hakdamaNum = 0;

    
    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("גבורות השם") && line.wordCount() <= 10;
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
                break;

            case BEGIN_HAKDAMA:
                jsonObjectFlush();
                hakdamaNum++;
                addTextUri( getBookId()+"-0-" + (hakdamaNum-1));
                addBook( getBookId());
                addPosition(hakdamaNum);
                if (hakdamaNum ==1)
                   addLabel("גבורות השם הקדמה א");
                if (hakdamaNum ==2)
                    addLabel("גבורות השם הקדמה ב");
                if (hakdamaNum ==3)
                    addLabel("גבורות השם הקדמה ג");
                break;


            case BEGIN_PEREK:
                jsonObjectFlush();
                chapterNum++;
                String chapterName = HEB_LETTERS_INDEX[chapterNum-1];
                addTextUri( getUri());
                addPosition( chapterNum+3);
                addBook( getBookId());
                String label = "גבורות השם " + chapterName;
                addLabel(label);
                break;

            case NO_MATCH:
                appendText( line.getLine());

                break;
        }
    }

    @Override
    protected String getUri() {
        return getBookId()+"-" + chapterNum ;    }

    @Override
    protected String getBookId() {
        return "gevurothashem";
    }

}
