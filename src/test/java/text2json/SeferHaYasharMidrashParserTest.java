package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.SeferHaYasharMidrashParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class SeferHaYasharMidrashParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new SeferHaYasharMidrashParser() , "seferhayasharmidrash");

    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(18, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "seferhayasharmidrash-0");
        assertPositionProperty(object ,"0");
        assertLabelProperty( object ,"ספר הישר (מדרש) הקדמה");
        assertBookProperty(object,"seferhayasharmidrash");

        object = json.getObject(4);
        assertTextUriProperty(object, "seferhayasharmidrash-1-4");
        assertLabelProperty( object ,"ספר הישר (מדרש) וירא");
        assertPositionProperty(object ,"4");
        assertBookProperty(object,"seferhayasharmidrash");

        object = json.getObject(7);
        assertTextUriProperty(object, "seferhayasharmidrash-1-7");
        assertLabelProperty( object ,"ספר הישר (מדרש) וישלח");
        assertPositionProperty(object ,"7");
        assertBookProperty(object,"seferhayasharmidrash");

        object = json.getObject(9);
        assertTextUriProperty(object, "seferhayasharmidrash-1-9");
        assertLabelProperty( object ,"ספר הישר (מדרש) מקץ");
        assertPositionProperty(object ,"9");
        assertBookProperty(object,"seferhayasharmidrash");

        object = json.getObject(11);
        assertTextUriProperty(object, "seferhayasharmidrash-1-11");
        assertLabelProperty( object ,"ספר הישר (מדרש) ויחי");
        assertPositionProperty(object ,"11");
        assertBookProperty(object,"seferhayasharmidrash");


        object = json.getObject(14);
        assertTextUriProperty(object, "seferhayasharmidrash-4-0");
        assertLabelProperty( object ,"ספר הישר (מדרש) ספר במדבר");
        assertPositionProperty(object ,"14");
        assertBookProperty(object,"seferhayasharmidrash");


        object = json.getObject(17);
        assertTextUriProperty(object, "seferhayasharmidrash-7-0");
        assertLabelProperty( object ,"ספר הישר (מדרש) ספר שופטים");
        assertPositionProperty(object ,"17");
        assertBookProperty(object,"seferhayasharmidrash");


    }
}
