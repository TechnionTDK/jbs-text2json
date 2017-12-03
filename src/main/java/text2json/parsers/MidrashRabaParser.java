package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;

import java.io.IOException;

import static text2json.JbsOntology.*;
import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by omishali on 16/02/2017.
 */
public class MidrashRabaParser extends Parser {
    private static final String MULTIPLE_LINE_SAIF = "multipul_line_saif";
    int bookNum = 0;
    String bookName = null;
    String SederName = null;
    int sederNum = 0;
    String parashaName = null;
    int parashaNum = 0;
    String SeifLetter = null;
    int seifPosition = 0;
    int getSeifPositionInParasha = 0;
    String seif = null;
    Boolean longSeif = false;

    public MidrashRabaParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_SEFER;
            }

            @Override
            public boolean match(Line line) {
                return line.contains("מדרש רבה");
            }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_SEDER;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("סדר") && line.wordCount() <= 4;
            }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_PARASHA;
            }

            @Override
            public boolean match(Line line) {
                return (line.beginsWith("פרשה") && line.wordCount() <= 2) || (line.getLine().equals("פתיחתא דחכימי"))
                        || (line.getLine().equals("פתיחתא דרות רבה")) || (line.getLine().equals("פתיחתא דאסתר רבא"));
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_SAIF;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith(HEB_LETTERS_INDEX, " ");
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return MULTIPLE_LINE_SAIF;
            }

            @Override
            public boolean match(Line line) {
                return ((!(line.beginsWith(HEB_LETTERS_INDEX, " "))) && longSeif);
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:
                bookName = line.extract("מדרש רבה - ", " ");
                bookNum = getBookNum(bookName);
                // adding sefer object in packages json
                packagesJsonObject().add(URI, JBR_SECTION + "tanach-"+getBookId()+"-"+ bookNum);
                packagesJsonObject().add(JBO_BOOK, JBR_BOOK + getBookId());
                packagesJsonObject().add(RDFS_LABEL, "מדרש רבה " + bookName);
                packagesJsonObjectFlush();
                break;
            case BEGIN_SEDER:
                if (sederNum!=0 && seif!=null){
                    createObject();
                    longSeif = false;
                }
                SederName = line.extract("סדר ", " ");
                sederNum++;
                break;
            case BEGIN_PARASHA:
                if ((parashaNum!=0 && seif!=null) || (parashaName == "פתיחתא דחכימי" && seif!=null) ||
                        (parashaName == "פתיחתא דרות רבה" && seif!=null) || (parashaName == "פתיחתא דאסתר רבא" && seif!=null)){
                    createObject();
                    seif = null;
                    longSeif = false;
                }
                parashaName = line.extract("פרשה ", " ");
                parashaNum++;
                if ((line.getLine().contains("פתיחתא דחכימי")) || (line.getLine().contains("פתיחתא דרות רבה")) ||
                        (line.getLine().contains("פתיחתא דאסתר רבא"))){
                    parashaNum = 0;
                    parashaName = "פתיחתא דחכימי";
                }
                seifPosition = 0;
                //getSeifPositionInParasha = 0;
                // adding parasha object in packages json
                packagesJsonObject().add(URI, JBR_SECTION + "tanach-"+getBookId()+"-"+ bookNum +"-"+parashaNum);
                packagesJsonObject().addToArray(JBO_WITHIN, JBR_SECTION + "tanach-"+getBookId()+"-"+ bookNum);
                packagesJsonObject().add(RDFS_LABEL, "מדרש רבה " + bookName + " " + parashaName);
                packagesJsonObject().add(JBO_BOOK, getBookId());
                packagesJsonObject().add(JBO_POSITION, parashaNum);
                packagesJsonObjectFlush();
                break;
            case BEGIN_SAIF:
                if((seifPosition != 0)&& (seif!=null)) {
                    createObject();
                }
                SeifLetter = line.getFirstWord();
                seif = line.extract(SeifLetter+" ", " ");
                longSeif = true;
                seifPosition = getSeifNum(SeifLetter);
                endOfFile(line);
                break;
            case MULTIPLE_LINE_SAIF:
                seif = seif + " " + line.getLine();
                endOfFile(line);
                break;
            case NO_MATCH:
                break;
        }

    }

    private int getBookNum(String bookTitle) {
        String bookNames[] = {"חומש בראשית", "חומש שמות", "חומש ויקרא", "חומש במדבר", "חומש דברים", "שיר השירים רבה",
                "רות רבה","איכה רבתי", "קהלת רבה","אסתר רבה"};
        int bookNums[] = {1,2,3,4,5,30,31,32,33,34};
        for (int i = 0; i < 39; i++){
            if(bookTitle.equals(bookNames[i]))
                return bookNums[i];
        }
        return -1;
    }

    private int getSeifNum(String seifLetter){
        for (int i = 0; i<HEB_LETTERS_INDEX.length; i++){
            if (HEB_LETTERS_INDEX[i].equals(seifLetter)){
                return i+1;
            }
        }
        return -1;
    }

    protected void createObject() throws IOException{
        jsonObjectFlush();
        jsonObject().add(URI, getUri());
        jsonObject().add(JBO_TEXT, stripVowels(seif));
        jsonObject().add(JBO_TEXT_NIKUD, seif);
        jsonObject().addToArray(JBO_WITHIN, JBR_SECTION + "tanach-"+getBookId()+"-"+ bookNum);
        jsonObject().addToArray(JBO_WITHIN, JBR_SECTION + "tanach-"+getBookId()+"-"+ bookNum + "-" + parashaNum);
        jsonObject().add(JBO_BOOK, JBR_BOOK + getBookId());
        jsonObject().add(JBO_POSITION, seifPosition);
        if (bookNum <=5) {
            jsonObject().add(RDFS_LABEL, "מדרש רבה " + SederName + " " + parashaName + " " + SeifLetter);
        }
        else{
            jsonObject().add(RDFS_LABEL, "מדרש רבה " + bookName + " " + parashaName + " " + SeifLetter);
        }
        return;
    }

    @Override
    protected String getUri() {
        return JBR_TEXT + "tanach-"+getBookId()+"-"+ bookNum + "-" + parashaNum + "-" + seifPosition;
    }

    @Override
    protected String getBookId() {
        return "midrashraba";
    }

    protected void endOfFile(Line line) throws IOException{
        switch (bookNum){
            case 1:
                if ((parashaNum==100) && (seifPosition==13) && (stripVowels(line.getLine()).endsWith("ימצא בה תודה וקול זמרה."))){
                    createObject();
                }
                break;
            case 2:
                if ((parashaNum==52) && (seifPosition==5) && (stripVowels(line.getLine()).endsWith("ימצא בה תודה וקול זמרה."))){
                    createObject();
                }
                break;
            case 3:
                if ((parashaNum==37) && (seifPosition==4) && (stripVowels(line.getLine()).endsWith("כי לעולם חסדו."))){
                    createObject();
                }
                break;
            case 4:
                if ((parashaNum==23) && (seifPosition==14) && (stripVowels(line.getLine()).endsWith("ששון ושמחה ישיגו ונסו יגון ואנחה."))){
                    createObject();
                }
                break;
            case 5:
                if ((parashaNum==11) && (seifPosition==9) && (stripVowels(line.getLine()).endsWith("ברוך ה' לעולם אמן ואמן."))){
                    createObject();
                }
                break;
            case 30:
                if ((parashaNum==8) && (seifPosition==1) && (stripVowels(line.getLine()).endsWith("יהי רצון במהרה בימינו אמן."))){
                    createObject();
                }
                break;
            case 31:
                if ((parashaNum==8) && (seifPosition==1) && (stripVowels(line.getLine()).endsWith("מצאתי דוד עבדי."))){
                    createObject();
                }
                break;
            case 32:
                if ((parashaNum==5) && (seifPosition==22) && (stripVowels(line.getLine()).endsWith("דכעיס סופיה לאיתרציא."))){
                    createObject();
                }
                break;
            case 33:
                if ((parashaNum==12) && (seifPosition==1) && (stripVowels(line.getLine()).endsWith("שאני בת רב חסדא דקים ליה בחסדה בגוה [אם טוב ואם רע]."))){
                    createObject();
                }
                break;
            case 34:
                if ((parashaNum==10) && (seifPosition==14) && (stripVowels(line.getLine()).endsWith("נשלם מדרש אסתר"))){
                    createObject();
                }
                break;

        }
    }
}
