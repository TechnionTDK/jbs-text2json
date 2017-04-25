package text2json.parsers;
import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;
import java.io.IOException;
import static text2json.JbsOntology.*;
/**
 * Created by shilonoa on 4/24/2017.
 */
public class SeferHakuzariParser extends Parser {
    String[] MaamarimLong = {"ראשון", "שני", "שלישי", "רביעי", "חמישי"};
    String[] MaamarimShort = {"א", "ב", "ג", "ד", "ה"};
    private static final String BEGIN_MAAMAR = "begin_maamar";
    private static final String BEGIN_SEIF = "begin_seif";
    private static final String MULTIPLE_LINE_SEIF = "multipul_line_seif";
    String long_maamar = null;
    String short_maamar = null;
    Boolean multiple_line_seif = false;
    String begining_of_long_seif = null;
    String seif = null;
    String short_seif = null;
    int maamarNum = 0;
    int seifNum = 0;

    public SeferHakuzariParser() {
        createPackagesJson();
    }

    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            public String type() {
                return BEGIN_MAAMAR;
            }
            public boolean match(Line line) {
                return line.beginsWith("ספר הכוזרי מאמר") && line.wordCount() <= 4;
            }
        });


        registerMatcher(new LineMatcher() {
            public String type() {
                return BEGIN_SEIF;
            }
            public boolean match(Line line) { return (line.getLine().substring(0,6).contains(" - "));
            }
        });

        registerMatcher(new LineMatcher() {
            public String type() {return MULTIPLE_LINE_SEIF;}
            public boolean match(Line line) { return ((begining_of_long_seif != null) && (!line.getLine().substring(0,6).contains(" - ")));}
        });
    }

    private int getSeifNum(String long_maamar_HE) {
        for (int i = 0; i < 5; i++){
            if(MaamarimLong[i].contains(long_maamar_HE))
                return i+1;
        }
        return -1;
    }

    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type){
            case BEGIN_MAAMAR:
                long_maamar = line.extract("ספר הכוזרי מאמר ", " ");
                maamarNum = getSeifNum(long_maamar);
                short_maamar = MaamarimShort[maamarNum-1];
                if (maamarNum != 1){
                    CreateObject();
                }
                if (maamarNum == 1){
                    // adding sefer object in packages json
                    packagesJsonObject().add(URI, "jbr:seferhakuzari");
                    packagesJsonObject().add(JBO_SEFER, "jbr:seferhakuzari");
                    packagesJsonObject().add(RDFS_LABEL, "הכוזרי");
                    packagesJsonObjectFlush();
                }
                // adding maamar object in packages json
                packagesJsonObject().add(URI, "jbr:seferhakuzari-" + maamarNum);
                packagesJsonObject().add(JBO_SEFER, "jbr:seferhakuzari");
                packagesJsonObject().add(RDFS_LABEL, "הכוזרי " + short_maamar);
                packagesJsonObject().add(JBO_CHELEK, "jbr:seferhakuzari-" + maamarNum);
                packagesJsonObjectFlush();
                seifNum = 0;
                break;
            case BEGIN_SEIF:
                if (seifNum != 0) {
                    //creating the JsonObject
                    seif = begining_of_long_seif;
                    CreateObject();
                }
                begining_of_long_seif = line.extract(" - ", " ");
                short_seif = line.extract(" ", " -");
                seifNum ++;
                break;
            case MULTIPLE_LINE_SEIF:
                begining_of_long_seif = begining_of_long_seif + " " + line.getLine();
                if ( (maamarNum==5) && (seifNum==20) && (stripVowels(line.getLine())).endsWith("והחסרון והדומה לזה .")){
                    CreateObject();
                }
                break;
            case NO_MATCH:
                break;
        }
    }

    protected String getUri() {
        return "jbr:seferhakuzari-" + maamarNum + "-" + seifNum;
    }

    protected void CreateObject() throws IOException{
        jsonObjectFlush();
        jsonObject().add(URI, getUri());
        jsonObject().add(JBO_TEXT, stripVowels(seif));
        jsonObject().add(JBO_TEXT_NIKUD, seif);
        jsonObject().add(JBO_SEFER, "jbr:seferhakuzari");
        jsonObject().add(JBO_POSITION, seifNum);
        jsonObject().add(RDFS_LABEL, "הכוזרי " + short_maamar + " " + short_seif);
        jsonObject().add(JBO_CHELEK, "jbr:seferhakuzari-" + maamarNum);
        jsonObjectFlush();
    }
}
