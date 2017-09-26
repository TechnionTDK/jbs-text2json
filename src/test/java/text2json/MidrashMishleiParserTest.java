package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MidbarShurParser;
import text2json.parsers.MidrashMishleiParser;

import java.io.BufferedReader;
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
        Parser parser = new MidrashMishleiParser();
        createOutputFolderIfNotExists("midrashmishlei");
        BufferedReader reader = getText("midrashmishlei/midrashmishlei.txt");
        createOutputFolderIfNotExists("midrashmishlei");
        parser.parse(reader, "json/midrashmishlei/midrashmishlei.json");
        json = getJson("json/midrashmishlei/midrashmishlei.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(31, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "midrashmishlei-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"מדרש משלי - פרק 1");
        assertBookProperty(object,"midrashmishlei");

        object = json.getObject(3);
        assertTextUriProperty(object, "midrashmishlei-4");
        assertLabelProperty( object ,"מדרש משלי - פרק 4");
        assertPositionProperty(object ,"4");
        assertBookProperty(object,"midrashmishlei");

        object = json.getObject(18);
        assertTextUriProperty(object, "midrashmishlei-19");
        assertLabelProperty( object ,"מדרש משלי - פרק 19");
        assertPositionProperty(object ,"19");
        assertBookProperty(object,"midrashmishlei");

        object = json.getObject(19);
        assertTextUriProperty(object, "midrashmishlei-20");
        assertLabelProperty( object ,"מדרש משלי - פרק 20");
        assertPositionProperty(object ,"20");
        assertBookProperty(object,"midrashmishlei");

        object = json.getObject(28);
        assertTextUriProperty(object, "midrashmishlei-29");
        assertLabelProperty( object ,"מדרש משלי - פרק 29");
        assertPositionProperty(object ,"29");
        assertBookProperty(object,"midrashmishlei");

        object = json.getObject(30);
        assertTextUriProperty(object, "midrashmishlei-31");
        assertLabelProperty( object ,"מדרש משלי - פרק 31");
        assertPositionProperty(object ,"31");
        assertBookProperty(object,"midrashmishlei");
    }
}
