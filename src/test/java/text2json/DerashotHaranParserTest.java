package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.DerashotHaranParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 05/05/2017.
 */
    public class DerashotHaranParserTest {
    static SubjectsJson json;

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new DerashotHaranParser() , "derashotharan");

    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(12, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertTextUriProperty(object, "derashotharan-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"דרשות הר\"ן א");
        assertBookProperty(object,"derashotharan");

        object = json.getObject(4);
        assertTextUriProperty(object, "derashotharan-5");
        assertLabelProperty( object ,"דרשות הר\"ן ה");
        assertPositionProperty(object ,"5");
        assertBookProperty(object,"derashotharan");

        object = json.getObject(8);
        assertTextUriProperty(object, "derashotharan-9");
        assertLabelProperty( object ,"דרשות הר\"ן ט");
        assertPositionProperty(object ,"9");
        assertBookProperty(object,"derashotharan");

        object = json.getObject(11);
        assertTextUriProperty(object, "derashotharan-12");
        assertLabelProperty( object ,"דרשות הר\"ן יב");
        assertPositionProperty(object ,"12");
        assertBookProperty(object,"derashotharan");
    }
}