package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.*;
import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 08/06/2017.
 */
public class BeerHagolaParser extends JbsParser {

    private int beerNum = -1;


//    public BeerHagolaParser() {
//        createPackagesJson();
//    }

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
                // No need to create an object for the entire book anymore!
                // It is created outside text2json
                break;

            case BEGIN_HAKDAMA:
                jsonObjectFlush();
                beerNum++;
                addUri( getUri());
                addBook( "beerhagola");
                addPosition( beerNum+1);
                addRdfs("באר הגולה - הקדמה");
                break;


            case BEGIN_PEREK:
//                packagesJsonObjectFlush();
                jsonObjectFlush();
                beerNum++;
                String beerName = "באר " + HEB_LETTERS_INDEX[beerNum-1];
                addUri( getUri());
                addBook( "beerhagola");
                addPosition( beerNum+1);
                String rdfs = "באר הגולה - " + beerName;
                addRdfs(rdfs);
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return "beerhagola-" + beerNum ;    }
    protected String getcorpus() { return JBR + "beerhagola";    }


}
