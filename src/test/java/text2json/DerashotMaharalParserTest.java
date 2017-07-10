package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.DerashotMaharalParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class DerashotMaharalParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new DerashotMaharalParser();
        BufferedReader reader = getText("derashotmaharal/derashotmaharal.txt");
        createOutputFolderIfNotExists("derashotmaharal");
        parser.parse(reader, "json/derashotmaharal/derashotmaharal.json");
        json = getJson("json/derashotmaharal/derashotmaharal.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(5, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertEquals(JBR_TEXT + "derashotmaharal-0", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("דרשות מהר\"ל - הקדמה לדרוש על התורה", object.get(RDFS_LABEL));
        assertEquals("jbr:book-derashotmaharal", object.get(JBO_BOOK));

        object = json.getObject(1);
        assertEquals(JBR_TEXT + "derashotmaharal-1", object.get(URI));
        assertEquals("דרשות מהר\"ל - דרוש על התורה", object.get(RDFS_LABEL));
        assertEquals("2", object.get(JBO_POSITION));
        assertEquals("jbr:book-derashotmaharal", object.get(JBO_BOOK));

        object = json.getObject(2);
        assertEquals(JBR_TEXT + "derashotmaharal-2", object.get(URI));
        assertEquals("דרשות מהר\"ל - דרוש על המצות", object.get(RDFS_LABEL));
        assertEquals("3", object.get(JBO_POSITION));
        assertEquals("jbr:book-derashotmaharal", object.get(JBO_BOOK));

        object = json.getObject(3);
        assertEquals(JBR_TEXT + "derashotmaharal-3", object.get(URI));
        assertEquals("דרשות מהר\"ל - דרוש לשבת תשובה", object.get(RDFS_LABEL));
        assertEquals("4", object.get(JBO_POSITION));

        object = json.getObject(4);
        assertEquals(JBR_TEXT + "derashotmaharal-4", object.get(URI));
        assertEquals("דרשות מהר\"ל - דרוש לשבת הגדול", object.get(RDFS_LABEL));
        assertEquals("5", object.get(JBO_POSITION));
        assertEquals("jbr:book-derashotmaharal", object.get(JBO_BOOK));
    }
}
