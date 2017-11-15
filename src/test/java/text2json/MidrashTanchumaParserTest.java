package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MidrashTanchumaParser;

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
        json = setupParser(new MidrashTanchumaParser() , "midrashtanchuma");

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
        assertLabelProperty( object ,"מדרש תנחומא בראשית א");
        assertBookProperty(object,"midrashtanchuma");

        object = json.getObject(121);
        assertTextUriProperty(object, "midrashtanchuma-9-2");
        assertLabelProperty( object ,"מדרש תנחומא וישב ב");
        assertPositionProperty(object ,"122");
        assertBookProperty(object,"midrashtanchuma");

        object = json.getObject(222);
        assertTextUriProperty(object, "midrashtanchuma-15-10");
        assertLabelProperty( object ,"מדרש תנחומא בא י");
        assertPositionProperty(object ,"223");
        assertBookProperty(object,"midrashtanchuma");

        object = json.getObject(333);
        assertTextUriProperty(object, "midrashtanchuma-21-17");
        assertLabelProperty( object ,"מדרש תנחומא כי תשא יז");
        assertPositionProperty(object ,"334");
        assertBookProperty(object,"midrashtanchuma");

        object = json.getObject(444);
        assertTextUriProperty(object, "midrashtanchuma-30-4");
        assertLabelProperty( object ,"מדרש תנחומא קדושים ד");
        assertPositionProperty(object ,"445");
        assertBookProperty(object,"midrashtanchuma");


        object = json.getObject(555);
        assertTextUriProperty(object, "midrashtanchuma-36-10");
        assertLabelProperty( object ,"מדרש תנחומא בהעלותך י");
        assertPositionProperty(object ,"556");
        assertBookProperty(object,"midrashtanchuma");


        object = json.getObject(666);
        assertTextUriProperty(object, "midrashtanchuma-43-6");
        assertLabelProperty( object ,"מדרש תנחומא מסעי ו");
        assertPositionProperty(object ,"667");
        assertBookProperty(object,"midrashtanchuma");


        object = json.getObject(700);
        assertTextUriProperty(object, "midrashtanchuma-47-6");
        assertLabelProperty( object ,"מדרש תנחומא ראה ו");
        assertPositionProperty(object ,"701");
        assertBookProperty(object,"midrashtanchuma");


        object = json.getObject(750);
        assertTextUriProperty(object, "midrashtanchuma-51-4");
        assertLabelProperty( object ,"מדרש תנחומא נצבים ד");
        assertPositionProperty(object ,"751");
        assertBookProperty(object,"midrashtanchuma");


        object = json.getObject(775);
        assertTextUriProperty(object, "midrashtanchuma-54-7");
        assertLabelProperty( object ,"מדרש תנחומא וזאת הברכה ז");
        assertPositionProperty(object ,"776");
        assertBookProperty(object,"midrashtanchuma");


    }
}
