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
    private int parashaNum = 0;
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
                perush = line.extract(" ", ":");

                jsonObjectFlush();
                jsonObjectAdd(URI, getUri());
                if(line.contains(":")) {
                    jsonObjectAdd(JBO_TEXT, stripVowels(line.extract(" ", ":")));
                    //jsonObjectAdd(JBO_TEXT_NIKUD, line.extract(" ", ":"));
                } else {
                    jsonObjectAdd(JBO_TEXT, stripVowels(line.extract(" ", " ")));
                    //jsonObjectAdd(JBO_TEXT_NIKUD, line.extract(" ", " "));
                }

                jsonObjectAdd(RDFS_LABEL, bookLetter + " " + perekLetter + " " + pasukLetter);
                jsonObjectAdd(JBO_SEFER, "jbr:tanach-" + bookNum);

                jsonObjectOpenArray("titles");
                jsonObjectOpenObject();
                jsonObjectAdd("title", bookLetter + " " + perekLetter + " " + pasukLetter);
                jsonObjectCloseObject();
                jsonObjectOpenObject();
                jsonObjectAdd("title", bookLetter + " פרק " + perekLetter + " פסוק " + pasukLetter);
                jsonObjectCloseObject();
                jsonObjectCloseArray();
                jsonObjectAdd(JBO_PARASHA, "jbr:parasha-" + parashaNum);
                jsonObjectAdd(JBO_PEREK, "jbr:tanach-" + bookNum + "-" + perekNum);
                jsonObjectAdd(JBO_INTERPRETS, "jbr:tanach-" + bookNum + "-" + perekNum + "-" + pasukNum);
                jsonObjectAdd(JBO_POSITION_IN_PEREK, Integer.toString(positionInPerek));
                jsonObjectAdd(JBO_POSITION_IN_PARASHA, Integer.toString(positionInParasha));
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
    @Override
    public String getId() {
        return MEFARESH_PARSER_ID;
    }


}
