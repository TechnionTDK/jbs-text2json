package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MidrashTehillimParser;
import text2json.parsers.PesiktaRabbatiParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class PesiktaRabbatiParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new PesiktaRabbatiParser();
        createOutputFolderIfNotExists("pesiktarabbati");
        BufferedReader reader = getText("pesiktarabbati/pesiktarabbati.txt");
        createOutputFolderIfNotExists("pesiktarabbati");
        parser.parse(reader, "json/pesiktarabbati/pesiktarabbati.json");
        json = getJson("json/pesiktarabbati/pesiktarabbati.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(47, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "pesiktarabbati-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"פסיקתא רבתי פרק 1");
        assertBookProperty(object,"pesiktarabbati");

        object = json.getObject(5);
        assertTextUriProperty(object, "pesiktarabbati-6");
        assertLabelProperty( object ,"פסיקתא רבתי פרק 6");
        assertPositionProperty(object ,"6");
        assertBookProperty(object,"pesiktarabbati");

        object = json.getObject(13);
        assertTextUriProperty(object, "pesiktarabbati-14");
        assertLabelProperty( object ,"פסיקתא רבתי פרק 14");
        assertPositionProperty(object ,"14");
        assertBookProperty(object,"pesiktarabbati");

        object = json.getObject(21);
        assertTextUriProperty(object, "pesiktarabbati-22");
        assertLabelProperty( object ,"פסיקתא רבתי פרק 22");
        assertPositionProperty(object ,"22");
        assertBookProperty(object,"pesiktarabbati");

        object = json.getObject(32);
        assertTextUriProperty(object, "pesiktarabbati-33");
        assertLabelProperty( object ,"פסיקתא רבתי פרק 33");
        assertPositionProperty(object ,"33");
        assertBookProperty(object,"pesiktarabbati");

        object = json.getObject(46);
        assertTextUriProperty(object, "pesiktarabbati-47");
        assertLabelProperty( object ,"פסיקתא רבתי פרק 47");
        assertPositionProperty(object ,"47");
        assertBookProperty(object,"pesiktarabbati");
    }
}
