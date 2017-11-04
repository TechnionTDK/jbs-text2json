package text2json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omishali on 12/02/2017.
 */
public class JbsUtils {
    public static final String[] MEFARSHIM_HE = {"רש\"י", "הרמב\"ן", "אור החיים", "אבן עזרא", "בעל הטורים" , "אונקלוס", "ספורנו", "כלי יקר",
            "דעת זקנים", "מצודת דוד", "מצודת ציון", "מלבי\"ם - באור הענין", "מלבי\"ם - באור המלות", "רלב\"ג", "מלבי\"ם",
            "יונתן", "שפתי חכמים"};
    public static final String[] SEFARIM_TANACH_HE = {"בראשית","שמות","ויקרא","במדבר","דברים","יהושע","שופטים","שמואל א","שמואל ב","מלכים א",
            "מלכים ב","ישעיה","ירמיה","יחזקאל","הושע","יואל","עמוס","עובדיה","יונה","מיכה","נחום","חבקוק","צפניה","חגי","זכריה","מלאכי",
            "תהילים","משלי","איוב","שיר השירים","רות","איכה","קהלת","אסתר","דניאל","עזרא","נחמיה","דברי הימים א","דברי הימים ב"};
    public static final String[] SEFARIM_TANACH_EN = {"bereshit","shemot","vayikra","bamidbar","devarim","yehoshua","shoftim","shemuelA","shemuelB","melachimA",
            "melachimB","yeshaaya","yirmiya","yechezkel","hoshea","yoel","amos","ovadya","yona","micha","nachum","chavakuk","tzefanya","chagay","zecharya","malachi",
            "tehilim","mishley","iyov","shirhashirim","rut","eycha","kohelet","ester","daniel","ezra","nechemya","divreyhayamimA","divreyhayamimB"};
    public static final String[] PARASHOT_HE = {"בראשית","נח","לך לך","וירא","חיי שרה","תולדות","ויצא","וישלח","וישב","מקץ","לחנוכה","ויגש","ויחי","שמות","וארא","בא","בשלח","יתרו","משפטים","פרשת שקלים","תרומה","תצוה","פרשת זכור","לפורים","כי תשא","פרשת פרה","ויקהל","פקודי","ויקרא","פרשת החודש","צו","לשבת הגדול","פסח","שמיני","תזריע","מצורע","אחרי מות","קדושים","אמור","בהר","בחוקתי","במדבר","שבועות","נשא","בהעלותך","שלח","קרח","חקת","בלק","פנחס","מטות","מסעי","דברים","ואתחנן","עקב","ראה","שופטים","כי תצא","כי תבוא","נצבים","וילך","לחודש אלול","ראש השנה","שבת תשובה","ליום כיפור","האזינו","לסוכות","וזאת הברכה"};

    private static List<String> HEB_LETTERS_LIST = new ArrayList<>();

    static {
        for (int i =1; i<= 1000; i++)
            HEB_LETTERS_LIST.add(numberToHebrew(i));
    }
    public static final String[] HEB_LETTERS_INDEX = HEB_LETTERS_LIST.toArray(new String[HEB_LETTERS_LIST.size()]);


    public static String numberToHebrew(int num) {
        String[] tenLetters = {"א", "ב", "ג", "ד", "ה", "ו", "ז", "ח", "ט"};
        String[] overTenLetters = {"י", "כ", "ל", "מ", "נ", "ס", "ע", "פ", "צ"};
        String[] overHundretLetters = {"ק", "ר", "ש", "ת", "תק", "תר", "תש", "תת", "תתק"};
        String answer = "";
        while (num > 0) {
            if (num>2000){
                answer = numberToHebrew(num/1000) + answer ;
                num = num - (num / 1000) * 1000;
            }

            if (num >= 1000 && num<2000) {
                answer = "תתר" + answer ;
                num = num - (num / 1000) * 1000;
                continue;
            }
            if (num >= 100) {
                answer = answer + overHundretLetters[num / 100 - 1];
                num = num - (num / 100) * 100;
                continue;
            }

            if (num >= 10) {
                if (num == 15){
                    answer = answer + "טו";
                    num =0;
                    continue;
                }

                if (num == 16){
                    answer = answer + "טז";
                    num =0;
                    continue;
                }

                answer = answer + overTenLetters[num / 10 - 1];
                num = num - (num / 10) * 10;
                continue;
            }

            if (num < 10) {
                answer = answer + tenLetters[num - 1];
                num = 0;
                continue;
            }

        }
        return answer;
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

}
