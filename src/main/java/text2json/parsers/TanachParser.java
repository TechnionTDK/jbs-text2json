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

    private int book_index = 0, perek = 0, parasha = 0, pasuk = 0, position = 0, perek_position = 0;

    public TanachParser() {
        createPackagesJson();
    }

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
                perek_position++;
                pasuk = 0;
                addPackageUri(JbsUtils.getTanachPerekUri(book_index, perek));
                addLabel(packagesJsonObject(), getPerekLabel());
                addPosition(packagesJsonObject(), perek_position);
                addBook(packagesJsonObject(), getCurrentBookName());
                packagesJsonObjectFlush();
                break;
            case BEGIN_PARASHA:
                parasha++;
                addPackageUri(JbsUtils.getTorahParashaUri(parasha));
                addLabel(packagesJsonObject(), line.getLine());
                addPosition(packagesJsonObject(), parasha);
                addBook(packagesJsonObject(), getCurrentBookName());
                packagesJsonObjectFlush();
                break;
            case NO_MATCH: //every regular line is a pasuk
                if(line.wordCount() == 0) break;
                position++;
                pasuk++;
                appendText(JbsUtils.removeNikkud(line.getLine()));
                //putting a correct ":" at the end of the string. the u202 characters are to fix right-to-left issues
                String nikudText = line.getLine().substring(0,line.getLine().length() - 1)
                        + "\u202B" + ":" + "\u202C";
                appendNikudText(nikudText);
                addBook(jsonObject(), getCurrentBookName());
                addTextUri(getUri());
                addPosition(jsonObject(), position);
                addLabel(jsonObject(), getLabel());
                addWithin(JbsUtils.getTanachPerekUri(book_index, perek));
                if(book_index <= 5) addWithin(JbsUtils.getTorahParashaUri(parasha));
                jsonObjectFlush();
                break;
        }
    }

    private String getCurrentBookName() {
        return JbsUtils.SEFARIM_TANACH_URI_EN[book_index-1];
    }

    @Override
    protected String getUri() {
        return JbsUtils.getPasukUri(book_index, perek, pasuk);
    }

    private String getLabel() {
        String hebrewBookName = JbsUtils.SEFARIM_TANACH_HE[book_index-1];
        return hebrewBookName + " " +
                JbsUtils.numberToHebrew(perek) + " " + JbsUtils.numberToHebrew(pasuk);
    }

    private String getPerekLabel() {
        String hebrewBookName = JbsUtils.SEFARIM_TANACH_HE[book_index-1];
        return hebrewBookName + " פרק " +
                JbsUtils.numberToHebrew(perek);
    }

    @Override
    protected String getBookId() {
        return "tanach";
    }
}
