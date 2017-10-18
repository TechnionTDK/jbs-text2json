package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.PesiktaDeRavKahannaParser;
import text2json.parsers.PesiktaRabbatiParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class PesiktaDeRavKahannaParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new PesiktaDeRavKahannaParser();
        createOutputFolderIfNotExists("pesiktaderavkahanna");
        BufferedReader reader = getText("pesiktaderavkahanna/pesiktaderavkahanna.txt");
        createOutputFolderIfNotExists("pesiktaderavkahanna");
        parser.parse(reader, "json/pesiktaderavkahanna/pesiktaderavkahanna.json");
        json = getJson("json/pesiktaderavkahanna/pesiktaderavkahanna.json");
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
        assertTextUriProperty(object, "pesiktaderavkahanna-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"פסיקתא דרב כהנא פסקה 1");
        assertBookProperty(object,"pesiktaderavkahanna");

        object = json.getObject(5);
        assertTextUriProperty(object, "pesiktaderavkahanna-6");
        assertLabelProperty( object ,"פסיקתא דרב כהנא פסקה 6");
        assertPositionProperty(object ,"6");
        assertBookProperty(object,"pesiktaderavkahanna");

        object = json.getObject(13);
        assertTextUriProperty(object, "pesiktaderavkahanna-14");
        assertLabelProperty( object ,"פסיקתא דרב כהנא פסקה 14");
        assertPositionProperty(object ,"14");
        assertBookProperty(object,"pesiktaderavkahanna");

        object = json.getObject(21);
        assertTextUriProperty(object, "pesiktaderavkahanna-22");
        assertLabelProperty( object ,"פסיקתא דרב כהנא פסקה 22");
        assertPositionProperty(object ,"22");
        assertBookProperty(object,"pesiktaderavkahanna");

        object = json.getObject(27);
        assertTextUriProperty(object, "pesiktaderavkahanna-28");
        assertLabelProperty( object ,"פסיקתא דרב כהנא פסקה 28");
        assertPositionProperty(object ,"28");
        assertBookProperty(object,"pesiktaderavkahanna");

    }
}
