package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;

import java.io.IOException;

/**
 * Created by USER on 06-Jan-17.
 */
public class TanachParser extends Parser {
    private static final String BEGIN_PEREK = "begin_perek";
    private static final String BEGIN_PASUK = "begin_pasuk";
    private static final String BEGIN_PERUSH = "begin_perush";
    private static final String BEGIN_PARASHA = "begin_parasha";

    private int bookNum;
    private String bookTitle;
    private int perekNum = 0;
    private String perekTitle;
    private int pasukNum = 0;
    private String pasukTitle;
    //private String pasukText;
    //private int positionInParasha = 0;

    protected TanachParser(int bookNum){
        this.bookNum = bookNum;
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            public String type() { return BEGIN_PARASHA;}
            public boolean match(Line line) {
                return line.beginsWith("פרשת") && line.wordCount() <= 5;}
        });
        registerMatcher(new LineMatcher() {
            public String type() { return BEGIN_PEREK;}
            public boolean match(Line line) {
                //System.out.println("    contains perek- = " + line.contains(" פרק-") + "  word count = " + line.wordCount());
                return line.contains(" פרק-") && line.wordCount() <= 4;}
        });
        registerMatcher(new LineMatcher() {
            public String type() { return BEGIN_PASUK;}
            public boolean match(Line line) {
                return line.beginsWith("{") && line.contains("}") && !line.beginsWith("(") && !line.contains("\"")
                        && !line.contains("!") && !line.endsWith("}") && !line.endsWith("} ");}
        });
        registerMatcher(new LineMatcher() {
            public String type() { return BEGIN_PERUSH;}
            public boolean match(Line line) {
                return line.beginsWith(")");}
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch (type){
            case BEGIN_PARASHA:
                //jsonObjectFlush();
                break;
            case BEGIN_PEREK:
                //System.out.println("In BEGIN_PEREK");
                if (bookTitle == null) {
                    bookTitle = line.extract(" ", " פרק");
                }
                //System.out.println("bookTitle = " + bookTitle);
                jsonObjectFlush();
                perekNum++;
                pasukNum = 0;
                perekTitle = line.extract("פרק-", " ");
                //System.out.println("============ perek num = " + perekNum + " ==============");
                break;
            case BEGIN_PASUK:
                jsonObjectFlush();
                pasukNum++;
                pasukTitle = line.extract("{", "}");
                jsonObjectAdd("uri", getUri());
                if(line.contains(":")) {
                    jsonObjectAdd("jbo:text", stripVowels(line.extract("}", ":")));
                    jsonObjectAdd("jbo:textNikud", line.extract("}", ":"));
                } else {
                    jsonObjectAdd("jbo:text", stripVowels(line.extract("}", " ")));
                    jsonObjectAdd("jbo:textNikud", line.extract("}", " "));
                }

                jsonObjectAdd("rdfs:label", bookTitle + " " + perekTitle + " " + pasukTitle);
                jsonObjectAdd("jbo:sefer", "jbr:tanach-" + bookNum);

                jsonObjectOpenArray("titles");
                jsonObjectOpenObject();
                jsonObjectAdd("title", bookTitle + " " + perekTitle + " " + pasukTitle);
                jsonObjectCloseObject();
                jsonObjectOpenObject();
                jsonObjectAdd("title", bookTitle + " פרק " + perekTitle + " פסוק " + pasukTitle);
                jsonObjectCloseObject();
                jsonObjectCloseArray();
                jsonObjectFlush();
                break;
            case BEGIN_PERUSH:
                jsonObjectFlush();
                break;
            case NO_MATCH:
                break;
        }
    }

    @Override
    protected String getUri() {
        return "jbr:tanach-" + bookNum + "-" + perekNum + "-" + pasukNum;
    }

    @Override
    public String getId() {
        return "tanach-" + bookNum;
    }
}
