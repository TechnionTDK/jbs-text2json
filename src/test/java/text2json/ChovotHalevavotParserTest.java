package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.ChovotHalevavotParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class ChovotHalevavotParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new ChovotHalevavotParser();
        BufferedReader reader = getText("chovothalevavot/chovothalevavot.txt");
        createOutputFolderIfNotExists("chovothalevavot");
        parser.parse(reader, "json/chovothalevavot/chovothalevavot.json");
        json = getJson("json/chovothalevavot/chovothalevavot.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(90, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertEquals(JBR_TEXT + "chovothalevavot-0-0", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("jbr:book-chovothalevavot", object.get(JBO_BOOK));
        assertEquals("חובות הלבבות - הקדמת המחבר", object.get(RDFS_LABEL));

        object = json.getObject(5);
        assertEquals(JBR_TEXT + "chovothalevavot-1-4", object.get(URI));
        assertEquals("חובות הלבבות - שער ראשון - שער ייחוד - ד", object.get(RDFS_LABEL));
        assertEquals("6", object.get(JBO_POSITION));


        object = json.getObject(13);
        assertEquals(JBR_TEXT + "chovothalevavot-2-1", object.get(URI));
        assertEquals("חובות הלבבות - שער שני - שער הבחינה - א", object.get(RDFS_LABEL));
        assertEquals("14", object.get(JBO_POSITION));

        object = json.getObject(34);
        assertEquals(JBR_TEXT + "chovothalevavot-4-4", object.get(URI));
        assertEquals("חובות הלבבות - שער רביעי - שער הביטחון - ד", object.get(RDFS_LABEL));
        assertEquals("35", object.get(JBO_POSITION));

        object = json.getObject(54);
        assertEquals(JBR_TEXT + "chovothalevavot-6-9", object.get(URI));
        assertEquals("חובות הלבבות - שער שישי - שער הכניעה - ט", object.get(RDFS_LABEL));
        assertEquals("55", object.get(JBO_POSITION));

        object = json.getObject(71);
        assertEquals(JBR_TEXT + "chovothalevavot-8-4", object.get(URI));
        assertEquals("חובות הלבבות - שער שמיני - שער חשבון הנפש - ד", object.get(RDFS_LABEL));
        assertEquals("72", object.get(JBO_POSITION));

        object = json.getObject(84);
        assertEquals(JBR_TEXT + "chovothalevavot-10-2", object.get(URI));
        assertEquals("חובות הלבבות - שער עשירי - שער אהבת ה' - ב", object.get(RDFS_LABEL));
        assertEquals("85", object.get(JBO_POSITION));

        object = json.getObject(89);
        assertEquals(JBR_TEXT + "chovothalevavot-10-7", object.get(URI));
        assertEquals("חובות הלבבות - שער עשירי - שער אהבת ה' - ז", object.get(RDFS_LABEL));
        assertEquals("90", object.get(JBO_POSITION));
    }
}
