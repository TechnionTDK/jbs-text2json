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
    private static final String BEGIN_PASUK = "begin_pasuk";
    private static final String MULTIPLE_LINE_PASUK = "long_pasuk";
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
    private String sederName = null;
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
    private String pasuk = null;
    boolean new_mishna = false;
    boolean first_masechet = true;

    public MishnaParser() {
        createPackagesJson();
    }
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
            public String type() { return BEGIN_PASUK; }
            public boolean match(Line line) { return line.endsWith(":");
            }
        });

        registerMatcher(new LineMatcher() {
            public String type() {
                return MULTIPLE_LINE_PASUK;
            }
            public boolean match(Line line) { return ((pasuk==null) && (new_mishna));}
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

                if (first_masechet){
                    // adding sefer object in packages json
                    packagesJsonObject().add(URI, "jbr:mishna");
                    packagesJsonObject().add(RDFS_LABEL, "משנה");
                    packagesJsonObjectFlush();
                    // adding seder object in packages json
                    packagesJsonObject().add(URI, "jbr:mishna-" + sederNum);
                    packagesJsonObject().add(RDFS_LABEL, "סדר " + sederName);
                    packagesJsonObject().addToArray(JBO_WITHIN, "jbr:mishna");
                    packagesJsonObject().add(JBO_POSITION, sederNum);
                    packagesJsonObjectFlush();
                }
                // adding masechet object in packages json
                packagesJsonObject().add(URI, getMasechetUri());
                packagesJsonObject().add(RDFS_LABEL, line.getLine());
                packagesJsonObject().addToArray(JBO_WITHIN, "jbr:mishna");
                packagesJsonObject().addToArray(JBO_WITHIN, "jbr:mishna-" + sederNum);
                packagesJsonObject().add(JBO_POSITION, masechetNum);
                packagesJsonObjectFlush();
                break;
            case BEGIN_PEREK:
                perekLetter = line.extract("פרק", " ");
                perekNum ++;
                mishnaNum = 0;
                // adding perek object in packages json
                packagesJsonObject().add(URI, "jbr:mishna-" + sederNum + "-" + masechetNum + "-" + perekNum);
                packagesJsonObject().add(RDFS_LABEL, "משנה " + masechetHE + " " +  line.getLine());
                packagesJsonObject().addToArray(JBO_WITHIN, "jbr:mishna-" + sederNum);
                packagesJsonObject().addToArray(JBO_WITHIN, "jbr:mishna-" + sederNum + "-" + masechetNum);
                packagesJsonObject().addToArray(JBO_WITHIN, "jbr:mishna");
                packagesJsonObject().add(JBO_POSITION, perekNum);
                packagesJsonObjectFlush();
                break;
            case BEGIN_MISHNA:
                mishnaLetter = line.extract(" - משנה ", " ");
                mishnaNum ++;
                new_mishna = true;
                pasuk = null;
                break;
            case MULTIPLE_LINE_PERUSH:
                new_mishna = false;
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
                new_mishna = false;
                mefareshId = get_mefareshId(line);
                mefaresh = MefarshimEn[mefareshId];
                perush = line.extract(" ", ": (" + MefarshimHe[mefareshId] + ")");

                if (begining_of_long_perush != null) {
                    perush = begining_of_long_perush + " " + perush;
                    begining_of_long_perush = null;
                }
                jsonObjectFlush();
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_TEXT, stripVowels(perush));
                jsonObject().add(JBO_TEXT_NIKUD, perush);
                jsonObject().add(RDFS_LABEL, MefarshimHe[mefareshId] + " משנה " + masechetHE + " " + perekLetter + " " + mishnaLetter);
                jsonObject().add(JBO_NAME, MefarshimHe[mefareshId]);
                jsonObject().addToArray(JBO_WITHIN, "jbr:mishna");
                jsonObject().addToArray(JBO_WITHIN, "jbr:mishna-" + sederNum);
                jsonObject().addToArray(JBO_WITHIN, "jbr:mishna-" + sederNum + "-" + masechetNum);
                jsonObject().addToArray(JBO_WITHIN, "jbr:mishna-" + sederNum + "-" + masechetNum + "-" + perekNum);
                jsonObject().add(JBO_INTERPRETS, "jbr:mishna-" + sederNum + "-" + masechetNum + "-" + perekNum + "-" + mishnaNum);
                jsonObjectFlush();
                Just_finished_perush = true;
                break;
            case MULTIPLE_LINE_PASUK:
                if (pasuk != null) {
                    pasuk = pasuk + " " + line.extract(" ", " ");
                }
                else pasuk = line.extract(" ", " ");
                break;
            case BEGIN_PASUK:
                new_mishna = false;
                if (pasuk != null) {
                    pasuk = pasuk + " " + line.extract(" ", ":");
                }
                else pasuk = line.extract(" ", ":");

                jsonObjectFlush();
                jsonObject().add(URI, "jbr:mishna-" + sederNum + "-" + masechetNum + "-" + perekNum + "-" + mishnaNum);
                jsonObject().add(JBO_TEXT, stripVowels(pasuk));
                jsonObject().add(JBO_TEXT_NIKUD, pasuk);
                jsonObject().add(RDFS_LABEL, "משנה " + masechetHE + " " + perekLetter + " " + mishnaLetter);
                jsonObject().addToArray(JBO_WITHIN, "jbr:mishna");
                jsonObject().addToArray(JBO_WITHIN, "jbr:mishna-" + sederNum);
                jsonObject().addToArray(JBO_WITHIN, "jbr:mishna-" + sederNum + "-" + masechetNum);
                jsonObject().addToArray(JBO_WITHIN, "jbr:mishna-" + sederNum + "-" + masechetNum + "-" + perekNum);
                jsonObject().add(JBO_INTERPRETS, "jbr:mishna-" + sederNum + "-" + masechetNum + "-" + perekNum + "-" + mishnaNum);
                jsonObjectFlush();
                pasuk = null;
                break;
            case NO_MATCH:
                break;
        }
    }
    protected String getUri() {
        return "jbr:mishna-" + mefaresh + "-" + sederNum + "-" + masechetNum + "-" + perekNum + "-" + mishnaNum;
    }

    protected  String getMasechetUri(){
        return "jbr:mishna-" + sederNum + "-" + masechetNum;
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
            sederName = "זרעים";
            masechetNum = i + 1;
        }
        else if (i < 23){
            sederNum = 2;
            sederName = "מועד";
            masechetNum = i + 1 -11;
        }
        else if (i < 30){
            sederNum = 3;
            sederName = "נשים";
            masechetNum = i + 1 -23;
        }
        else if (i < 40){
            sederNum = 4;
            sederName = "נזיקין";
            masechetNum = i + 1 -30;
        }
        else if (i < 51){
            sederNum = 5;
            sederName = "קדשים";
            masechetNum = i + 1 -40;
        }
        else if (i < 63){
            sederNum = 6;
            sederName = "טהרות";
            masechetNum = i + 1 -51;
        }
        return -1;
    }

}
