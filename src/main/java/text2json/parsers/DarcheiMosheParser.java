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
public class DarcheiMosheParser extends JbsParser {

    protected static final String BEGIN_TUR = "begin_tur";
    protected static final String BEGIN_SEIF = "begin_seif";
    protected static final String BEGIN_SIMAN = "begin_siman";

    private int turNum = 0,position =0,packagePosition =1;
    private int seifNum = 0;
    private int simanNum = 0; //, counter =0;
    private String turName ="";

    public DarcheiMosheParser() {
        createPackagesJson();
    }

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { return BEGIN_SEFER;}

            @Override
            public boolean match(Line line) {
                return line.beginsWith("דרכי משה") && line.wordCount() <= 10;
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
                addBook(packagesJsonObject(), "darcheimoshe");
                addPosition(packagesJsonObject(),packagePosition);
                packagePosition++;
                addPackageUri( "darcheimoshe-"+turNum);
                addRdfs(packagesJsonObject(),"דרכי משה " + turName );
                packagesJsonObjectFlush();
//                System.out.println("counter: "+ counter);
                break;

            case BEGIN_SIMAN :
                jsonObjectFlush();
                seifNum=0;
                simanNum++;
                addBook(packagesJsonObject(), "darcheimoshe");
                addPosition(packagesJsonObject(), packagePosition);
                packagePosition++;
                addPackageUri( "darcheimoshe-"+ turNum + "-" + simanNum);
                String rdfs = "דרכי משה " + turName + " סימן " + numberToHebrew(simanNum);
                addRdfs(packagesJsonObject(), rdfs);
                packagesJsonObjectFlush();

                break;

            case BEGIN_SEIF:
                jsonObjectFlush();
                seifNum++;
                position++;
                addUri( getUri());
                addPosition( position);
                addBook( "darcheimoshe");
                addWithin( "darcheimoshe-" + turNum);
                addWithin( "darcheimoshe-" + turNum +"-" + simanNum);
                String rdfs1 = "דרכי משה " + turName  + " סימן " + numberToHebrew(simanNum) + " סעיף " + numberToHebrew(seifNum);
                addRdfs(rdfs1);
                packagesJsonObjectFlush();
//                counter++;
                break;


            case NO_MATCH:
                jsonObject().append(JBO_TEXT, line.getLine());
                break;
        }
    }

    @Override
    protected String getUri() {
        return  "darcheimoshe-" + turNum +"-"  + simanNum + "-" + seifNum;   }


}