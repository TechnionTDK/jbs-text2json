package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.NetivotOlamParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class NetivotOlamParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new NetivotOlamParser();
        createOutputFolderIfNotExists("netivotolam");
        BufferedReader reader = getText("netivotolam/netivotolam.txt");
        createOutputFolderIfNotExists("netivotolam");
        parser.parse(reader, "json/netivotolam/netivotolam.json");
        json = getJson("json/netivotolam/netivotolam.json");
    }

//    @Test
//    //test the correctness with sampling a few values
//    public void test() {
//        System.out.println("bla bla");
//        System.out.println();
//    }


    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(136, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertEquals(JBR_TEXT + "netivotolam-0", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("נתיבות עולם - הקדמה", object.get(RDFS_LABEL));
        assertBookProperty("netivotolam", object.get(JBO_BOOK));

        object = json.getObject(3);
        assertEquals(JBR_TEXT + "netivotolam-1-3", object.get(URI));
        assertEquals("נתיבות עולם - נתיב התורה ג", object.get(RDFS_LABEL));
        assertEquals("4", object.get(JBO_POSITION));
        assertBookProperty("netivotolam", object.get(JBO_BOOK));

        object = json.getObject(23);
        assertEquals(JBR_TEXT + "netivotolam-2-5", object.get(URI));
        assertEquals("נתיבות עולם - נתיב העבודה ה", object.get(RDFS_LABEL));
        assertEquals("24", object.get(JBO_POSITION));
        assertBookProperty("netivotolam", object.get(JBO_BOOK));

        object = json.getObject(32);
        assertEquals(JBR_TEXT + "netivotolam-2-14", object.get(URI));
        assertEquals("נתיבות עולם - נתיב העבודה יד", object.get(RDFS_LABEL));
        assertEquals("33", object.get(JBO_POSITION));

        object = json.getObject(57);
        assertEquals(JBR_TEXT + "netivotolam-8-2", object.get(URI));
        assertEquals("נתיבות עולם - נתיב השלום ב", object.get(RDFS_LABEL));
        assertEquals("58", object.get(JBO_POSITION));

        object = json.getObject(64);
        //assertTextUriProperty(object, "netivotolam-10-3");
        //assertPositionProperty(object, "65");
        //assertLabelProperty(object, "נתיבות עולם - נתיב הענוה ג");
        //assertBookProperty(object, "netivotolam");
        assertEquals(JBR_TEXT + "netivotolam-10-3", object.get(URI));
        assertEquals("נתיבות עולם - נתיב הענוה ג", object.get(RDFS_LABEL));
        assertEquals("65", object.get(JBO_POSITION));
        assertBookProperty("netivotolam", object.get(JBO_BOOK));

        object = json.getObject(91);
        assertEquals(JBR_TEXT + "netivotolam-15-2", object.get(URI));
        assertEquals("נתיבות עולם - נתיב הפּרישות ב", object.get(RDFS_LABEL));
        assertEquals("92", object.get(JBO_POSITION));

        object = json.getObject(117);
        assertEquals(JBR_TEXT + "netivotolam-22-1", object.get(URI));
        assertEquals("נתיבות עולם - נתיב התוכחה א", object.get(RDFS_LABEL));
        assertEquals("118", object.get(JBO_POSITION));

        object = json.getObject(135);
        assertEquals(JBR_TEXT + "netivotolam-33-1", object.get(URI));
        assertEquals("נתיבות עולם - נתיב דרך ארץ א", object.get(RDFS_LABEL));
        assertEquals("136", object.get(JBO_POSITION));
        assertBookProperty("netivotolam", object.get(JBO_BOOK));
    }
}
