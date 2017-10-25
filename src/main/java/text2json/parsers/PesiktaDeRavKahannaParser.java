package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.JBO_TEXT;

/**
 * Created by Assaf on 08/06/2017.
 */
public class PesiktaDeRavKahannaParser extends JbsParser {

    private int chapterNum = 0;
    private int hakdamaNum = 0;


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
                return line.beginsWith("פסיקתא דרב כהנא") && line.wordCount() <= 10;
            }
        });


        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Piska ") && line.wordCount() <= 10;
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
//                packagesJsonObjectFlush();
                jsonObjectFlush();
                chapterNum++;
                String chapterName = "פסקה " + numberToHebrew(chapterNum);
                addUri( getUri());
                addBook( "pesiktaderavkahanna");
                addPosition(chapterNum);
                String rdfs = "פסיקתא דרב כהנא " + chapterName;
                addRdfs(rdfs);
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  "pesiktaderavkahanna-" + chapterNum ;    }
}
