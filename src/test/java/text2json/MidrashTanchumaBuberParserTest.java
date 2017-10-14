package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MidrashTanchumaBuberParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class MidrashTanchumaBuberParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new MidrashTanchumaBuberParser();
        createOutputFolderIfNotExists("midrashtanchumabuber");
        BufferedReader reader = getText("midrashtanchumabuber/midrashtanchumabuber.txt");
        createOutputFolderIfNotExists("midrashtanchumabuber");
        parser.parse(reader, "json/midrashtanchumabuber/midrashtanchumabuber.json");
        json = getJson("json/midrashtanchumabuber/midrashtanchumabuber.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(1042, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "midrashtanchumabuber-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"מדרש תנחומא בובר פרשת בראשית סימן א");
        assertBookProperty(object,"midrashtanchumabuber");

        object = json.getObject(64);
        assertTextUriProperty(object, "midrashtanchumabuber-2-25");
        assertLabelProperty( object ,"מדרש תנחומא בובר פרשת נח סימן כה");
        assertPositionProperty(object ,"65");
        assertBookProperty(object,"midrashtanchumabuber");

        object = json.getObject(128);
        assertTextUriProperty(object, "midrashtanchumabuber-4-34");
        assertLabelProperty( object ,"מדרש תנחומא בובר פרשת וירא סימן לד");
        assertPositionProperty(object ,"129");
        assertBookProperty(object,"midrashtanchumabuber");

        object = json.getObject(256);
        assertTextUriProperty(object, "midrashtanchumabuber-10-7");
        assertLabelProperty( object ,"מדרש תנחומא בובר פרשת מקץ סימן ז");
        assertPositionProperty(object ,"257");
        assertBookProperty(object,"midrashtanchumabuber");

        object = json.getObject(512);
        assertTextUriProperty(object, "midrashtanchumabuber-26-4");
        assertLabelProperty( object ,"מדרש תנחומא בובר פרשת שמיני סימן ד");
        assertPositionProperty(object ,"513");
        assertBookProperty(object,"midrashtanchumabuber");


        object = json.getObject(721);
        assertTextUriProperty(object, "midrashtanchumabuber-36-16");
        assertLabelProperty( object ,"מדרש תנחומא בובר פרשת בהעלותך סימן טז");
        assertPositionProperty(object ,"722");
        assertBookProperty(object,"midrashtanchumabuber");


        object = json.getObject(800);
        assertTextUriProperty(object, "midrashtanchumabuber-39-15");
        assertLabelProperty( object ,"מדרש תנחומא בובר פרשת קרח סימן טו");
        assertPositionProperty(object ,"801");
        assertBookProperty(object,"midrashtanchumabuber");


        object = json.getObject(900);
        assertTextUriProperty(object, "midrashtanchumabuber-43-26");
        assertLabelProperty( object ,"מדרש תנחומא בובר פרשת בלק סימן כו");
        assertPositionProperty(object ,"901");
        assertBookProperty(object,"midrashtanchumabuber");


        object = json.getObject(1024);
        assertTextUriProperty(object, "midrashtanchumabuber-57-2");
        assertLabelProperty( object ,"מדרש תנחומא בובר פרשת נצבים סימן ב");
        assertPositionProperty(object ,"1025");
        assertBookProperty(object,"midrashtanchumabuber");


        object = json.getObject(1041);
        assertTextUriProperty(object, "midrashtanchumabuber-59-7");
        assertLabelProperty( object ,"מדרש תנחומא בובר פרשת וזאת הברכה סימן ז");
        assertPositionProperty(object ,"1042");
        assertBookProperty(object,"midrashtanchumabuber");


    }
}
