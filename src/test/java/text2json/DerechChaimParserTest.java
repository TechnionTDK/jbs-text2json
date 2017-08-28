package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.DerechChaimParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.JbsOntology.*;
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
        assertEquals(JBR_TEXT + "derechchaim-0-0", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("דרך חיים - הקדמה", object.get(RDFS_LABEL));
        assertEquals("jbr:book-derechchaim", object.get(JBO_BOOK));

        object = json.getObject(3);
        assertEquals(JBR_TEXT + "derechchaim-1-3", object.get(URI));
        assertEquals("דרך חיים א ג", object.get(RDFS_LABEL));
        assertEquals("4", object.get(JBO_POSITION));
        assertEquals("jbr:book-derechchaim", object.get(JBO_BOOK));

        object = json.getObject(23);
        assertEquals(JBR_TEXT + "derechchaim-2-5", object.get(URI));
        assertEquals("דרך חיים ב ה", object.get(RDFS_LABEL));
        assertEquals("24", object.get(JBO_POSITION));
        assertEquals("jbr:book-derechchaim", object.get(JBO_BOOK));

        object = json.getObject(32);
        assertEquals(JBR_TEXT + "derechchaim-2-14", object.get(URI));
        assertEquals("דרך חיים ב יד", object.get(RDFS_LABEL));
        assertEquals("33", object.get(JBO_POSITION));

        object = json.getObject(57);
        assertEquals(JBR_TEXT + "derechchaim-4-5", object.get(URI));
        assertEquals("דרך חיים ד ה", object.get(RDFS_LABEL));
        assertEquals("58", object.get(JBO_POSITION));
        assertEquals("jbr:book-derechchaim", object.get(JBO_BOOK));

        object = json.getObject(64);
        assertEquals(JBR_TEXT + "derechchaim-4-12", object.get(URI));
        assertEquals("דרך חיים ד יב", object.get(RDFS_LABEL));
        assertEquals("65", object.get(JBO_POSITION));
        assertEquals("jbr:book-derechchaim", object.get(JBO_BOOK));

        object = json.getObject(83);
        assertEquals(JBR_TEXT + "derechchaim-5-9", object.get(URI));
        assertEquals("דרך חיים ה ט", object.get(RDFS_LABEL));
        assertEquals("84", object.get(JBO_POSITION));

    }
}
