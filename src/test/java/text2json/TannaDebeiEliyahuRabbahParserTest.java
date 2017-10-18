package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.PirkeiDeRabbiEliezerParser;
import text2json.parsers.TannaDebeiEliyahuRabbahParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class TannaDebeiEliyahuRabbahParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new TannaDebeiEliyahuRabbahParser();
        createOutputFolderIfNotExists("tannadebeieliyahurabbah");
        BufferedReader reader = getText("tannadebeieliyahurabbah/tannadebeieliyahurabbah.txt");
        createOutputFolderIfNotExists("tannadebeieliyahurabbah");
        parser.parse(reader, "json/tannadebeieliyahurabbah/tannadebeieliyahurabbah.json");
        json = getJson("json/tannadebeieliyahurabbah/tannadebeieliyahurabbah.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(31, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "tannadebeieliyahurabbah-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תנא דבי אליהו רבה פרק 1");
        assertBookProperty(object,"tannadebeieliyahurabbah");

        object = json.getObject(5);
        assertTextUriProperty(object, "tannadebeieliyahurabbah-6");
        assertLabelProperty( object ,"תנא דבי אליהו רבה פרק 6");
        assertPositionProperty(object ,"6");
        assertBookProperty(object,"tannadebeieliyahurabbah");

        object = json.getObject(13);
        assertTextUriProperty(object, "tannadebeieliyahurabbah-14");
        assertLabelProperty( object ,"תנא דבי אליהו רבה פרק 14");
        assertPositionProperty(object ,"14");
        assertBookProperty(object,"tannadebeieliyahurabbah");

        object = json.getObject(21);
        assertTextUriProperty(object, "tannadebeieliyahurabbah-22");
        assertLabelProperty( object ,"תנא דבי אליהו רבה פרק 22");
        assertPositionProperty(object ,"22");
        assertBookProperty(object,"tannadebeieliyahurabbah");

        object = json.getObject(30);
        assertTextUriProperty(object, "tannadebeieliyahurabbah-31");
        assertLabelProperty( object ,"תנא דבי אליהו רבה פרק 31");
        assertPositionProperty(object ,"31");
        assertBookProperty(object,"tannadebeieliyahurabbah");

    }
}
