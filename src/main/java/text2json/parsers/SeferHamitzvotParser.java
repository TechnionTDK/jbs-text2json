package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;

import java.io.IOException;

import static text2json.JbsOntology.*;

/**
 * Created by omishali on 18/01/2017.
 */
public class SeferHamitzvotParser extends Parser {
    private static final String BEGIN_MITZVAT_ASE = "begin_ase";
    private static final String BEGIN_MITZVAT_LOTAASE = "begin_lotaase";
    private static final String BEGIN_MITZVA = "begin_mitzva";
    private static final String SEFER_HAMITZVOT = "ספר המצוות";

    private int sectionNum;
    private int mitzvaNum;
    private String mitzvaType;

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_MITZVAT_ASE;
            }

            @Override
            public boolean match(Line line) {
                return line.is("מצות עשה");
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_MITZVAT_LOTAASE;
            }

            @Override
            public boolean match(Line line) {
                return line.is("מצות לא תעשה");
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_MITZVA;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("מצוה") && line.wordCount() == 2;
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_MITZVAT_ASE:
                sectionNum = 3; // hakdama is section 1, shorshei hamitzvot section 2
                mitzvaNum = 0;
                mitzvaType = "עשה";
                break;
            case BEGIN_MITZVAT_LOTAASE:
                sectionNum = 4;
                mitzvaNum = 0;
                mitzvaType = "לא תעשה";
                break;
            case BEGIN_MITZVA:
                jsonObjectFlush();
                mitzvaNum++;
                jsonObject().add(URI, getUri());
                jsonObject().add(RDFS_LABEL, SEFER_HAMITZVOT + " " + mitzvaType + " " + line.getLine());
                jsonObject().add(JBO_SEFER, JBR + "seferhamitzvot");
                break;
            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }

    }

    @Override
    protected String getUri() {
        return JBR + "seferhamitzvot-" + sectionNum + "-" + mitzvaNum;
    }
}
