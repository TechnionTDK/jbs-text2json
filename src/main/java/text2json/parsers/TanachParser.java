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
    private static final String BEGIN_SEFER = "begin_sefer";

    private int bookNum = 0;
    private String bookName;
    private int perekNum = 0;
    private String perekTitle;
    private int pasukNum = 0;
    private int parashaNum = 0;
    private String pasukTitle;
    private int positionInParasha;

    public TanachParser() {
        createPackagesJson();
    }

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
            public String type() { return BEGIN_SEFER;}
            @Override
            public boolean match(Line line) {
                return line.beginsWith("ספר") && line.wordCount() <= 4;}
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
            case BEGIN_SEFER:
                bookName = line.extract("ספר", " ");
                bookNum = getBookNum(bookName);
                // adding sefer triples in packages json
                packagesJsonObject().add(URI, getBookUri());
                packagesJsonObject().add(RDFS_LABEL, line.getLine());
                packagesJsonObject().add(JBO_POSITION, bookNum);
                packagesJsonObjectFlush();
                break;
            case BEGIN_PARASHA: // we assume bookNum is initialized
                jsonObjectFlush();
                positionInParasha = 0;
                parashaNum++;
                // adding parasha triples in packages json
                packagesJsonObject().add(URI, getParashaUri());
                packagesJsonObject().add(RDFS_LABEL, line.getLine());
                packagesJsonObject().add(JBO_POSITION, getFixedParashaPosition());
                packagesJsonObjectFlush();
                break;
            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                pasukNum = 0;
                perekTitle = line.extract("פרק-", " ");

                // adding perek triples in packages json
                packagesJsonObject().add(URI, getPerekUri());
                packagesJsonObject().add(RDFS_LABEL, line.getLine());
                packagesJsonObject().add(JBO_POSITION, perekNum);
                packagesJsonObjectFlush();
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
                jsonObject().add(RDFS_LABEL, bookName + " " + perekTitle + " " + pasukTitle);
                jsonObject().add(JBO_SEFER, "jbr:tanach-" + bookNum);
                if(bookNum <= 5) {
                    jsonObject().add(JBO_POSITION_IN_PARASHA, positionInParasha);
                }
                addTitlesArray(bookName, perekTitle, pasukTitle);
                jsonObjectFlush();
                break;
            case BEGIN_PERUSH:
                jsonObjectFlush();
                break;
            case NO_MATCH:
                break;
        }
    }

    private String getParashaUri() {
        return JBR + "parasha-" + getFixedParashaPosition();
    }
    private String getBookUri() {
        return JBR + "tanach-" + bookNum;
    }
    private String getPerekUri() {
        return JBR + "tanach-" + bookNum + "-" + perekNum;
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

    private int getFixedParashaPosition() {
        switch(bookNum) {
            case 1: // Bereshit
                return parashaNum;
            case 2: // Shemot
                return parashaNum + 12;
            case 3: // Vayikra
                return parashaNum + 23;
            case 4: // Bamidbar
                return parashaNum + 33;
            case 5: // Devarim
                return parashaNum + 43;
        }

        return 0;
    }

    @Override
    protected String getUri() {
        return "jbr:tanach-" + bookNum + "-" + perekNum + "-" + pasukNum;
    }
}
