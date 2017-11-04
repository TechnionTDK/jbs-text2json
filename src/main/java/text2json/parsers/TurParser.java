package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.JBO_TEXT;
import static text2json.JbsUtils.numberToHebrew;


public class TurParser extends JbsParser {

    protected static final String BEGIN_TUR = "begin_tur";
    private int turNum = 0,position=0;
    private int simanNum = 0;
    private String turName = "";

    public TurParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("ארבעה טורים") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_TUR;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("טור ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_PEREK;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Siman") && line.wordCount() <= 10;
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



            case BEGIN_TUR:
                jsonObjectFlush();
                simanNum=0;
                turNum++;
                turName = line.getLine();
                addBook(packagesJsonObject() ,"tur");
                addPackageUri( "tur-" + turNum);
                addPosition(packagesJsonObject(), turNum);
                addRdfs(packagesJsonObject(), turName);
                packagesJsonObjectFlush();
                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                simanNum++;
                position++;
                addUri( getUri());
                addPosition( position);
                addBook( "tur");
                addWithin( "tur-" + turNum);
                String rdfs = "ארבעה טורים " + turName +" " + numberToHebrew(simanNum);
                addRdfs(rdfs);

                break;

            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  "tur-" + turNum + "-"+ simanNum;    }
}
