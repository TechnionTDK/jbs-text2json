package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 08/06/2017.
 */
public class MekhiltaDeRabbiShimonBarYochaiParser extends JbsParser {

    protected static final String BEGIN_PART = "begin_part";
    private int partNum = 0,position=1 ;
    private int perekNum = 0;

    

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
                break;

            case BEGIN_PART:
                partNum++;
                perekNum=0;
                break;


            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                addBook(getBookId());
                addUri(getUri());
                addPosition(position);
                position++;
                if (partNum == 1)
                    addLabel("מכילתא דרשב\"י " + HEB_LETTERS_INDEX[perekNum-1]);
                else
                    if (partNum==2)
                        addLabel("מכילתא דרשב\"י " +"הוספה "+ HEB_LETTERS_INDEX[perekNum-1]);
                
                break;

            case NO_MATCH:
                appendText( line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  getBookId()+"-" + partNum + "-"+ perekNum;    }

    @Override
    protected String getBookId() {
        return "mekhiltaderabbishimonbaryochai";
    }
}
