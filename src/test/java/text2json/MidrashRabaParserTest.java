package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MidrashRabaParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static text2json.JbsOntology.JBO_TEXT;
import static text2json.TestUtils.*;

/**
 * Created by omishali on 05/02/2017.
 */
public class MidrashRabaParserTest {
    static int bookNums[] = {1,2,3,4,5,30,31,32,33,34};
    static SubjectsJson[] json = new SubjectsJson[bookNums.length];
    static SubjectsJson[] packagesJson = new SubjectsJson[bookNums.length];
    @BeforeClass
    public static void setup() throws Exception {
        MidrashRabaParser parser = new MidrashRabaParser();
        for (int i=0; i< bookNums.length; i++) {
            BufferedReader reader = TestUtils.getText("midrashraba/tanach-midrashraba-" + bookNums[i] + ".txt");
            createOutputFolderIfNotExists("midrashraba");
            parser.parse(reader, "json/midrashraba/tanach-midrashraba-" + bookNums[i] + ".json");
            json[i] = getJson("json/midrashraba/tanach-midrashraba-" + bookNums[i] + ".json");
            packagesJson[i] = getJson("json/midrashraba/tanach-midrashraba-" + bookNums[i] + "-packages.json");
        }
    }

    @Test
    public void testSpecificObjects() {
        Map<String, String> object;

        // first object
        object = json[0].getObject(0);
        assertTextUriProperty( object , "tanach-midrashraba-1-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"מדרש רבה בראשית א א");
        assertBookProperty( object,"midrashraba");

        // last object
        object = json[0].getObject(json[0].subjects.size()-1);
        assertTrue(object.get(JBO_TEXT).contains("ויעש לאביו אבל שבעת ימים"));
        assertBookProperty( object,"midrashraba");
    }

}
