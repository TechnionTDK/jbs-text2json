package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MidrashTanchumaParser;
import text2json.parsers.SeferHaYasharMidrashParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class MidrashTanchumaParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new MidrashTanchumaParser();
        createOutputFolderIfNotExists("midrashtanchuma");
        BufferedReader reader = getText("midrashtanchuma/midrashtanchuma.txt");
        createOutputFolderIfNotExists("midrashtanchuma");
        parser.parse(reader, "json/midrashtanchuma/midrashtanchuma.json");
        json = getJson("json/midrashtanchuma/midrashtanchuma.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(776, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "midrashtanchuma-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"מדרש תנחומא פרשת בראשית סימן א");
        assertBookProperty(object,"midrashtanchuma");

        object = json.getObject(121);
        assertTextUriProperty(object, "midrashtanchuma-9-2");
        assertLabelProperty( object ,"מדרש תנחומא פרשת וישב סימן ב");
        assertPositionProperty(object ,"122");
        assertBookProperty(object,"midrashtanchuma");

        object = json.getObject(222);
        assertTextUriProperty(object, "midrashtanchuma-15-10");
        assertLabelProperty( object ,"מדרש תנחומא פרשת בא סימן י");
        assertPositionProperty(object ,"223");
        assertBookProperty(object,"midrashtanchuma");

        object = json.getObject(333);
        assertTextUriProperty(object, "midrashtanchuma-21-17");
        assertLabelProperty( object ,"מדרש תנחומא פרשת כי תשא סימן יז");
        assertPositionProperty(object ,"334");
        assertBookProperty(object,"midrashtanchuma");

        object = json.getObject(444);
        assertTextUriProperty(object, "midrashtanchuma-30-4");
        assertLabelProperty( object ,"מדרש תנחומא פרשת קדושים סימן ד");
        assertPositionProperty(object ,"445");
        assertBookProperty(object,"midrashtanchuma");


        object = json.getObject(555);
        assertTextUriProperty(object, "midrashtanchuma-36-10");
        assertLabelProperty( object ,"מדרש תנחומא פרשת בהעלותך סימן י");
        assertPositionProperty(object ,"556");
        assertBookProperty(object,"midrashtanchuma");


        object = json.getObject(666);
        assertTextUriProperty(object, "midrashtanchuma-43-6");
        assertLabelProperty( object ,"מדרש תנחומא פרשת מסעי סימן ו");
        assertPositionProperty(object ,"667");
        assertBookProperty(object,"midrashtanchuma");


        object = json.getObject(700);
        assertTextUriProperty(object, "midrashtanchuma-47-6");
        assertLabelProperty( object ,"מדרש תנחומא פרשת ראה סימן ו");
        assertPositionProperty(object ,"701");
        assertBookProperty(object,"midrashtanchuma");


        object = json.getObject(750);
        assertTextUriProperty(object, "midrashtanchuma-51-4");
        assertLabelProperty( object ,"מדרש תנחומא פרשת נצבים סימן ד");
        assertPositionProperty(object ,"751");
        assertBookProperty(object,"midrashtanchuma");


        object = json.getObject(775);
        assertTextUriProperty(object, "midrashtanchuma-54-7");
        assertLabelProperty( object ,"מדרש תנחומא פרשת וזאת הברכה סימן ז");
        assertPositionProperty(object ,"776");
        assertBookProperty(object,"midrashtanchuma");


    }
}
