package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.RambanParser;
import text2json.parsers.TanachParser;

import static org.junit.Assert.*;
import static text2json.TestUtils.getJson;
import static text2json.TestUtils.setupParser;

/**
 * Created by orel on 26/07/18.
 */
public class RambanParserTest {
    static SubjectsJson json;
//    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new RambanParser() , "ramban");
//        packageJson = getJson("json/tanach/tanach-packages.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
//        assertEquals(23206, json.subjects.size()); //total number of psukim
//        assertEquals(54 + 929, packageJson.subjects.size()); //total number of parashot and perakim
    }

    @Test
    public void testSpecificObjects() {
    }
}