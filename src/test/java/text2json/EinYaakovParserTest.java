package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.EinYaakovParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class EinYaakovParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new EinYaakovParser() , "einyaakov");

    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(349, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "einyaakov-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"עין יעקב ברכות פרק א מאימתי קורין");
        assertBookProperty(object,"einyaakov");

        object = json.getObject(42);
        assertTextUriProperty(object, "einyaakov-3-10");
        assertLabelProperty( object ,"עין יעקב עירובין פרק י המוצא תפילין");
        assertPositionProperty(object ,"43");
        assertBookProperty(object,"einyaakov");

        object = json.getObject(98);
        assertTextUriProperty(object, "einyaakov-11-10");
        assertLabelProperty( object ,"עין יעקב יבמות פרק י");
        assertPositionProperty(object ,"99");
        assertBookProperty(object,"einyaakov");

        object = json.getObject(150);
        assertTextUriProperty(object, "einyaakov-16-5");
        assertLabelProperty( object ,"עין יעקב סוטה פרק ה");
        assertPositionProperty(object ,"151");
        assertBookProperty(object,"einyaakov");

        object = json.getObject(201);
        assertTextUriProperty(object, "einyaakov-19-2");
        assertLabelProperty( object ,"עין יעקב מכות פרק ב");
        assertPositionProperty(object ,"202");
        assertBookProperty(object,"einyaakov");

        object = json.getObject(303);
        assertTextUriProperty(object, "einyaakov-33-2");
        assertLabelProperty( object ,"עין יעקב כלים פרק ב");
        assertPositionProperty(object ,"304");
        assertBookProperty(object,"einyaakov");

        object = json.getObject(348);
        assertTextUriProperty(object, "einyaakov-38-1");
        assertLabelProperty( object ,"עין יעקב מידות פרק ב");
        assertPositionProperty(object ,"349");
        assertBookProperty(object,"einyaakov");
    }
}
