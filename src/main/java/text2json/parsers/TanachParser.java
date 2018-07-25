package text2json.parsers;

import text2json.JbsParser;
import text2json.JbsUtils;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

/**
 * Created by orel on 25/07/18.
 */
public class TanachParser extends JbsParser {

    String[] booksNames = JbsUtils.SEFARIM_TANACH_URI_EN;
    private int book_index = 0, perek = 0, parasha = 0, pasuk = 0, position = 0;
    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PARASHA;}
            @Override
            public boolean match(Line line) {
                return line.beginsWith("פרשת ") && line.wordCount() <= 5;}
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}
            @Override
            public boolean match(Line line) {
                return line.beginsWith("ספר ") && line.wordCount() <= 5;
            }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK;}
            @Override
            public boolean match(Line line) {
                return line.beginsWith("Chapter") && line.wordCount() == 2;}
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch (type) {
            case BEGIN_SEFER:
                book_index++;
                perek = 0;
                break;
            case BEGIN_PEREK:
                perek++;
                pasuk = 0;
                break;
            case BEGIN_PARASHA:
                parasha++;
                break;
            case NO_MATCH: //every regular line is a pasuk
                if(line.wordCount() == 0) break;
                position++;
                pasuk++;
                appendText(JbsUtils.removeNikkud(line.getLine()));
                appendNikudText(line.getLine());
                addBook(jsonObject(), getCurrentBookName());
                addUri(getUri());
                addPosition(jsonObject(), position);
                addLabel(jsonObject(), getLabel());
                jsonObjectFlush();
                break;
        }
    }

    private String getCurrentBookName() {
        return booksNames[book_index-1];
    }

    @Override
    protected String getUri() {
        return getBookId() + "-" + book_index + "-" + perek + "-" + pasuk;
    }

    private String getLabel() {
        String hebrewBookName = JbsUtils.SEFARIM_TANACH_HE[book_index-1];
        return hebrewBookName + " " +
                JbsUtils.numberToHebrew(perek) + " " + JbsUtils.numberToHebrew(pasuk);
    }

    @Override
    protected String getBookId() {
        return "tanach";
    }
}
