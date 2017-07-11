package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.TiferetIsraelParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class TiferetIsraelParserTest {
    static SubjectsJson json;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new TiferetIsraelParser();
        BufferedReader reader = getText("tiferetisrael/tiferetisrael.txt");
        createOutputFolderIfNotExists("tiferetisrael");
        parser.parse(reader, "json/tiferetisrael/tiferetisrael.json");
        json = getJson("json/tiferetisrael/tiferetisrael.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(71, json.subjects.size());
    }

    @Test
    public void testSpecificObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertEquals(JBR_TEXT + "tiferetisrael-0", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("תפארת ישראל - הקדמה", object.get(RDFS_LABEL));
        assertBookProperty("tiferetisrael", object.get(JBO_BOOK));

        object = json.getObject(3);
        assertEquals(JBR_TEXT + "tiferetisrael-3", object.get(URI));
        assertEquals("תפארת ישראל ג", object.get(RDFS_LABEL));
        assertEquals("4", object.get(JBO_POSITION));
        assertBookProperty("tiferetisrael", object.get(JBO_BOOK));

        object = json.getObject(23);
        assertEquals(JBR_TEXT + "tiferetisrael-23", object.get(URI));
        assertEquals("תפארת ישראל כג", object.get(RDFS_LABEL));
        assertEquals("24", object.get(JBO_POSITION));

        object = json.getObject(32);
        assertEquals(JBR_TEXT + "tiferetisrael-32", object.get(URI));
        assertEquals("תפארת ישראל לב", object.get(RDFS_LABEL));
        assertEquals("33", object.get(JBO_POSITION));
        assertBookProperty("tiferetisrael", object.get(JBO_BOOK));

        object = json.getObject(57);
        assertEquals(JBR_TEXT + "tiferetisrael-57", object.get(URI));
        assertEquals("תפארת ישראל נז", object.get(RDFS_LABEL));
        assertEquals("58", object.get(JBO_POSITION));

        object = json.getObject(64);
        assertEquals(JBR_TEXT + "tiferetisrael-64", object.get(URI));
        assertEquals("תפארת ישראל סד", object.get(RDFS_LABEL));
        assertEquals("65", object.get(JBO_POSITION));

        object = json.getObject(69);
        assertEquals(JBR_TEXT + "tiferetisrael-69", object.get(URI));
        assertEquals("תפארת ישראל סט", object.get(RDFS_LABEL));
        assertEquals("70", object.get(JBO_POSITION));

        object = json.getObject(70);
        assertEquals(JBR_TEXT + "tiferetisrael-70", object.get(URI));
        assertEquals("תפארת ישראל ע", object.get(RDFS_LABEL));
        assertEquals("71", object.get(JBO_POSITION));
        assertBookProperty("tiferetisrael", object.get(JBO_BOOK));
    }
}
