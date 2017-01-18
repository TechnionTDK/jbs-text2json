package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;

/**
 * Change to MefareshParser and provide in the c'tor
 * the name of the Mefaresh.
 * Created by omishali on 12/12/2016.
 */

public class MefareshParser extends Parser {
    private static final String BEGIN_PARASHA = "begin_parasha";
    private static final String BEGIN_PEREK = "begin_perek";
    private static final String BEGIN_RASHI = "begin_rashi";
    private static final String BEGIN_PASUK = "begin_pasuk";
    private static final String MEFARESH_PARSER_ID = "parser.mefareshTanach";
    private int bookNum = 0;
    private int parashaNum = 0;
    private int perekNum = 0;
    private int positionInParasha = 0;
    private int positionInPerek = 0;
    private String mefaresh;
    private String perekLetter;
    private String pasukLetter;
    private String perush;

    public MefareshParser(String mefaresh){
        super();
        mefaresh = mefaresh;
    }

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
            public String type() { return BEGIN_PASUK; }
            public boolean match(Line line) { return line.beginsWith("{");
            }
        });

        registerMatcher(new LineMatcher() {
            public String type() {
                return BEGIN_RASHI;
            }
            public boolean match(Line line) { return line.beginsWith(": (" + mefaresh + ")");
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
                // TODO: get bookNum in constructor or from book name?
                // if(bookNum == 0) bookNum = getBookNum(line.extract(" ", " פרק-" ));
                perekNum++;
                positionInPerek = 0;
                perekLetter = line.extract("פרק-", "");
                break;
            case BEGIN_PASUK:
                String curr_pasuk = line.extract("{", "}");
                if(pasukLetter == curr_pasuk)
                    break;
                pasukLetter = curr_pasuk;
                positionInPerek++;
                positionInParasha++;
                break;
            case BEGIN_RASHI:
                perush = line.extract(" ", ":");
                //TODO: add json creation code here

                break;

        }
    }

    @Override
    protected String getUri() {
        return null;
    }

    /*
    @Override
    protected void onEOF() {

    }
    */
}
