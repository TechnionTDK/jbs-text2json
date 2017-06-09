package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.SfatEmetParser;

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
public class SfatEmetParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new SfatEmetParser();
        BufferedReader reader = getText("sfatemet/sfatemet.txt");
        parser.parse(reader, "json/sfatemet/sfatemet.json");
        json = getJson("json/sfatemet/sfatemet.json");
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
        assertEquals("jbr:tanach-sefetemet-1-1-18", object.get(URI));
        assertEquals("18", object.get(JBO_POSITION_IN_PARASHA));

        //chelek 1
        object = json.getObject(68);
        assertEquals("jbr:tanach-sefetemet-1-3-6", object.get(URI));
        assertEquals("שפת אמת, ספר בראשית לך לך סעיף ו ", object.get(RDFS_LABEL));
        assertEquals("6", object.get(JBO_POSITION_IN_PARASHA));

        //chelek 2
        object = json.getObject(268);
        assertEquals("jbr:tanach-sefetemet-1-9-14", object.get(URI));
        assertEquals("שפת אמת, ספר בראשית וישב סעיף יד ", object.get(RDFS_LABEL));
        assertEquals("14", object.get(JBO_POSITION_IN_PARASHA));
    }
}
