package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.SeferHakuzariParser;

import java.util.Map;

import static text2json.TestUtils.*;

/**
 * Created by shilonoa on 4/24/2017.
 */
public class SeferHakuzariParserTest {
    static SubjectsJson json;

    @BeforeClass
    public static void setUp() throws Exception {
        json = setupParser(new SeferHakuzariParser() , "seferhakuzari");

    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "seferhakuzari-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"הכוזרי א א");
        assertBookProperty(object,"seferhakuzari");
    }
}