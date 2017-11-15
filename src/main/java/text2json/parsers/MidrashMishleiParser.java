package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.JBO_TEXT;
import static text2json.JbsUtils.numberToHebrew;

/**
 * Created by Assaf on 08/06/2017.
 */
public class MidrashMishleiParser extends JbsParser {

    private int chapterNum = 0;
    private int pasukNum = 0;
    private int position = 0;
    protected static final String BEGIN_PASUK = "begin_pasuk";


    public MidrashMishleiParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("מדרש משלי") && line.wordCount() <= 10;
            }
        });


        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Perek ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PASUK;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("(משלי ");
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:
                break;

            case BEGIN_PEREK:
                packagesJsonObjectFlush();
                jsonObjectFlush();
                chapterNum++;
                pasukNum=0;
                String chapterName = "מדרש משלי " + numberToHebrew(chapterNum);
                addPackageUri(getBookId()+"-" + chapterNum );
                addBook(packagesJsonObject(), getBookId());
                addPosition(packagesJsonObject(),chapterNum);
                String label = "מדרש משלי " + chapterName;
                addLabel(packagesJsonObject(),label);
                break;

                case BEGIN_PASUK:
                jsonObjectFlush();
                pasukNum++;
                position++;
                int end = line.getLine().indexOf(":");
                String pasukLabel = line.getLine().substring(1,end-1);
                addUri( getUri());
                addBook( getBookId());
                addPosition(position);
                addLabel(pasukLabel);
                jsonObject().add(JBO_TEXT, line.getLine().substring(end));
                break;

            case NO_MATCH:
                appendText( line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  getBookId()+"-" + chapterNum  +"-" + pasukNum;    }

    @Override
    protected String getBookId() {
        return "midrashmishlei";
    }
}
