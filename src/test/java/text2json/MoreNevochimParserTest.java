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
        assertEquals(183, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertEquals("jbr:morenevochim-0-1", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("מורה נבוכים הקדמות המתרגמים פתיחת אבן תיבון", object.get(RDFS_LABEL));

        object = json.getObject(15);
        assertEquals("jbr:morenevochim-1-13", object.get(URI));
        assertEquals("מורה נבוכים א יג", object.get(RDFS_LABEL));
        assertEquals("16", object.get(JBO_POSITION));


        object = json.getObject(33);
        assertEquals("jbr:morenevochim-1-31", object.get(URI));
        assertEquals("מורה נבוכים א לא", object.get(RDFS_LABEL));
        assertEquals("34", object.get(JBO_POSITION));

        object = json.getObject(54);
        assertEquals("jbr:morenevochim-1-52", object.get(URI));
        assertEquals("מורה נבוכים א נב", object.get(RDFS_LABEL));
        assertEquals("55", object.get(JBO_POSITION));

        object = json.getObject(74);
        assertEquals("jbr:morenevochim-1-72", object.get(URI));
        assertEquals("מורה נבוכים א עב", object.get(RDFS_LABEL));
        assertEquals("75", object.get(JBO_POSITION));

        object = json.getObject(91);
        assertEquals("jbr:morenevochim-2-12", object.get(URI));
        assertEquals("מורה נבוכים ב יב", object.get(RDFS_LABEL));
        assertEquals("92", object.get(JBO_POSITION));

        object = json.getObject(114);
        assertEquals("jbr:morenevochim-2-35", object.get(URI));
        assertEquals("מורה נבוכים ב לה", object.get(RDFS_LABEL));
        assertEquals("115", object.get(JBO_POSITION));

        object = json.getObject(146);
        assertEquals("jbr:morenevochim-3-18", object.get(URI));
        assertEquals("מורה נבוכים ג יח", object.get(RDFS_LABEL));
        assertEquals("147", object.get(JBO_POSITION));

        object = json.getObject(160);
        assertEquals("jbr:morenevochim-3-32", object.get(URI));
        assertEquals("מורה נבוכים ג לב", object.get(RDFS_LABEL));
        assertEquals("161", object.get(JBO_POSITION));

        object = json.getObject(182);
        assertEquals("jbr:morenevochim-3-54", object.get(URI));
        assertEquals("מורה נבוכים ג נד", object.get(RDFS_LABEL));
        assertEquals("183", object.get(JBO_POSITION));
    }
}
