package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.GevurotHashemParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class GevurotHashemParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new GevurotHashemParser();
        createOutputFolderIfNotExists("gevurothashem");
        BufferedReader reader = getText("gevurothashem/gevurothashem.txt");
        createOutputFolderIfNotExists("gevurothashem");
        parser.parse(reader, "json/gevurothashem/gevurothashem.json");
        json = getJson("json/gevurothashem/gevurothashem.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(57, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertTextUriProperty(object, "gevurothashem-0-0");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"גבורות השם - הקדמה א");
        assertBookProperty(object,"gevurothashem");

        object = json.getObject(13);
        assertTextUriProperty(object, "gevurothashem-11");
        assertLabelProperty( object ,"גבורות השם יא");
        assertPositionProperty(object ,"14");
        assertBookProperty(object,"gevurothashem");

        object = json.getObject(34);
        assertTextUriProperty(object, "gevurothashem-32");
        assertLabelProperty( object ,"גבורות השם לב");
        assertPositionProperty(object ,"35");

        object = json.getObject(41);
        assertTextUriProperty(object, "gevurothashem-39");
        assertLabelProperty( object ,"גבורות השם לט");
        assertPositionProperty(object ,"42");

        object = json.getObject(54);
        assertTextUriProperty(object, "gevurothashem-52");
        assertLabelProperty( object ,"גבורות השם נב");
        assertPositionProperty(object ,"55");
        assertBookProperty(object,"gevurothashem");

        object = json.getObject(56);
        assertTextUriProperty(object, "gevurothashem-54");
        assertLabelProperty( object ,"גבורות השם נד");
        assertPositionProperty(object ,"57");
        assertBookProperty(object,"gevurothashem");
    }
}
