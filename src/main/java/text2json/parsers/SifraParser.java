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
public class SifraParser extends JbsParser {

    protected static final String BEGIN_HAKDAMA = "begin_hakdama";
    protected static final String BEGIN_PARASHA = "begin_parasha";
    protected static final String BEGIN_SUB_PARASHA = "begin_sub_parasha";
    private int position=1 ,packagePosition=1;
    private int perekNum = 0,parashaNum = 0,hakdamaNum = 0, subParahaNum =0;
    private String label1 = "";
    private String label2 = "";

    public SifraParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("ספרא") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_HAKDAMA;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("הקדמה") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("פרק ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PARASHA;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("פרשת ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SUB_PARASHA;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("פרשה ") && line.wordCount() <= 10;
            }
        });

    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:
                // No need to create an object for the entire book anymore!
                // It is created manually, outside text2json
                break;

            case BEGIN_PARASHA:
                packagesJsonObjectFlush();
                parashaNum++;
                perekNum=0;
                subParahaNum=0;
                addBook(packagesJsonObject(),"sifra");
                addPackageUri("sifra-" + parashaNum);
                addPosition(packagesJsonObject(),packagePosition);
                packagePosition++;
                label2 = line.getLine();
                addRdfs(packagesJsonObject(), "ספרא " + label2);
                // No need to create an object for the entire book anymore!
                // It is created manually, outside text2json
                break;

            case BEGIN_HAKDAMA:
                jsonObjectFlush();
                addBook("sifra");
                addUri("sifra-0");
                addPosition(0);
                addRdfs("ספרא הקדמה" );
                packagesJsonObjectFlush();
                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                addBook("sifra");
                addUri(getPerekUri());
                addWithin("sifra-" + parashaNum);
                addPosition(position);
                position++;
                addRdfs("ספרא " + label2  + " פרק " + HEB_LETTERS_INDEX[perekNum-1] );
                packagesJsonObjectFlush();
                break;

              case BEGIN_SUB_PARASHA:
                jsonObjectFlush();
                subParahaNum++;
                addBook("sifra");
                addUri(getSubParashaUri());
                addWithin("sifra-" + parashaNum);
                addPosition(position);
                position++;
                addRdfs("ספרא " + label2  + " פרשה " + HEB_LETTERS_INDEX[subParahaNum-1] );
                packagesJsonObjectFlush();
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  "sifra";    }


    protected String getPerekUri() {
        return  "sifra-" + parashaNum + "-1-"+ perekNum;    }


    protected String getSubParashaUri() {
        return  "sifra-" + parashaNum + "-2-"+ subParahaNum;    }
}
