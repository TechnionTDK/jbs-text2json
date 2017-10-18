package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.OrChadashParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class OrChadashParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new OrChadashParser();
        createOutputFolderIfNotExists("orchadash");
        BufferedReader reader = getText("orchadash/orchadash.txt");
        createOutputFolderIfNotExists("orchadash");
        parser.parse(reader, "json/orchadash/orchadash.json");
        json = getJson("json/orchadash/orchadash.json");
    }


    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(12, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertTextUriProperty(object, "orchadash-00");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"אור חדש הקדמה א");
        assertBookProperty(object,"orchadash");

        object = json.getObject(2);
        assertTextUriProperty(object, "orchadash-1");
        assertLabelProperty( object ,"אור חדש א");
        assertPositionProperty(object ,"3");
        assertBookProperty(object,"orchadash");

        object = json.getObject(5);
        assertTextUriProperty(object, "orchadash-4");
        assertLabelProperty( object ,"אור חדש ד");
        assertPositionProperty(object ,"6");
        assertBookProperty(object,"orchadash");

        object = json.getObject(6);
        assertTextUriProperty(object, "orchadash-5");
        assertLabelProperty( object ,"אור חדש ה");
        assertPositionProperty(object ,"7");
        assertBookProperty(object,"orchadash");

        object = json.getObject(9);
        assertTextUriProperty(object, "orchadash-8");
        assertLabelProperty( object ,"אור חדש ח");
        assertPositionProperty(object ,"10");
        assertBookProperty(object,"orchadash");

        object = json.getObject(11);
        assertTextUriProperty(object, "orchadash-10");
        assertLabelProperty( object ,"אור חדש י");
        assertPositionProperty(object ,"12");
        assertBookProperty(object,"orchadash");
    }
}
