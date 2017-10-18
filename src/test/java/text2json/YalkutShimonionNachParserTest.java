package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.SifreiDevarimParser;
import text2json.parsers.YalkutShimonionNachParser;

import java.io.BufferedReader;
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
        Parser parser = new YalkutShimonionNachParser();
        createOutputFolderIfNotExists("yalkutshimonionnach");
        BufferedReader reader = getText("yalkutshimonionnach/yalkutshimonionnach.txt");
        createOutputFolderIfNotExists("yalkutshimonionnach");
        parser.parse(reader, "json/yalkutshimonionnach/yalkutshimonionnach.json");
        json = getJson("json/yalkutshimonionnach/yalkutshimonionnach.json");
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
        assertLabelProperty( object ,"ילקוט שמעוני על נך רמז 1");
        assertBookProperty(object,"yalkutshimonionnach");

        object = json.getObject(144);
        assertTextUriProperty(object, "yalkutshimonionnach-145");
        assertLabelProperty( object ,"ילקוט שמעוני על נך רמז 145");
        assertPositionProperty(object ,"145");
        assertBookProperty(object,"yalkutshimonionnach");

        object = json.getObject(376);
        assertTextUriProperty(object, "yalkutshimonionnach-377");
        assertLabelProperty( object ,"ילקוט שמעוני על נך רמז 377");
        assertPositionProperty(object ,"377");
        assertBookProperty(object,"yalkutshimonionnach");

        object = json.getObject(542);
        assertTextUriProperty(object, "yalkutshimonionnach-543");
        assertLabelProperty( object ,"ילקוט שמעוני על נך רמז 543");
        assertPositionProperty(object ,"543");
        assertBookProperty(object,"yalkutshimonionnach");

        object = json.getObject(641);
        assertTextUriProperty(object, "yalkutshimonionnach-642");
        assertLabelProperty( object ,"ילקוט שמעוני על נך רמז 642");
        assertPositionProperty(object ,"642");
        assertBookProperty(object,"yalkutshimonionnach");

        object = json.getObject(899);
        assertTextUriProperty(object, "yalkutshimonionnach-900");
        assertLabelProperty( object ,"ילקוט שמעוני על נך רמז 900");
        assertPositionProperty(object ,"900");
        assertBookProperty(object,"yalkutshimonionnach");

        object = json.getObject(1084);
        assertTextUriProperty(object, "yalkutshimonionnach-1085");
        assertLabelProperty( object ,"ילקוט שמעוני על נך רמז 1085");
        assertPositionProperty(object ,"1085");
        assertBookProperty(object,"yalkutshimonionnach");
    }
}
