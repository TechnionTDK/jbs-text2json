package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.SeferHakuzariParser;

import java.io.BufferedReader;
import java.util.Map;

import static text2json.TestUtils.*;

/**
 * Created by shilonoa on 4/24/2017.
 */
public class SeferHakuzariParserTest {
    static SubjectsJson json;

    @BeforeClass
    public static void setUp() throws Exception {
        SeferHakuzariParser parser = new SeferHakuzariParser();
        createOutputFolderIfNotExists("seferhakuzari");
        BufferedReader reader = getText("seferhakuzari/seferhakuzari.txt");
        createOutputFolderIfNotExists("seferhakuzari");
        parser.parse(reader, "json/seferhakuzari/seferhakuzari.json");
        json = getJson("json/seferhakuzari/seferhakuzari.json");
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "seferhakuzari-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"הכוזרי א א");
        assertBookProperty(object,"seferhakuzari");
    }
}