package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MoreNevochimParser;

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
public class MoreNevochimParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new MoreNevochimParser();
        BufferedReader reader = getText("morenevochim/morenevochim.txt");
        parser.parse(reader, "json/morenevochim/morenevochim.json");
        json = getJson("json/morenevochim/morenevochim.json");
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
        assertEquals(184, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertEquals("jbr:morenevochim-0-0", object.get(URI));
        assertEquals("0", object.get(JBO_POSITION));
        assertEquals("מורה נבוכים - הקדמות המתרגמים", object.get(RDFS_LABEL));

        object = json.getObject(15);
        assertEquals("jbr:morenevochim-1-12", object.get(URI));
        assertEquals("מורה נבוכים א יב", object.get(RDFS_LABEL));
        assertEquals("12", object.get(JBO_POSITION));


        object = json.getObject(33);
        assertEquals("jbr:morenevochim-1-30", object.get(URI));
        assertEquals("מורה נבוכים א ל", object.get(RDFS_LABEL));
        assertEquals("30", object.get(JBO_POSITION));

        object = json.getObject(54);
        assertEquals("jbr:morenevochim-1-51", object.get(URI));
        assertEquals("מורה נבוכים א נא", object.get(RDFS_LABEL));
        assertEquals("51", object.get(JBO_POSITION));

        object = json.getObject(74);
        assertEquals("jbr:morenevochim-1-71", object.get(URI));
        assertEquals("מורה נבוכים א עא", object.get(RDFS_LABEL));
        assertEquals("71", object.get(JBO_POSITION));

        object = json.getObject(91);
        assertEquals("jbr:morenevochim-2-11", object.get(URI));
        assertEquals("מורה נבוכים ב יא", object.get(RDFS_LABEL));
        assertEquals("11", object.get(JBO_POSITION));

        object = json.getObject(114);
        assertEquals("jbr:morenevochim-2-34", object.get(URI));
        assertEquals("מורה נבוכים ב לד", object.get(RDFS_LABEL));
        assertEquals("34", object.get(JBO_POSITION));

        object = json.getObject(146);
        assertEquals("jbr:morenevochim-3-17", object.get(URI));
        assertEquals("מורה נבוכים ג יז", object.get(RDFS_LABEL));
        assertEquals("17", object.get(JBO_POSITION));

        object = json.getObject(160);
        assertEquals("jbr:morenevochim-3-31", object.get(URI));
        assertEquals("מורה נבוכים ג לא", object.get(RDFS_LABEL));
        assertEquals("31", object.get(JBO_POSITION));

        object = json.getObject(183);
        assertEquals("jbr:morenevochim-3-54", object.get(URI));
        assertEquals("מורה נבוכים ג נד", object.get(RDFS_LABEL));
        assertEquals("54", object.get(JBO_POSITION));
    }
}
