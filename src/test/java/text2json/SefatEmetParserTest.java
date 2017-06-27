package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.SefatEmetParser;

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
public class SefatEmetParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new SefatEmetParser();
        BufferedReader reader = getText("sefatemet/sefatemet.txt");
        parser.parse(reader, "json/sefatemet/sefatemet.json");
        json = getJson("json/sefatemet/sefatemet.json");
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
        assertEquals(1787, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        //sefer
        object = json.getObject(17);
        assertEquals("jbr:tanach-sefatemet-1-1-18", object.get(URI));
        assertEquals("18", object.get(JBO_POSITION));


        object = json.getObject(68);
        assertEquals("jbr:tanach-sefatemet-1-3-6", object.get(URI));
        assertEquals("שפת אמת לך לך ו", object.get(RDFS_LABEL));
        assertEquals("69", object.get(JBO_POSITION));


        object = json.getObject(268);
        assertEquals("jbr:tanach-sefatemet-1-9-14", object.get(URI));
        assertEquals("שפת אמת וישב יד", object.get(RDFS_LABEL));
        assertEquals("269", object.get(JBO_POSITION));

        object = json.getObject(374);
        assertEquals("jbr:tanach-sefatemet-1-12-23", object.get(URI));
        assertEquals("שפת אמת ויגש כג", object.get(RDFS_LABEL));
        assertEquals("375", object.get(JBO_POSITION));

        object = json.getObject(560);
        assertEquals("jbr:tanach-sefatemet-2-18-24", object.get(URI));
        assertEquals("שפת אמת יתרו כד", object.get(RDFS_LABEL));
        assertEquals("561", object.get(JBO_POSITION));

        object = json.getObject(754);
        assertEquals("jbr:tanach-sefatemet-2-25-26", object.get(URI));
        assertEquals("שפת אמת כי תשא כו", object.get(RDFS_LABEL));
        assertEquals("755", object.get(JBO_POSITION));

        object = json.getObject(999);
        assertEquals("jbr:tanach-sefatemet-3-37-9", object.get(URI));
        assertEquals("שפת אמת אחרי מות ט", object.get(RDFS_LABEL));
        assertEquals("1000", object.get(JBO_POSITION));

        object = json.getObject(1268);
        assertEquals("jbr:tanach-sefatemet-4-46-28", object.get(URI));
        assertEquals("שפת אמת שלח כח", object.get(RDFS_LABEL));
        assertEquals("1269", object.get(JBO_POSITION));

        object = json.getObject(1581);
        assertEquals("jbr:tanach-sefatemet-5-58-17", object.get(URI));
        assertEquals("שפת אמת כי תצא יז", object.get(RDFS_LABEL));
        assertEquals("1582", object.get(JBO_POSITION));

        object = json.getObject(1700);
        assertEquals("jbr:tanach-sefatemet-5-63-27", object.get(URI));
        assertEquals("שפת אמת ראש השנה כז", object.get(RDFS_LABEL));
        assertEquals("1701", object.get(JBO_POSITION));

        object = json.getObject(1786);
        assertEquals("jbr:tanach-sefatemet-5-68-1", object.get(URI));
        assertEquals("שפת אמת וזאת הברכה א", object.get(RDFS_LABEL));
        assertEquals("1787", object.get(JBO_POSITION));
    }
}
