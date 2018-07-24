package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;

import java.io.IOException;

import static text2json.JbsOntology.*;
import static text2json.JbsUtils.SEFARIM_TANACH_EN;
import static text2json.JbsUtils.SEFARIM_TANACH_HE;

/**
 * Created by USER on 06-Jan-17.
 */
public class TanachParserOld extends Parser {
    private static final String BEGIN_PASUK = "begin_pasuk";
    private static final int PSUKIM_FOR_BOOK[] = {0, 1533, 1210, 859, 1288, 956, 658, 618, 811, 695, 817,
            719, 1291, 1364, 1273, 197, 73, 146, 21, 48, 105, 47, 56, 53, 38, 211,
            55, 2527, 915, 1070, 117, 85, 154, 222, 167, 357, 280, 405, 943, 822};

    private int bookNum = 0;
    private String bookName;
    private String bookNameEn;
    private int perekNum = 0;
    private String perekTitle;
    private int pasukNum = 0;
    private int parashaNum = 0;
    private String pasukTitle;
    private int position;

    public TanachParserOld() {
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
                return line.beginsWith("ספר ") && line.wordCount() <= 5;
            }
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK;}
            @Override
            public boolean match(Line line) {
                return line.contains("פרק-") && line.wordCount() <= 4;}
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
                bookNameEn = SEFARIM_TANACH_EN[bookNum-1];
                position = initPositionForBook(bookNum);
               // No need to create an object for the entire book anymore!
                // It is created outside text2json
                break;

            case BEGIN_PARASHA: // we assume bookNum is initialized
                jsonObjectFlush();
                parashaNum++;
                // adding parasha triples in packages json
                packagesJsonObject().add(URI, getParashaUri());
                packagesJsonObject().add(RDFS_LABEL, line.getLine());
                packagesJsonObject().add(JBO_POSITION, getFixedParashaPosition());
                packagesJsonObject().add(JBO_BOOK, JBR_BOOK + bookNameEn);
                packagesJsonObjectFlush();
                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                pasukNum = 0;
                perekTitle = line.extract("פרק-", " ");

                // adding perek triples in packages json
                packagesJsonObject().add(URI, getPerekUri());
                packagesJsonObject().add(RDFS_LABEL, line.getLine().replace('-', ' '));
                packagesJsonObject().add(JBO_POSITION, perekNum);
                packagesJsonObject().add(JBO_BOOK, JBR_BOOK_TANACH + bookNameEn);
                packagesJsonObjectFlush();
                break;

            case BEGIN_PASUK:
                jsonObjectFlush();
                position++;
                pasukNum++;
                pasukTitle = line.extract("{", "}");
                jsonObject().add(URI, getUri());
                jsonObject().addToArray(JBO_WITHIN, getPerekUri());
                String end = line.contains(":") ? ":" : " ";
                jsonObject().add(JBO_TEXT, stripVowels(line.extract("}", end)));
                jsonObject().add(JBO_TEXT_NIKUD, line.extract("}", end));
                jsonObject().add(RDFS_LABEL, bookName + " " + perekTitle + " " + pasukTitle);
                jsonObject().add(JBO_BOOK, JBR_BOOK_TANACH + bookNameEn);

                jsonObject().add(JBO_POSITION, position);
                if(bookNum <= 5) {
                    jsonObject().addToArray(JBO_WITHIN, getParashaUri());

                }
                jsonObjectFlush();
                break;

            case BEGIN_PERUSH:
                jsonObjectFlush();
                break;

            case NO_MATCH:
                break;
        }
    }

    private int initPositionForBook(int bookNum) {
        int pos = 0;
        for (int i=0; i<bookNum;i++)
            pos+=PSUKIM_FOR_BOOK[i];

        return pos;
    }

    private String getParashaUri() {
        return JBR_SECTION + "tanach-parasha-" + getFixedParashaPosition();
    }
    private String getPerekUri() {
        return JBR_SECTION + "tanach-" + bookNum + "-" + perekNum;
    }


    private int getBookNum(String bookTitle) {
        for (int i = 0; i < 39; i++){
            if(bookTitle.equals(SEFARIM_TANACH_HE[i]))
                return i+1;
        }
        return -1;
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
        return JBR_TEXT + "tanach-" + bookNum + "-" + perekNum + "-" + pasukNum;
    }

    @Override
    protected String getBookId() {
        return null;
    }
}
