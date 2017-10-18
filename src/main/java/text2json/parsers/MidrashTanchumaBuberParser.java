package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.JBO_TEXT;
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
    private String label1 = "";
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
                // No need to create an object for the entire book anymore!
                // It is created manually, outside text2json
                break;

            case BEGIN_PARASHA:
                jsonObjectFlush();
                packagesJsonObjectFlush();
                parashaNum++;
                simanNum = 0;
                addBook(packagesJsonObject(), "midrashtanchumabuber");
                addPackageUri("midrashtanchumabuber-" + parashaNum);
                addPosition(packagesJsonObject(), packagePosition);
                packagePosition++;
                label2 = line.getLine();
                addRdfs(packagesJsonObject(), "מדרש תנחומא בובר " + label2);
                // No need to create an object for the entire book anymore!
                // It is created manually, outside text2json
                break;


            case BEGIN_ADD_PARASHA:
                jsonObjectFlush();
                packagesJsonObjectFlush();
                parashaNum++;
                simanNum = 0;
                label2 = line.getLine();
                // No need to create an object for the entire book anymore!
                // It is created manually, outside text2json
                break;


            case BEGIN_SIMAN:
                jsonObjectFlush();
                simanNum++;
                addBook("midrashtanchumabuber");
                addUri(getUri());
                addWithin("midrashtanchumabuber-" + parashaNum);
                addPosition(position);
                position++;
                addRdfs("מדרש תנחומא בובר " + label2 + " סימן " + HEB_LETTERS_INDEX[simanNum-1]);
                packagesJsonObjectFlush();
                break;


            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return "midrashtanchumabuber-" + parashaNum + "-" + simanNum;
    }


}