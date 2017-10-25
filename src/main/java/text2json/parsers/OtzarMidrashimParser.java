package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.JBO_TEXT;

/**
 * Created by Assaf on 08/06/2017.
 */
public class OtzarMidrashimParser extends JbsParser {

    protected static final String BEGIN_HAKDAMA = "begin_hakdama";
    protected static final String BEGIN_MIDRASH = "begin_midrash";

    private int position = 1, packagePosition = 1;
    private int perekNum = 0, midrashNum = 0;
    private String label1 = "";
    private String label2 = "";

    public OtzarMidrashimParser() {
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
                return line.beginsWith("אוצר מדרשים") && line.wordCount() <= 10;
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
                return line.beginsWith("Chapter ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_MIDRASH; }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("מדרש - ") && line.wordCount() <= 10;
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

            case BEGIN_MIDRASH:
//                System.out.println(line.getLine());
                jsonObjectFlush();
                perekNum = 0;
                midrashNum++;
                packagesJsonObjectFlush();
                addBook(packagesJsonObject(), "otzarmidrashim");
                addPackageUri("otzarmidrashim-" + midrashNum);
                addPosition(packagesJsonObject(), packagePosition);
                packagePosition++;
                label2 = line.getLine().replace(" - "," ");
                addRdfs(packagesJsonObject(), "אוצר מדרשים " + label2);


                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                addBook("otzarmidrashim");
                addUri(getUri());
                addWithin("otzarmidrashim-" + midrashNum);
                addPosition(position);
                position++;
                addRdfs("אוצר מדרשים " + label2 + " פרק " + perekNum);
                packagesJsonObjectFlush();
                break;

            case BEGIN_HAKDAMA:
                jsonObjectFlush();
                addBook("otzarmidrashim");
                addUri("otzarmidrashim-" + midrashNum + "-" + perekNum);
                perekNum++;
                addWithin("otzarmidrashim-" + midrashNum);
                addPosition(position);
                position++;
                addRdfs("אוצר מדרשים " + label2 + " הקדמה");
                packagesJsonObjectFlush();
                break;


            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return "otzarmidrashim-" + midrashNum + "-" + perekNum;
    }


}