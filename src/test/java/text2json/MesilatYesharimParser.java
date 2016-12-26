package text2json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 23-Dec-16.
 */
public class MesilatYesharimParser extends Parser {
    private static final String BEGIN_PEREK = "begin_perek";
    private static final String END_PEREK = "end_perek";
    private static final String EOF = "end_of_file";
    private static final String MESILAT_YESHARIM_PARSER_ID = "parser.mesilatYesharim";

    private int perekNum = 0;
    private String perekTitle;
    private String perekText;

    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            public String type() {
                return BEGIN_PEREK; }
            public boolean match(Line line) {
                return (line.beginsWith("פרק") || line.beginsWith("הקדמת הרב") || line.beginsWith("חתימה"))
                        && line.wordCount() <= 10;
            }
        });
        registerMatcher(new LineMatcher() {
            public String type() { return END_PEREK; }
            public boolean match(Line line) {
                return ((line.beginsWith("פרק") || line.is("חתימה")) && line.wordCount() <= 10);
            }
        });
    }


    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_HAKDAMA:
                jsonObjectAdd("uri", getUri());
                jsonObjectAdd("title", line.extract(" - ", " "));
            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                jsonObjectAdd("uri", getUri());
                jsonObjectAdd("title", line.extract(" - ", " "));
                perekText = null;
                break;
            case NO_MATCH:
                super.jsonObjectAppend("text", line.getLine());
        }
    }

    @Override
    protected String getUri() {
        return "mesilatyesharim" + "-" + perekNum;
    }

    @Override
    public String getId() {
        return MESILAT_YESHARIM_PARSER_ID;
    }

//    private class MesilatYesharimSubject {
//        int perekNum;
//        String perekTitle;
//        String sefer = "mesilatyesharim";
//        String perekText;
//
//        MesilatYesharimSubject(int perekNum, String perekTitle, String perekText){
//            this.perekNum = perekNum;
//            this.perekTitle = perekTitle;
//            this.perekText = perekText;
//        }
//
//        protected List<JsonObject> getMesilatYesharimSubject(){
//            List<JsonObject> objects = new ArrayList<JsonObject>();
//            objects.add(new JsonObject("uri", "mesilatyesharim-" + this.perekNum));
//            objects.add(new JsonObject("title", this.perekTitle));
//            objects.add(new JsonObject("sefer", this.sefer));
//            objects.add(new JsonObject("text", this.perekText));
//            return objects;
//        }
//    }
}
