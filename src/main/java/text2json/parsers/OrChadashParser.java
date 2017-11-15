package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 08/06/2017.
 */
public class OrChadashParser extends JbsParser {

    private int chapterNum = 0;
    private int hakdamaNum = -1;
    

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("אור חדש") && line.wordCount() <= 10;
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
                addUri( getBookId()+"-0" +hakdamaNum);
                addBook(getBookId());
                addPosition(hakdamaNum+1);
                if (hakdamaNum ==00)
                    addLabel("אור חדש הקדמה א");
                if (hakdamaNum ==01)
                    addLabel("אור חדש הקדמה ב");
                break;


            case BEGIN_PEREK:
                jsonObjectFlush();
                chapterNum++;
                String chapterName =HEB_LETTERS_INDEX[chapterNum-1];
                addUri( getUri());
                addPosition(chapterNum+2);
                addBook(getBookId());
                String label = "אור חדש " + chapterName;
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
        return "orchadash";
    }
}
