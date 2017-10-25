package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.JBO_TEXT;
import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 08/06/2017.
 */
public class MekhiltaDeRabbiShimonBarYochaiParser extends JbsParser {

    protected static final String BEGIN_PART = "begin_part";
    private int partNum = 0,position=1 ,packagePosition=1;
    private int perekNum = 0;
    private String label1 = "";
    private String label2 = "";

    public MekhiltaDeRabbiShimonBarYochaiParser() {
//        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("מכילתא דרבי שמעון בר יוחאי") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PART;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Part ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Chapter ") && line.wordCount() <= 10;
            }
        });

    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:
                // No need to create an object for the entire book anymore!
                // It is created manually, outside text2json
                break;

            case BEGIN_PART:
                partNum++;
                perekNum=0;
                // No need to create an object for the entire book anymore!
                // It is created manually, outside text2json
                break;


            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                addBook("mekhiltaderabbishimonbaryochai");
                addUri(getUri());
                addPosition(position);
                position++;
                if (partNum == 1)
                    addRdfs("מכילתא דרשב\"י " + HEB_LETTERS_INDEX[perekNum-1]);
                else
                    if (partNum==2)
                        addRdfs("מכילתא דרשב\"י " +"הוספה "+ HEB_LETTERS_INDEX[perekNum-1]);


//                packagesJsonObjectFlush();
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  "mekhiltaderabbishimonbaryochai-" + partNum + "-"+ perekNum;    }
}
