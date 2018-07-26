package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

/**
 * Created by Assaf on 08/06/2017.
 */
public class SederOlamRabbahParser extends JbsParser {

    private int chapterNum = 0;
    protected static final String BEGIN_PEREK_HEB = "begin_perek_heb";



    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("סדר עולם רבה") && line.wordCount() <= 10;
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

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK_HEB;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("פרק ") && line.wordCount() <= 10;
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

                case BEGIN_PEREK:
                break;

            case BEGIN_PEREK_HEB:
                jsonObjectFlush();
                chapterNum++;
                String chapterName = line.getLine().replace("פרק ","");
                addTextUri( getUri());
                addBook( getBookId());
                addPosition(chapterNum);
                String label = "סדר עולם רבה " + chapterName;
                addLabel(label);
                break;

            case NO_MATCH:
                appendText( line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  getBookId()+"-" + chapterNum ;    }

    @Override
    protected String getBookId() {
        return "sederolamrabbah";
    }
}
