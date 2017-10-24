package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.PesiktaRabbatiParser;
import text2json.parsers.SederOlamRabbahParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class SederOlamRabbahParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new SederOlamRabbahParser();
        createOutputFolderIfNotExists("sederolamrabbah");
        BufferedReader reader = getText("sederolamrabbah/sederolamrabbah.txt");
        createOutputFolderIfNotExists("sederolamrabbah");
        parser.parse(reader, "json/sederolamrabbah/sederolamrabbah.json");
        json = getJson("json/sederolamrabbah/sederolamrabbah.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(30, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "sederolamrabbah-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"סדר עולם רבה א");
        assertBookProperty(object,"sederolamrabbah");

        object = json.getObject(5);
        assertTextUriProperty(object, "sederolamrabbah-6");
        assertLabelProperty( object ,"סדר עולם רבה ו");
        assertPositionProperty(object ,"6");
        assertBookProperty(object,"sederolamrabbah");

        object = json.getObject(13);
        assertTextUriProperty(object, "sederolamrabbah-14");
        assertLabelProperty( object ,"סדר עולם רבה יד");
        assertPositionProperty(object ,"14");
        assertBookProperty(object,"sederolamrabbah");

        object = json.getObject(21);
        assertTextUriProperty(object, "sederolamrabbah-22");
        assertLabelProperty( object ,"סדר עולם רבה כב");
        assertPositionProperty(object ,"22");
        assertBookProperty(object,"sederolamrabbah");

        object = json.getObject(29);
        assertTextUriProperty(object, "sederolamrabbah-30");
        assertLabelProperty( object ,"סדר עולם רבה ל");
        assertPositionProperty(object ,"30");
        assertBookProperty(object,"sederolamrabbah");

    }
}
