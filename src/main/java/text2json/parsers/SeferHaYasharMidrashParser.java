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
public class SeferHaYasharMidrashParser extends JbsParser {

    protected static final String BEGIN_HAKDAMA = "begin_hakdama";
    protected static final String BEGIN_PARASHA = "begin_parasha";
    protected static final String BEGIN_NO_PARASHA = "begin_no_parasha";
    private int position = 1, packagePosition = 1;
    private int perekNum = 0, parashaNum = 0, hakdamaNum = 0, subParahaNum = 0;
    private String label1 = "";
    private String label2 = "";

    public SeferHaYasharMidrashParser() {
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
                return line.beginsWith("ספר-הישר (מדרש)") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_HAKDAMA;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("הקדמה") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK; }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("ספר ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_PARASHA;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("פרשה ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_NO_PARASHA;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("ללא פרשות") && line.wordCount() <= 10;
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

            case BEGIN_PEREK:
                jsonObjectFlush();
                packagesJsonObjectFlush();
                perekNum++;
                parashaNum = 0;
                addBook(packagesJsonObject(), "seferhayasharmidrash");
                addPackageUri("seferhayasharmidrash-" + perekNum);
                addPosition(packagesJsonObject(), packagePosition);
                packagePosition++;
                label2 = line.getLine();
                addRdfs(packagesJsonObject(), "ספר הישר (מדרש) " + label2);
                // No need to create an object for the entire book anymore!
                // It is created manually, outside text2json
                break;

            case BEGIN_HAKDAMA:
                jsonObjectFlush();
                addBook("seferhayasharmidrash");
                addUri("seferhayasharmidrash-0");
                addPosition(0);
                addRdfs("ספר הישר (מדרש) הקדמה");
                packagesJsonObjectFlush();
                break;


            case BEGIN_PARASHA:
                jsonObjectFlush();
                parashaNum++;
                addBook("seferhayasharmidrash");
                addUri(getUri());
                addWithin("seferhayasharmidrash-" + perekNum);
                addPosition(position);
                position++;
                label1 = line.getLine().replace("פרשה ","");
                addRdfs("ספר הישר (מדרש) " + label1);
                packagesJsonObjectFlush();
                break;

            case BEGIN_NO_PARASHA:
                jsonObjectFlush();
                parashaNum++;
                addBook("seferhayasharmidrash");
                addUri("seferhayasharmidrash-" + perekNum + "-0");
                addWithin("seferhayasharmidrash");
                addPosition(position);
                position++;
                addRdfs("ספר הישר (מדרש) " + label2);
                packagesJsonObjectFlush();
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return "seferhayasharmidrash-" + perekNum + "-" + parashaNum;
    }


}