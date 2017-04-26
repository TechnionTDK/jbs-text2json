package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.DemoParser;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.util.Map;

public class DemoTest {

    @Test
    public void test() throws Exception {
        DemoParser parser = new DemoParser();
        BufferedReader reader = getText("/Demo/demo.txt");
        parser.parse(reader, "../../jbs-text/Demo/demo.json");
    }

}
