package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 08/06/2017.
 */
public class MidrashTanchumaBuberParser extends JbsParser {

    protected static final String BEGIN_PARASHA = "begin_parasha";
    protected static final String BEGIN_SIMAN = "begin_siman";
    protected static final String BEGIN_ADD_PARASHA = "begin_adding_psrasha";
    private int position = 1, packagePosition = 1;
    private int simanNum = 0, parashaNum = 0;
    private String label2 = "";

    public MidrashTanchumaBuberParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_SEFER;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("מדרש תנחומא - בובר") && line.wordCount() <= 10;
            }
        });


        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_PARASHA;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("פרשת ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_SIMAN;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Siman ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_ADD_PARASHA; }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("הוספה ל") && line.wordCount() <= 10;
            }
        });

    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch (type) {
            case BEGIN_SEFER:
                break;

            case BEGIN_PARASHA:
                jsonObjectFlush();
                packagesJsonObjectFlush();
                parashaNum++;
                simanNum = 0;
                addBook(packagesJsonObject(), getBookId());
                addPackageUri(getBookId()+"-" + parashaNum);
                addPosition(packagesJsonObject(), packagePosition);
                packagePosition++;
                label2 = line.getLine().replace("פרשת ","");
                addLabel(packagesJsonObject(), "מדרש תנחומא בובר " + label2);
                break;


            case BEGIN_ADD_PARASHA:
                jsonObjectFlush();
                packagesJsonObjectFlush();
                parashaNum++;
                simanNum = 0;
                label2 = line.getLine().replace("פרשת ","");
                break;


            case BEGIN_SIMAN:
                jsonObjectFlush();
                simanNum++;
                addBook(getBookId());
                addUri(getUri());
                addWithin(getBookId()+"-" + parashaNum);
                addPosition(position);
                position++;
                addLabel("מדרש תנחומא בובר " + label2 + " " + HEB_LETTERS_INDEX[simanNum-1]);
                packagesJsonObjectFlush();
                break;


            case NO_MATCH:
                appendText( line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return getBookId()+"-" + parashaNum + "-" + simanNum;
    }

    @Override
    protected String getBookId() {
        return "midrashtanchumabuber";
    }


}