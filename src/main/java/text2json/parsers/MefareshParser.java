package text2json.parsers;

import text2json.*;

import static text2json.JbsOntology.*;

import java.io.IOException;

/**
 * Created by orel on 26/07/18.
 */
public abstract class MefareshParser extends JbsParser {
    private final static String BEGIN_TITLE = "begin_title";
    private final static String BEGIN_OPENING = "begin_opening";

    private int book_index = 0;
    private int perek = 0;
    private int pasuk = 0;
    private int position = 1;

    @Override
    protected void registerMatchers() {
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
            public String type() { return BEGIN_TITLE;}
            @Override
            public boolean match(Line line) {
                return line.beginsWith(getMefareshHebrewName()) && line.wordCount() <= 5;}
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
                break;
            case BEGIN_HAKDAMA:
                perek = 0;
                jsonFlushTextOrClear();
                addInformation();
                break;
            case BEGIN_OPENING:
                jsonFlushTextOrClear();
                addBook(getMefareshName());
                //this is somewhat of a special case.
                addTextUri(getMefareshName() + "-" + JbsUtils.getTanachPerekUri(book_index, 1));
                addPosition(jsonObject(), position);
                addLabel(jsonObject(), getMefareshHebrewName() + " " +
                        JbsUtils.SEFARIM_TANACH_HE[book_index-1] + " " + "פתיחה לפירוש התורה");
                addTextInterprets(JbsUtils.getTanachPerekUri(book_index, 0));
                addName(getMefareshHebrewName());
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
        addBook(getMefareshName());
        addTextUri(getUri());
        addPosition(position);
        addLabel(getLabel());
        addTextInterprets(getTanachUri());
        addName(getMefareshHebrewName());
    }

    //flushes the json if it has text. otherwise clears it
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
        return getMefareshName() + "-" + getTanachUri();
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
        return getMefareshHebrewName() + " " + hebrewBookName + " " + str2;
    }

    @Override
    protected String getBookId() {
        return null;
    }

    //this name is for the URI. no spaces allowed, only english non-caps or "-" character
    protected abstract String getMefareshName();

    //this string is in the title of the mefaresh file and will be in the "name" property
    protected abstract String getMefareshHebrewName();
}
