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
public class DerechChaimParser extends Parser {

    protected static final String BEGIN_MISHNA = "begin_mishna";
    private int mishnaNum = 0;
    private int perekNum = 0;
    private int position = 0;
    private String nativName = "";

    public DerechChaimParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("דרך חיים") && line.wordCount() <= 10;
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

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_MISHNA;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Mishna") && line.wordCount() <= 10;
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:
                // No need to create an object for the entire book anymore!
                // It is created outside text2json
//                packagesJsonObject().add(URI, getcorpus());
//                packagesJsonObject().add(RDFS_LABEL, "דרך חיים ");
//                packagesJsonObjectFlush();
                break;

            case BEGIN_HAKDAMA:
                jsonObjectFlush();
                position++;
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_BOOK,JBR_BOOK + "derechchaim");
                jsonObject().add(JBO_POSITION, position);
                jsonObject().add(RDFS_LABEL,"דרך חיים - הקדמה");

                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                mishnaNum=0;
                perekNum++;
                packagesJsonObject().add(URI, JBR + "derechchaim-" + perekNum);
                String rdfs1 = "דרך חיים " + HEB_LETTERS_INDEX[perekNum-1];
                packagesJsonObject().add(RDFS_LABEL, rdfs1);
                packagesJsonObjectFlush();
                break;

            case BEGIN_MISHNA:
                jsonObjectFlush();
                mishnaNum++;
                position++;
                jsonObject().add(URI, getUri());
                jsonObject().add(JBO_POSITION, position);
                jsonObject().add(JBO_BOOK,JBR_BOOK + "derechchaim");
                jsonObject().add(JBO_EXPLAINS, "jbr:mishna-4-9-" + perekNum+"-"+mishnaNum);
                jsonObject().addToArray(JBO_WITHIN, JBR + "derechchaim-" +  perekNum);

                String rdfs = "דרך חיים " + HEB_LETTERS_INDEX[perekNum-1] + " " + HEB_LETTERS_INDEX[mishnaNum-1];
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
        return JBR + "derechchaim-" + perekNum + "-"+ mishnaNum;    }
    protected String getcorpus() { return JBR + "derechchaim";    }


}
