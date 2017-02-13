package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MishneTorahParser;

import java.io.BufferedReader;

import static org.junit.Assert.*;
import static text2json.TestUtils.*;

/**
 * Created by omishali on 05/02/2017.
 */
public class MishneTorahParserTest {
    static final int[] hilchotNum = {5, 6, 10, 5, 3};
    // number of perakim in each sefer
    static final int[] perakimNum = {46, 46, 97, 53, 53};
    // number of halachot in each sefer (or part of them...)
    static final int[] halachotNum = {457};
    private static final int NUM_OF_BOOKS = hilchotNum.length;
    static SubjectsJson[] packagesJson = new SubjectsJson[NUM_OF_BOOKS];
    static SubjectsJson[] json = new SubjectsJson[NUM_OF_BOOKS];
    // number of hilchot in each sefer


    @BeforeClass
    public static void setup() throws Exception {
        for (int i = 0; i < NUM_OF_BOOKS; i++) {
            int bookNum = i + 1;
            MishneTorahParser parser = new MishneTorahParser();
            BufferedReader reader = getText("/mishnetorah/mishnetorah-" + bookNum + ".txt");
            parser.parse(reader, "json/mishnetorah/mishnetorah-" + bookNum + ".json");
            json[i] = getJson("json/mishnetorah/mishnetorah-" + bookNum + ".json");
            packagesJson[i] = getJson("json/mishnetorah/mishnetorah-" + bookNum + "-packages.json");
        }
    }

    @Test
    public void testNumberOfPackageElements() {
        for (int i=0; i < NUM_OF_BOOKS; i++) {
            int expected = 1 + hilchotNum[i] + perakimNum[i]; // sefer + hilchot + perakim
            String message = "Test " + i + " failed";
            assertEquals(message, expected, packagesJson[i].subjects.size());
        }
    }

    @Test
    public void testNumberofHalachot() {
        // sefer Hamada
        assertEquals(halachotNum[0] + 45 + 309 + 198 + 49, json[0].subjects.size()); // perush, kesefmishne, lechemmishne + raabad
    }

}
