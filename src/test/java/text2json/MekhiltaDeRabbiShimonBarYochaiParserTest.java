package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MekhiltaDeRabbiShimonBarYochaiParser;
import text2json.parsers.MekhiltaDeRabbiYishmaelParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class MekhiltaDeRabbiShimonBarYochaiParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new MekhiltaDeRabbiShimonBarYochaiParser();
        createOutputFolderIfNotExists("mekhiltaderabbishimonbaryochai");
        BufferedReader reader = getText("mekhiltaderabbishimonbaryochai/mekhiltaderabbishimonbaryochai.txt");
        createOutputFolderIfNotExists("mekhiltaderabbishimonbaryochai");
        parser.parse(reader, "json/mekhiltaderabbishimonbaryochai/mekhiltaderabbishimonbaryochai.json");
        json = getJson("json/mekhiltaderabbishimonbaryochai/mekhiltaderabbishimonbaryochai.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(45, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "mekhiltaderabbishimonbaryochai-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"מכילתא דרשב\"י א");
        assertBookProperty(object,"mekhiltaderabbishimonbaryochai");

        object = json.getObject(9);
        assertTextUriProperty(object, "mekhiltaderabbishimonbaryochai-1-10");
        assertLabelProperty( object ,"מכילתא דרשב\"י י");
        assertPositionProperty(object ,"10");
        assertBookProperty(object,"mekhiltaderabbishimonbaryochai");

        object = json.getObject(15);
        assertTextUriProperty(object, "mekhiltaderabbishimonbaryochai-1-16");
        assertLabelProperty( object ,"מכילתא דרשב\"י טז");
        assertPositionProperty(object ,"16");
        assertBookProperty(object,"mekhiltaderabbishimonbaryochai");

        object = json.getObject(22);
        assertTextUriProperty(object, "mekhiltaderabbishimonbaryochai-1-23");
        assertLabelProperty( object ,"מכילתא דרשב\"י כג");
        assertPositionProperty(object ,"23");
        assertBookProperty(object,"mekhiltaderabbishimonbaryochai");

        object = json.getObject(30);
        assertTextUriProperty(object, "mekhiltaderabbishimonbaryochai-1-31");
        assertLabelProperty( object ,"מכילתא דרשב\"י לא");
        assertPositionProperty(object ,"31");
        assertBookProperty(object,"mekhiltaderabbishimonbaryochai");

        object = json.getObject(37);
        assertTextUriProperty(object, "mekhiltaderabbishimonbaryochai-2-3");
        assertLabelProperty( object ,"מכילתא דרשב\"י הוספה ג");
        assertPositionProperty(object ,"38");
        assertBookProperty(object,"mekhiltaderabbishimonbaryochai");

        object = json.getObject(44);
        assertTextUriProperty(object, "mekhiltaderabbishimonbaryochai-2-10");
        assertLabelProperty( object ,"מכילתא דרשב\"י הוספה י");
        assertPositionProperty(object ,"45");
        assertBookProperty(object,"mekhiltaderabbishimonbaryochai");
    }
}
