package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.DerechEretzZutaParser;
import text2json.parsers.MidbarShurParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class DerechEretzZutaParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new DerechEretzZutaParser();
        createOutputFolderIfNotExists("derecheretzzuta");
        BufferedReader reader = getText("derecheretzzuta/derecheretzzuta.txt");
        createOutputFolderIfNotExists("derecheretzzuta");
        parser.parse(reader, "json/derecheretzzuta/derecheretzzuta.json");
        json = getJson("json/derecheretzzuta/derecheretzzuta.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(10, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "derecheretzzuta-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"דרך ארץ זוטא - פרק 1");
        assertBookProperty(object,"derecheretzzuta");

        object = json.getObject(3);
        assertTextUriProperty(object, "derecheretzzuta-4");
        assertLabelProperty( object ,"דרך ארץ זוטא - פרק 4");
        assertPositionProperty(object ,"4");
        assertBookProperty(object,"derecheretzzuta");

        object = json.getObject(4);
        assertTextUriProperty(object, "derecheretzzuta-5");
        assertLabelProperty( object ,"דרך ארץ זוטא - פרק 5");
        assertPositionProperty(object ,"5");
        assertBookProperty(object,"derecheretzzuta");

        object = json.getObject(5);
        assertTextUriProperty(object, "derecheretzzuta-6");
        assertLabelProperty( object ,"דרך ארץ זוטא - פרק 6");
        assertPositionProperty(object ,"6");
        assertBookProperty(object,"derecheretzzuta");

        object = json.getObject(7);
        assertTextUriProperty(object, "derecheretzzuta-8");
        assertLabelProperty( object ,"דרך ארץ זוטא - פרק 8");
        assertPositionProperty(object ,"8");
        assertBookProperty(object,"derecheretzzuta");

        object = json.getObject(9);
        assertTextUriProperty(object, "derecheretzzuta-10");
        assertLabelProperty( object ,"דרך ארץ זוטא - פרק 10");
        assertPositionProperty(object ,"10");
        assertBookProperty(object,"derecheretzzuta");
    }
}
