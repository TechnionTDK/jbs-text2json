package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.OrotParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class OrotParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new OrotParser();
        createOutputFolderIfNotExists("orot");
        BufferedReader reader = getText("orot/orot.txt");
        createOutputFolderIfNotExists("orot/");
        parser.parse(reader, "json/orot/orot.json");
        json = getJson("json/orot/orot.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(143, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertTextUriProperty(object, "orot-1-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"אורות - ארץ ישראל א");
        assertBookProperty(object,"orot");

        object = json.getObject(13);
        assertTextUriProperty(object, "orot-1-2-6");
        assertLabelProperty( object ,"אורות - המלחמה ו");
        assertPositionProperty(object ,"14");
        assertBookProperty(object,"orot");

        object = json.getObject(34);
        assertTextUriProperty(object, "orot-1-3-17");
        assertLabelProperty( object ,"אורות - ישראל ותחיתו יז");
        assertPositionProperty(object ,"35");
        assertBookProperty(object,"orot");

        object = json.getObject(41);
        assertTextUriProperty(object, "orot-1-3-24");
        assertLabelProperty( object ,"אורות - ישראל ותחיתו כד");
        assertPositionProperty(object ,"42");
        assertBookProperty(object,"orot");

        object = json.getObject(54);
        assertTextUriProperty(object, "orot-1-4-5");
        assertLabelProperty( object ,"אורות - אורות התחיה ה");
        assertPositionProperty(object ,"55");

        object = json.getObject(56);
        assertTextUriProperty(object, "orot-1-4-7");
        assertLabelProperty( object ,"אורות - אורות התחיה ז");
        assertPositionProperty(object ,"57");
        assertBookProperty(object,"orot");
    }
}
