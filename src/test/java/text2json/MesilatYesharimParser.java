package text2json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 23-Dec-16.
 */
public class MesilatYesharimParser extends Parser{
    private static final String BEGIN_HAKDAMA = "begin_hakdama";
    private static final String END_HAKDAMA = "end_hakdama";
    private static final String BEGIN_PEREK = "begin_perek";
    private static final String END_PEREK = "end_perek";
    private static final String BEGIN_HATIMA = "begin_hatima";
    private static final String END_HATIMA = "end_hatima";
    private static final String EOF = "end_of_file";
    private static final String MESILAT_YESHARIM_PARSER_ID = "parser.mesilatYesharim";

    private int perekNum = 0;
    private String perekTitle;
    private String perekText;

    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            public String type() { return BEGIN_HAKDAMA;}
            public boolean match(Line line) { return line.is("הקדמת הרב המחבר זצ\"ל");}
        });
        registerMatcher(new LineMatcher() {
            public String type() { return END_HAKDAMA;}
            public boolean match(Line line) { return line.is("פרק א' - בביאור כלל חובת האדם בעולמו");}
        });
        registerMatcher(new LineMatcher() {
            public String type() { return BEGIN_PEREK; }
            public boolean match(Line line) { return line.beginsWith("פרק") && line.wordCount() <= 10;}
        });
        registerMatcher(new LineMatcher() {
            public String type() { return END_PEREK; }
            public boolean match(Line line) {
                return ((line.beginsWith("פרק") || line.is("חתימה")) && line.wordCount() <= 10);
            }
        });
        registerMatcher(new LineMatcher() {
            public String type() { return BEGIN_HATIMA; }
            public boolean match(Line line) { return line.is("חתימה");}
        });
        registerMatcher(new LineMatcher() {
            public String type() { return END_HATIMA; }
            public boolean match(Line line) { return line.is("משה בן כבוד רבי יעקב חי לוצאטי ס\"ט.");}
        });
        registerMatcher(new LineMatcher() {
            public String type() { return EOF; }
            public boolean match(Line line) { return true;}
        });
    }


    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_HAKDAMA:
                perekNum++;
                perekTitle = line.extract("  ", " ");
                perekText = null;
                break;
            case END_HAKDAMA:
                MesilatYesharimSubject hakdamaSubject = new MesilatYesharimSubject(perekNum, perekTitle, perekText);
                addJsonSubject(hakdamaSubject.getMesilatYesharimSubject());
                break;
            case BEGIN_PEREK:
                perekText = null;
                perekNum++;
                perekTitle = line.extract(" - ", " ");
                break;
            case END_PEREK:
                MesilatYesharimSubject perekSubject = new MesilatYesharimSubject(perekNum, perekTitle, perekText);
                addJsonSubject(perekSubject.getMesilatYesharimSubject());
                break;
            case BEGIN_HATIMA:
                perekNum++;
                perekText = null;
                perekTitle = line.extract("  ", " ");
                break;
            case END_HATIMA:
                MesilatYesharimSubject hatimaSubject = new MesilatYesharimSubject(perekNum, perekTitle, perekText);
                addJsonSubject(hatimaSubject.getMesilatYesharimSubject());
                break;
        }
    }

    @Override
    protected void onNoMatch(Line line) {
        perekText += line.getLine();
    }

    @Override
    protected void onEOF() throws IOException {
        onLineMatch(END_HATIMA, null);
    }

    @Override
    public String getId() {
        return MESILAT_YESHARIM_PARSER_ID;
    }

    private class MesilatYesharimSubject {
        int perekNum;
        String perekTitle;
        String sefer = "mesilatyesharim";
        String perekText;

        MesilatYesharimSubject(int perekNum, String perekTitle, String perekText){
            this.perekNum = perekNum;
            this.perekTitle = perekTitle;
            this.perekText = perekText;
        }

        protected List<JsonObject> getMesilatYesharimSubject(){
            List<JsonObject> objects = new ArrayList<JsonObject>();
            objects.add(new JsonObject("uri", "mesilatyesharim-" + this.perekNum));
            objects.add(new JsonObject("title", this.perekTitle));
            objects.add(new JsonObject("sefer", this.sefer));
            objects.add(new JsonObject("text", this.perekText));
            return objects;
        }
    }
}
