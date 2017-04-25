package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;

import java.io.IOException;

import static text2json.JbsOntology.*;
import static text2json.JbsOntology.JBO_TEXT;
import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by omishali on 16/02/2017.
 */
public class MidrashRabaParser extends Parser {
    private static final String MULTIPLE_LINE_SAIF = "multipul_line_saif";
    int BookNum = 0;
    String BookName = null;
    String SederName = null;
    int sederNum = 0;
    String ParashaName = null;
    int parashaNum = 0;
    String SeifLetter = null;
    int seifPosition = 0;
    int getSeifPositionInParasha = 0;
    String seif = null;
    Boolean Long_Seif = false;

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
                return (line.beginsWith("פרשה") && line.wordCount() <= 2) || (line.getLine().equals("פתיחתא דחכימי"));
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
                return ((!(line.beginsWith(HEB_LETTERS_INDEX, " "))) && Long_Seif);
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:
                BookName = line.extract("מדרש רבה - ", " ");
                BookNum = getBookNum(BookName);
                // adding sefer object in packages json
                packagesJsonObject().add(URI, "jbr:tanach-midrashraba-"+BookNum);
                packagesJsonObject().add(JBO_TEXT, line.getLine());
                packagesJsonObject().add(JBO_SEFER, "jbr:tanach-midrashraba-"+BookNum);
                packagesJsonObject().add(RDFS_LABEL, "מדרש רבה " + BookName);
                packagesJsonObjectFlush();
                break;
            case BEGIN_SEDER:
                if (sederNum!=0 && seif!=null){
                    CreateObject();
                    Long_Seif = false;
                }
                SederName = line.extract("סדר ", " ");
                sederNum++;
                break;
            case BEGIN_PARASHA:
                if ((parashaNum!=0 && seif!=null) || (ParashaName == "פתיחתא דחכימי" && seif!=null)){
                    CreateObject();
                    seif = null;
                    Long_Seif = false;
                }
                ParashaName = line.extract("פרשה ", " ");
                parashaNum++;
                if (line.getLine().contains("פתיחתא דחכימי")){
                    parashaNum = 0;
                    ParashaName = "פתיחתא דחכימי";
                }
                seifPosition = 0;
                //getSeifPositionInParasha = 0;
                // adding parasha object in packages json
                packagesJsonObject().add(URI, "jbr:tanach-midrashraba-"+BookNum+"-"+parashaNum);
                packagesJsonObject().add(JBO_TEXT, line.getLine());
                packagesJsonObject().add(JBO_SEFER, "jbr:tanach-midrashraba-"+BookNum);
                packagesJsonObject().add(RDFS_LABEL, "מדרש רבה " + BookName + " " + ParashaName );
                packagesJsonObject().add(JBO_POSITION, parashaNum);
                packagesJsonObjectFlush();
                break;
            case BEGIN_SAIF:
                if((seifPosition != 0)&& (seif!=null)) {
                    CreateObject();
                }
                SeifLetter = line.getFirstWord();
                seif = line.extract(SeifLetter+" ", " ");
                Long_Seif = true;
                seifPosition = getSeifNum(SeifLetter);
                EndOfFile(line);
                break;
            case MULTIPLE_LINE_SAIF:
                seif = seif + " " + line.getLine();
                EndOfFile(line);
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

    protected void  CreateObject() throws IOException{
        jsonObjectFlush();
        jsonObject().add(URI, getUri());
        jsonObject().add(JBO_TEXT, stripVowels(seif));
        jsonObject().add(JBO_TEXT_NIKUD, seif);
        jsonObject().add(JBO_SEFER, "jbr:tanach-midrashraba-"+ BookNum);
        jsonObject().add(JBO_PARASHA, "jbr:tanach-midrashraba-"+ BookNum + "-" + parashaNum);
        jsonObject().add(JBO_POSITION, seifPosition);
        if (BookNum<=5) {
            jsonObject().add(RDFS_LABEL, "מדרש רבה " + SederName + " " + ParashaName + " " + SeifLetter);
        }
        else{
            jsonObject().add(RDFS_LABEL, "מדרש רבה " + BookName + " " + ParashaName + " " + SeifLetter);
        }
        return;
    }

    @Override
    protected String getUri() {
        return "jbr:tanach-midrashraba-"+ BookNum + "-" + parashaNum + "-" + seifPosition;
    }

    protected void EndOfFile(Line line) throws IOException{
        switch (BookNum){
            case 1:
                if ((parashaNum==100) && (seifPosition==13) && (stripVowels(line.getLine()).endsWith("ימצא בה תודה וקול זמרה."))){
                    CreateObject();
                }
                break;
            case 2:
                if ((parashaNum==52) && (seifPosition==5) && (stripVowels(line.getLine()).endsWith("ימצא בה תודה וקול זמרה."))){
                    CreateObject();
                }
                break;
            case 3:
                if ((parashaNum==37) && (seifPosition==4) && (stripVowels(line.getLine()).endsWith("כי לעולם חסדו."))){
                    CreateObject();
                }
                break;
            case 4:
                if ((parashaNum==23) && (seifPosition==14) && (stripVowels(line.getLine()).endsWith("ששון ושמחה ישיגו ונסו יגון ואנחה."))){
                    CreateObject();
                }
                break;
            case 5:
                if ((parashaNum==11) && (seifPosition==9) && (stripVowels(line.getLine()).endsWith("ברוך ה' לעולם אמן ואמן."))){
                    CreateObject();
                }
                break;
            case 30:
                if ((parashaNum==8) && (seifPosition==1) && (stripVowels(line.getLine()).endsWith("יהי רצון במהרה בימינו אמן."))){
                    CreateObject();
                }
                break;
            case 31:
                if ((parashaNum==8) && (seifPosition==1) && (stripVowels(line.getLine()).endsWith("מצאתי דוד עבדי."))){
                    CreateObject();
                }
                break;
            case 32:
                if ((parashaNum==5) && (seifPosition==22) && (stripVowels(line.getLine()).endsWith("דכעיס סופיה לאיתרציא"))){
                    CreateObject();
                }
                break;
            case 33:
                if ((parashaNum==12) && (seifPosition==1) && (stripVowels(line.getLine()).endsWith("שאני בת רב חסדא דקים ליה בחסדה בגוה [אם טוב ואם רע]."))){
                    CreateObject();
                }
                break;
            case 34:
                if ((parashaNum==10) && (seifPosition==14) && (stripVowels(line.getLine()).endsWith("נשלם מדרש אסתר"))){
                    CreateObject();
                }
                break;

        }
    }
}
