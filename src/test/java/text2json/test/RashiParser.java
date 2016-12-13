package text2json.test;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;

/**
 * Change to MefareshParser and provide in the c'tor
 * the name of the Mefaresh.
 * Created by omishali on 12/12/2016.
 */
public class RashiParser extends Parser {
    private static final String BEGIN_PARASHA = "begin_parasha";
    private static final String BEGIN_PEREK = "begin_perek";
    private static final String BEGIN_RASHI = "begin_rashi";
    private int parashaNum = 0;
    private int perekNum = 0;
    private int positionInParasha = 0;
    private int positionInPerek = 0;
    private String perekLetter;

    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            public String type() {
                return BEGIN_PARASHA;
            }

            public boolean match(Line line) {
                return line.beginsWith("פרשת") && line.wordCount() <= 4;
            }
        });

        registerMatcher(new LineMatcher() {
            public String type() {
                return BEGIN_PEREK;
            }

            public boolean match(Line line) {
                return line.contains("פרק-") && line.wordCount() <= 4;
            }
        });

        registerMatcher(new LineMatcher() {
            public String type() {
                return BEGIN_RASHI;
            }

            public boolean match(Line line) {
                return line.beginsWith("רש\"י");
            }
        });
    }

    protected void onLineMatch(String type, Line line) {
        switch(type){
            case BEGIN_PARASHA:
                parashaNum++;
                positionInParasha = 0;
                break;
            case BEGIN_PEREK:
                perekNum++;
                positionInPerek = 0;
                perekLetter = line.extract("פרק-", "");
                break;
            case BEGIN_RASHI:
                break;

        }
    }


}
