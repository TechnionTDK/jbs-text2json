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
    private static final String BEGIN_PEREK = "begin_perek";
    private static final String BEGIN_AMUD = "begin_amud";
    private static final String BEGIN_PERUSH = "begin_perush";
    private static final String BEGIN_AMUD_TEXT = "begin_daf_text";

    private boolean perekText = false;

    private int masechetNum = 0;
    private String masechetTitle;
    private int perekNum = 0;
    private String perekTitle;
    private int dafNum = 1;
    private int amudNum = 0;
    private String dafTitle;
    private String amudTitle;
    private int positionInMasechet = 0;
    private String mefarshim[] = {"rashi", "tosafot"};
    private String mefarshimHeb[] = {"רשי", "תוספות"};

    private int currAmud = 0;
    private int currIdx = 1;
    private int checkedMasechet = 4;

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_MASECHET;
            }
            @Override
            public boolean match(Line line) {
                return line.beginsWith("מסכת ") && line.wordCount() <= 5;
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
                        line.beginsWith("תוספות") || line.beginsWith(" תוספות");
                        //|| line.beginsWith("רשב\"ם") || line.beginsWith(" רשב\"ם");
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
                masechetTitle = line.extract("מסכת ", " (מ)");
                masechetNum = getMasechetNum(masechetTitle);

                //System.out.println("Masechet " + masechetTitle + " Num " + masechetNum);

                break;
            case BEGIN_PEREK:
                jsonObjectFlush();
                perekText = true;
                perekNum++;
                perekTitle = line.getLine();

                //System.out.println("================>> perekNum " + perekNum);
                //System.out.println("================>> perekTitle " + perekTitle);

                break;
            case BEGIN_AMUD:
                jsonObjectFlush();
                perekText = false;
                dafTitle = line.extract("דף ", "-");
                amudTitle = line.extract(" - ", " ");
                if(amudTitle.equals("א")){
                    amudNum = 1;
                    dafNum++;}
                else {
                    amudNum++;}
                positionInMasechet++;

                //System.out.println("==========>> dafNum " + dafNum);
                //System.out.println("=============>> amudNum " + amudNum);
                if(amudNum == currAmud && masechetNum == checkedMasechet) {
                    System.out.println("Skiped amud! <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    System.out.println("masechetNum " + masechetNum + " dafNum " + dafNum + " amudNum " + amudNum);
                }
                currAmud = amudNum;

                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_MASECHET, "bavli-" + masechetNum);
                jsonObject().add(JBO_PEREK, perekTitle);
                jsonObject().add(RDFS_LABEL, masechetTitle + " " + dafTitle + " " + amudTitle);
                jsonObject().add(JBO_POSITION, positionInMasechet);
                break;
            case BEGIN_AMUD_TEXT:
                jsonObject().add(JBO_TEXT, line.getLine());
                jsonObjectFlush();
                break;
            case BEGIN_PERUSH:
                //TODO: what to do with other mefarshim? starting from masechet 4
                if(perekText)
                    break;
                jsonObjectFlush();
                int mefareshIdx = line.beginsWith("רש\"י") || line.beginsWith(" רש\"י") ? 0 :
                        line.beginsWith("תוספות") || line.beginsWith(" תוספות") ? 1 : -1;

                if(mefareshIdx == currIdx && masechetNum == checkedMasechet) {
                    System.out.println("Skiped perush! <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<=========");
                    System.out.println("masechetNum " + masechetNum + " dafNum " + dafNum + " amudNum " + amudNum + " mefaresh: " + mefarshim[mefareshIdx]);
                }
                currIdx = mefareshIdx;

                jsonObject().add(URI, getMefareshUri(mefareshIdx));
                jsonObject().add(JBO_MASECHET, "bavli-" + masechetNum);
                jsonObject().add(JBO_PEREK, perekTitle);
                jsonObject().add(RDFS_LABEL, mefarshimHeb[mefareshIdx] + " " + masechetTitle + " " + dafTitle + " " + amudTitle);
                jsonObject().add(JBO_POSITION, positionInMasechet);
                jsonObject().add(JBO_MEFARESH, getUri());
                jsonObject().add(JBO_TEXT, line.getLine());
                jsonObjectFlush();
                break;
            case NO_MATCH:
                if(!perekText)
                    onLineMatch(BEGIN_AMUD_TEXT, line);
                break;
        }
    }


    private int getMasechetNum(String masechetTitle) {
        String masachotNames[] = {"ברכות", "שבת", "עירובין", "פסחים", "שקלים", "ראש השנה", "יומא", "סוכה", "ביצה",
                "תענית","מגילה", "מועד קטן","חגיגה","יבמות","כתובות","נדרים","נזיר","סוטה","גיטין","קידושין","בבא קמא",
                "בבא מציעא","בבא בתרא","סנהדרין","מכות","שבועות", "עבודה זרה", "הוריות", "עדיות", "זבחים", "מנחות",
                "חולין", "בכורות", "ערכין", "תמורה", "כריתות", "מעילה", "תמיד", "נדה"};
        for (int i = 0; i < 39; i++){
            if(masechetTitle.equals(masachotNames[i]))
                return i+1;
        }
        return -1;
    }

    @Override
    protected String getUri() {
        return "jbr:bavli-" + masechetNum + "-" + dafNum + "-" + amudNum;
    }

    private String getMefareshUri(int mefareshIdx) {
        return "jbr:bavli-" + mefarshim[mefareshIdx] + "-" + masechetNum + "-" + dafNum + "-" + amudNum;
    }
}
