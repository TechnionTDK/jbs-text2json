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
public class BeerHagolaParser extends JbsParser {

    private int beerNum = -1;



    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Beer Hagola") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_HAKDAMA;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("הקדמה") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("באר ") && line.wordCount() <= 10;
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:
                break;

            case BEGIN_HAKDAMA:
                jsonObjectFlush();
                beerNum++;
                addTextUri( getUri());
                addBook( getBookId());
                addPosition( beerNum+1);
                addLabel("באר הגולה הקדמה");
                break;


            case BEGIN_PEREK:
                if (jsonObject().hasKey(JBO_TEXT))
                    jsonObjectFlush();
                else{
                    jsonObject().clear();
                }
                beerNum++;
                String beerName =  HEB_LETTERS_INDEX[beerNum-1];
                addTextUri( getUri());
                addBook( getBookId());
                addPosition( beerNum+1);
                String label = "באר הגולה " + beerName;
                addLabel(label);
                break;

            case NO_MATCH:
                appendText(line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return getBookId() + "-" + beerNum ;    }

    @Override
    protected String getBookId() {
        return "beerhagola";
    }


}
