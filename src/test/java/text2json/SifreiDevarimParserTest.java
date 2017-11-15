package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.SifreiDevarimParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class SifreiDevarimParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {

        json = setupParser(new SifreiDevarimParser() , "sifreidevarim");

    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(357, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "sifreidevarim-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"ספרי דברים א");
        assertBookProperty(object,"sifreidevarim");

        object = json.getObject(67);
        assertTextUriProperty(object, "sifreidevarim-68");
        assertLabelProperty( object ,"ספרי דברים סח");
        assertPositionProperty(object ,"68");
        assertBookProperty(object,"sifreidevarim");

        object = json.getObject(101);
        assertTextUriProperty(object, "sifreidevarim-102");
        assertLabelProperty( object ,"ספרי דברים קב");
        assertPositionProperty(object ,"102");
        assertBookProperty(object,"sifreidevarim");

        object = json.getObject(198);
        assertTextUriProperty(object, "sifreidevarim-199");
        assertLabelProperty( object ,"ספרי דברים קצט");
        assertPositionProperty(object ,"199");
        assertBookProperty(object,"sifreidevarim");

        object = json.getObject(278);
        assertTextUriProperty(object, "sifreidevarim-279");
        assertLabelProperty( object ,"ספרי דברים רעט");
        assertPositionProperty(object ,"279");
        assertBookProperty(object,"sifreidevarim");

        object = json.getObject(356);
        assertTextUriProperty(object, "sifreidevarim-357");
        assertLabelProperty( object ,"ספרי דברים שנז");
        assertPositionProperty(object ,"357");
        assertBookProperty(object,"sifreidevarim");
    }
}
