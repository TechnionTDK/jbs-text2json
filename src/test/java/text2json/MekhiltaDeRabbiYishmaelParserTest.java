package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MekhiltaDeRabbiYishmaelParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class MekhiltaDeRabbiYishmaelParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new MekhiltaDeRabbiYishmaelParser();
        createOutputFolderIfNotExists("mekhiltaderabbiyishmael");
        BufferedReader reader = getText("mekhiltaderabbiyishmael/mekhiltaderabbiyishmael.txt");
        createOutputFolderIfNotExists("mekhiltaderabbiyishmael");
        parser.parse(reader, "json/mekhiltaderabbiyishmael/mekhiltaderabbiyishmael.json");
        json = getJson("json/mekhiltaderabbiyishmael/mekhiltaderabbiyishmael.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(380, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "mekhiltaderabbiyishmael-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"מכילתא דרבי ישמעאל פרק א פסוק א");
        assertBookProperty(object,"mekhiltaderabbiyishmael");

        object = json.getObject(42);
        assertTextUriProperty(object, "mekhiltaderabbiyishmael-12-21");
        assertLabelProperty( object ,"מכילתא דרבי ישמעאל פרק יב פסוק כא");
        assertPositionProperty(object ,"43");
        assertBookProperty(object,"mekhiltaderabbiyishmael");

        object = json.getObject(98);
        assertTextUriProperty(object, "mekhiltaderabbiyishmael-14-6");
        assertLabelProperty( object ,"מכילתא דרבי ישמעאל פרק יד פסוק ו");
        assertPositionProperty(object ,"99");
        assertBookProperty(object,"mekhiltaderabbiyishmael");

        object = json.getObject(150);
        assertTextUriProperty(object, "mekhiltaderabbiyishmael-15-27");
        assertLabelProperty( object ,"מכילתא דרבי ישמעאל פרק טו פסוק כז");
        assertPositionProperty(object ,"151");
        assertBookProperty(object,"mekhiltaderabbiyishmael");

        object = json.getObject(201);
        assertTextUriProperty(object, "mekhiltaderabbiyishmael-17-15");
        assertLabelProperty( object ,"מכילתא דרבי ישמעאל פרק יז פסוק טו");
        assertPositionProperty(object ,"202");
        assertBookProperty(object,"mekhiltaderabbiyishmael");

        object = json.getObject(303);
        assertTextUriProperty(object, "mekhiltaderabbiyishmael-21-30");
        assertLabelProperty( object ,"מכילתא דרבי ישמעאל פרק כא פסוק ל");
        assertPositionProperty(object ,"304");
        assertBookProperty(object,"mekhiltaderabbiyishmael");

        object = json.getObject(379);
        assertTextUriProperty(object, "mekhiltaderabbiyishmael-35-3");
        assertLabelProperty( object ,"מכילתא דרבי ישמעאל פרק לה פסוק ג");
        assertPositionProperty(object ,"380");
        assertBookProperty(object,"mekhiltaderabbiyishmael");
    }
}
