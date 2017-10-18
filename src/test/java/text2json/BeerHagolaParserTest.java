package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.BeerHagolaParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class BeerHagolaParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new BeerHagolaParser();
        createOutputFolderIfNotExists("beerhagola");
        BufferedReader reader = getText("beerhagola/beerhagola.txt");
        createOutputFolderIfNotExists("beerhagola");
        parser.parse(reader, "json/beerhagola/beerhagola.json");
        json = getJson("json/beerhagola/beerhagola.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(8, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertTextUriProperty(object,"beerhagola-0");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"באר הגולה הקדמה");
        assertBookProperty( object,"beerhagola");


        object = json.getObject(6);
        assertTextUriProperty(object,"beerhagola-6");
        assertLabelProperty( object ,"באר הגולה באר ו");
        assertPositionProperty(object ,"7");
        assertBookProperty( object,"beerhagola");

        object = json.getObject(2);
        assertTextUriProperty(object,"beerhagola-2");
        assertLabelProperty( object ,"באר הגולה באר ב");
        assertPositionProperty(object ,"3");
        assertBookProperty( object,"beerhagola");
    }
}
