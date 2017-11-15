package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.BeerHagolaParser;
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
        json = setupParser(new BeerHagolaParser() , "beerhagola");
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
        assertLabelProperty( object ,"באר הגולה ו");
        assertPositionProperty(object ,"7");
        assertBookProperty( object,"beerhagola");

        object = json.getObject(2);
        assertTextUriProperty(object,"beerhagola-2");
        assertLabelProperty( object ,"באר הגולה ב");
        assertPositionProperty(object ,"3");
        assertBookProperty( object,"beerhagola");
    }
}
