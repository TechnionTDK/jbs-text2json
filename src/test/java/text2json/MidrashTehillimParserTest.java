package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MidrashMishleiParser;
import text2json.parsers.MidrashTehillimParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class MidrashTehillimParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new MidrashTehillimParser();
        createOutputFolderIfNotExists("mdrashtehillim");
        BufferedReader reader = getText("mdrashtehillim/mdrashtehillim.txt");
        createOutputFolderIfNotExists("mdrashtehillim");
        parser.parse(reader, "json/mdrashtehillim/mdrashtehillim.json");
        json = getJson("json/mdrashtehillim/mdrashtehillim.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(150, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "mdrashtehillim-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"מדרש תהילים מזמור 1");
        assertBookProperty(object,"mdrashtehillim");

        object = json.getObject(15);
        assertTextUriProperty(object, "mdrashtehillim-16");
        assertLabelProperty( object ,"מדרש תהילים מזמור 16");
        assertPositionProperty(object ,"16");
        assertBookProperty(object,"mdrashtehillim");

        object = json.getObject(48);
        assertTextUriProperty(object, "mdrashtehillim-49");
        assertLabelProperty( object ,"מדרש תהילים מזמור 49");
        assertPositionProperty(object ,"49");
        assertBookProperty(object,"mdrashtehillim");

        object = json.getObject(69);
        assertTextUriProperty(object, "mdrashtehillim-70");
        assertLabelProperty( object ,"מדרש תהילים מזמור 70");
        assertPositionProperty(object ,"70");
        assertBookProperty(object,"mdrashtehillim");

        object = json.getObject(100);
        assertTextUriProperty(object, "mdrashtehillim-101");
        assertLabelProperty( object ,"מדרש תהילים מזמור 101");
        assertPositionProperty(object ,"101");
        assertBookProperty(object,"mdrashtehillim");

        object = json.getObject(149);
        assertTextUriProperty(object, "mdrashtehillim-150");
        assertLabelProperty( object ,"מדרש תהילים מזמור 150");
        assertPositionProperty(object ,"150");
        assertBookProperty(object,"mdrashtehillim");
    }
}
