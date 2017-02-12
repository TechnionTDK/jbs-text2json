package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;

import java.io.IOException;

import static text2json.JbsOntology.*;
import static text2json.JbsUtils.*;

/**
 * Created by omishali on 05/02/2017.
 */
public class MishneTorahParser extends Parser {
    private static final String BEGIN_SEFER = "begin_sefer";
    private static final String BEGIN_HILCHOT = "begin_hilchot";
    private static final String BEGIN_PEREK = "begin_perek";
    private static final String BEGIN_PERUSH = "begin_perush";
    private static final String BEGIN_HALACHA = "begin_halacha";
    private static final String[] mefarshim = {"פירוש", "כסף משנה", "לחם משנה", "ההראב\"ד", "מגיד משנה"};
    private static final String[] sefarim = {"המדע", "אהבה", "זמנים", "נשים", "קדושה"};


    private int seferNum;
    private int hilchotNum = 0;
    private int perekNum = 0;
    private int halachaNum = 0;


    public MishneTorahParser() {
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
                return line.beginsWith("ספר") && line.wordCount() == 2;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_HILCHOT;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("הלכות") && !line.contains("פרק");
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_PEREK;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("הלכות") && line.contains("פרק");
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_PERUSH;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith(mefarshim);
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_HALACHA;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith(HEB_LETTERS_INDEX, " ");
            }
        });
    }



    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:
                seferNum = getSeferNum(line.extract("ספר", " "));
                packagesJsonObject().add(URI, getSeferURI());
                packagesJsonObject().add(RDFS_LABEL, line.getLine());
                packagesJsonObject().add(JBO_POSITION, seferNum);
                packagesJsonObjectFlush();
                break;
            case BEGIN_HILCHOT:
                hilchotNum++;
                perekNum = 0;
                packagesJsonObject().add(URI, getHilchotURI());
                packagesJsonObject().add(RDFS_LABEL, line.getLine());
                packagesJsonObject().add(JBO_POSITION, hilchotNum);
                packagesJsonObjectFlush();
                break;
            case BEGIN_PEREK:
                perekNum++;
                halachaNum = 0;
                packagesJsonObject().add(URI, getPerekURI());
                packagesJsonObject().add(RDFS_LABEL, line.getLine());
                packagesJsonObject().add(JBO_POSITION, perekNum);
                packagesJsonObjectFlush();
                break;
            case BEGIN_HALACHA:
                halachaNum++;
                jsonObject().add(URI, getHalachaURI());
                jsonObject().add(JBO_POSITION, halachaNum);
                jsonObject().add(JBO_TEXT_NIKUD, line.getLine());
                jsonObject().add(JBO_TEXT, stripVowels(line.getLine()));
                jsonObject().add(JBO_SEFER, getSeferURI());
                jsonObject().add(JBO_PEREK, getPerekURI());
                jsonObjectFlush();
                break;
            case BEGIN_PERUSH:
                break;
            case NO_MATCH:
                break;
        }
    }

    private int getSeferNum(String name) {
        return getIndexInArray(sefarim, name) + 1;
    }

    private int getIndexInArray(String[] arr, String e) {
        for (int i=0; i < arr.length; i++)
            if (arr[i].equals(e))
                return i;

        return -1;
    }

    private String getSeferURI() {
        return JBR + "mishnetorah-" + seferNum;
    }

    private String getHilchotURI() {
        return getSeferURI() + "-" + hilchotNum;
    }

    private String getPerekURI() {
        return getHilchotURI() + "-" + perekNum;
    }

    private String getHalachaURI() {
        return getPerekURI() + "-" + halachaNum;
    }

    @Override
    protected String getUri() {
        return null;
    }
}
