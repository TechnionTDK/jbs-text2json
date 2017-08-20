package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;

import java.io.IOException;

import static text2json.JbsOntology.*;
import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 08/06/2017.
 */
public class OrChadashParser extends JbsParser {

    private int chapterNum = 0;
    private int hakdamaNum = -1;


    public OrChadashParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("אור חדש") && line.wordCount() <= 10;
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
                return line.beginsWith("Chapter ") && line.wordCount() <= 10;
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
                hakdamaNum++;
                jsonObject().add(URI, JBR_TEXT + "orchadash-0" +hakdamaNum);
                addBook("orchadash");
                addPosition(hakdamaNum+1);
                if (hakdamaNum ==00)
                    jsonObject().add(RDFS_LABEL,"אור חדש - הקדמה א");
                if (hakdamaNum ==01)
                    jsonObject().add(RDFS_LABEL,"אור חדש - הקדמה ב");
                break;


            case BEGIN_PEREK:
                packagesJsonObjectFlush();
                jsonObjectFlush();
                chapterNum++;
                String chapterName =HEB_LETTERS_INDEX[chapterNum-1];
                jsonObject().add(URI, getUri());
                addPosition(chapterNum+2);
                addBook("orchadash");
                String rdfs = "אור חדש " + chapterName;
                jsonObject().add(RDFS_LABEL,rdfs);
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return JBR_TEXT + "orchadash-" + chapterNum ;    }
}
