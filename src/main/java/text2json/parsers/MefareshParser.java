package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;
import java.io.IOException;
import static text2json.JbsOntology.*;


public class MefareshParser extends Parser {
    String[] MefarshimEn = {"rashi", "ramban", "orhachaim", "ibnezra", "baalhaturim", "onkelos", "sforno", "kliyekar",
            "daatzkenim", "metzudatdavid", "metzudattzion", "malbiminyan", "malbimmilot", "ralbag", "malbim", "yonatan", "sifteychachamim"};
    String[] MefarshimHe = {"רש\"י", "הרמב\"ן", "אור החיים", "אבן עזרא", "בעל הטורים" , "אונקלוס", "ספורנו", "כלי יקר",
            "דעת זקנים", "מצודת דוד", "מצודת ציון", "מלבי\"ם באור הענין", "מלבי\"ם באור המלות", "רלב\"ג", "מלבי\"ם",
            "תרגום יונתן", "שפתי חכמים"};
    String[] ParashotHe = {"פרשת בראשית", "פרשת נח", "פרשת לך לך", "פרשת וירא", "פרשת חיי שרה" ,"פרשת תולדות" ,"פרשת ויצא",
            "פרשת וישלח", "פרשת וישב", "פרשת מקץ", "פרשת ויגש", "פרשת ויחי", "פרשת שמות", "פרשת וארא", "פרשת בא",
            "פרשת בשלח", "פרשת יתרו", "פרשת משפטים", "פרשת תרומה", "פרשת תצוה", "פרשת תשא כי", "פרשת ויקהל", "פרשת פקודי",
            "פרשת ויקרא", "פרשת צו", "פרשת שמיני", "פרשת תזריע", "פרשת מצורע", "פרשת אחרי מות", "פרשת קדושים", "פרשת אמור",
            "פרשת בהר", "פרשת בחקתי", "פרשת במדבר", "פרשת נשא", "פרשת בהעלתך", "פרשת שלח לך", "פרשת קרח", "פרשת חקת",
            "פרשת בלק", "פרשת פינחס", "פרשת מטות", "פרשת מסעי", "פרשת דברים", "פרשת ואתחנן", "פרשת עקב", "פרשת ראה",
            "פרשת שופטים", "פרשת כי תצא", "פרשת כי תבוא", "פרשת נצבים", "פרשת וילך", "פרשת האזינו", "פרשת וזאת הברכה"};
    private static final String BEGIN_PARASHA = "begin_parasha";
    private static final String BEGIN_PEREK = "begin_perek";
    private static final String BEGIN_PERUSH = "begin_perush";
    private static final String BEGIN_PASUK = "begin_pasuk";
    private static final String MULTIPLE_LINE_PERUSH = "multiple_line_perush";
    private static final String MEFARESH_PARSER_ID = "parser.mefareshTanach";
    private int bookNum = 0;
    private int parashaNum = 1;
    private int perekNum = 0;
    private int pasukNum = 0;
    private int positionInParasha = 0;
    private int positionInPerek = 0;
    private String mefaresh;
    private int mefareshId;
    private String bookTitle;
    private String perekLetter;
    private String pasukLetter;
    private String begining_of_long_perush = null;
    private int mefareshId_Long_Perush;
    boolean Just_finished_perush = false;
    private String perush;

        protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            public String type() {
                return BEGIN_PARASHA;
            }
            public boolean match(Line line) {
                return line.beginsWith("פרשת") && line.wordCount() <= 4;
            }
        });

        registerMatcher(new LineMatcher() {
            public String type() {
                return BEGIN_PEREK;
            }
            public boolean match(Line line) {
                return line.contains("פרק-") && line.wordCount() <= 4;
            }
        });

        registerMatcher(new LineMatcher() {
            public String type() { return BEGIN_PASUK; }
            public boolean match(Line line) { return line.beginsWith("{");
            }
        });

        registerMatcher(new LineMatcher() {
            public String type() {
                return BEGIN_PERUSH;
            }
            public boolean match(Line line) { return line.endsWith(MefarshimHe);
            }
        });

        registerMatcher(new LineMatcher() {
            public String type() {
                return MULTIPLE_LINE_PERUSH;
            }
            public boolean match(Line line) { return ((line.beginsWith(MefarshimHe) && !(line.endsWith(MefarshimHe)))
                    || (begining_of_long_perush != null));}
        });

    }

    public MefareshParser() { return;}

    protected int get_mefareshId(Line line){
        String baseLine = line.getLine();
        for(int i=0; i < MefarshimHe.length; i++){
            if (baseLine.endsWith(": (" + MefarshimHe[i] + ")")) return i;
        }
        return -1;
    }
    protected int get_mefareshId_from_beginning(Line line){
        String baseLine = line.getLine();
        for(int i=0; i < MefarshimHe.length; i++){
            if (baseLine.startsWith(MefarshimHe[i])) return i;
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
            case BEGIN_PARASHA:
                parashaNum = Find_Index_In_Arr(ParashotHe, line.getLine()) + 1;
                positionInParasha = 0;
                break;
            case BEGIN_PEREK:
                if (bookNum == 0){
                    bookTitle = line.extract(" ", " פרק");
                    bookNum = getBookNum(bookTitle);
                    System.out.println("booknum:" + bookNum);
                }
                perekNum++;
                pasukNum = 0;
                positionInPerek = 0;
                perekLetter = line.extract("פרק-", " ");
                break;
            case BEGIN_PASUK:
                String curr_pasuk = line.extract("{", "}");
                if(pasukLetter == curr_pasuk)
                    break;
                pasukLetter = curr_pasuk;
                pasukNum++;
                positionInPerek++;
                positionInParasha++;
                break;
            case MULTIPLE_LINE_PERUSH:
                if (begining_of_long_perush == null){
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
                jsonObject().add(RDFS_LABEL, MefarshimHe[mefareshId] + " " + bookTitle + " " + perekLetter + " " + pasukLetter);
                jsonObject().add(JBO_NAME, MefarshimHe[mefareshId]);
                jsonObject().add(JBO_SEFER, "jbr:tanach-" + bookNum);
                //addTitlesArray (bookTitle, perekLetter, perekLetter);
                if (bookNum <= 5) {
                    jsonObject().add(JBO_PARASHA, "jbr:parasha-" + parashaNum);
                }
                jsonObject().add(JBO_PEREK, "jbr:tanach-" + bookNum + "-" + perekNum);
                jsonObject().add(JBO_INTERPRETS, "jbr:tanach-" + bookNum + "-" + perekNum + "-" + pasukNum);
                jsonObject().add(JBO_POSITION_IN_PEREK, Integer.toString(positionInPerek));
                if (bookNum <=5) {
                    jsonObject().add(JBO_POSITION_IN_PARASHA, Integer.toString(positionInParasha));
                }
                jsonObjectFlush();
                begining_of_long_perush = null;
                break;
            case NO_MATCH:
                break;
        }
    }

    private int getBookNum(String bookTitle) {
        String bookNames[] = {"בראשית", "שמות", "ויקרא", "במדבר", "דברים", "יהושע", "שופטים", "שמואל א", "שמואל ב",
                "מלכים א","מלכים ב", "ישעיה","ירמיה","יחזקאל","הושע","יואל","עמוס","עובדיה","יונה","מיכה","נחום",
                "חבקוק","צפניה","חגי","זכריה","מלאכי", "תהילים", "משלי", "איוב", "שיר השירים", "רות", "איכה", "קהלת",
                "אסתר", "דניאל", "עזרא", "נחמיה", "דברי הימים א", "דברי הימים ב"};
        for (int i = 0; i < 39; i++){
            if(bookTitle.contains(bookNames[i]))
                return i+1;
        }
        return -1;
    }
        @Override
    protected String getUri() {
        return "jbr:tanach-" + mefaresh + "-" + bookNum + "-" + perekNum + "-" + pasukNum;
    }

    /*
    @Override
    protected void onEOF() {

    }
    */
}
