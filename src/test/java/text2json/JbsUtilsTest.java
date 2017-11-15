package text2json;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static text2json.JbsUtils.HEB_LETTERS_INDEX;
import static text2json.JbsUtils.hebrewToNumber;
/**
 * Created by omishali on 25/10/2017.
 */
public class JbsUtilsTest {
    @Test
    public void testNumberToHebrew() {
        assertEquals("תריג", text2json.JbsUtils.numberToHebrew(613));
        assertEquals("ב", text2json.JbsUtils.numberToHebrew(2));
        assertEquals("טו", text2json.JbsUtils.numberToHebrew(15));
        assertEquals("טז", text2json.JbsUtils.numberToHebrew(16));
        assertEquals("יח", text2json.JbsUtils.numberToHebrew(18));
        assertEquals("סב", text2json.JbsUtils.numberToHebrew(62));
        assertEquals("קסא", text2json.JbsUtils.numberToHebrew(161));
        assertEquals("תק", text2json.JbsUtils.numberToHebrew(500));
        assertEquals("תר", text2json.JbsUtils.numberToHebrew(600));
        assertEquals("תשכג", text2json.JbsUtils.numberToHebrew(723));
        assertEquals("תתצא", text2json.JbsUtils.numberToHebrew(891));
        assertEquals("תתק", text2json.JbsUtils.numberToHebrew(900));
        assertEquals("תתררנ", text2json.JbsUtils.numberToHebrew(1250));
    }

    @Test
    public void testHebrewLettersArray(){
        assertEquals("תריג", HEB_LETTERS_INDEX[613-1]);
        assertEquals("ב", HEB_LETTERS_INDEX[2-1]);
        assertEquals("טו", HEB_LETTERS_INDEX[15-1]);
        assertEquals("טז", HEB_LETTERS_INDEX[16-1]);
        assertEquals("יח", HEB_LETTERS_INDEX[18-1]);
        assertEquals("סב", HEB_LETTERS_INDEX[62-1]);
        assertEquals("קסא", HEB_LETTERS_INDEX[161-1]);
        assertEquals("תק", HEB_LETTERS_INDEX[500-1]);
        assertEquals("תר", HEB_LETTERS_INDEX[600-1]);
        assertEquals("תשכג", HEB_LETTERS_INDEX[723-1]);
        assertEquals("תתצא", HEB_LETTERS_INDEX[891-1]);
        assertEquals("תתק", HEB_LETTERS_INDEX[900-1]);
    }

    @Test
    public void testHebrewToNumber() {
        assertEquals(613, hebrewToNumber("תריג"));
        assertEquals(151, hebrewToNumber("קנא"));
        assertEquals(4, hebrewToNumber("ד"));
        assertEquals(21, hebrewToNumber("כא"));
        assertEquals(1300, hebrewToNumber("תתתק"));
        assertEquals(87, hebrewToNumber("פז"));
    }
}
