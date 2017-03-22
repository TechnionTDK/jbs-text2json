package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;
import java.io.IOException;
import static text2json.JbsOntology.*;


/**
 * Created by shilonoa on 3/16/2017.
 */
public class MishnaParser extends Parser {
    String[] MefarshimEn = {"bartanura", "yomtov"};
    String[] MefarshimHe = {"ר\"ע מברטנורה", "עיקר תוי\"ט"};
    String[] Sdarim = {"zraim","moed","nashim","nezikin","kdashim","teharot"};

    private static final String BEGIN_MASECHET = "begin_masechet";
    private static final String BEGIN_PEREK = "begin_perek";
    private static final String BEGIN_PERUSH = "begin_perush";
    private static final String BEGIN_MISHNA = "begin_mishna";
    private static final String MULTIPLE_LINE_PERUSH = "multiple_line_perush";
    private static final String MEFARESH_PARSER_ID = "parser.mefareshMishna";
    private int sederNum = 0;
    private String masechetHE = null;
    private String masechetEN = null;
    private int masechetNum = 0;
    private int perekNum = 0;
    private String perekLetter;
    private int mishnaNum = 0;
    private String mishnaLetter = null;
    private String mefaresh;
    private int mefareshId;
    private String begining_of_long_perush = null;
    private int mefareshId_Long_Perush;
    boolean Just_finished_perush = false;
    private String perush;

    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            public String type() {
                return BEGIN_MASECHET;
            }
            public boolean match(Line line) {
                return line.contains("משנה מסכת");
            }
        });

        registerMatcher(new LineMatcher() {
            public String type() {
                return BEGIN_PEREK;
            }
            public boolean match(Line line) {
                return line.contains("פרק ") && line.wordCount() <= 2;
            }
        });

        registerMatcher(new LineMatcher() {
            public String type() { return BEGIN_MISHNA; }
            public boolean match(Line line) { return line.beginsWith( "פרק " + perekLetter + " - משנה");
            }
        });

        registerMatcher(new LineMatcher() {
            public String type() {
                return BEGIN_PERUSH;
            }
            public boolean match(Line line) {
                return line.endsWith(MefarshimHe, ".");
            }
        });

        registerMatcher(new LineMatcher() {
            public String type() {
                return MULTIPLE_LINE_PERUSH;
            }
            public boolean match(Line line) { return (line.beginsWith(MefarshimHe) || (begining_of_long_perush != null));}
        });

    }

    protected int get_mefareshId(Line line){
        String baseLine = line.getLine();
        for(int i=0; i < MefarshimHe.length; i++){
            if (baseLine.endsWith(": (" + MefarshimHe[i] + ")" + ".")) return i;
        }
        return -1;
    }


    private int Find_Index_In_Arr(String[] Arr, String val){
        for (int i = 0; i < Arr.length; i++){
            if (Arr[i].contains(val)) return i;
        }
        return -1;
    }

    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type){
            case BEGIN_MASECHET:
                //parashaNum = Find_Index_In_Arr(ParashotHe, line.getLine()) + 1;
                masechetHE = line.extract("משנה מסכת", " ");
                getSederNum(masechetHE);
                break;
            case BEGIN_PEREK:
                perekLetter = line.extract("פרק", " ");
                perekNum ++;
                mishnaNum = 0;
                break;
            case BEGIN_MISHNA:
                mishnaLetter = line.extract(" - משנה ", " ");
                mishnaNum ++;
                break;
            case MULTIPLE_LINE_PERUSH:
                if (Just_finished_perush){
                    begining_of_long_perush = null;
                    Just_finished_perush = false;
                }
                else if (begining_of_long_perush == null){
                    begining_of_long_perush = line.getLine();
                }
                else {
                    begining_of_long_perush = begining_of_long_perush + " " + line.getLine();
                }
                break;
            case BEGIN_PERUSH:
                mefareshId = get_mefareshId(line);
                mefaresh = MefarshimEn[mefareshId];
                perush = line.extract(" ", ": (" + MefarshimHe[mefareshId] + ")");

                if (begining_of_long_perush != null) {
                    perush = begining_of_long_perush + " " + perush;
                    begining_of_long_perush = null;
                }
                jsonObjectFlush();
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_TEXT, perush);
                jsonObject().add(RDFS_LABEL, MefarshimHe[mefareshId] + " " + masechetHE + " " + perekLetter + " " + mishnaLetter);
                jsonObject().add(JBO_NAME, MefarshimHe[mefareshId]);
                jsonObject().add(JBO_SEDER, "jbr:mishna-" + sederNum);
                jsonObject().add(JBO_MASECHET, "jbr:mishna-" + sederNum + "-" + masechetNum);
                jsonObject().add(JBO_PEREK, "jbr:mishna-" + sederNum + "-" + masechetNum + "-" + perekNum);
                jsonObject().add(JBO_MISHNA, "jbr:mishna-" + sederNum + "-" + masechetNum + "-" + perekNum + "-" + mishnaNum);
                jsonObjectFlush();
                Just_finished_perush = true;
                break;
            case NO_MATCH:
                break;
        }
    }
    protected String getUri() {
        return "jbr:mishna-" + mefaresh + "-" + sederNum + "-" + masechetNum + "-" + perekNum + "-" + mishnaNum;
    }

    private int getSederNum(String MasechetName) {
        String masechetNames[] = {"ברכות", "פאה", "דמאי", "כלאים", "שביעית", "תרומות", "מעשרות", "מעשר שני", "חלה",
                "ערלה","ביכורים",
                "שבת","עירובין","פסחים","שקלים","יומא","סוכה","ביצה", "ראש השנה", "תענית", "מגילה", "מועד קטן", "חגיגה",
                "יבמות","כתובות", "נדרים", "נזיר", "סוטה", "גיטין", "קידושין",
                "בבא קמא", "בבא מציעא", "בבא בתרא", "סנהדרין", "מכות", "שבועות", "עדיות", "עבודה זרה","אבות", "הוריות",
                "זבחים", "מנחות", "חולין", "בכורות", "ערכין", "תמורה", "כריתות", "מעילה", "תמיד", "מדות", "קנים",
                "כלים", "אהלות", "נגעים", "פרה", "טהרות", "מקוואות", "נדה", "מכשירין", "זבים", "טבול יום","ידים", "עוקצין"
        };
        int i;
        for (i = 0; i < 63; i++){
            if(MasechetName.contains(masechetNames[i]))
                break;
        }
        if (i < 11){
            sederNum = 1;
            masechetNum = i + 1;
        }
        else if (i < 23){
            sederNum = 2;
            masechetNum = i + 1 -11;
        }
        else if (i < 30){
            sederNum = 3;
            masechetNum = i + 1 -23;
        }
        else if (i < 40){
            sederNum = 4;
            masechetNum = i + 1 -30;
        }
        else if (i < 51){
            sederNum = 5;
            masechetNum = i + 1 -40;
        }
        else if (i < 63){
            sederNum = 6;
            masechetNum = i + 1 -51;
        }
        return -1;
    }

}
