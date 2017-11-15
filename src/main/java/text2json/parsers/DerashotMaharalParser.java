package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;


/**
 * Created by Assaf on 08/06/2017.
 */
public class DerashotMaharalParser extends JbsParser {

    private int chapterNum = -1;


    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Drashot Maharal") && line.wordCount() <= 10;
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
                return line.beginsWith("דרוש ") && line.wordCount() <= 10;
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

            case BEGIN_PEREK:
                jsonObjectFlush();
                chapterNum++;
                String chapterName = line.getLine();
                addUri( getUri());
                addPosition( chapterNum+1);
                addBook( getBookId() );
                String label = "דרשות מהר\"ל - " + chapterName;
                addLabel(label);
                break;

            case NO_MATCH:
                appendText( line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  getBookId() +"-" + chapterNum ;    }

    @Override
    protected String getBookId() {
        return "derashotmaharal";
    }

}