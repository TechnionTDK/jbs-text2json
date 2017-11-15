package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.SefatEmetParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class SefatEmetParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new SefatEmetParser() , "sefatemet");

    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(1787, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        //sefer
        object = json.getObject(17);
        assertTextUriProperty(object, "tanach-sefatemet-1-1-18");
        assertPositionProperty(object ,"18");
        assertBookProperty(object,"sefatemet");

        object = json.getObject(68);
        assertTextUriProperty(object, "tanach-sefatemet-1-3-6");
        assertLabelProperty( object ,"שפת אמת לך לך ו");
        assertPositionProperty(object ,"69");
        assertBookProperty(object,"sefatemet");

        object = json.getObject(268);
        assertTextUriProperty(object, "tanach-sefatemet-1-9-14");
        assertLabelProperty( object ,"שפת אמת וישב יד");
        assertPositionProperty(object ,"269");
        assertBookProperty(object,"sefatemet");

        object = json.getObject(374);
        assertTextUriProperty(object, "tanach-sefatemet-1-12-23");
        assertLabelProperty( object ,"שפת אמת ויגש כג");
        assertPositionProperty(object ,"375");
        assertBookProperty(object,"sefatemet");

        object = json.getObject(560);
        assertTextUriProperty(object, "tanach-sefatemet-2-18-24");
        assertLabelProperty( object ,"שפת אמת יתרו כד");
        assertPositionProperty(object ,"561");

        object = json.getObject(754);
        assertTextUriProperty(object, "tanach-sefatemet-2-25-26");
        assertLabelProperty( object ,"שפת אמת כי תשא כו");
        assertPositionProperty(object ,"755");
        assertBookProperty(object,"sefatemet");

        object = json.getObject(999);
        assertTextUriProperty(object, "tanach-sefatemet-3-37-9");
        assertLabelProperty( object ,"שפת אמת אחרי מות ט");
        assertPositionProperty(object ,"1000");

        object = json.getObject(1268);
        assertTextUriProperty(object, "tanach-sefatemet-4-46-28");
        assertLabelProperty( object ,"שפת אמת שלח כח");
        assertPositionProperty(object ,"1269");

        object = json.getObject(1581);
        assertTextUriProperty(object, "tanach-sefatemet-5-58-17");
        assertLabelProperty( object ,"שפת אמת כי תצא יז");
        assertPositionProperty(object ,"1582");

        object = json.getObject(1700);
        assertTextUriProperty(object, "tanach-sefatemet-5-63-27");
        assertLabelProperty( object ,"שפת אמת ראש השנה כז");
        assertPositionProperty(object ,"1701");

        object = json.getObject(1786);
        assertTextUriProperty(object, "tanach-sefatemet-5-68-1");
        assertLabelProperty( object ,"שפת אמת וזאת הברכה א");
        assertPositionProperty(object ,"1787");
        assertBookProperty(object,"sefatemet");
    }
}
