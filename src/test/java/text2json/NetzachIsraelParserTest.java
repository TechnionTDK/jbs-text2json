package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.NetzachIsraelParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class NetzachIsraelParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new NetzachIsraelParser() , "netzachisrael");

    }


    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(64, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertTextUriProperty(object, "netzachisrael-0");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"נצח ישראל הקדמה");
        assertBookProperty(object,"netzachisrael");

        object = json.getObject(3);
        assertTextUriProperty(object, "netzachisrael-3");
        assertLabelProperty( object ,"נצח ישראל ג");
        assertPositionProperty(object ,"4");
        assertBookProperty(object,"netzachisrael");

        object = json.getObject(23);
        assertTextUriProperty(object, "netzachisrael-23");
        assertLabelProperty( object ,"נצח ישראל כג");
        assertPositionProperty(object ,"24");
        assertBookProperty(object,"netzachisrael");

        object = json.getObject(32);
        assertTextUriProperty(object, "netzachisrael-32");
        assertLabelProperty( object ,"נצח ישראל לב");
        assertPositionProperty(object ,"33");
        assertBookProperty(object,"netzachisrael");

        object = json.getObject(57);
        assertTextUriProperty(object, "netzachisrael-57");
        assertLabelProperty( object ,"נצח ישראל נז");
        assertPositionProperty(object ,"58");
        assertBookProperty(object,"netzachisrael");

        object = json.getObject(63);
        assertTextUriProperty(object, "netzachisrael-63");
        assertLabelProperty( object ,"נצח ישראל סג");
        assertPositionProperty(object ,"64");
        assertBookProperty(object,"netzachisrael");
    }
}
