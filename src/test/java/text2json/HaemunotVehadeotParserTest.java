package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.HaemunotVehadeotParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class HaemunotVehadeotParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new HaemunotVehadeotParser();
        createOutputFolderIfNotExists("haemunotvehadeot");
        BufferedReader reader = getText("haemunotvehadeot/haemunotvehadeot.txt");
        createOutputFolderIfNotExists("haemunotvehadeot");
        parser.parse(reader, "json/haemunotvehadeot/haemunotvehadeot.json");
        json = getJson("json/haemunotvehadeot/haemunotvehadeot.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(110, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertEquals(JBR_TEXT + "haemunotvehadeot-0-1", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("האמונות והדעות - הקדמה א", object.get(RDFS_LABEL));
        assertBookProperty("haemunotvehadeot", object.get(JBO_BOOK));

        object = json.getObject(5);
        assertEquals(JBR_TEXT + "haemunotvehadeot-0-6", object.get(URI));
        assertEquals("האמונות והדעות - הקדמה ו", object.get(RDFS_LABEL));
        assertEquals("6", object.get(JBO_POSITION));
        assertBookProperty("haemunotvehadeot", object.get(JBO_BOOK));

        object = json.getObject(13);
        assertEquals(JBR_TEXT + "haemunotvehadeot-1-5", object.get(URI));
        assertEquals("האמונות והדעות - מאמר הראשון חדוש ה", object.get(RDFS_LABEL));
        assertEquals("14", object.get(JBO_POSITION));
        assertBookProperty("haemunotvehadeot", object.get(JBO_BOOK));

        object = json.getObject(34);
        assertEquals(JBR_TEXT + "haemunotvehadeot-3-6", object.get(URI));
        assertEquals("האמונות והדעות - מאמר השלישי צווי ואזהרה ו", object.get(RDFS_LABEL));
        assertEquals("35", object.get(JBO_POSITION));

        object = json.getObject(53);
        assertEquals(JBR_TEXT + "haemunotvehadeot-5-8", object.get(URI));
        assertEquals("האמונות והדעות - מאמר החמישי זכיות וחובות ח", object.get(RDFS_LABEL));
        assertEquals("54", object.get(JBO_POSITION));

        object = json.getObject(81);
        assertEquals(JBR_TEXT + "haemunotvehadeot-9-3", object.get(URI));
        assertEquals("האמונות והדעות - מאמר התשיעי גמול ועונש ג", object.get(RDFS_LABEL));
        assertEquals("82", object.get(JBO_POSITION));
        assertBookProperty("haemunotvehadeot", object.get(JBO_BOOK));

        object = json.getObject(102);
        assertEquals(JBR_TEXT + "haemunotvehadeot-10-12", object.get(URI));
        assertEquals("האמונות והדעות - מאמר העשירי הנהגת האדם יב", object.get(RDFS_LABEL));
        assertEquals("103", object.get(JBO_POSITION));
        assertBookProperty("haemunotvehadeot", object.get(JBO_BOOK));

        object = json.getObject(109);
        assertEquals(JBR_TEXT + "haemunotvehadeot-10-19", object.get(URI));
        assertEquals("האמונות והדעות - מאמר העשירי הנהגת האדם יט", object.get(RDFS_LABEL));
        assertEquals("110", object.get(JBO_POSITION));
    }
}
