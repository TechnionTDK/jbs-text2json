package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.DarcheiMosheParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class DarcheiMosheParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new DarcheiMosheParser();
        createOutputFolderIfNotExists("darcheimoshe");
        BufferedReader reader = getText("darcheimoshe/darcheimoshe.txt");
        createOutputFolderIfNotExists("darcheimoshe");
        parser.parse(reader, "json/darcheimoshe/darcheimoshe.json");
        json = getJson("json/darcheimoshe/darcheimoshe.json");
    }

//    @Test
//    //test the correctness with sampling a few values
//    public void test() {
//        System.out.println("bla bla");
//        System.out.println();
//    }


    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(5981, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        //First Tur אורח חיים : 0-1819
        object = json.getObject(0);
        assertTextUriProperty(object, "darcheimoshe-1-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"דרכי משה אורח חיים א א");
        assertBookProperty(object,"darcheimoshe");

        object = json.getObject(987);
        assertTextUriProperty(object, "darcheimoshe-1-366-1");
        assertLabelProperty( object ,"דרכי משה אורח חיים שסו א");
        assertPositionProperty(object ,"988");
        assertBookProperty(object,"darcheimoshe");

        object = json.getObject(1819);
        assertTextUriProperty(object, "darcheimoshe-1-697-3");
        assertLabelProperty( object ,"דרכי משה אורח חיים תרצז ג");
        assertPositionProperty(object ,"1820");
        assertBookProperty(object,"darcheimoshe");

        //Second Tur יורה דעה : 1820-3488
        object = json.getObject(1820);
        assertTextUriProperty(object, "darcheimoshe-2-1-1");
        assertLabelProperty( object ,"דרכי משה יורה דעה א א");
        assertPositionProperty(object ,"1821");

        object = json.getObject(2555);
        assertTextUriProperty(object, "darcheimoshe-2-124-9");
        assertLabelProperty( object ,"דרכי משה יורה דעה קכד ט");
        assertPositionProperty(object ,"2556");

        object = json.getObject(3488);
        assertTextUriProperty(object, "darcheimoshe-2-403-1");
        assertLabelProperty( object ,"דרכי משה יורה דעה תג א");
        assertPositionProperty(object ,"3489");
        assertBookProperty(object,"darcheimoshe");

        //Third Tur אבן העזר : 3489-4591
        object = json.getObject(3489);
        assertTextUriProperty(object, "darcheimoshe-3-1-1");
        assertLabelProperty( object ,"דרכי משה אבן העזר א א");
        assertPositionProperty(object ,"3490");

        object = json.getObject(4000);
        assertTextUriProperty(object, "darcheimoshe-3-93-14");
        assertLabelProperty( object ,"דרכי משה אבן העזר צג יד");
        assertPositionProperty(object ,"4001");

        object = json.getObject(4590);
        assertTextUriProperty(object, "darcheimoshe-3-178-5");
        assertLabelProperty( object ,"דרכי משה אבן העזר קעח ה");
        assertPositionProperty(object ,"4591");
        assertBookProperty(object,"darcheimoshe");

        //Forth Tur חושן משפט : 4592-5981
        object = json.getObject(4592);
        assertTextUriProperty(object, "darcheimoshe-4-1-1");
        assertLabelProperty( object ,"דרכי משה חושן משפט א א");
        assertPositionProperty(object ,"4593");
        assertBookProperty(object,"darcheimoshe");

        object = json.getObject(5123);
        assertTextUriProperty(object, "darcheimoshe-4-110-5");
        assertLabelProperty( object ,"דרכי משה חושן משפט קי ה");
        assertPositionProperty(object ,"5124");
        assertBookProperty(object,"darcheimoshe");

        object = json.getObject(5980);
        assertTextUriProperty(object, "darcheimoshe-4-426-1");
        assertLabelProperty( object ,"דרכי משה חושן משפט תכו א");
        assertPositionProperty(object ,"5981");
        assertBookProperty(object,"darcheimoshe");

    }
}
