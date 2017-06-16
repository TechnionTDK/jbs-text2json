package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.GevurotHashemParser;

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
public class GevurotHashemParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new GevurotHashemParser();
        BufferedReader reader = getText("gevurothashem/gevurothashem.txt");
        parser.parse(reader, "json/gevurothashem/gevurothashem.json");
        json = getJson("json/gevurothashem/gevurothashem.json");
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
        assertEquals(57, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertEquals("jbr:gevurothashem-0", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("גבורות השם - הקדמה א", object.get(RDFS_LABEL));


        object = json.getObject(13);
        assertEquals("jbr:gevurothashem-11", object.get(URI));
        assertEquals("גבורות השם - פרק יא ", object.get(RDFS_LABEL));
        assertEquals("14", object.get(JBO_POSITION));

        object = json.getObject(34);
        assertEquals("jbr:gevurothashem-32", object.get(URI));
        assertEquals("גבורות השם - פרק לב ", object.get(RDFS_LABEL));
        assertEquals("35", object.get(JBO_POSITION));

        object = json.getObject(41);
        assertEquals("jbr:gevurothashem-39", object.get(URI));
        assertEquals("גבורות השם - פרק לט ", object.get(RDFS_LABEL));
        assertEquals("42", object.get(JBO_POSITION));

        object = json.getObject(54);
        assertEquals("jbr:gevurothashem-52", object.get(URI));
        assertEquals("גבורות השם - פרק נב ", object.get(RDFS_LABEL));
        assertEquals("55", object.get(JBO_POSITION));

        object = json.getObject(56);
        assertEquals("jbr:gevurothashem-54", object.get(URI));
        assertEquals("גבורות השם - פרק נד ", object.get(RDFS_LABEL));
        assertEquals("57", object.get(JBO_POSITION));
    }
}
