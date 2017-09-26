package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.JBO_TEXT;

/**
 * Created by Assaf on 08/06/2017.
 */
public class SederOlamRabbahParser extends JbsParser {

    private int chapterNum = 0;
    private int hakdamaNum = 0;
//    private String hebPerek ="";
    protected static final String BEGIN_PEREK_HEB = "begin_perek_heb";


//    public MidbarShurParser() {
//        createPackagesJson();
//    }

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
//                packagesJsonObjectFlush();
                jsonObjectFlush();
                chapterNum++;
                String chapterName = line.getLine();

                addUri( getUri());
                addBook( "sederolamrabbah");
                addPosition(chapterNum);
                String rdfs = "סדר עולם רבה - " + chapterName;
                addRdfs(rdfs);
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  "sederolamrabbah-" + chapterNum ;    }
}
