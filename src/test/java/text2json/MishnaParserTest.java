package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MishnaParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.*;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;

public class MishnaParserTest {
    static SubjectsJson[] json = new SubjectsJson[1];
    static SubjectsJson[] packagesJson = new SubjectsJson[1];


    @BeforeClass
    public static void setup() throws Exception {
        MishnaParser parser = new MishnaParser();
        BufferedReader reader = getText("mishna/mishna-1-1.txt");
        createOutputFolderIfNotExists("mishna");
        parser.parse(reader, "json/mishna/mishna-1-1.json");
        json[0] = getJson("json/mishna/mishna-1-1.json");
        packagesJson[0] = getJson("json/mishna/mishna-1-1" + "-packages.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json[0]);
        // Note: in fact we have here 7 empty perushim that should be removed!
        assertEquals(170, json[0].subjects.size()); // summed all mishnayot + existing perushim
    }

    @Test
    public void testSpecificObjects() {
        Map<String, String> o;

        o = json[0].getObject("jbr:mishna-bartanura-1-1-2-2");
        assertTrue(o.get(JBO_TEXT).contains("ויאמר אינו נוהג אלא ביום"));
        assertBookProperty("bartanura", o.get(JBO_BOOK));

        o = json[0].getObject("jbr:mishna-1-1-2-2");
        assertBookProperty("mishna", o.get(JBO_BOOK));

        o = json[0].getObject("jbr:mishna-yomtov-1-1-2-2");
        assertBookProperty("yomtov", o.get(JBO_BOOK));
    }

}