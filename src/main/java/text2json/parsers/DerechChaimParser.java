package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.JBO_INTERPRETS;
import static text2json.JbsOntology.JBR_TEXT;
import static text2json.JbsUtils.HEB_LETTERS_INDEX;

/**
 * Created by Assaf on 08/06/2017.
 */
public class DerechChaimParser extends JbsParser {

    protected static final String BEGIN_MISHNA = "begin_mishna";
    private int mishnaNum = 0;
    private int perekNum = 0;
    private int position = 0, packagePosition =1;


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
                break;

            case BEGIN_HAKDAMA:
                jsonObjectFlush();
                position++;
                addTextUri( getUri());
                addBook( getBookId() );
                addPosition(position);
                addLabel("דרך חיים הקדמה");

                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                mishnaNum=0;
                perekNum++;
                addPackageUri( getBookId() +"-" + perekNum);
                addPosition(packagesJsonObject(),packagePosition);
                packagePosition++;
                String label1 = "דרך חיים " + HEB_LETTERS_INDEX[perekNum-1];
                addLabel(packagesJsonObject(), label1);
                packagesJsonObjectFlush();
                break;

            case BEGIN_MISHNA:
                jsonObjectFlush();
                mishnaNum++;
                position++;
                addTextUri( getUri());
                addPosition(position);
                addBook( getBookId() );
                jsonObject().add(JBO_INTERPRETS, JBR_TEXT + "mishna-4-9-" + perekNum+"-"+mishnaNum);
                addWithin( getBookId() +"-" +  perekNum);
                String label = "דרך חיים " + HEB_LETTERS_INDEX[perekNum-1] + " " + HEB_LETTERS_INDEX[mishnaNum-1];
                addLabel(label);

                break;

            case NO_MATCH:
                appendText( line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  getBookId() +"-" + perekNum + "-"+ mishnaNum;    }

    @Override
    protected String getBookId() {
        return "derechchaim";
    }
}
