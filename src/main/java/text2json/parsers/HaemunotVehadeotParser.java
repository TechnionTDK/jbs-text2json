package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;

import java.io.IOException;

import static text2json.JbsOntology.*;
import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 08/06/2017.
 */
public class HaemunotVehadeotParser extends Parser {

    protected static final String BEGIN_MAAMAR = "begin_maamar";
    protected static final String BEGIN_PATIAH = "begin_ptiha";
    protected static final String BEGIN_HAKDAMA = "begin_hakdama";


    private int maamarNum = 0;
    private int perekNum = 0;
    private String maamarName ="";
    private String perekName = "";
    private int hakdamaBool = 0; // 0 false, 1 true
    public HaemunotVehadeotParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("האמונות והדעות") && line.wordCount() <= 10;
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
            public String type() { return BEGIN_HAKDAMA;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("הקדמה") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PATIAH;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("פתיחה ") && line.wordCount() <= 10;
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
                packagesJsonObject().add(URI, getcorpus());
                packagesJsonObject().add(RDFS_LABEL, "האמונות והדעות");
                packagesJsonObjectFlush();
                break;

            case BEGIN_HAKDAMA:
                jsonObjectFlush();
                perekNum=0;
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_POSITION, "0");
                jsonObject().add(JBO_BOOK, JBR + "haemunotvehadeot");
                jsonObject().add(RDFS_LABEL,"האמונות והדעות - הקדמה");
                packagesJsonObject().add(URI, "haemunotvehadeot - hakdama");
                packagesJsonObject().add(RDFS_LABEL,"האמונות והדעות - הקדמה");
                packagesJsonObjectFlush();
                hakdamaBool=1;
                break;

            case BEGIN_MAAMAR:
                jsonObjectFlush();
                perekNum=0;
                maamarName = line.getLine();
                maamarNum++;
                packagesJsonObject().add(URI, "haemunotvehadeot - " + maamarNum);
                packagesJsonObject().add(RDFS_LABEL,"האמונות והדעות - " + maamarName );
                packagesJsonObjectFlush();
                hakdamaBool=0;
                break;


            case BEGIN_PATIAH:
                jsonObjectFlush();
                perekNum=0;
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_POSITION, perekNum);
                jsonObject().add(JBO_BOOK, JBR + "haemunotvehadeot");
                jsonObject().addToArray(JBO_WITHIN, getcorpus());
                jsonObject().addToArray(JBO_WITHIN, maamarName);
                String rdfs = "האמונות והדעות - פתיחה";
                jsonObject().add(RDFS_LABEL,rdfs);
                break;

            case BEGIN_PEREK:
                switch(hakdamaBool) {
                    case (1): // in hakdam's chapters
                        jsonObjectFlush();
                        perekNum++;
                        perekName = HEB_LETTERS_INDEX[perekNum - 1];
                        jsonObject().add(URI, getUri());
                        jsonObject().add(JBO_BOOK, JBR + "haemunotvehadeot");
                        jsonObject().add(JBO_POSITION, perekNum);
                        jsonObject().addToArray(JBO_WITHIN, "האמונות והדעות - הקדמה");
                        String rdfs0 = "האמונות והדעות - הקדמה "  + perekName;
                        jsonObject().add(RDFS_LABEL,rdfs0);
                        break;

                    case (0): // in regular maamar chapters
                        jsonObjectFlush();
                        perekNum++;
                        perekName = HEB_LETTERS_INDEX[perekNum - 1];
                        jsonObject().add(URI, getUri());
                        jsonObject().add(JBO_BOOK, JBR + "haemunotvehadeot");
                        jsonObject().add(JBO_POSITION, perekNum);
                        jsonObject().addToArray(JBO_WITHIN, maamarName);
                        String rdfs1 = "האמונות והדעות - " + maamarName + " " + perekName;
                        jsonObject().add(RDFS_LABEL,rdfs1);
//                        packagesJsonObject().add(URI, "haemunotvehadeot - " + maamarNum + " - " + perekNum);
//                        packagesJsonObject().add(RDFS_LABEL,"האמונות והדעות - " + maamarName + " - " + perekName );
//                        packagesJsonObjectFlush();
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
        return JBR + "haemunotvehadeot-" + maamarNum + "-" + perekNum;   }
    protected String getcorpus() { return JBR + "haemunotvehadeot";    }

}
