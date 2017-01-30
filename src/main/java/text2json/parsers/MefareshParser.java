package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;
import java.io.IOException;
import static text2json.JbsOntology.*;

/**
 * Change to MefareshParser and provide in the c'tor
 * the name of the Mefaresh.
 * Created by omishali on 12/12/2016.
 */

public class MefareshParser extends Parser {
    String[] MefarshimEn = {"Rashi", "ramban", "orhachaim", "ibnezra", "baalhaturim", "onkelos", "sforno", "kliyekar",
            "daatzkenim", "metzudatdavid", "metzudattzion", "malbiminyan", "malbimmilot", "ralbag", "malbim"};
    String[] MefarshimHe = {"רש\"י", "הרמב\"ן", "אור החיים", "אבן עזרא", "בעל הטורים" , "אונקלוס", "ספורנו", "כלי יקר",
            "דעת זקנים", "מצודת דוד", "מצודת ציון", "מלבי\"ם באור הענין", "מלבי\"ם באור המלות", "רלב\"ג", "מלבי\"ם"};
    private static final String BEGIN_PARASHA = "begin_parasha";
    private static final String BEGIN_PEREK = "begin_perek";
    private static final String BEGIN_PERUSH = "begin_perush";
    private static final String BEGIN_PASUK = "begin_pasuk";
    private static final String MEFARESH_PARSER_ID = "parser.mefareshTanach";
    private int bookNum = 0;
    private int parashaNum = 1;
    private int perekNum = 0;
    private int pasukNum = 0;
    private int positionInParasha = 0;
    private int positionInPerek = 0;
    private String mefaresh;
    private int mefareshId;
    private String bookLetter;
    private String perekLetter;
    private String pasukLetter;
    private String perush;

    public MefareshParser(int bookNum){
        //super();
        this.bookNum = bookNum;
        mefaresh = mefaresh;
    }

    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            public String type() {
                return BEGIN_PARASHA;
            }
            public boolean match(Line line) {
                return line.beginsWith("פרשת") && line.wordCount() <= 4;
            }
        });

        registerMatcher(new LineMatcher() {
            public String type() {
                return BEGIN_PEREK;
            }
            public boolean match(Line line) {
                return line.contains("פרק-") && line.wordCount() <= 4;
            }
        });

        registerMatcher(new LineMatcher() {
            public String type() { return BEGIN_PASUK; }
            public boolean match(Line line) { return line.beginsWith("{");
            }
        });

        registerMatcher(new LineMatcher() {
            public String type() {
                return BEGIN_PERUSH;
            }
            public boolean match(Line line) { return line.endsWith(MefarshimHe);
            }
        });

    }

    protected int get_mefareshId(Line line){
        String baseLine = line.getLine();
        for(int i=0; i<MefarshimHe.length; i++){
            if (baseLine.endsWith(": (" + MefarshimHe[i] + ")")) return i;
        }
        return -1;
    }

    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type){
            case BEGIN_PARASHA:
                parashaNum++;
                positionInParasha = 0;
                break;
            case BEGIN_PEREK:
                if (bookLetter == null) {
                    bookLetter = line.extract(" ", " פרק");
                }
                // if(bookNum == 0) bookNum = getBookNum(line.extract(" ", " פרק-" ));
                perekNum++;
                pasukNum = 0;
                positionInPerek = 0;
                perekLetter = line.extract("פרק-", " ");
                break;
            case BEGIN_PASUK:
                String curr_pasuk = line.extract("{", "}");
                if(pasukLetter == curr_pasuk)
                    break;
                pasukLetter = curr_pasuk;
                pasukNum++;
                positionInPerek++;
                positionInParasha++;
                break;
            case BEGIN_PERUSH:
                mefareshId = get_mefareshId(line);
                mefaresh = MefarshimEn[mefareshId];
                perush = line.extract(" ", ": (" + MefarshimHe[mefareshId] + ")");
                jsonObjectFlush();
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_TEXT, perush);
                /*if(line.contains(":")) {
                    jsonObjectAdd(JBO_TEXT, stripVowels(perush));
                    //jsonObjectAdd(JBO_TEXT_NIKUD, line.extract(" ", ":"));
                } else {
                    jsonObjectAdd(JBO_TEXT, stripVowels(perush));
                    //jsonObjectAdd(JBO_TEXT_NIKUD, line.extract(" ", " "));
                }*/

                jsonObject().add(RDFS_LABEL, bookLetter + " " + perekLetter + " " + pasukLetter);
                jsonObject().add(JBO_SEFER, "jbr:tanach-" + bookNum);

                jsonObject().openArray("titles");
                jsonObject().openObject();
                jsonObject().add("title", bookLetter + " " + perekLetter + " " + pasukLetter);
                jsonObject().closeObject();
                jsonObject().openObject();
                jsonObject().add("title", bookLetter + " פרק " + perekLetter + " פסוק " + pasukLetter);
                jsonObject().closeObject();
                jsonObject().closeArray();
                if (bookNum <= 5) {
                    jsonObject().add(JBO_PARASHA, "jbr:parasha-" + parashaNum);
                }
                jsonObject().add(JBO_PEREK, "jbr:tanach-" + bookNum + "-" + perekNum);
                jsonObject().add(JBO_INTERPRETS, "jbr:tanach-" + bookNum + "-" + perekNum + "-" + pasukNum);
                jsonObject().add(JBO_POSITION_IN_PEREK, Integer.toString(positionInPerek));
                if (bookNum <=5) {
                    jsonObject().add(JBO_POSITION_IN_PARASHA, Integer.toString(positionInParasha));
                }
                jsonObjectFlush();
                break;
            case NO_MATCH:
                break;
        }
    }

    @Override
    protected String getUri() {
        return "jbr:tanach-" + mefaresh + "-" + bookNum + "-" + perekNum + "-" + pasukNum;
    }

    /*
    @Override
    protected void onEOF() {

    }
    */
}
