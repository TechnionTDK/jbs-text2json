package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;
import static text2json.JbsOntology.*;
import java.io.IOException;

/**
 * Created by USER on 06-Jan-17.
 */
public class TanachParser extends Parser {
    private static final String BEGIN_PEREK = "begin_perek";
    private static final String BEGIN_PASUK = "begin_pasuk";
    private static final String BEGIN_PERUSH = "begin_perush";
    private static final String BEGIN_PARASHA = "begin_parasha";

    private int bookNum = 0;
    private String bookTitle;
    private int perekNum = 0;
    private String perekTitle;
    private int pasukNum = 0;
    private String pasukTitle;
    //private String pasukText;
    private int positionInParasha;

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PARASHA;}
            @Override
            public boolean match(Line line) {
                return line.beginsWith("פרשת") && line.wordCount() <= 5;}
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK;}
            @Override
            public boolean match(Line line) {
                return line.contains(" פרק-") && line.wordCount() <= 4;}
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PASUK;}
            @Override
            public boolean match(Line line) {
                return line.beginsWith("{") && line.contains("}") && !line.beginsWith("(") && !line.contains("\"")
                        && !line.contains("!") && !line.endsWith("}") && !line.endsWith("} ");}
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PERUSH;}
            @Override
            public boolean match(Line line) {
                return line.beginsWith(")");}
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch (type){
            case BEGIN_PARASHA:
                jsonObjectFlush();
                positionInParasha = 0;
                break;
            case BEGIN_PEREK:
                if (bookNum == 0) {
                    bookTitle = line.extract(" ", " פרק");
                    bookNum = getBookNum(bookTitle);}
                jsonObjectFlush();
                perekNum++;
                pasukNum = 0;
                perekTitle = line.extract("פרק-", " ");
                break;
            case BEGIN_PASUK:
                jsonObjectFlush();
                positionInParasha++;
                pasukNum++;
                pasukTitle = line.extract("{", "}");
                jsonObject().add(URI, getUri());
                String end = line.contains(":") ? ":" : " ";
                jsonObject().add(JBO_TEXT, stripVowels(line.extract("}", end)));
                jsonObject().add(JBO_TEXT_NIKUD, line.extract("}", end));
                jsonObject().add(RDFS_LABEL, bookTitle + " " + perekTitle + " " + pasukTitle);
                jsonObject().add(JBO_SEFER, "jbr:tanach-" + bookNum);
                if(bookNum <= 5) {
                    jsonObject().add(JBO_POSITION_IN_PARASHA, positionInParasha);
                }
                addTitlesArray(bookTitle, perekTitle, pasukTitle);
                jsonObjectFlush();
                break;
            case BEGIN_PERUSH:
                jsonObjectFlush();
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
            if(bookTitle.equals(bookNames[i]))
                return i+1;
        }
        return -1;
    }

    private void addTitlesArray(String bookTitle, String perekTitle, String pasukTitle) {
        jsonObject().openArray("titles");
        jsonObject().openObject();
        jsonObject().add("title", bookTitle + " " + perekTitle + " " + pasukTitle);
        jsonObject().closeObject();
        jsonObject().openObject();
        jsonObject().add("title", bookTitle + " פרק " + perekTitle + " פסוק " + pasukTitle);
        jsonObject().closeObject();
        jsonObject().closeArray();
    }

    @Override
    protected String getUri() {
        return "jbr:tanach-" + bookNum + "-" + perekNum + "-" + pasukNum;
    }
}
