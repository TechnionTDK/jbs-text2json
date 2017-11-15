package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.TanachKtivMaleParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.setupParser;

/**
 * Created by omishali on 05/11/2017.
 */
public class TanachKtivMaleParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new TanachKtivMaleParser() , "tanach_ktivmale");

    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(23200, json.subjects.size());
    }
}
