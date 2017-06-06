package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;
import java.io.IOException;
import static text2json.JbsOntology.*;


public class MefareshParser extends Parser {
    String[] MEFARSHIM_EN = {"rashi", "ramban", "orhachaim", "ibnezra", "baalhaturim", "onkelos", "sforno", "keliyekar",
            "daatzkenim", "metzudatdavid", "metzudattzion", "malbiminyan", "malbimmilot", "ralbag", "malbim", "yonatan", "sifteychachamim"};
    String[] MEFARSHIM_HE = {"רש\"י", "הרמב\"ן", "אור החיים", "אבן עזרא", "בעל הטורים" , "אונקלוס", "ספורנו", "כלי יקר",
            "דעת זקנים", "מצודת דוד", "מצודת ציון", "מלבי\"ם באור הענין", "מלבי\"ם באור המלות", "רלב\"ג", "מלבי\"ם",
            "תרגום יונתן", "שפתי חכמים"};
    String[] PARASHOT_HE = {"פרשת בראשית", "פרשת נח", "פרשת לך לך", "פרשת וירא", "פרשת חיי שרה" ,"פרשת תולדות" ,"פרשת ויצא",
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
            public boolean match(Line line) { return line.beginsWith(MEFARSHIM_HE);
            }
        });

    }

    protected int getMefareshId(Line line){
        String text = line.getLine();
        for(int i = 0; i < MEFARSHIM_HE.length; i++){
            if (text.startsWith(MEFARSHIM_HE[i])) return i;
        }
        return -1;
    }

    private int findIndexInArray(String[] Arr, String val){
        for (int i = 0; i < Arr.length; i++){
            if (Arr[i].contains(val)) return i;
        }
        return -1;
    }
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type){
            case BEGIN_PARASHA:
                parashaNum = findIndexInArray(PARASHOT_HE, line.getLine()) + 1;
                positionInParasha = 0;
                break;
            case BEGIN_PEREK:
                if (bookNum == 0){
                    bookTitle = line.extract(" ", " פרק");
                    bookNum = getBookNum(bookTitle);
                }
                perekNum++;
                pasukNum = 0;
                positionInPerek = 0;
                perekLetter = line.extract("פרק-", " ");
                break;
            case BEGIN_PASUK:
                jsonObjectFlush(); // for the previous perush...
                String currPasuk = line.extract("{", "}");
                pasukLetter = currPasuk;
                pasukNum++;
                positionInPerek++;
                positionInParasha++;
                break;
            case BEGIN_PERUSH:
                mefareshId = getMefareshId(line);
                mefaresh = MEFARSHIM_EN[mefareshId];
                jsonObjectFlush();
                jsonObject().add(URI, getUri());
                jsonObject().append(JBO_TEXT, line.getLine());
                jsonObject().add(RDFS_LABEL, MEFARSHIM_HE[mefareshId] + " " + bookTitle + " " + perekLetter + " " + pasukLetter);
                jsonObject().add(JBO_NAME, MEFARSHIM_HE[mefareshId]);
                jsonObject().addToArray(JBO_WITHIN, "jbr:tanach-" + bookNum);
                if (bookNum <= 5) {
                    jsonObject().addToArray(JBO_WITHIN, "jbr:parasha-" + parashaNum);
                }
                jsonObject().addToArray(JBO_WITHIN, "jbr:tanach-" + bookNum + "-" + perekNum);
                jsonObject().add(JBO_EXPLAINS, "jbr:tanach-" + bookNum + "-" + perekNum + "-" + pasukNum);
                jsonObject().add(JBO_POSITION_IN_PEREK, Integer.toString(positionInPerek));
                if (bookNum <=5) {
                    jsonObject().add(JBO_POSITION_IN_PARASHA, Integer.toString(positionInParasha));
                }
                break;
            case NO_MATCH:
                // add line to the current perush
                jsonObject().append(JBO_TEXT, line.getLine());
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
}
