package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MidrashMishleiParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class MidrashMishleiParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {

        json = setupParser(new MidrashMishleiParser() , "midrashmishlei");

    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(296, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "midrashmishlei-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"משלי א א");
        assertBookProperty(object,"midrashmishlei");

        object = json.getObject(31);
        assertTextUriProperty(object, "midrashmishlei-1-32");
        assertLabelProperty( object ,"משלי א לב");
        assertPositionProperty(object ,"32");
        assertBookProperty(object,"midrashmishlei");

        object = json.getObject(62);
        assertTextUriProperty(object, "midrashmishlei-5-14");
        assertLabelProperty( object ,"משלי ה יד");
        assertPositionProperty(object ,"63");
        assertBookProperty(object,"midrashmishlei");

        object = json.getObject(87);
        assertTextUriProperty(object, "midrashmishlei-6-16");
        assertLabelProperty( object ,"משלי ו טז");
        assertPositionProperty(object ,"88");
        assertBookProperty(object,"midrashmishlei");

        object = json.getObject(100);
        assertTextUriProperty(object, "midrashmishlei-6-29");
        assertLabelProperty( object ,"משלי ו לג");
        assertPositionProperty(object ,"101");
        assertBookProperty(object,"midrashmishlei");

        object = json.getObject(145);
        assertTextUriProperty(object, "midrashmishlei-11-9");
        assertLabelProperty( object ,"משלי יא כד");
        assertPositionProperty(object ,"146");
        assertBookProperty(object,"midrashmishlei");

        object = json.getObject(189);
        assertTextUriProperty(object, "midrashmishlei-19-4");
        assertLabelProperty( object ,"משלי יט יח");
        assertPositionProperty(object ,"190");
        assertBookProperty(object,"midrashmishlei");

        object = json.getObject(222);
        assertTextUriProperty(object, "midrashmishlei-24-1");
        assertLabelProperty( object ,"משלי כד י");
        assertPositionProperty(object ,"223");
        assertBookProperty(object,"midrashmishlei");

        object = json.getObject(267);
        assertTextUriProperty(object, "midrashmishlei-30-12");
        assertLabelProperty( object ,"משלי ל כב");
        assertPositionProperty(object ,"268");
        assertBookProperty(object,"midrashmishlei");

        object = json.getObject(295);
        assertTextUriProperty(object, "midrashmishlei-31-13");
        assertLabelProperty( object ,"משלי לא לא");
        assertPositionProperty(object ,"296");
        assertBookProperty(object,"midrashmishlei");


    }
}
