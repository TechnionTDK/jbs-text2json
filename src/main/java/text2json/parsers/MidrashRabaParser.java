package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;

import java.io.IOException;

import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by omishali on 16/02/2017.
 */
public class MidrashRabaParser extends Parser {
    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_SEFER;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("מדרש רבה");
            }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_SEDER;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("סדר") && line.wordCount() <= 4;
            }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_PARASHA;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("פרשה") && line.wordCount() <= 2;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_SAIF;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith(HEB_LETTERS_INDEX, " ");
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {

    }

    @Override
    protected String getUri() {
        return null;
    }
}
