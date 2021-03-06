package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.TurParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class TurParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new TurParser() , "tur");

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
        assertEquals(1704, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "tur-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"ארבעה טורים טור אורח חיים א");
        assertBookProperty(object,"tur");

        object = json.getObject(13);
        assertTextUriProperty(object, "tur-1-14");
        assertLabelProperty( object ,"ארבעה טורים טור אורח חיים יד");
        assertPositionProperty(object ,"14");
        assertBookProperty(object,"tur");

        object = json.getObject(599);
        assertTextUriProperty(object, "tur-1-600");
        assertLabelProperty( object ,"ארבעה טורים טור אורח חיים תר");
        assertPositionProperty(object ,"600");
        assertBookProperty(object,"tur");

        object = json.getObject(696);
        assertTextUriProperty(object, "tur-1-697");
        assertLabelProperty( object ,"ארבעה טורים טור אורח חיים תרצז");
        assertPositionProperty(object ,"697");

        object = json.getObject(697);
        assertTextUriProperty(object, "tur-2-1");
        assertLabelProperty( object ,"ארבעה טורים טור יורה דעה א");
        assertPositionProperty(object ,"698");

        object = json.getObject(897);
        assertTextUriProperty(object, "tur-2-201");
        assertLabelProperty( object ,"ארבעה טורים טור יורה דעה רא");
        assertPositionProperty(object ,"898");
        assertBookProperty(object,"tur");

        object = json.getObject(1099);
        assertTextUriProperty(object, "tur-2-403");
        assertLabelProperty( object ,"ארבעה טורים טור יורה דעה תג");
        assertPositionProperty(object ,"1100");

        object = json.getObject(1100);
        assertTextUriProperty(object, "tur-3-1");
        assertLabelProperty( object ,"ארבעה טורים טור אבן העזר א");
        assertPositionProperty(object ,"1101");

        object = json.getObject(1200);
        assertTextUriProperty(object, "tur-3-101");
        assertLabelProperty( object ,"ארבעה טורים טור אבן העזר קא");
        assertPositionProperty(object ,"1201");
        assertBookProperty(object,"tur");

        object = json.getObject(1277);
        assertTextUriProperty(object, "tur-3-178");
        assertLabelProperty( object ,"ארבעה טורים טור אבן העזר קעח");
        assertPositionProperty(object ,"1278");
        assertBookProperty(object,"tur");

        object = json.getObject(1278);
        assertTextUriProperty(object, "tur-4-1");
        assertLabelProperty( object ,"ארבעה טורים טור חושן משפט א");
        assertPositionProperty(object ,"1279");
        assertBookProperty(object,"tur");

        object = json.getObject(1478);
        assertTextUriProperty(object, "tur-4-201");
        assertLabelProperty( object ,"ארבעה טורים טור חושן משפט רא");
        assertPositionProperty(object ,"1479");
        assertBookProperty(object,"tur");

        object = json.getObject(1703);
        assertTextUriProperty(object, "tur-4-426");
        assertLabelProperty( object ,"ארבעה טורים טור חושן משפט תכו");
        assertPositionProperty(object ,"1704");
        assertBookProperty(object,"tur");

    }
}
