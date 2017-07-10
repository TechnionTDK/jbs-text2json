package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.BeerHagolaParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class BeerHagolaParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new BeerHagolaParser();
        BufferedReader reader = getText("beerhagola/beerhagola.txt");
        createOutputFolderIfNotExists("beerhagola");
        parser.parse(reader, "json/beerhagola/beerhagola.json");
        json = getJson("json/beerhagola/beerhagola.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(8, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertEquals(JBR_TEXT + "beerhagola-0", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("באר הגולה - הקדמה", object.get(RDFS_LABEL));
        assertEquals("jbr:book-beerhagola", object.get(JBO_BOOK));


        object = json.getObject(6);
        assertEquals(JBR_TEXT + "beerhagola-6", object.get(URI));
        assertEquals("באר הגולה - באר ו", object.get(RDFS_LABEL));
        assertEquals("7", object.get(JBO_POSITION));
        assertEquals("jbr:book-beerhagola", object.get(JBO_BOOK));

        object = json.getObject(2);
        assertEquals(JBR_TEXT + "beerhagola-2", object.get(URI));
        assertEquals("באר הגולה - באר ב", object.get(RDFS_LABEL));
        assertEquals("3", object.get(JBO_POSITION));
        assertEquals("jbr:book-beerhagola", object.get(JBO_BOOK));
    }
}
