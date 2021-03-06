package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.*;
import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by omishali on 05/02/2017.
 */
public class MishneTorahParser extends JbsParser {
    private static final String BEGIN_HILCHOT = "begin_hilchot";
    private static final String BEGIN_HALACHA = "begin_halacha";
    private static final String[] MEFARSHIM_HEB = {"פירוש", "כסף משנה", "לחם משנה", "ההראב\"ד", "מגיד משנה", "מהר\"ל נ' חביב"};
    private static final String[] MEFARSHIM_EN = {"perushperush", "kesefmishne", "lechemmishne", "raabad", "magidmishne", "maharalchaviv"};
    private static final String[] SEFARIM = {"המדע", "אהבה", "זמנים", "נשים", "קדושה", "הפלאה", "זרעים",
                                            "עבודה", "קרבנות", "טהרה", "נזיקין", "קנין", "משפטים", "שופטים"};

    private int seferNum;
    private int hilchotNum = 0;
    private int perekNum = 0;
    private int halachaNum = 0;
    private int perushNum = 0;
    private String hilchotName;

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
                return line.beginsWith(MEFARSHIM_HEB);
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
                packagesJsonObject().add(JBO_BOOK, JBR_BOOK + getBookId());
                packagesJsonObjectFlush();
                break;
            case BEGIN_HILCHOT:
                hilchotNum++;
                perekNum = 0;
                hilchotName = line.getLine();
                packagesJsonObject().add(URI, getHilchotURI());
                packagesJsonObject().add(RDFS_LABEL, line.getLine());
                packagesJsonObject().add(JBO_POSITION, hilchotNum);
                packagesJsonObject().add(JBO_BOOK, JBR_BOOK + getBookId());
                packagesJsonObjectFlush();
                break;
            case BEGIN_PEREK:
                perekNum++;
                halachaNum = 0;
                perushNum = 0;
                packagesJsonObject().add(URI, getPerekURI());
                packagesJsonObject().add(RDFS_LABEL, line.getLine());
                packagesJsonObject().add(JBO_POSITION, perekNum);
                packagesJsonObject().add(JBO_BOOK, JBR_BOOK + getBookId());
                packagesJsonObjectFlush();
                break;
            case BEGIN_HALACHA:
                halachaNum++;
                jsonObjectFlush();
                jsonObject().add(URI, getHalachaURI());
                jsonObject().add(JBO_POSITION, halachaNum);
                jsonObject().add(JBO_TEXT_NIKUD, line.getLine());
                jsonObject().add(JBO_TEXT, stripVowels(line.getLine()));
                jsonObject().addToArray(JBO_WITHIN, getSeferURI());
                jsonObject().addToArray(JBO_WITHIN, getPerekURI());
                jsonObject().add(JBO_BOOK, JBR_BOOK + getBookId());
                jsonObject().add(RDFS_LABEL, getHalachaLabel());
                break;
            case BEGIN_PERUSH:
                perushNum++;
                jsonObjectFlush();
                jsonObject().add(URI, getPerushURI(line));
                jsonObject().add(JBO_POSITION, perushNum);
                jsonObject().add(JBO_TEXT, line.getLine());
                jsonObject().add(JBO_INTERPRETS, getHalachaURI());
                jsonObject().addToArray(JBO_WITHIN, getSeferURI());
                jsonObject().addToArray(JBO_WITHIN, getPerekURI());
                jsonObject().add(JBO_BOOK, JBR_BOOK + getMefareshName(line));

                String mefareshLabel = getMefareshLabel(line);
                jsonObject().add(RDFS_LABEL, mefareshLabel + " " + getHalachaLabel());
                break;
            case NO_MATCH:
                if (jsonObject().hasKey(JBO_INTERPRETS))
                    appendText( line.getLine());
                break;
        }
    }

    private String getMefareshLabel(Line line) {
        switch(line.getFirstWord()) {
            case "פירוש":
                return "פירוש";
            case "כסף":
                return "כסף משנה";
            case "לחם":
                return "לחם משנה";
            case "מגיד":
                return "מגיד משנה";
            case "ההראב\"ד":
                return "השגות הראב\"ד";
            case "מהר\"ל":
                return "מהר\"ל חביב";
        }
        return null;
    }

    private String getHalachaLabel() {
        return hilchotName + " " + HEB_LETTERS_INDEX[perekNum -1] + " " + HEB_LETTERS_INDEX[halachaNum - 1];
    }

    private int getSeferNum(String name) {
        return getIndexInArray(SEFARIM, name) + 1;
    }

    private int getIndexInArray(String[] arr, String e) {
        for (int i=0; i < arr.length; i++)
            if (arr[i].equals(e))
                return i;

        return -1;
    }

    private String getSeferURI() {
        return JBR_SECTION + getBookId()+"-" + seferNum;
    }

    private String getHilchotURI() {
        return getSeferURI() + "-" + hilchotNum;
    }

    private String getPerekURI() {
        return getHilchotURI() + "-" + perekNum;
    }

    private String getHalachaURI() {
        return JBR_TEXT + getBookId()+"-" + seferNum + "-" + hilchotNum + "-" + perekNum + "-" + halachaNum;
    }
    private String getPerushURI(Line line) {
        int i = 0;
        for (; i < MEFARSHIM_HEB.length; i++)
            if (line.beginsWith(MEFARSHIM_HEB[i]))
                break;

        return JBR_TEXT + getBookId()+"-" + MEFARSHIM_EN[i] + "-" + seferNum + "-" +
                        hilchotNum + "-" + perekNum + "-" + halachaNum;
    }

    private String getMefareshName(Line line) {
        int i = 0;
        for (; i < MEFARSHIM_HEB.length; i++)
            if (line.beginsWith(MEFARSHIM_HEB[i]))
                break;

        return MEFARSHIM_EN[i];
    }

    @Override
    protected String getUri() {
        return null;
    }

    @Override
    protected String getBookId() {
        return "mishnetorah";
    }
}
