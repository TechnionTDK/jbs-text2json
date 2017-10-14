package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.EinYaakovGlickEditionParser;
import text2json.parsers.SifraParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class SifraParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new SifraParser();
        createOutputFolderIfNotExists("sifra");
        BufferedReader reader = getText("sifra/sifra.txt");
        createOutputFolderIfNotExists("sifra");
        parser.parse(reader, "json/sifra/sifra.json");
        json = getJson("json/sifra/sifra.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(277, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "sifra-0");
        assertPositionProperty(object ,"0");
        assertLabelProperty( object ,"ספרא הקדמה");
        assertBookProperty(object,"sifra");

        object = json.getObject(1);
        assertTextUriProperty(object, "sifra-1-1-1");
        assertLabelProperty( object ,"ספרא פרשת ויקרא דבורא דנדבה פרק א");
        assertPositionProperty(object ,"1");
        assertBookProperty(object,"sifra");

        object = json.getObject(2);
        assertTextUriProperty(object, "sifra-1-1-2");
        assertLabelProperty( object ,"ספרא פרשת ויקרא דבורא דנדבה פרק ב");
        assertPositionProperty(object ,"2");
        assertBookProperty(object,"sifra");

        object = json.getObject(3);
        assertTextUriProperty(object, "sifra-1-2-1");
        assertLabelProperty( object ,"ספרא פרשת ויקרא דבורא דנדבה פרשה א");
        assertPositionProperty(object ,"3");
        assertBookProperty(object,"sifra");

        object = json.getObject(18);
        assertTextUriProperty(object, "sifra-1-2-8");
        assertLabelProperty( object ,"ספרא פרשת ויקרא דבורא דנדבה פרשה ח");
        assertPositionProperty(object ,"18");
        assertBookProperty(object,"sifra");


        object = json.getObject(71);
        assertTextUriProperty(object, "sifra-3-2-1");
        assertLabelProperty( object ,"ספרא פרשת צו פרשה א");
        assertPositionProperty(object ,"71");
        assertBookProperty(object,"sifra");


        object = json.getObject(101);
        assertTextUriProperty(object, "sifra-4-1-3");
        assertLabelProperty( object ,"ספרא פרשת שמיני פרק ג");
        assertPositionProperty(object ,"101");
        assertBookProperty(object,"sifra");


        object = json.getObject(140);
        assertTextUriProperty(object, "sifra-6-1-11");
        assertLabelProperty( object ,"ספרא פרשת תזריע - נגעים פרק יא");
        assertPositionProperty(object ,"140");
        assertBookProperty(object,"sifra");


        object = json.getObject(161);
        assertTextUriProperty(object, "sifra-8-1-2");
        assertLabelProperty( object ,"ספרא פרשת מצורע - זבים פרק ב");
        assertPositionProperty(object ,"161");
        assertBookProperty(object,"sifra");


        object = json.getObject(201);
        assertTextUriProperty(object, "sifra-10-2-3");
        assertLabelProperty( object ,"ספרא פרשת קדושים פרשה ג");
        assertPositionProperty(object ,"201");
        assertBookProperty(object,"sifra");

        object = json.getObject(251);
        assertTextUriProperty(object, "sifra-12-1-5");
        assertLabelProperty( object ,"ספרא פרשת בהר פרק ה");
        assertPositionProperty(object ,"251");
        assertBookProperty(object,"sifra");

        object = json.getObject(276);
        assertTextUriProperty(object, "sifra-13-1-13");
        assertLabelProperty( object ,"ספרא פרשת בחוקתי פרק יג");
        assertPositionProperty(object ,"276");
        assertBookProperty(object,"sifra");


    }
}
