package text2json.parsers;

import text2json.JbsParser;
import text2json.JbsUtils;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

/**
 * Created by orel on 22/07/18.
 */
public class ShemonaPerakimParser extends JbsParser {

    private int perekNum = 0;
    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_SEFER;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("שמונה פרקים לרמב\"ם");
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_HAKDAMA;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("הקדמה") && line.wordCount() == 1;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_PEREK;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Chapter") && line.wordCount() == 2;
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
                addBook(jsonObject(), getBookId());
                addTextUri(getBookId()+"-" + perekNum);
                addPosition(jsonObject(), perekNum);
                addLabel(jsonObject(), "שמונה פרקים לרמב\"ם " + JbsUtils.numberToHebrew(perekNum));
                break;

            case BEGIN_HAKDAMA:
                jsonObjectFlush();
                addBook(getBookId());
                addTextUri(getBookId()+"-0");
                addPosition(0);
                addLabel("שמונה פרקים לרמב\"ם הקדמה");
                break;

            case NO_MATCH:
                appendText( line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return null;
    }

    @Override
    protected String getBookId() {
        return "shemonaperakim";
    }
}
