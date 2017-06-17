package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.ChovotHalevavotParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.getJson;
import static text2json.TestUtils.getText;

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
        parser.parse(reader, "json/chovothalevavot/chovothalevavot.json");
        json = getJson("json/chovothalevavot/chovothalevavot.json");
    }

    @Test
    //test the correctness with sampling a few values
    public void test() {
        System.out.println("bla bla");
        System.out.println();
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
        assertEquals("jbr:chovothalevavot-0-0", object.get(URI));
        assertEquals("0", object.get(JBO_POSITION));
        assertEquals("חובות הלבבות - הקדמת המחבר", object.get(RDFS_LABEL));

        object = json.getObject(5);
        assertEquals("jbr:chovothalevavot-1-4", object.get(URI));
        assertEquals("חובות הלבבות - שער ראשון - שער ייחוד - פרק ד", object.get(RDFS_LABEL));
        assertEquals("4", object.get(JBO_POSITION));


        object = json.getObject(13);
        assertEquals("jbr:chovothalevavot-2-1", object.get(URI));
        assertEquals("חובות הלבבות - שער שני - שער הבחינה - פרק א", object.get(RDFS_LABEL));
        assertEquals("1", object.get(JBO_POSITION));

        object = json.getObject(34);
        assertEquals("jbr:chovothalevavot-4-4", object.get(URI));
        assertEquals("חובות הלבבות - שער רביעי - שער הביטחון - פרק ד", object.get(RDFS_LABEL));
        assertEquals("4", object.get(JBO_POSITION));

        object = json.getObject(54);
        assertEquals("jbr:chovothalevavot-6-9", object.get(URI));
        assertEquals("חובות הלבבות - שער שישי - שער הכניעה - פרק ט", object.get(RDFS_LABEL));
        assertEquals("9", object.get(JBO_POSITION));

        object = json.getObject(71);
        assertEquals("jbr:chovothalevavot-8-4", object.get(URI));
        assertEquals("חובות הלבבות - שער שמיני - שער חשבון הנפש - פרק ד", object.get(RDFS_LABEL));
        assertEquals("4", object.get(JBO_POSITION));

        object = json.getObject(84);
        assertEquals("jbr:chovothalevavot-10-2", object.get(URI));
        assertEquals("חובות הלבבות - שער עשירי - שער אהבת ה' - פרק ב", object.get(RDFS_LABEL));
        assertEquals("2", object.get(JBO_POSITION));

        object = json.getObject(89);
        assertEquals("jbr:chovothalevavot-10-7", object.get(URI));
        assertEquals("חובות הלבבות - שער עשירי - שער אהבת ה' - פרק ז", object.get(RDFS_LABEL));
        assertEquals("7", object.get(JBO_POSITION));
    }
}
