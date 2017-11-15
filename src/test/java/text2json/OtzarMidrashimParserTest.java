package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.OtzarMidrashimParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class OtzarMidrashimParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new OtzarMidrashimParser() , "otzarmidrashim");

    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(157, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "otzarmidrashim-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"אוצר מדרשים מדרש מעשה אברהם אבינו פרק 1");
        assertBookProperty(object,"otzarmidrashim");

        object = json.getObject(14);
        assertTextUriProperty(object, "otzarmidrashim-10-0");
        assertLabelProperty( object ,"אוצר מדרשים מדרש אלפא ביתא דבן סירא הקדמה");
        assertPositionProperty(object ,"15");
        assertBookProperty(object,"otzarmidrashim");

        object = json.getObject(32);
        assertTextUriProperty(object, "otzarmidrashim-20-1");
        assertLabelProperty( object ,"אוצר מדרשים מדרש מסכת גן אדן פרק 1");
        assertPositionProperty(object ,"33");
        assertBookProperty(object,"otzarmidrashim");

        object = json.getObject(56);
        assertTextUriProperty(object, "otzarmidrashim-28-5");
        assertLabelProperty( object ,"אוצר מדרשים מדרש ואשה אחת מנשי בני הנביאים פרק 5");
        assertPositionProperty(object ,"57");
        assertBookProperty(object,"otzarmidrashim");

        object = json.getObject(78);
        assertTextUriProperty(object, "otzarmidrashim-37-0");
        assertLabelProperty( object ,"אוצר מדרשים מדרש מדרשי חסר ויתר הקדמה");
        assertPositionProperty(object ,"79");
        assertBookProperty(object,"otzarmidrashim");


        object = json.getObject(98);
        assertTextUriProperty(object, "otzarmidrashim-46-5");
        assertLabelProperty( object ,"אוצר מדרשים מדרש ספר יצירה פרק 5");
        assertPositionProperty(object ,"99");
        assertBookProperty(object,"otzarmidrashim");


        object = json.getObject(131);
        assertTextUriProperty(object, "otzarmidrashim-57-10");
        assertLabelProperty( object ,"אוצר מדרשים מדרש לעולם פרק 10");
        assertPositionProperty(object ,"132");
        assertBookProperty(object,"otzarmidrashim");


        object = json.getObject(156);
        assertTextUriProperty(object, "otzarmidrashim-63-2");
        assertLabelProperty( object ,"אוצר מדרשים מדרש מדרש מִנַיִן פרק 2");
        assertPositionProperty(object ,"157");
        assertBookProperty(object,"otzarmidrashim");


    }
}
