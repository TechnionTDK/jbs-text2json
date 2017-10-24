package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MidbarShurParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class MidbarShurParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new MidbarShurParser();
        createOutputFolderIfNotExists("midbarshur");
        BufferedReader reader = getText("midbarshur/midbarshur.txt");
        createOutputFolderIfNotExists("midbarshur");
        parser.parse(reader, "json/midbarshur/midbarshur.json");
        json = getJson("json/midbarshur/midbarshur.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(38, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "midbarshur-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"מדבר שור א");
        assertBookProperty(object,"midbarshur");

        object = json.getObject(3);
        assertTextUriProperty(object, "midbarshur-4");
        assertLabelProperty( object ,"מדבר שור ד");
        assertPositionProperty(object ,"4");
        assertBookProperty(object,"midbarshur");

        object = json.getObject(18);
        assertTextUriProperty(object, "midbarshur-19");
        assertLabelProperty( object ,"מדבר שור יט");
        assertPositionProperty(object ,"19");
        assertBookProperty(object,"midbarshur");

        object = json.getObject(19);
        assertTextUriProperty(object, "midbarshur-20");
        assertLabelProperty( object ,"מדבר שור כ");
        assertPositionProperty(object ,"20");
        assertBookProperty(object,"midbarshur");

        object = json.getObject(28);
        assertTextUriProperty(object, "midbarshur-29");
        assertLabelProperty( object ,"מדבר שור כט");
        assertPositionProperty(object ,"29");
        assertBookProperty(object,"midbarshur");

        object = json.getObject(37);
        assertTextUriProperty(object, "midbarshur-38");
        assertLabelProperty( object ,"מדבר שור לח");
        assertPositionProperty(object ,"38");
        assertBookProperty(object,"midbarshur");
    }
}
