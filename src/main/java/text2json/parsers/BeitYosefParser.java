package text2json.parsers;

import text2json.JbsParser;
import text2json.Line;
import text2json.LineMatcher;

import java.io.IOException;

import static text2json.JbsOntology.JBO_TEXT;

/**
 * Created by Assaf on 08/06/2017.
 */
public class BeitYosefParser extends JbsParser {

    protected static final String BEGIN_TUR = "begin_tur";
    protected static final String BEGIN_SEIF = "begin_seif";
    protected static final String BEGIN_SIMAN = "begin_siman";

    private int turNum = 0,position =0,packagePosition =1;
    private int seifNum = 0;
    private int simanNum = 0;
    private String turName ="";

    public BeitYosefParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("בית יוסף") && line.wordCount() <= 10;
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
            public String type() { return BEGIN_SEIF;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Seif ") && line.wordCount() <= 10;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SIMAN;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("Siman ") && line.wordCount() <= 10;
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
                seifNum=0;
                simanNum=0;
                turName = line.getLine();
                turNum++;
                addBook(packagesJsonObject(), "beityosef");
                addPosition(packagesJsonObject(),packagePosition);
                packagePosition++;
                addPackageUri( "beityosef-"+turNum);
                addRdfs(packagesJsonObject(),"בית יוסף - " + turName );
                packagesJsonObjectFlush();
                break;

            case BEGIN_SIMAN :
                jsonObjectFlush();
                seifNum=0;
                simanNum++;
//                addBook(packagesJsonObject(), "beityosef");
//                addPosition(packagesJsonObject(), packagePosition);
//                packagePosition++;
//                addPackageUri( "beityosef-"+ turNum + "-" + seifNum);
//                String rdfs = "בית יוסף - " + turName + " סעיף " + seifNum;
//                addRdfs(packagesJsonObject(), rdfs);
                packagesJsonObjectFlush();

                break;

            case BEGIN_SEIF:
                jsonObjectFlush();
                seifNum++;
                position++;
                addUri( getUri());
                addPosition( position);
                addBook( "beityosef");
                addWithin( "beityosef-" + turNum);
                addWithin( "beityosef-" + turNum +"-" + simanNum);
                String rdfs1 = "בית יוסף - " + turName  + " סימן " + simanNum + " סעיף " + seifNum;
                addRdfs(rdfs1);
                packagesJsonObjectFlush();

                break;


            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  "beityosef-" + turNum +"-"  + simanNum + "-" + seifNum;   }


}
