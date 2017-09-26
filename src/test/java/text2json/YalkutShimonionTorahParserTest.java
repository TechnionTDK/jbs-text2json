package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.YalkutShimonionNachParser;
import text2json.parsers.YalkutShimonionTorahParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class YalkutShimonionTorahParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new YalkutShimonionTorahParser();
        createOutputFolderIfNotExists("yalkutshimoniontorah");
        BufferedReader reader = getText("yalkutshimoniontorah/yalkutshimoniontorah.txt");
        createOutputFolderIfNotExists("yalkutshimoniontorah");
        parser.parse(reader, "json/yalkutshimoniontorah/yalkutshimoniontorah.json");
        json = getJson("json/yalkutshimoniontorah/yalkutshimoniontorah.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(966, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "yalkutshimoniontorah-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"ילקוט שמעוני על התורה - רמז 1");
        assertBookProperty(object,"yalkutshimoniontorah");

        object = json.getObject(144);
        assertTextUriProperty(object, "yalkutshimoniontorah-145");
        assertLabelProperty( object ,"ילקוט שמעוני על התורה - רמז 145");
        assertPositionProperty(object ,"145");
        assertBookProperty(object,"yalkutshimoniontorah");

        object = json.getObject(376);
        assertTextUriProperty(object, "yalkutshimoniontorah-377");
        assertLabelProperty( object ,"ילקוט שמעוני על התורה - רמז 377");
        assertPositionProperty(object ,"377");
        assertBookProperty(object,"yalkutshimoniontorah");

        object = json.getObject(542);
        assertTextUriProperty(object, "yalkutshimoniontorah-543");
        assertLabelProperty( object ,"ילקוט שמעוני על התורה - רמז 543");
        assertPositionProperty(object ,"543");
        assertBookProperty(object,"yalkutshimoniontorah");

        object = json.getObject(641);
        assertTextUriProperty(object, "yalkutshimoniontorah-642");
        assertLabelProperty( object ,"ילקוט שמעוני על התורה - רמז 642");
        assertPositionProperty(object ,"642");
        assertBookProperty(object,"yalkutshimoniontorah");

        object = json.getObject(899);
        assertTextUriProperty(object, "yalkutshimoniontorah-900");
        assertLabelProperty( object ,"ילקוט שמעוני על התורה - רמז 900");
        assertPositionProperty(object ,"900");
        assertBookProperty(object,"yalkutshimoniontorah");

        object = json.getObject(965);
        assertTextUriProperty(object, "yalkutshimoniontorah-966");
        assertLabelProperty( object ,"ילקוט שמעוני על התורה - רמז 966");
        assertPositionProperty(object ,"966");
        assertBookProperty(object,"yalkutshimoniontorah");
    }
}
