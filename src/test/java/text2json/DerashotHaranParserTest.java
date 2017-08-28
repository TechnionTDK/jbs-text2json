package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.DerashotHaranParser;
import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.*;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 05/05/2017.
 */
    public class DerashotHaranParserTest {
    static SubjectsJson json;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new DerashotHaranParser();
        createOutputFolderIfNotExists("derashotharan");
        BufferedReader reader = getText("derashotharan/derashotharan.txt");
        createOutputFolderIfNotExists("derashotharan");
        parser.parse(reader, "json/derashotharan/derashotharan.json");
        json = getJson("json/derashotharan/derashotharan.json");
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
        assertEquals(JBR_TEXT + "derashotharan-1", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("דרשות הר\"ן א", object.get(RDFS_LABEL));
        assertBookProperty("derashotharan", object.get(JBO_BOOK));

        object = json.getObject(4);
        assertEquals(JBR_TEXT + "derashotharan-5", object.get(URI));
        assertEquals("דרשות הר\"ן ה", object.get(RDFS_LABEL));
        assertEquals("5", object.get(JBO_POSITION));
        assertBookProperty("derashotharan", object.get(JBO_BOOK));

        object = json.getObject(8);
        assertEquals(JBR_TEXT + "derashotharan-9", object.get(URI));
        assertEquals("דרשות הר\"ן ט", object.get(RDFS_LABEL));
        assertEquals("9", object.get(JBO_POSITION));
        assertBookProperty("derashotharan", object.get(JBO_BOOK));

        object = json.getObject(11);
        assertEquals(JBR_TEXT + "derashotharan-12", object.get(URI));
        assertEquals("דרשות הר\"ן יב", object.get(RDFS_LABEL));
        assertEquals("12", object.get(JBO_POSITION));
        assertBookProperty("derashotharan", object.get(JBO_BOOK));
    }
}