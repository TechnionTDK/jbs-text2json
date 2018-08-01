package text2json.parsers;

import text2json.*;

import static text2json.JbsOntology.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by orel on 26/07/18.
 */
public class MefareshParser extends JbsParser {
    private final static String BEGIN_TITLE = "begin_title";
    private final static String BEGIN_OPENING = "begin_opening";

    private int book_index = 0;
    private int perek = 0;
    private int pasuk = 0;
    private int position = 1;

    private String mefareshName, mefareshHebrewName;

    //map from hebrew name to english name. when adding new mefarshim, simply add them to this map
    private static final Map<String, String> mefareshMap = createMefareshMap();

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_TITLE;}
            @Override
            public boolean match(Line line) {
                return line.beginsWith("פירוש ") && line.wordCount() <= 6;}
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_HAKDAMA;}
            @Override
            public boolean match(Line line) {
                return line.beginsWith("הקדמה") && line.wordCount() <= 2;}
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_OPENING;}
            @Override
            public boolean match(Line line) {
                return line.beginsWith("פתיחה לפירוש התורה") && line.wordCount() <= 4;}
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
                return line.beginsWith("Chapter") && line.wordCount() == 2;}
        });
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PERUSH;}
            @Override
            public boolean match(Line line) {
                return line.beginsWith("Verse") && line.wordCount() == 2;}
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch (type) {
            case BEGIN_TITLE:
                //the title of the raw file must be in the first line and have the following format:
                // פירוש *שם מפרש בעברית*
                mefareshHebrewName = line.getLine().replace("פירוש ", "");
                mefareshName = mefareshMap.get(mefareshHebrewName);
                break;

            case BEGIN_HAKDAMA:
                perek = 0;
                jsonFlushTextOrClear();
                addInformation();
                break;

            case BEGIN_OPENING:
                jsonFlushTextOrClear();
                addBook(mefareshName);
                //this is somewhat of a special case.
                addTextUri(mefareshName + "-" + JbsUtils.getTanachPerekUri(book_index, 1));
                addPosition(position);
                addLabel(mefareshHebrewName + " " +
                        JbsUtils.SEFARIM_TANACH_HE[book_index-1] + " " + "פתיחה לפירוש התורה");
                addName(mefareshHebrewName);
                break;

            case BEGIN_SEFER:
                book_index++;
                perek = 0;
                pasuk = 0;
                break;

            case BEGIN_PEREK:
                perek++;
                pasuk = 0;
                jsonFlushTextOrClear();
                break;

            case BEGIN_PERUSH:
                pasuk++;
                jsonFlushTextOrClear();
                addInformation();
                addTextInterprets(getTanachUri());
                break;

            case NO_MATCH:
                String strLine = getCleanLine(line.getLine());
                String strNoNikud = JbsUtils.removeNikkud(strLine);
                appendText(strNoNikud);
                if(!strNoNikud.equals(strLine)) appendNikudText(strLine);
                break;
        }
    }

    //cleans the line from the <b> and such characters
    private String getCleanLine(String strLine) {
        strLine = strLine.replace("<b>", "");
        strLine = strLine.replace("</b>", "");
        strLine = strLine.replace("<br>", "");
        strLine = strLine.replace("</br>", "");
        return strLine;
    }

    private void addInformation() {
        addBook(mefareshName);
        addTextUri(getUri());
        addPosition(position);
        addLabel(getLabel());
        addName(mefareshHebrewName);
    }

    //flushes the json if it has text and increments the position. otherwise clears it
    //this is needed because some of the Verses in the text are empty
    private void jsonFlushTextOrClear() throws IOException{
        JsonObject jo = jsonObject();
        if(!jo.hasKey(JBO_TEXT)) {
            jo.clear();
            return;
        }
        jsonObjectFlush();
        position++;
    }

    @Override
    protected String getUri() {
        return mefareshName + "-" + getTanachUri();
    }

    //returns the URI without the pasuk if perek = 0
    private String getTanachUri() {
        if(perek > 0) return JbsUtils.getPasukUri(book_index, perek, pasuk);
        else { //hakdama (intro)
            return JbsUtils.getTanachPerekUri(book_index, perek);
        }
    }

    private String getLabel() {
        String hebrewBookName = JbsUtils.SEFARIM_TANACH_HE[book_index-1];
        String str2;
        if(perek == 0) {
            str2 = "הקדמה";
        } else {
            str2 = JbsUtils.numberToHebrew(perek) + " " + JbsUtils.numberToHebrew(pasuk);
        }
        return mefareshHebrewName + " " + hebrewBookName + " " + str2;
    }

    @Override
    protected String getBookId() {
        return null;
    }

    //when adding new mefarshim, simply add them to this map
    private static Map<String, String> createMefareshMap()
    {
        Map<String,String> myMap = new HashMap<>();
        myMap.put("רמב\"ן", "ramban");
        myMap.put("אברבנאל", "abarbanel");
        return myMap;
    }
}
