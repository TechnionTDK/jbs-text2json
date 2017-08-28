package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.OrotHaKodeshParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class OrotHaKodeshParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new OrotHaKodeshParser();
        createOutputFolderIfNotExists("orothakodesh");
        BufferedReader reader = getText("orothakodesh/orothakodesh.txt");
        createOutputFolderIfNotExists("orothakodesh");
        parser.parse(reader, "json/orothakodesh/orothakodesh.json");
        json = getJson("json/orothakodesh/orothakodesh.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(121, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertEquals(JBR_TEXT + "orothakodesh-1-1", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("אורות הקודש א א", object.get(RDFS_LABEL));
        assertBookProperty("orothakodesh", object.get(JBO_BOOK));

        object = json.getObject(3);
        assertEquals(JBR_TEXT + "orothakodesh-1-4", object.get(URI));
        assertEquals("אורות הקודש א ד", object.get(RDFS_LABEL));
        assertEquals("4", object.get(JBO_POSITION));
        assertBookProperty("orothakodesh", object.get(JBO_BOOK));

        object = json.getObject(23);
        assertEquals(JBR_TEXT + "orothakodesh-2-4", object.get(URI));
        assertEquals("אורות הקודש ב ד", object.get(RDFS_LABEL));
        assertEquals("24", object.get(JBO_POSITION));
        assertBookProperty("orothakodesh", object.get(JBO_BOOK));

        object = json.getObject(32);
        assertEquals(JBR_TEXT + "orothakodesh-2-13", object.get(URI));
        assertEquals("אורות הקודש ב יג", object.get(RDFS_LABEL));
        assertEquals("33", object.get(JBO_POSITION));
        assertBookProperty("orothakodesh", object.get(JBO_BOOK));

        object = json.getObject(57);
        assertEquals(JBR_TEXT + "orothakodesh-3-18", object.get(URI));
        assertEquals("אורות הקודש ג יח", object.get(RDFS_LABEL));
        assertEquals("58", object.get(JBO_POSITION));
        assertBookProperty("orothakodesh", object.get(JBO_BOOK));

        object = json.getObject(94);
        assertEquals(JBR_TEXT + "orothakodesh-5-15", object.get(URI));
        assertEquals("אורות הקודש ה טו", object.get(RDFS_LABEL));
        assertEquals("95", object.get(JBO_POSITION));
        assertBookProperty("orothakodesh", object.get(JBO_BOOK));

        object = json.getObject(120);
        assertEquals(JBR_TEXT + "orothakodesh-7-18", object.get(URI));
        assertEquals("אורות הקודש ז יח", object.get(RDFS_LABEL));
        assertEquals("121", object.get(JBO_POSITION));
        assertBookProperty("orothakodesh", object.get(JBO_BOOK));
    }
}
