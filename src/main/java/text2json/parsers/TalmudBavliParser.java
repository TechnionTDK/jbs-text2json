package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;
import static text2json.JbsOntology.*;
import java.io.IOException;

/**
 * Created by USER on 20-Jan-17.
 */
public class TalmudBavliParser extends Parser {
    private static final String BEGIN_MASECHET = "begin_masechet";
    private static final String BEGIN_AMUD = "begin_amud";
    private static final String BEGIN_AMUD_TEXT = "begin_daf_text";

    private boolean amud2ndPartText = false;

    private int masechetNum = 0;
    private String masechetTitle;
    private int perekNum = 0;
    private String perekTitle;
    private int dafNum = 1;
    private int amudNum = 0;
    private String dafTitle;
    private String amudTitle;
    private int positionInMasechet = 0;
    private String mefarshim[] = {"rashi", "tosafot", "rashbam", "ran"};
    private String mefarshimHeb[] = {"רש\"י", "תוספות","רשב\"ם", "ר\"נ"};

    public TalmudBavliParser() { createPackagesJson(); }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_MASECHET;
            }
            @Override
            public boolean match(Line line) {
                return (line.beginsWith("מסכת") || line.endsWith(" (מ)")) && line.wordCount() <= 5;
            }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_PEREK;
            }
            @Override
            public boolean match(Line line) {
                return line.beginsWith("פרק ") && line.wordCount() <= 7 && line.contains(" - ");
            }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_AMUD;
            }
            @Override
            public boolean match(Line line) {
                return line.beginsWith("דף ") && line.wordCount() <= 5
                        && line.contains(" - ");
            }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_PERUSH;
            }
            @Override
            public boolean match(Line line) {
                return line.beginsWith("רש\"י") || line.beginsWith(" רש\"י") ||
                        line.beginsWith("פירוש") || line.beginsWith(" פירוש") ||
                        line.beginsWith("תוספות") || line.beginsWith(" תוספות") ||
                        line.beginsWith("רשב\"ם") || line.beginsWith(" רשב\"ם") ||
                        line.beginsWith("ר\"נ") || line.beginsWith(" ר\"נ");
            }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_AMUD_TEXT;
            }
            @Override
            public boolean match(Line line) {
                return false;
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type){
            case BEGIN_MASECHET:
                masechetTitle =  line.extract("מסכת ", " (מ)");
                masechetNum = getMasechetNum(masechetTitle);
                if (masechetNum == 36){ //masechet tamid starts from daf 25 amud 2
                    dafNum = 25;
                    amudNum = 1;
                }
                // adding masechet triplets to packages json
                packagesJsonObject().add(URI, getMasechetUri());
                packagesJsonObject().add(RDFS_LABEL, "מסכת " + masechetTitle);
                packagesJsonObject().add(JBO_POSITION, masechetNum);
                packagesJsonObjectFlush();
                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                amud2ndPartText = true;
                perekNum++;
                perekTitle = line.getLine();

                // adding masechet triplets to packages json
                packagesJsonObject().add(URI, getPerekUri());
                packagesJsonObject().add(RDFS_LABEL, perekTitle);
                packagesJsonObject().add(JBO_POSITION, perekNum);
                packagesJsonObjectFlush();
                break;

            case BEGIN_AMUD:
                jsonObjectFlush();
                amud2ndPartText = false;
                dafTitle = line.extract("דף ", "-");
                amudTitle = line.extract(" - ", " ");
                if(amudTitle.equals("א")){
                    amudNum = 1;
                    dafNum++;}
                else {
                    amudNum++;}
                positionInMasechet++;
                jsonObject().add(URI, getUri());
                jsonObject().addToArray(JBO_WITHIN, "bavli-" + masechetNum);
                jsonObject().addToArray(JBO_WITHIN, getPerekUri());
                jsonObject().add(RDFS_LABEL, "מסכת " + masechetTitle + " " + dafTitle + " " + amudTitle);
                jsonObject().add(JBO_POSITION, positionInMasechet);

                /* no need for amud package
                if(amudTitle.equals("א") || (masechetNum == 36 && dafNum == 25)) {
                    // adding daf triplets to packages json
                    packagesJsonObject().add(URI, getDafUri());
                    packagesJsonObject().add(RDFS_LABEL, dafTitle);
                    packagesJsonObject().add(JBO_POSITION, dafNum);
                    packagesJsonObjectFlush();
                }
                */
                break;

            case BEGIN_AMUD_TEXT:
                if(amud2ndPartText) { //same daf and amud continues in next perek
                    jsonObject().add(URI, getUri());
                    jsonObject().addToArray(JBO_WITHIN, "bavli-" + masechetNum);
                    jsonObject().addToArray(JBO_WITHIN, getPerekUri());
                    jsonObject().add(RDFS_LABEL, masechetTitle + " " + dafTitle + " " + amudTitle);
                    jsonObject().add(JBO_POSITION, positionInMasechet);
                    jsonObject().append(JBO_TEXT, line.getLine());
                }
                else {
                    jsonObject().append(JBO_TEXT, line.getLine());
                }
                //jsonObjectFlush();
                break;

            case BEGIN_PERUSH:
                jsonObjectFlush();
                int mefareshIdx = line.beginsWith("רש\"י") || line.beginsWith(" רש\"י")
                        || line.beginsWith("פירוש") || line.beginsWith(" פירוש") ? 0 :
                        line.beginsWith("תוספות") || line.beginsWith(" תוספות") ? 1 :
                        line.beginsWith("רשב\"ם") || line.beginsWith(" רשב\"ם") ? 2 :
                        line.beginsWith("ר\"נ") || line.beginsWith(" ר\"נ") ? 3 : -1;
                jsonObject().add(URI, getMefareshUri(mefareshIdx));
                jsonObject().addToArray(JBO_WITHIN, "bavli-" + masechetNum);
                jsonObject().addToArray(JBO_WITHIN, getPerekUri());
                jsonObject().add(RDFS_LABEL, mefarshimHeb[mefareshIdx] + " " + masechetTitle + " " + dafTitle + " " + amudTitle);
                jsonObject().add(JBO_POSITION, positionInMasechet);
                jsonObject().add(JBO_EXPLAINS, getUri());
                jsonObject().append(JBO_TEXT, line.getLine());
                //jsonObjectFlush();
                break;

            case NO_MATCH:
                if(perekNum > 0) {
                    onLineMatch(BEGIN_AMUD_TEXT, line);
                }
                break;
        }
    }


    private int getMasechetNum(String masechetTitle) {
        String masachotNames[] = {"ברכות", "שבת", "עירובין", "פסחים", "ראש השנה", "יומא", "סוכה", "ביצה",
                "תענית","מגילה", "מועד קטן","חגיגה","יבמות","כתובות","נדרים","נזיר","סוטה","גיטין","קידושין","בבא קמא",
                "בבא מציעא","בבא בתרא","סנהדרין","מכות","שבועות", "עבודה זרה", "הוריות", "זבחים", "מנחות",
                "חולין", "בכורות", "ערכין", "תמורה", "כריתות", "מעילה", "תמיד", "נדה"};
        for (int i = 0; i < 37; i++){
            if(masechetTitle.equals(masachotNames[i]))
                return i+1;
        }
        return -1;
    }

    @Override
    protected String getUri() {
        return "jbr:bavli-" + masechetNum + "-" + dafNum + "-" + amudNum;
    }
    protected String getMasechetUri() { return "jbr:bavli-" + masechetNum; }
    private String getMefareshUri(int mefareshIdx) {
        return "jbr:bavli-" + mefarshim[mefareshIdx] + "-" + masechetNum + "-" + dafNum + "-" + amudNum;
    }
    private String getDafUri() {return "jbr:bavli-" + masechetNum + "-" + dafNum; }
    private String getPerekUri() { return "jbr:bavli-perek-" + masechetNum + "-" + perekNum; }
}
