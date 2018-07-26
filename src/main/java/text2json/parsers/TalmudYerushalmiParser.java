package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsUtils.numberToHebrew;

/**
 * Created by Assaf on 08/06/2017.
 */
public class TalmudYerushalmiParser extends JbsParser {

    protected static final String BEGIN_MASECHET = "begin_masechet";
    protected static final String BEGIN_DAF = "begin_daf";

    private int masechetNum = 0,position =0,packagePosition =1;
    private int dafNum = 1;
    private int subDafNum = 0;
    private String masechetName ="";
    private String dafName ="";

    public TalmudYerushalmiParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_MASECHET;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("תלמוד ירושלמי ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_DAF;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Daf ") && line.wordCount() <= 10;
            }
        });

    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_MASECHET:
                // No need to create an object for the entire book anymore!
                // It is created outside text2json
                masechetName = (line.getLine()).replace("תלמוד ירושלמי ","");
                masechetNum = getMasechetNum(masechetName);
                addBook(packagesJsonObject(), getBookId());
                addPosition(packagesJsonObject(),packagePosition);
                packagePosition++;
                addPackageUri( getBookId()+"-"+masechetNum);
                addLabel(packagesJsonObject(),"תלמוד ירושלמי " + masechetName );
                packagesJsonObjectFlush();
                break;

            case BEGIN_DAF:
                jsonObjectFlush();
                dafName = line.getLine().split(" ")[1];
                dafNum = Integer.parseInt((line.getLine().split(" ")[1]).substring(0,dafName.length() - 1));
                if (dafName.charAt(dafName.length() - 1) == 'a'){
                    subDafNum=1;
                } else {
                    subDafNum=2;
                }
                position++;
                addTextUri( getUri());
                addPosition( position);
                addBook( getBookId());
                addWithin( getBookId()+"-" + masechetNum);
                String label1 ="";
                if(subDafNum==1)
                    label1 = "תלמוד ירושלמי " + masechetName  + " " + numberToHebrew(dafNum) + " א";
                else
                    label1 = "תלמוד ירושלמי " + masechetName  + " " +  numberToHebrew(dafNum)  + " ב";
                addLabel(label1);

                break;


            case NO_MATCH:
                appendText( line.getLine());
                break;
        }
    }

    private int getMasechetNum(String masechetTitle) {
        String masachotNames[] = {"ברכות", "פאה","דמאי","כלאים", "שביעית", "תרומות", "מעשרות", "מעשר שני", "חלה", "ערלה",
                "בכורים","שבת", "עירובין","פסחים","שקלים","יומא","סוכה","ביצה","ראש השנה","תענית","מגילה","מועד קטן",
                "חגיגה","יבמות","כתובות","נדרים","נזיר", "סוטה", "גיטין", "קידושין", "בבא קמא",
                "בבא מציעא", "בבא בתרא", "סנהדרין", "מכות", "שבועות", "עבודה זרה", "הוריות", "נידה"};
        for (int i = 0; i <= 38; i++){
            if(masechetTitle.equals(masachotNames[i]))
                return i+1;
        }
        return -1;
    }

    @Override
    protected String getUri() {
        return getBookId()+"-" + masechetNum + "-" + dafNum + "-" + subDafNum;
    }

    @Override
    protected String getBookId() {
        return "yerushalmi";
    }


}
