package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsUtils.numberToHebrew;

/**
 * Created by Assaf on 08/06/2017.
 */
public class TannaDeBeiEliyahuZutaParser extends JbsParser {

    protected static final String BEGIN_HAKDAMA = "begin_hakdama";
    protected static final String BEGIN_PEREK2 = "begin_perek2";
    protected static final String BEGIN_MAVO = "begin_mavo";
    protected static final String BEGIN_PART = "begin_part";
    protected static final String BEGIN_CHAPTERS = "begin_chapters";
    private int position = 1, packagePosition = 1;
    private int perekNum = 0, partNum = 0, hakdamaNum = 0, subParahaNum = 0;
    private String label1 = "";
    private String label2 = "";

    public TannaDeBeiEliyahuZutaParser() {
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
                return line.beginsWith("תנא דבי אליהו זוטא") && line.wordCount() <= 10;
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
            public String type() { return BEGIN_MAVO; }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("מבוא ") && line.wordCount() <= 10;
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
            public String type() { return BEGIN_PEREK2; }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("פרק ") && line.wordCount() <= 10;
            }
        });


        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_PART;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("חלק ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_CHAPTERS;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("פרקי ") && line.wordCount() <= 10;
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

            case BEGIN_PART:
                jsonObjectFlush();
                perekNum = 0;
                partNum++;
                packagesJsonObjectFlush();
                addBook(packagesJsonObject(), getBookId());
                addPackageUri(getBookId()+"-" + partNum);
                addPosition(packagesJsonObject(), packagePosition);
                packagePosition++;
                label2 = line.getLine().replace(" - ", " ").split(" ",3)[2];
                addLabel(packagesJsonObject(), "תנא דבי אליהו זוטא - " + label2);
                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                addBook(getBookId());
                addTextUri(getUri());
                addWithin(getBookId()+"-" + partNum);
                addPosition(position);
                position++;
                addLabel("תנא דבי אליהו זוטא - " + label2 + " " + numberToHebrew(perekNum));
                packagesJsonObjectFlush();
                break;

            case BEGIN_HAKDAMA:
                jsonObjectFlush();
                addBook(getBookId());
                addTextUri(getBookId()+"-" + partNum + "-" + perekNum);
                perekNum++;
                addWithin(getBookId()+"-" + partNum);
                addPosition(position);
                position++;
                addLabel("תנא דבי אליהו זוטא נספחים -" + " הקדמה");
                packagesJsonObjectFlush();
                break;

            case BEGIN_MAVO:
                jsonObjectFlush();
                perekNum++;
                addBook(getBookId());
                addTextUri(getBookId()+"-" + partNum + "-" + perekNum);
                addWithin(getBookId()+"-" + partNum);
                addPosition(position);
                position++;
                addLabel("תנא דבי אליהו זוטא - " + label2 + " מבוא");
                packagesJsonObjectFlush();
                break;

            case BEGIN_CHAPTERS:
                jsonObjectFlush();
                perekNum++;
                addBook(getBookId());
                addTextUri(getBookId()+"-" + partNum + "-" + perekNum);
                addWithin(getBookId()+"-" + partNum);
                addPosition(position);
                position++;
                label1 = line.getLine().replace(" - "," ");
                addLabel("תנא דבי אליהו זוטא נספחים -" + " " + label1);
                packagesJsonObjectFlush();
                break;

            case BEGIN_PEREK2:
                jsonObjectFlush();
                perekNum++;
                addBook(getBookId());
                addTextUri(getBookId()+"-" + partNum + "-" + perekNum);
                addWithin(getBookId()+"-" + partNum);
                addPosition(position);
                position++;
                label1 = line.getLine().replace(" - ", " ").replace("פרק ","");
                addLabel("תנא דבי אליהו זוטא - " + label2 + " " + label1);
                packagesJsonObjectFlush();
                break;

            case NO_MATCH:
                appendText( line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return getBookId()+"-" + partNum + "-" + perekNum;
    }

    @Override
    protected String getBookId() {
        return "tannadebeieliyahuzuta";
    }


}