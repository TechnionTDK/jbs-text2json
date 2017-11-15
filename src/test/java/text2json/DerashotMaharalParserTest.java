package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.DerashotMaharalParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class DerashotMaharalParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new DerashotMaharalParser() , "derashotmaharal");

    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(5, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertTextUriProperty(object, "derashotmaharal-0");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"דרשות מהר\"ל - הקדמה לדרוש על התורה");
        assertBookProperty( object,"derashotmaharal");

        object = json.getObject(1);
        assertTextUriProperty(object, "derashotmaharal-1");
        assertLabelProperty( object ,"דרשות מהר\"ל - דרוש על התורה");
        assertPositionProperty(object ,"2");
        assertBookProperty( object,"derashotmaharal");

        object = json.getObject(2);
        assertTextUriProperty(object, "derashotmaharal-2");
        assertLabelProperty( object ,"דרשות מהר\"ל - דרוש על המצות");
        assertPositionProperty(object ,"3");
        assertBookProperty( object,"derashotmaharal");

        object = json.getObject(3);
        assertTextUriProperty(object, "derashotmaharal-3");
        assertLabelProperty( object ,"דרשות מהר\"ל - דרוש לשבת תשובה");
        assertPositionProperty(object ,"4");

        object = json.getObject(4);
        assertTextUriProperty(object, "derashotmaharal-4");
        assertLabelProperty( object ,"דרשות מהר\"ל - דרוש לשבת הגדול");
        assertPositionProperty(object ,"5");
        assertBookProperty( object,"derashotmaharal");
    }
}
