package text2json.parsers;

import text2json.JbsParser;
import text2json.JbsUtils;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;


/**
 * Created by orel on 22/07/18.
 */

public class NefeshHachaimParser extends JbsParser {

    private int perekNum = 0, gateNum = 0, position = 0;
    private final static String BEGIN_GATE = "begin_gate"; //gate is shaar

    public NefeshHachaimParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_SEFER;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("נפש החיים");
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_PEREK;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Chapter") && line.wordCount() == 2;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_GATE;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Gate") && line.wordCount() == 2;
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_SEFER:
                break;

            case BEGIN_GATE:
                jsonObjectFlush();
                packagesJsonObjectFlush();
                gateNum++;
                perekNum = 0;
                addBook(packagesJsonObject(), getBookId());
                addPackageUri(getBookId() + "-" + Integer.toString(gateNum));
                addPosition(packagesJsonObject(), gateNum);
                addLabel(packagesJsonObject(),"נפש החיים " + JbsUtils.numberToHebrew(gateNum));
                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                position++;
                addBook(jsonObject(), getBookId());
                addTextUri(getUri());
                addPosition(jsonObject(), position);
                addLabel(jsonObject(), getLabel());
                packagesJsonObjectFlush();
                break;

            case NO_MATCH:
                appendText(line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return getBookId() + "-" + Integer.toString(gateNum) + "-" + Integer.toString(perekNum);
    }

    private String getLabel() {
        String str1 = "נפש החיים ";
        return str1 + JbsUtils.numberToHebrew(gateNum) + " " + JbsUtils.numberToHebrew(perekNum);
    }

    @Override
    protected String getBookId() {
        return "nefeshhachaim";
    }
}

