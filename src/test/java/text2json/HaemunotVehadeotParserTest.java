package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.HaemunotVehadeotParser;

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
public class HaemunotVehadeotParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new HaemunotVehadeotParser();
        BufferedReader reader = getText("haemunotvehadeot/haemunotvehadeot.txt");
        parser.parse(reader, "json/haemunotvehadeot/haemunotvehadeot.json");
        json = getJson("json/haemunotvehadeot/haemunotvehadeot.json");
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
        assertEquals(111, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertEquals("jbr:haemunotvehadeot-0-0", object.get(URI));
        assertEquals("0", object.get(JBO_POSITION));
        assertEquals("האמונות והדעות - הקדמה", object.get(RDFS_LABEL));

        object = json.getObject(5);
        assertEquals("jbr:haemunotvehadeot-0-5", object.get(URI));
        assertEquals("האמונות והדעות - הקדמה ה", object.get(RDFS_LABEL));
        assertEquals("5", object.get(JBO_POSITION));


        object = json.getObject(13);
        assertEquals("jbr:haemunotvehadeot-1-4", object.get(URI));
        assertEquals("האמונות והדעות - מאמר הראשון חדוש ד", object.get(RDFS_LABEL));
        assertEquals("4", object.get(JBO_POSITION));

        object = json.getObject(34);
        assertEquals("jbr:haemunotvehadeot-3-5", object.get(URI));
        assertEquals("האמונות והדעות - מאמר השלישי צווי ואזהרה ה", object.get(RDFS_LABEL));
        assertEquals("5", object.get(JBO_POSITION));

        object = json.getObject(54);
        assertEquals("jbr:haemunotvehadeot-5-8", object.get(URI));
        assertEquals("האמונות והדעות - מאמר החמישי זכיות וחובות ח", object.get(RDFS_LABEL));
        assertEquals("8", object.get(JBO_POSITION));

        object = json.getObject(81);
        assertEquals("jbr:haemunotvehadeot-9-2", object.get(URI));
        assertEquals("האמונות והדעות - מאמר התשיעי גמול ועונש ב", object.get(RDFS_LABEL));
        assertEquals("2", object.get(JBO_POSITION));

        object = json.getObject(102);
        assertEquals("jbr:haemunotvehadeot-10-11", object.get(URI));
        assertEquals("האמונות והדעות - מאמר העשירי הנהגת האדם יא", object.get(RDFS_LABEL));
        assertEquals("11", object.get(JBO_POSITION));

        object = json.getObject(110);
        assertEquals("jbr:haemunotvehadeot-10-19", object.get(URI));
        assertEquals("האמונות והדעות - מאמר העשירי הנהגת האדם יט", object.get(RDFS_LABEL));
        assertEquals("19", object.get(JBO_POSITION));
    }
}
