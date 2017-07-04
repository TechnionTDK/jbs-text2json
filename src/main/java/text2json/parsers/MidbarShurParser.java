package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;

import java.io.IOException;

import static text2json.JbsOntology.*;
import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 08/06/2017.
 */
public class MidbarShurParser extends Parser {

    private int chapterNum = 0;
    private int hakdamaNum = 0;


    public MidbarShurParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("מדבר שור") && line.wordCount() <= 10;
            }
        });


        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Drush ") && line.wordCount() <= 10;
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:
                // No need to create an object for the entire book anymore!
                // It is created manually, outside text2json
//                packagesJsonObject().add(URI, getcorpus());
//                packagesJsonObject().add(RDFS_LABEL, "אור חדש");
//                packagesJsonObjectFlush();
                break;

            case BEGIN_PEREK:
                packagesJsonObjectFlush();
                jsonObjectFlush();
                chapterNum++;
                String chapterName = "דרוש " + HEB_LETTERS_INDEX[chapterNum-1];
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_BOOK, JBR_BOOK + "midbarshur");
                jsonObject().add(JBO_POSITION, chapterNum);
                String rdfs = "מדבר שור - " + chapterName;
                jsonObject().add(RDFS_LABEL,rdfs);
//                packagesJsonObject().add(URI, getUri());
//                packagesJsonObject().add(RDFS_LABEL, rdfs);
//                packagesJsonObjectFlush();
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return JBR + "midbarshur-" + chapterNum ;    }
    protected String getcorpus() { return JBR + "midbarshur";    }


}
