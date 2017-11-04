package text2json;

import org.junit.*;

import java.io.IOException;

import static text2json.JbsUtils.HEB_LETTERS_INDEX;
import static text2json.JbsUtils.numberToHebrew;
import static org.junit.Assert.*;
/**
 * Created by omishali on 25/10/2017.
 */
public class JbsParserTest {
    private JbsParser parser;

    @Before
    public void before() {
        parser = new JbsParser() {
            @Override
            protected void registerMatchers() {

            }

            @Override
            protected void onLineMatch(String type, Line line) throws IOException {

            }

            @Override
            protected String getUri() {
                return null;
            }
        };
    }
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

//        1 = א
//        2 = ב
//        3 = ג
//        4 = ד
//        5 = ה
//        6 = ו
//        7 = ז
//        8 = ח
//        9 = ט
//        10 = י
//        20 = כ
//        30 = ל
//        40 = מ
//        50 = נ
//        60 = ס
//        70 = ע
//        80 = פ
//        90 = צ
//        100 = ק
//        200 = ר
//        300 = ש
//        400 = ת
//        500 = תק
//        600 = תר
//        700 = תש
//        800 = תת
//        900 = תתק
//        1000 = תתר
    }

    @Test
    public void testHebrewLettersArray(){
//        for (int i =0; i<= 1000; i++)
//            System.out.println(HEB_LETTERS_INDEX[i]+" ");
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
}
