package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.SeferHakuzariParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static text2json.JbsOntology.*;
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
        assertEquals(JBR_TEXT + "seferhakuzari-1-1", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("הכוזרי א א", object.get(RDFS_LABEL));
        assertBookProperty("seferhakuzari", object.get(JBO_BOOK));
    }
}