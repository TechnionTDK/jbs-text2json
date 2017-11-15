package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.OrotHaKodeshParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class OrotHaKodeshParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {

        json = setupParser(new OrotHaKodeshParser() , "orothakodesh");

    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(121, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "orothakodesh-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"אורות הקודש א א");
        assertBookProperty(object,"orothakodesh");

        object = json.getObject(3);
        assertTextUriProperty(object, "orothakodesh-1-4");
        assertLabelProperty( object ,"אורות הקודש א ד");
        assertPositionProperty(object ,"4");
        assertBookProperty(object,"orothakodesh");

        object = json.getObject(23);
        assertTextUriProperty(object, "orothakodesh-2-4");
        assertLabelProperty( object ,"אורות הקודש ב ד");
        assertPositionProperty(object ,"24");
        assertBookProperty(object,"orothakodesh");

        object = json.getObject(32);
        assertTextUriProperty(object, "orothakodesh-2-13");
        assertLabelProperty( object ,"אורות הקודש ב יג");
        assertPositionProperty(object ,"33");
        assertBookProperty(object,"orothakodesh");

        object = json.getObject(57);
        assertTextUriProperty(object, "orothakodesh-3-18");
        assertLabelProperty( object ,"אורות הקודש ג יח");
        assertPositionProperty(object ,"58");
        assertBookProperty(object,"orothakodesh");

        object = json.getObject(94);
        assertTextUriProperty(object, "orothakodesh-5-15");
        assertLabelProperty( object ,"אורות הקודש ה טו");
        assertPositionProperty(object ,"95");
        assertBookProperty(object,"orothakodesh");

        object = json.getObject(120);
        assertTextUriProperty(object, "orothakodesh-7-18");
        assertLabelProperty( object ,"אורות הקודש ז יח");
        assertPositionProperty(object ,"121");
        assertBookProperty(object,"orothakodesh");
    }
}
