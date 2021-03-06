package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.NetivotOlamParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class NetivotOlamParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new NetivotOlamParser() , "netivotolam");

    }



    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(136, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "netivotolam-0");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"נתיבות עולם הקדמה");
        assertBookProperty(object,"netivotolam");

        object = json.getObject(3);
        assertTextUriProperty(object, "netivotolam-1-3");
        assertLabelProperty( object ,"נתיבות עולם התורה ג");
        assertPositionProperty(object ,"4");
        assertBookProperty(object,"netivotolam");

        object = json.getObject(23);
        assertTextUriProperty(object, "netivotolam-2-5");
        assertLabelProperty( object ,"נתיבות עולם העבודה ה");
        assertPositionProperty(object ,"24");
        assertBookProperty(object,"netivotolam");

        object = json.getObject(32);
        assertTextUriProperty(object, "netivotolam-2-14");
        assertLabelProperty( object ,"נתיבות עולם העבודה יד");
        assertPositionProperty(object ,"33");

        object = json.getObject(57);
        assertTextUriProperty(object, "netivotolam-8-2");
        assertLabelProperty( object ,"נתיבות עולם השלום ב");
        assertPositionProperty(object ,"58");

        object = json.getObject(64);
        assertTextUriProperty(object, "netivotolam-10-3");
        assertLabelProperty( object ,"נתיבות עולם הענוה ג");
        assertPositionProperty(object ,"65");
        assertBookProperty(object,"netivotolam");

        object = json.getObject(91);
        assertTextUriProperty(object, "netivotolam-15-2");
        assertLabelProperty( object ,"נתיבות עולם הפּרישות ב");
        assertPositionProperty(object ,"92");

        object = json.getObject(117);
        assertTextUriProperty(object, "netivotolam-22-1");
        assertLabelProperty( object ,"נתיבות עולם התוכחה א");
        assertPositionProperty(object ,"118");

        object = json.getObject(135);
        assertTextUriProperty(object, "netivotolam-33-1");
        assertLabelProperty( object ,"נתיבות עולם דרך ארץ א");
        assertPositionProperty(object ,"136");
        assertBookProperty(object,"netivotolam");
    }
}
