package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.YalkutShimonionNachParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class YalkutShimonionNachParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new YalkutShimonionNachParser() , "yalkutshimonionnach");

    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(1085, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "yalkutshimonionnach-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"ילקוט שמעוני על נ\"ך א");
        assertBookProperty(object,"yalkutshimonionnach");

        object = json.getObject(144);
        assertTextUriProperty(object, "yalkutshimonionnach-145");
        assertLabelProperty( object ,"ילקוט שמעוני על נ\"ך קמה");
        assertPositionProperty(object ,"145");
        assertBookProperty(object,"yalkutshimonionnach");

        object = json.getObject(376);
        assertTextUriProperty(object, "yalkutshimonionnach-377");
        assertLabelProperty( object ,"ילקוט שמעוני על נ\"ך שעז");
        assertPositionProperty(object ,"377");
        assertBookProperty(object,"yalkutshimonionnach");

        object = json.getObject(542);
        assertTextUriProperty(object, "yalkutshimonionnach-543");
        assertLabelProperty( object ,"ילקוט שמעוני על נ\"ך תקמג");
        assertPositionProperty(object ,"543");
        assertBookProperty(object,"yalkutshimonionnach");

        object = json.getObject(641);
        assertTextUriProperty(object, "yalkutshimonionnach-642");
        assertLabelProperty( object ,"ילקוט שמעוני על נ\"ך תרמב");
        assertPositionProperty(object ,"642");
        assertBookProperty(object,"yalkutshimonionnach");

        object = json.getObject(899);
        assertTextUriProperty(object, "yalkutshimonionnach-900");
        assertLabelProperty( object ,"ילקוט שמעוני על נ\"ך תתק");
        assertPositionProperty(object ,"900");
        assertBookProperty(object,"yalkutshimonionnach");

        object = json.getObject(1084);
        assertTextUriProperty(object, "yalkutshimonionnach-1085");
        assertLabelProperty( object ,"ילקוט שמעוני על נ\"ך תתרפה");
        assertPositionProperty(object ,"1085");
        assertBookProperty(object,"yalkutshimonionnach");
    }
}
