package text2json;

import org.junit.*;
import text2json.parsers.TanachKtivMaleParser;

import java.io.BufferedReader;

import static org.junit.Assert.*;
import static text2json.TestUtils.*;

/**
 * Created by omishali on 05/11/2017.
 */
public class TanachKtivMaleParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new TanachKtivMaleParser();
        createOutputFolderIfNotExists("tanach_ktivmale");
        BufferedReader reader = getText("tanach_ktivmale/tanach_ktivmale.txt");
        parser.parse(reader, "json/tanach_ktivmale/tanach_ktivmale.json");
        json = getJson("json/tanach_ktivmale/tanach_ktivmale.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(23200, json.subjects.size());
    }
}
