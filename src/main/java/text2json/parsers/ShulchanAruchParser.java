package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;
import static text2json.JbsOntology.*;
import java.io.IOException;
import static text2json.JbsUtils.*;
/**
 * Created by USER on 02-Mar-17.
 */
public class ShulchanAruchParser extends Parser {
    protected static final String BEGIN_CHELEK = "begin_sefer";  //in parser
    protected static final String BEGIN_HALACHOT = "begin_halacha";
    protected static final String BEGIN_SIMAN = "begin_siman";
    protected static final String BEGIN_SAIF = "begin_saif";  //in parser
    protected static final String BEGIN_PERUSH = "begin_perush";  //in parser

    private int chelekNum = 0;
    private String chelekTitle;
    private int halachotNum = 0;
    private String halachotTitle;
    private int simanNum = 0;
    private String simanHebIdx;
    private String simanTitle;
    private int saifNum = 0;
    private String saifHebIdx;
    private String saifText;
    private int positionInChelek = 0;

    public ShulchanAruchParser() { createPackagesJson(); }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_CHELEK;
            }
            @Override
            public boolean match(Line line) {
                return line.contains("שלחן ערוך - ") && line.endsWith("(מ)") && line.wordCount() <= 10; }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_HALACHOT;
            }
            @Override
            public boolean match(Line line) {
                return (line.beginsWith("הלכות ") || line.beginsWith("דיני ")) && line.wordCount() <= 10;
            }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_SIMAN;
            }
            @Override
            public boolean match(Line line) {
                return line.beginsWith("סימן ") && line.wordCount() <= 27; }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_SAIF;
            }
            @Override
            public boolean match(Line line) {
                return isLetterIdx(line.getFirstWord());
            }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_PERUSH;
            }
            @Override
            public boolean match(Line line) {
                return line.beginsWith("באר היטב ") && line.endsWith(":");
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_CHELEK:
                chelekTitle = line.extract("שולחן ערוך - "," (מ)");
                chelekNum = getSeferNum(chelekTitle);
                positionInChelek = 0;
                //add sefer to package json
                packagesJsonObject().add(URI, "jbr:shulchanaruch");
                packagesJsonObject().add(RDFS_LABEL, "שלחן ערוך");
                packagesJsonObjectFlush();
                //add sefer with perush to package json
                packagesJsonObject().add(URI, "jbr:shulchanaruch-baerheytev");
                packagesJsonObject().add(RDFS_LABEL, "שלחן ערוך באר היטב");
                packagesJsonObjectFlush();
                //add chelek to package json
                packagesJsonObject().add(URI, getChelekUri());
                packagesJsonObject().add(RDFS_LABEL, "שלחן ערוך - " + chelekTitle);
                packagesJsonObject().add(JBO_POSITION, chelekNum);
                packagesJsonObjectFlush();
                break;

            case BEGIN_HALACHOT:
                halachotNum++;
                halachotTitle = line.getLine();
                //add halachot to package json
                packagesJsonObject().add(URI, getHalachotUri());
                packagesJsonObject().add(RDFS_LABEL, halachotTitle);
                packagesJsonObject().addToArray(JBO_WITHIN, "shulchanaruch-" + chelekNum);
                packagesJsonObject().add(JBO_POSITION, halachotNum);
                packagesJsonObjectFlush();
                break;

            case BEGIN_SIMAN:
                simanNum++;
                if(line.wordCount() == 3){
                    simanHebIdx = line.extract("סימן ", " -");
                    simanTitle = " ";
                }
                else {
                    simanHebIdx = line.extract("סימן ", " - ");
                    simanTitle = line.extract(" - ", " ");
                }
                if(simanHebIdx.equals("קסחקסט")) simanNum++;
                if(simanHebIdx.equals("רצז (א)")) simanNum--;
                saifNum = 0;
                //add siman to package json
                if(simanHebIdx.equals("רצז (א)")) {
                    packagesJsonObject().add(URI, "jbr:shulchanaruch-" + chelekNum + "-197_1-" + saifNum);
                    packagesJsonObject().add(RDFS_LABEL, simanTitle);
                    packagesJsonObject().addToArray(JBO_WITHIN, "shulchanaruch-" + chelekNum);
                    packagesJsonObject().add(JBO_POSITION, "197_1");
                }
                else {
                    packagesJsonObject().add(URI, getSimanUri());
                    packagesJsonObject().add(RDFS_LABEL, simanTitle);
                    packagesJsonObject().addToArray(JBO_WITHIN, "shulchanaruch-" + chelekNum);
                    packagesJsonObject().add(JBO_POSITION, simanNum);
                }
                packagesJsonObjectFlush();
                break;

            case BEGIN_SAIF:
                positionInChelek++;
                saifNum++;
                saifHebIdx = line.getFirstWord();
                saifText = line.extract(saifHebIdx + " ", " ");
                if(simanHebIdx.equals("רצז (א)")) {
                    jsonObject().add(URI, "jbr:shulchanaruch-" + chelekNum + "-197_1-" + saifNum);
                }
                else{
                    jsonObject().add(URI, getUri());
                }
                jsonObject().addToArray(JBO_WITHIN, "jbr:shulchanaruch");
                jsonObject().addToArray(JBO_WITHIN, getHalachotUri());
                jsonObject().addToArray(JBO_WITHIN, "shulchanaruch-" + chelekNum);
                jsonObject().addToArray(JBO_WITHIN, getSimanUri());
                jsonObject().add(JBO_POSITION_IN_SIMAN, saifNum);
                jsonObject().add(JBO_POSITION, positionInChelek);
                jsonObject().add(RDFS_LABEL, chelekTitle + " " + halachotTitle + " " + simanHebIdx + " " + saifHebIdx);
                jsonObject().add(JBO_TEXT, stripVowels(saifText));
                jsonObject().add(JBO_TEXT_NIKUD, saifText);
                jsonObjectFlush();
                break;

            case BEGIN_PERUSH:
                if(simanHebIdx.equals("רצז (א)")) {
                    jsonObject().add(URI, "jbr:shulchanaruch-baerheytev-" +  chelekNum + "-197_7-" + saifNum);
                }
                else{
                    jsonObject().add(URI, getPerushUri());
                }
                jsonObject().add(JBO_INTERPRETS, getUri());
                jsonObject().addToArray(JBO_WITHIN, "jbr:shulchanaruch-baerheytev");
                jsonObject().addToArray(JBO_WITHIN, getHalachotUri());
                jsonObject().addToArray(JBO_WITHIN, "shulchanaruch-" + chelekNum);
                jsonObject().addToArray(JBO_WITHIN, getSimanUri());
                jsonObject().add(JBO_POSITION_IN_SIMAN, saifNum);
                jsonObject().add(JBO_POSITION, positionInChelek);
                jsonObject().add(RDFS_LABEL, "פירוש " + chelekTitle + " " + halachotTitle + " " + simanHebIdx + " " + saifHebIdx);
                jsonObject().add(JBO_TEXT, line.extract("באר היטב " , " "));
                jsonObjectFlush();
                break;

            case NO_MATCH:
                break;
        }
    }


    @Override
    protected String getUri() {
        return "jbr:shulchanaruch-" + chelekNum + "-" + simanNum + "-" + saifNum;
    }
    private String getPerushUri() {
        return "jbr:shulchanaruch-baerheytev-" +  chelekNum + "-" + simanNum + "-" + saifNum;
    }
    private String getChelekUri(){
        return "jbr:shulchanaruch-" + chelekNum;
    }
    private String getHalachotUri(){
        return "jbr:shulchanaruch-halachot-" + chelekNum + "-" + halachotNum;
    }
    private String getSimanUri(){
        return "jbr:shulchanaruch-" + chelekNum + "-" + simanNum;
    }


    private boolean isLetterIdx(String str) {
        for (int i = 0; i < HEB_LETTERS_INDEX.length; i++){
            if (HEB_LETTERS_INDEX[i].equals(str))
                return true;
        }
        return false;
    }

    private int getSeferNum(String seferTitle){
        String sfarimNames[] = {null, "אורח חיים", "יורה דעה", "אבן העזר", "חשן משפט"};
        for(int i = 1; i <= 4; i++){
            if (sfarimNames[i].equals(seferTitle))
                return i;
        }
        return -1;
    }
}
