package text2json;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static org.junit.Assert.*;

/**
 * Created by USER on 10-Jan-17.
 */
public class TanachParserTest {

    @BeforeClass
    public static void setup() throws IOException {
        TanachParser parser = new TanachParser(5);
        BufferedReader reader = TestUtils.getText("/tanach/tanach-5.txt");
        parser.parse(reader);
    }

    @Test
    public void test(){
        return;
    }


}