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
public class NetzachIsraelParser extends Parser {

    private int chapterNum = 0;


    public NetzachIsraelParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("נצח ישראל") && line.wordCount() <= 10;
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
                packagesJsonObject().add(URI, getcorpus());
                packagesJsonObject().add(RDFS_LABEL, "נצח ישראל");
                packagesJsonObjectFlush();
                break;


            case BEGIN_PEREK:
                packagesJsonObjectFlush();
                jsonObjectFlush();
                chapterNum++;
                String chapterName = "פרק " + HEB_LETTERS_INDEX[chapterNum-1]+ " ";
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_POSITION, chapterNum);
                jsonObject().addToArray(JBO_WITHIN, getcorpus());
                String rdfs = "נצח ישראל - " + chapterName;
                jsonObject().add(RDFS_LABEL,rdfs);
                packagesJsonObject().add(URI, getUri());
                packagesJsonObject().add(RDFS_LABEL, rdfs);
                packagesJsonObjectFlush();
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return JBR + "netzachisrael-" + chapterNum ;    }
    protected String getcorpus() { return JBR + "netzachisrael";    }


}
