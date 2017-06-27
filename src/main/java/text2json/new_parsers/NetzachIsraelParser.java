package text2json.new_parsers;

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

    private int chapterNum = -1, first =0;


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
                first = 1;
                if (chapterNum>0) {
                    String chapterName = HEB_LETTERS_INDEX[chapterNum - 1];
                    jsonObject().add(URI, getUri());
                    jsonObject().add(JBO_POSITION, chapterNum+1);
                    jsonObject().add(JBO_BOOK, JBR + "netzachisrael");
                    String rdfs = "נצח ישראל " + chapterName;
                    jsonObject().add(RDFS_LABEL, rdfs);
                    //                packagesJsonObject().add(URI, getUri());
                    //                packagesJsonObject().add(RDFS_LABEL, rdfs);
//                    packagesJsonObjectFlush();
                }
                else{
                    jsonObject().add(URI, getUri());
                    jsonObject().add(JBO_POSITION, chapterNum+1);
                    jsonObject().add(JBO_BOOK, JBR + "netzachisrael");
                    String rdfs = "נצח ישראל הקדמה";
                    jsonObject().add(RDFS_LABEL, rdfs);
                }
                break;

            case NO_MATCH:
                if(first == 0)
                    jsonObject().append(JBO_TEXT, line.getLine());
                else
                    first = 0;
                break;
        }
    }

    @Override
    protected String getUri() {
        return JBR + "netzachisrael-" + chapterNum ;    }
    protected String getcorpus() { return JBR + "netzachisrael";    }


}
