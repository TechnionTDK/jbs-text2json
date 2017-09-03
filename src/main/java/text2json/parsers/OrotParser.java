package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.*;
import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 08/06/2017.
 */
public class OrotParser extends JbsParser {

    protected static final String BEGIN_MAAMAR = "begin_maamar";
    protected static final String BEGIN_SUB_MAAMAR = "begin_sub_maamar";

    private int maamarNum = 0,position =0,packagePosition =1;
    private int subMaamarNum = 0;
    private int perekNum = 0;
    private String maamarName ="";
    private String subMaamarName ="";
    private String perekName = "";
    private int hasSubMaamar = 0 ; // 0 = false, 1 = true

    public OrotParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("אורות") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_MAAMAR;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("מאמר ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SUB_MAAMAR;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("תת מאמר ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Chapter") && line.wordCount() <= 10;
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:
                // No need to create an object for the entire book anymore!
                // It is created outside text2json
                break;

            case BEGIN_MAAMAR:
                jsonObjectFlush();
                subMaamarNum=0;
                perekNum=0;
                maamarName = getMaamarName(line.getLine());
                maamarNum++;
                addBook(packagesJsonObject(), "orot");
                addPosition(packagesJsonObject(),packagePosition);
                packagePosition++;
                addPackageUri( "orot-maamar-"+maamarNum);

                int res = maamarName.compareTo("- אורות ישראל");
                if (res == 0) {
                    maamarName = "אורות ישראל";
                }
                addRdfs(packagesJsonObject(),"אורות - " + maamarName );
                packagesJsonObjectFlush();
                hasSubMaamar=0;
                break;

            case BEGIN_SUB_MAAMAR:
                jsonObjectFlush();
                perekNum=0;
                subMaamarNum++;
                subMaamarName = getSubMaamarName (line.getLine());
                addBook(packagesJsonObject(), "orot");
                addPosition(packagesJsonObject(), packagePosition);
                packagePosition++;
                addPackageUri( "orot-maamar-"+ maamarNum + "-submaamar-" + subMaamarNum);
                addRdfs(packagesJsonObject(), "אורות - " + maamarName +" - " + subMaamarName);
                packagesJsonObjectFlush();
                hasSubMaamar = 1;
                break;

            case BEGIN_PEREK:
                switch(hasSubMaamar) {
                    case (1): // has submaamar so each chapters are numbered
                        jsonObjectFlush();
                        perekNum++;
                        position++;
                        String hebperek = HEB_LETTERS_INDEX[perekNum - 1];
                        addUri( getUri());
                        addPosition( position);
                        addBook( "orot");
                        addWithin( "orot-" + maamarNum);
                        addWithin( "orot-" + maamarNum +"-" + subMaamarNum);
                        String rdfs = "אורות - " + maamarName + " - " + subMaamarName + " " + hebperek;
                        addRdfs(rdfs);
                        break;

                    case (0): // no submaamar so each chapter has a unique name
                        jsonObjectFlush();
                        perekNum++;
                        position++;
                        perekName  = getPerekName (line.getLine());
                        addUri( getUri());
                        addPosition( position);
                        addBook( "orot");
                        addWithin( "orot-" + maamarNum);
                        addWithin( "orot-" + maamarNum +"-" + subMaamarNum);
                        String rdfs1 = "אורות " + maamarName + " - " + perekName;
                        addRdfs(rdfs1);
                        break;
                }
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  "orot-" + maamarNum +"-" + subMaamarNum + "-"+ perekNum;   }
    protected String getMaamarName(String line){ return line.substring(line.indexOf(' ')+1);     }
    protected String getSubMaamarName(String line){ return line.substring(line.indexOf(' ')+6);     }
    protected String getPerekName(String line){ return line.substring(line.indexOf(' ')+5);     }

}
