package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MishneTorahParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.*;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;

/**
 * Created by omishali on 05/02/2017.
 */
public class MishneTorahParserTest {
    static final int[] hilchotNum = {5, 6, 10, 5, 3, 4, 7, 9, 6, 8, 5, 5, 5, 5};
    // number of perakim in each sefer
    static final int[] perakimNum = {46, 46, 97, 53, 53, 43, 85, 95, 45, 144, 62, 75, 75, 81};
    // number of halachot in each sefer (or part of them...)
    static final int[] halachotNum = {457};
    private static final int NUM_OF_BOOKS = hilchotNum.length;
    static SubjectsJson[] packagesJson = new SubjectsJson[NUM_OF_BOOKS];
    static SubjectsJson[] json = new SubjectsJson[NUM_OF_BOOKS];
    // number of hilchot in each sefer


    @BeforeClass
    public static void setup() throws Exception {
        for (int i = 0; i < NUM_OF_BOOKS; i++) {
            int bookNum = i + 1;
            MishneTorahParser parser = new MishneTorahParser();
            BufferedReader reader = getText("mishnetorah/mishnetorah-" + bookNum + ".txt");
            createOutputFolderIfNotExists("mishnetorah");
            parser.parse(reader, "json/mishnetorah/mishnetorah-" + bookNum + ".json");
            json[i] = getJson("json/mishnetorah/mishnetorah-" + bookNum + ".json");
            packagesJson[i] = getJson("json/mishnetorah/mishnetorah-" + bookNum + "-packages.json");
        }
    }

    @Test
    public void testNumberOfPackageElements() {
        for (int i=0; i < NUM_OF_BOOKS; i++) {
            int expected = 1 + hilchotNum[i] + perakimNum[i]; // sefer + hilchot + perakim
            String message = "Test " + i + " failed";
            assertEquals(message, expected, packagesJson[i].subjects.size());
        }
    }

    @Test
    public void testNumberOfHalachot() {
        // sefer Hamada
        assertEquals(halachotNum[0] + 45 + 309 + 198 + 49, json[0].subjects.size()); // perush, kesefmishne, lechemmishne + raabad
    }

    @Test
    public void testFirstObject() {
        Map<String, String> o = json[0].getObject(0);
        assertEquals(JBR_TEXT + "mishnetorah-1-1-1-1", o.get(URI));
        assertEquals("הלכות יסודי התורה א א", o.get(RDFS_LABEL));
        assertBookProperty(o,"mishnetorah");
    }

    @Test
    public void testPerushWithTwoLines() {
        Map<String, String> o = json[0].getObject(JBR_TEXT + "mishnetorah-perushperush-1-1-1-1");
        assertTrue(o.get(JBO_TEXT).contains("קראו ספר המדע לפי שכלל בו המצוות התלויות במחשבה ובמדע ובדעות")); // text from first line
        assertTrue(o.get(JBO_TEXT).contains("כשתתבונן ארבע מלות")); // text from second line
        assertBookProperty(o,"perushperush");
    }
}
