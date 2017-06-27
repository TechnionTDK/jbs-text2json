package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;

import java.io.IOException;

import static text2json.JbsOntology.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class DerashotMaharalParser extends Parser {

    private int chapterNum = -1;
    private int hakdamaNum = 0;


    public DerashotMaharalParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Drashot Maharal") && line.wordCount() <= 10;
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
                return line.beginsWith("דרוש ") && line.wordCount() <= 10;
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:
                packagesJsonObject().add(URI, getcorpus());
                packagesJsonObject().add(RDFS_LABEL, "דרשות מהר\"ל");
                packagesJsonObjectFlush();
                break;

            case BEGIN_HAKDAMA:
                jsonObjectFlush();
//                hakdamaNum++;
//                jsonObject().add(URI, getUri());
//                jsonObject().add(JBO_BOOK, JBR + "derashotmaharal");
//                jsonObject().add(JBO_POSITION, hakdamaNum);
//                jsonObject().add(RDFS_LABEL,"דרשות מהר\"ל - הקדמה ");



            case BEGIN_PEREK:
                packagesJsonObjectFlush();
                jsonObjectFlush();
                chapterNum++;
                String chapterName = line.getLine();
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_POSITION, chapterNum+1);
                jsonObject().add(JBO_BOOK, JBR + "derashotmaharal");
                String rdfs = "דרשות מהר\"ל - " + chapterName;
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
        return JBR + "derashotmaharal-" + chapterNum ;    }
    protected String getcorpus() { return JBR + "derashotmaharal";    }


}