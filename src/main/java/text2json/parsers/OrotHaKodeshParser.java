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
public class OrotHaKodeshParser extends Parser {

    protected static final String BEGIN_TEACHING = "begin_teaching";
    private int teachingNum = 0,position=0;
    private int sectionNum = 0;
    private String nativName = "";

    public OrotHaKodeshParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("אורות הקודש") && line.wordCount() <= 10;
            }
        });


        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Section ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_TEACHING;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Teaching") && line.wordCount() <= 10;
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
                jsonObjectFlush();
                teachingNum=0;
                sectionNum++;
                packagesJsonObject().add(JBO_BOOK, JBR_BOOK + "orothakodesh");
                packagesJsonObject().add(URI,JBR_SECTION + "orothakodesh-" + sectionNum);
                packagesJsonObject().add(RDFS_LABEL,"אורות הקודש" + " " + HEB_LETTERS_INDEX[sectionNum-1]);
                packagesJsonObjectFlush();
                break;

            case BEGIN_TEACHING:
                jsonObjectFlush();
                teachingNum++;
                position++;
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_POSITION, position);
                jsonObject().add(JBO_BOOK, JBR_BOOK + "orothakodesh");
                jsonObject().addToArray(JBO_WITHIN, JBR_SECTION + "orothakodesh-" + sectionNum);
                String rdfs = "אורות הקודש" + " " + HEB_LETTERS_INDEX[sectionNum-1] + " " + HEB_LETTERS_INDEX[teachingNum-1];
                jsonObject().add(RDFS_LABEL,rdfs);
                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return JBR_TEXT + "orothakodesh-" + sectionNum + "-"+ teachingNum;    }
}
