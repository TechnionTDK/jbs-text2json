package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.*;
import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 05/05/2017.
 */
public class DerashotHaranParser extends JbsParser {
    private int perekNum = 0;

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("דרשות הר\"ן") && line.wordCount() <= 10;
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
                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_BOOK, getBookUri());
                jsonObject().add(JBO_POSITION,perekNum);
                String label = "דרשות הר\"ן " + HEB_LETTERS_INDEX[perekNum-1];
                jsonObject().add(RDFS_LABEL,label);
                break;

            case NO_MATCH:
                appendText(line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return JBR_TEXT + getBookId() + "-" + perekNum;
    }

    @Override
    protected String getBookId() {
        return "derashotharan";
    }

    protected String getBookUri() {
        return JBR_BOOK + getBookId();
    }
}
