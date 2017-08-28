package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.DerechChaimParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class DerechChaimParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new DerechChaimParser();
        createOutputFolderIfNotExists("derechchaim");
        BufferedReader reader = getText("derechchaim/derechchaim.txt");
        createOutputFolderIfNotExists("derechchaim");
        parser.parse(reader, "json/derechchaim/derechchaim.json");
        json = getJson("json/derechchaim/derechchaim.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(84, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertTextUriProperty(object, "derechchaim-0-0");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"דרך חיים - הקדמה");
        assertBookProperty( object,"derechchaim");

        object = json.getObject(3);
        assertTextUriProperty(object, "derechchaim-1-3");
        assertLabelProperty( object ,"דרך חיים א ג");
        assertPositionProperty(object ,"4");
        assertBookProperty( object,"derechchaim");

        object = json.getObject(23);
        assertTextUriProperty(object, "derechchaim-2-5");
        assertLabelProperty( object ,"דרך חיים ב ה");
        assertPositionProperty(object ,"24");
        assertBookProperty( object,"derechchaim");

        object = json.getObject(32);
        assertTextUriProperty(object, "derechchaim-2-14");
        assertLabelProperty( object ,"דרך חיים ב יד");
        assertPositionProperty(object ,"33");

        object = json.getObject(57);
        assertTextUriProperty(object, "derechchaim-4-5");
        assertLabelProperty( object ,"דרך חיים ד ה");
        assertPositionProperty(object ,"58");
        assertBookProperty( object,"derechchaim");

        object = json.getObject(64);
        assertTextUriProperty(object, "derechchaim-4-12");
        assertLabelProperty( object ,"דרך חיים ד יב");
        assertPositionProperty(object ,"65");
        assertBookProperty( object,"derechchaim");

        object = json.getObject(83);
        assertTextUriProperty(object, "derechchaim-5-9");
        assertLabelProperty( object ,"דרך חיים ה ט");
        assertPositionProperty(object ,"84");

    }
}
