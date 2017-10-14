package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.SeferHaYasharMidrashParser;
import text2json.parsers.TannaDeBeiEliyahuZutaParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class TannaDeBeiEliyahuZutaParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new TannaDeBeiEliyahuZutaParser();
        createOutputFolderIfNotExists("tannadebeieliyahuzuta");
        BufferedReader reader = getText("tannadebeieliyahuzuta/tannadebeieliyahuzuta.txt");
        createOutputFolderIfNotExists("tannadebeieliyahuzuta");
        parser.parse(reader, "json/tannadebeieliyahuzuta/tannadebeieliyahuzuta.json");
        json = getJson("json/tannadebeieliyahuzuta/tannadebeieliyahuzuta.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(22, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "tannadebeieliyahuzuta-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תנא דבי אליהו זוטא חלק א - סדר אליהו זוטא פרק 1");
        assertBookProperty(object,"tannadebeieliyahuzuta");

        object = json.getObject(4);
        assertTextUriProperty(object, "tannadebeieliyahuzuta-1-5");
        assertLabelProperty( object ,"תנא דבי אליהו זוטא חלק א - סדר אליהו זוטא פרק 5");
        assertPositionProperty(object ,"5");
        assertBookProperty(object,"tannadebeieliyahuzuta");

        object = json.getObject(7);
        assertTextUriProperty(object, "tannadebeieliyahuzuta-1-8");
        assertLabelProperty( object ,"תנא דבי אליהו זוטא חלק א - סדר אליהו זוטא פרק 8");
        assertPositionProperty(object ,"8");
        assertBookProperty(object,"tannadebeieliyahuzuta");

        object = json.getObject(9);
        assertTextUriProperty(object, "tannadebeieliyahuzuta-1-10");
        assertLabelProperty( object ,"תנא דבי אליהו זוטא חלק א - סדר אליהו זוטא פרק 10");
        assertPositionProperty(object ,"10");
        assertBookProperty(object,"tannadebeieliyahuzuta");

        object = json.getObject(11);
        assertTextUriProperty(object, "tannadebeieliyahuzuta-1-12");
        assertLabelProperty( object ,"תנא דבי אליהו זוטא חלק א - סדר אליהו זוטא פרק 12");
        assertPositionProperty(object ,"12");
        assertBookProperty(object,"tannadebeieliyahuzuta");


        object = json.getObject(15);
        assertTextUriProperty(object, "tannadebeieliyahuzuta-2-0");
        assertLabelProperty( object ,"תנא דבי אליהו זוטא חלק ב - נספחים לסדר אליהו זוטא הקדמה");
        assertPositionProperty(object ,"16");
        assertBookProperty(object,"tannadebeieliyahuzuta");


        object = json.getObject(18);
        assertTextUriProperty(object, "tannadebeieliyahuzuta-2-4");
        assertLabelProperty( object ,"תנא דבי אליהו זוטא חלק ב - נספחים לסדר אליהו זוטא פרקי דר' אליעזר");
        assertPositionProperty(object ,"19");
        assertBookProperty(object,"tannadebeieliyahuzuta");


        object = json.getObject(21);
        assertTextUriProperty(object, "tannadebeieliyahuzuta-3-3");
        assertLabelProperty( object ,"תנא דבי אליהו זוטא חלק ג - הירידות פרק ג'");
        assertPositionProperty(object ,"22");
        assertBookProperty(object,"tannadebeieliyahuzuta");


    }
}
