package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.TiferetIsraelParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class TiferetIsraelParserTest {
    static SubjectsJson json;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new TiferetIsraelParser();
        createOutputFolderIfNotExists("tiferetisrael");
        BufferedReader reader = getText("tiferetisrael/tiferetisrael.txt");
        createOutputFolderIfNotExists("tiferetisrael");
        parser.parse(reader, "json/tiferetisrael/tiferetisrael.json");
        json = getJson("json/tiferetisrael/tiferetisrael.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(71, json.subjects.size());
    }

    @Test
    public void testSpecificObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "tiferetisrael-0");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תפארת ישראל - הקדמה");
        assertBookProperty(object,"tiferetisrael");

        object = json.getObject(3);
        assertTextUriProperty(object, "tiferetisrael-3");
        assertLabelProperty( object ,"תפארת ישראל ג");
        assertPositionProperty(object ,"4");
        assertBookProperty(object,"tiferetisrael");

        object = json.getObject(23);
        assertTextUriProperty(object, "tiferetisrael-23");
        assertLabelProperty( object ,"תפארת ישראל כג");
        assertPositionProperty(object ,"24");

        object = json.getObject(32);
        assertTextUriProperty(object, "tiferetisrael-32");
        assertLabelProperty( object ,"תפארת ישראל לב");
        assertPositionProperty(object ,"33");
        assertBookProperty(object,"tiferetisrael");

        object = json.getObject(57);
        assertTextUriProperty(object, "tiferetisrael-57");
        assertLabelProperty( object ,"תפארת ישראל נז");
        assertPositionProperty(object ,"58");

        object = json.getObject(64);
        assertTextUriProperty(object, "tiferetisrael-64");
        assertLabelProperty( object ,"תפארת ישראל סד");
        assertPositionProperty(object ,"65");

        object = json.getObject(69);
        assertTextUriProperty(object, "tiferetisrael-69");
        assertLabelProperty( object ,"תפארת ישראל סט");
        assertPositionProperty(object ,"70");

        object = json.getObject(70);
        assertTextUriProperty(object, "tiferetisrael-70");
        assertLabelProperty( object ,"תפארת ישראל ע");
        assertPositionProperty(object ,"71");
        assertBookProperty(object,"tiferetisrael");
    }
}
