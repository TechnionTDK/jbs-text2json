package text2json.parsers;

import text2json.JbsParser;
import text2json.JbsUtils;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.JBO_TEXT;

/**
 * Created by omishali on 05/11/2017.
 */
public class TanachKtivMaleParser extends JbsParser {
    private static final String BEGIN_PASUK = "begin_pasuk";
    private int position = 0;
    private int sefer = 0;
    private String firstWord;

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PASUK;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("פסוק@");
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("ספר") && line.wordCount() <= 5;
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:
                sefer++;
                break;
            case BEGIN_PASUK:
                jsonObjectFlush();
                position++;
                firstWord = line.getFirstWord();
                addUri(getUri());
                line = line.removeFirstWord().removeExtraSpaces();
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
            case NO_MATCH:
                // in some psukim the content is spread into two (even three) lines
                line = line.removeExtraSpaces();
                jsonObject().appendWithSpace(JBO_TEXT, line.getLine());
                break;
        }

    }

    @Override
    protected String getUri() {
        String[] split = firstWord.split("@");
        int perek = JbsUtils.hebrewToNumber(split[1]);
        int pasuk = JbsUtils.hebrewToNumber(split[2]);
        return "tanach-" + Integer.toString(sefer) + "-" + Integer.toString(perek) + "-" + Integer.toString(pasuk);
    }
}
