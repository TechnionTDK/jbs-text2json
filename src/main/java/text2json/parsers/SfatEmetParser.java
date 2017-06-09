package text2json.parsers;
import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;
import java.io.IOException;

import static text2json.JbsOntology.*;
import static text2json.JbsUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class SfatEmetParser extends Parser {

    private int parashaNum = 0;
    private int seferNum = 0;
    private int seifNum = 0;

    public SfatEmetParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PERUSH;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("שפת אמת") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("ספר ") && line.wordCount() <= 10;
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
            public String type() { return BEGIN_SAIF;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Section") && line.wordCount() <= 10;
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_PERUSH:
//                System.out.println("found beginning of file - START");
                packagesJsonObject().add(URI, getcorpus());
                packagesJsonObject().add(RDFS_LABEL, "שפת אמת");
                packagesJsonObjectFlush();
                break;

            case BEGIN_SEFER:
                jsonObjectFlush();
                seferNum++;
//                System.out.println("\n            found sefer number: " + seferNum+"\n");
                break;

            case BEGIN_PARASHA:
                jsonObjectFlush();
//                System.out.println("last seif is: "+ seifNum);
                seifNum=0;
                parashaNum++;
//                System.out.println(seferNum +" "+ parashaNum);
//                String bookName = "ספר "+ SFARIM_TANACH_HE[seferNum-1] + " ";
//                String parashaName = PARASHOT_HE[parashaNum-1] + " ";
//                System.out.println("found parasha "+ parashaName +"(" +parashaNum+")"+ " of book "+ bookName);

                break;

            case BEGIN_SAIF:
                jsonObjectFlush();
                seifNum++;
//                System.out.println("found seif number: " + seifNum );
                String bookName1 = "ספר "+ SFARIM_TANACH_HE[seferNum-1] + " ";
                String parashaName1 = PARASHOT_HE[parashaNum-1] + " ";
                String seifName = "סעיף " + HEB_LETTERS_INDEX[seifNum-1]+ " ";
//                System.out.println(bookName +" "+ parashaName + " " + seifName);
//                System.out.println(seferNum +" "+ parashaNum + " " + seifNum);
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_EXPLAINS,parashaName1);
                jsonObject().add(JBO_POSITION_IN_PARASHA, seifNum);
                jsonObject().addToArray(JBO_WITHIN, getcorpus());
                jsonObject().addToArray(JBO_WITHIN, bookName1);
                jsonObject().addToArray(JBO_WITHIN, parashaName1);
                String rdfs = "שפת אמת, " + bookName1 + parashaName1 + seifName;
                jsonObject().add(RDFS_LABEL,rdfs);

                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return JBR + "tanach-sefetemet-" + seferNum +"-" + parashaNum + "-"+ seifNum;    }
    protected String getcorpus() { return JBR + "sefatemet";    }


}
