package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MesilatYesharimParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by omishali on 20/12/2016.
 */
public class MesilatYesharimParserTest {
    static SubjectsJson json;

    @BeforeClass
    public static void setup() throws Exception {
        json = setupParser(new MesilatYesharimParser() , "mesilatyesharim");

    }
    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(28, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertTextUriProperty(object, "mesilatyesharim-0");
        assertPositionProperty(object ,"0");
        assertLabelProperty( object ,"הקדמת הרב המחבר זצ\"ל");
        assertBookProperty(object,"mesilatyesharim");

        object = json.getObject(1);
        assertTextUriProperty(object, "mesilatyesharim-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"בביאור כלל חובת האדם בעולמו");
        assertBookProperty(object,"mesilatyesharim");

        object = json.getObject(27);
        assertTextUriProperty(object, "mesilatyesharim-27");
        assertPositionProperty(object ,"27");
        assertLabelProperty( object ,"חתימה");
        assertBookProperty(object,"mesilatyesharim");
    }
}
