package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.Parser;
import text2json.SubjectsJson;
import text2json.new_parsers.OrotHaKodeshParser;

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
public class OrotHaKodeshParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new OrotHaKodeshParser();
        BufferedReader reader = getText("orothakodesh/orothakodesh.txt");
        parser.parse(reader, "json/orothakodesh/orothakodesh.json");
        json = getJson("json/orothakodesh/orothakodesh.json");
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
        assertEquals(121, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertEquals("jbr:orothakodesh-1-1", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("אורות הקודש א א", object.get(RDFS_LABEL));


        object = json.getObject(3);
        assertEquals("jbr:orothakodesh-1-4", object.get(URI));
        assertEquals("אורות הקודש א ד", object.get(RDFS_LABEL));
        assertEquals("4", object.get(JBO_POSITION));

        object = json.getObject(23);
        assertEquals("jbr:orothakodesh-2-4", object.get(URI));
        assertEquals("אורות הקודש ב ד", object.get(RDFS_LABEL));
        assertEquals("4", object.get(JBO_POSITION));

        object = json.getObject(32);
        assertEquals("jbr:orothakodesh-2-13", object.get(URI));
        assertEquals("אורות הקודש ב יג", object.get(RDFS_LABEL));
        assertEquals("13", object.get(JBO_POSITION));

        object = json.getObject(57);
        assertEquals("jbr:orothakodesh-3-18", object.get(URI));
        assertEquals("אורות הקודש ג יח", object.get(RDFS_LABEL));
        assertEquals("18", object.get(JBO_POSITION));

        object = json.getObject(94);
        assertEquals("jbr:orothakodesh-5-15", object.get(URI));
        assertEquals("אורות הקודש ה טו", object.get(RDFS_LABEL));
        assertEquals("15", object.get(JBO_POSITION));

        object = json.getObject(120);
        assertEquals("jbr:orothakodesh-7-18", object.get(URI));
        assertEquals("אורות הקודש ז יח", object.get(RDFS_LABEL));
        assertEquals("18", object.get(JBO_POSITION));

    }
}
