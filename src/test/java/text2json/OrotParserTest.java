package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.OrotParser;

import java.io.BufferedReader;
import java.io.File;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class OrotParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new OrotParser();
        BufferedReader reader = getText("orot/orot.txt");
        createOutputFolderIfNotExists("orot/");
        parser.parse(reader, "json/orot/orot.json");
        json = getJson("json/orot/orot.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(143, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertEquals("jbr:orot-1-1-1", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("אורות - אורות מאופל - ארץ ישראל א", object.get(RDFS_LABEL));
        assertEquals("jbr:book-orot", object.get(JBO_BOOK));

        object = json.getObject(13);
        assertEquals("jbr:orot-1-2-6", object.get(URI));
        assertEquals("אורות - אורות מאופל - המלחמה ו", object.get(RDFS_LABEL));
        assertEquals("14", object.get(JBO_POSITION));
        assertEquals("jbr:book-orot", object.get(JBO_BOOK));

        object = json.getObject(34);
        assertEquals("jbr:orot-1-3-17", object.get(URI));
        assertEquals("אורות - אורות מאופל - ישראל ותחיתו יז", object.get(RDFS_LABEL));
        assertEquals("35", object.get(JBO_POSITION));
        assertEquals("jbr:book-orot", object.get(JBO_BOOK));

        object = json.getObject(41);
        assertEquals("jbr:orot-1-3-24", object.get(URI));
        assertEquals("אורות - אורות מאופל - ישראל ותחיתו כד", object.get(RDFS_LABEL));
        assertEquals("42", object.get(JBO_POSITION));
        assertEquals("jbr:book-orot", object.get(JBO_BOOK));

        object = json.getObject(54);
        assertEquals("jbr:orot-1-4-5", object.get(URI));
        assertEquals("אורות - אורות מאופל - אורות התחיה ה", object.get(RDFS_LABEL));
        assertEquals("55", object.get(JBO_POSITION));

        object = json.getObject(56);
        assertEquals("jbr:orot-1-4-7", object.get(URI));
        assertEquals("אורות - אורות מאופל - אורות התחיה ז", object.get(RDFS_LABEL));
        assertEquals("57", object.get(JBO_POSITION));
        assertEquals("jbr:book-orot", object.get(JBO_BOOK));
    }
}
