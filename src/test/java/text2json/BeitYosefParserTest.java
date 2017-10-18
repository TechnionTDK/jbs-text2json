package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.BeitYosefParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class BeitYosefParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new BeitYosefParser();
        createOutputFolderIfNotExists("beityosef");
        BufferedReader reader = getText("beityosef/beityosef.txt");
        createOutputFolderIfNotExists("beityosef");
        parser.parse(reader, "json/beityosef/beityosef.json");
        json = getJson("json/beityosef/beityosef.json");
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
        assertEquals(19328, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        //First Tur אורח חיים : 0-5984
        object = json.getObject(0);
        assertTextUriProperty(object, "beityosef-1-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"בית יוסף טור אורח חיים סימן 1 סעיף 1");
        assertBookProperty(object,"beityosef");

        object = json.getObject(3784);
        assertTextUriProperty(object, "beityosef-1-440-8");
        assertLabelProperty( object ,"בית יוסף טור אורח חיים סימן 440 סעיף 8");
        assertPositionProperty(object ,"3785");
        assertBookProperty(object,"beityosef");

        object = json.getObject(5984);
        assertTextUriProperty(object, "beityosef-1-697-1");
        assertLabelProperty( object ,"בית יוסף טור אורח חיים סימן 697 סעיף 1");
        assertPositionProperty(object ,"5985");
        assertBookProperty(object,"beityosef");

        //Second Tur יורה דעה : 5985-11860
        object = json.getObject(5985);
        assertTextUriProperty(object, "beityosef-2-1-1");
        assertLabelProperty( object ,"בית יוסף טור יורה דעה סימן 1 סעיף 1");
        assertPositionProperty(object ,"5986");

        object = json.getObject(9854);
        assertTextUriProperty(object, "beityosef-2-256-5");
        assertLabelProperty( object ,"בית יוסף טור יורה דעה סימן 256 סעיף 5");
        assertPositionProperty(object ,"9855");

        object = json.getObject(11860);
        assertTextUriProperty(object, "beityosef-2-403-14");
        assertLabelProperty( object ,"בית יוסף טור יורה דעה סימן 403 סעיף 14");
        assertPositionProperty(object ,"11861");
        assertBookProperty(object,"beityosef");

        //Third Tur אבן העזר : 11861-14719
        object = json.getObject(11861);
        assertTextUriProperty(object, "beityosef-3-1-1");
        assertLabelProperty( object ,"בית יוסף טור אבן העזר סימן 1 סעיף 1");
        assertPositionProperty(object ,"11862");

        object = json.getObject(12999);
        assertTextUriProperty(object, "beityosef-3-80-13");
        assertLabelProperty( object ,"בית יוסף טור אבן העזר סימן 80 סעיף 13");
        assertPositionProperty(object ,"13000");

        object = json.getObject(14718);
        assertTextUriProperty(object, "beityosef-3-178-31");
        assertLabelProperty( object ,"בית יוסף טור אבן העזר סימן 178 סעיף 31");
        assertPositionProperty(object ,"14719");
        assertBookProperty(object,"beityosef");

        //Forth Tur חושן משפט : 14719-1928
        object = json.getObject(14719);
        assertTextUriProperty(object, "beityosef-4-1-1");
        assertLabelProperty( object ,"בית יוסף טור חושן משפט סימן 1 סעיף 1");
        assertPositionProperty(object ,"14720");
        assertBookProperty(object,"beityosef");

        object = json.getObject(16863);
        assertTextUriProperty(object, "beityosef-4-155-51");
        assertLabelProperty( object ,"בית יוסף טור חושן משפט סימן 155 סעיף 51");
        assertPositionProperty(object ,"16864");
        assertBookProperty(object,"beityosef");

        object = json.getObject(19327);
        assertTextUriProperty(object, "beityosef-4-426-2");
        assertLabelProperty( object ,"בית יוסף טור חושן משפט סימן 426 סעיף 2");
        assertPositionProperty(object ,"19328");
        assertBookProperty(object,"beityosef");

    }
}
