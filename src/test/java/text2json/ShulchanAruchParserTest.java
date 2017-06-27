package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.ShulchanAruchParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;

/**
 * Created by USER on 02-Mar-17.
 */
public class ShulchanAruchParserTest {
    private static final int NUM_OF_SFARIM = 4;
    static SubjectsJson json[] = new SubjectsJson[NUM_OF_SFARIM + 1];
    static SubjectsJson packageJson[] = new SubjectsJson[NUM_OF_SFARIM + 1];

    @BeforeClass
    public static void setup() throws Exception {
        for (int seferNum = 1; seferNum <= NUM_OF_SFARIM; seferNum++) {
            System.out.println("Creating json for sefer number " + seferNum);
            ShulchanAruchParser parser = new ShulchanAruchParser();
            BufferedReader reader = getText("/shulchanaruch/shulchanaruch-" + seferNum + ".txt");
            parser.parse(reader, "../../jbs-text/shulchanaruch/shulchanaruch-" + seferNum + ".json");
            json[seferNum] = getJson("../../jbs-text/shulchanaruch/shulchanaruch-" + seferNum + ".json");
            packageJson[seferNum] = getJson("../../jbs-text/shulchanaruch/shulchanaruch-" + seferNum + "-packages.json");
        }
        System.out.println("");
    }

    @Test
    public void testNumOfPsukimPerSefer(){
        System.out.println("testNumOfPsukimPerSefer:");
        for(int seferNum = 1; seferNum <= NUM_OF_SFARIM; seferNum++){
            assertNotNull(json[seferNum]);
            assertEquals(NumOfPsukimPerSefer(seferNum) ,json[seferNum].subjects.size());
        }
        System.out.println("Success! :)");
    }

    @Test
    public void testNumOfObjectsPerSeferPackage(){
        System.out.println("testNumOfObjectsPerSeferPackage:");
        for(int seferNum = 1; seferNum <= NUM_OF_SFARIM; seferNum++){
            assertNotNull(packageJson[seferNum]);
            assertEquals(NumOfObjectsPerSeferPackage(seferNum) ,packageJson[seferNum].subjects.size());
        }
        System.out.println("Success! :)");
    }

    @Test
    public void testSpecificObjects(){
        System.out.print("testSpecificObjects: ");
        Map<String, String> object;
        String text_nikud;
        String text;

        //sefer 1 siman 1 saif 1
        object = json[1].getObject(0);
        assertEquals("jbr:shulchanaruch-1-1-1", object.get(URI));
        //assertEquals("jbr:shulchanaruch", object.get(JBO_SEFER));
        //assertEquals("shulchanaruch-1", object.get(JBO_CHELEK));
        //assertEquals("jbr:shulchanaruch-halachot-1-1", object.get(JBO_HALACHOT));
        //assertEquals("jbr:shulchanaruch-1-1", object.get(JBO_SIMAN));
        assertEquals("1", object.get(JBO_POSITION_IN_SIMAN));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("אורח חיים הלכות הנהגת אדם בבוקר א א", object.get(RDFS_LABEL));
        text_nikud = "יִתְגַּבֵּר (א) כַּאֲרִי לַעֲמֹד בַּבֹּקֶר לַעֲבוֹדַת בּוֹרְאוֹ שֶׁיְּהֵא הוּא מְעוֹרֵר (ב) הַשַּׁחַר: הגה: וְעַל כָּל פָּנִים לֹא יְאַחֵר זְמַן הַתְּפִלָּה שֶׁהַצִּבּוּר מִתְפַּלְּלִין. (טוּר) הגה: (ג) שִׁוִּיתִי ה' לְנֶגְדִּי תָמִיד הוּא כְּלָל גָּדוֹל בַּתּוֹרָה וּבְמַעֲלוֹת הַצַּדִּיקִים אֲשֶׁר הוֹלְכִים לִפְנֵי הָאֱלֹהִים, כִּי אֵין יְשִׁיבַת הָאָדָם וּתְנוּעוֹתָיו וַעֲסָקָיו וְהוּא לְבַדּוֹ בְּבֵיתוֹ כִּישִׁיבָתוֹ וּתְנוּעוֹתָיו וַעֲסָקָיו וְהוּא לִפְנֵי מֶלֶךְ גָּדוֹל, וְלֹא דִּבּוּרוֹ וְהַרְחָבַת פִּיו כִּרְצוֹנוֹ וְהוּא עִם אַנְשֵׁי בֵּיתוֹ וּקְרוֹבָיו כְּדִבּוּרוֹ בְּמוֹשַׁב הַמֶּלֶךְ, כָּל שֶׁכֵּן כְּשֶׁיָּשִׂים הָאָדָם אֶל לִבּוֹ שֶׁהַמֶּלֶךְ הַגָּדוֹל הָקָּבָּ''ה אֲשֶׁר מְלֹא כָל הָאָרֶץ כְּבוֹדוֹ עוֹמֵד עָלָיו וְרוֹאֶה בְּמַעֲשָׂיו, כְּמוֹ שֶׁנֶּאֱמַר: אִם יִסָּתֵר אִישׁ בַּמִּסְתָּרִים וַאֲנִי לֹא אֶרְאֶנּוּ נְאֻם ה', מִיָּד יַגִּיעַ אֵלָיו הַיִּרְאָה וְהַהַכְנָעָה וּפַחַד ה' יִתְבָּרַךְ וּבָשְׁתּוֹ מִמֶּנּוּ תָּמִיד (מוֹרֵה נְבוֹכִים ח''ג פ' כ''ב) וְלֹא יִתְבַּיֵּשׁ מִפְּנֵי בְּנֵי אָדָם (ד) הַמַּלְעִיגִים עָלָיו בַּעֲבוֹדַת ה' יִתְבָּרַךְ גַּם בְּהֶצְנֵעַ לֶכֶת. וּבְשָׁכְבּוֹ עַל מִשְׁכָּבוֹ יֵדַע לִפְנֵי מִי הוּא שׁוֹכֵב, וּמִיָּד כְּשֶׁיֵּעוֹר מִשְּׁנָתוֹ יָקוּם (ה) בִּזְרִיזוּת לַעֲבוֹדַת בּוֹרְאוֹ יִתְעַלֶּה וְיִתְרוֹמֵם (טוּר).";
        text = stripVowels(text_nikud);
        assertEquals(text_nikud, object.get(JBO_TEXT_NIKUD));
        assertEquals(text, object.get(JBO_TEXT));
        //perush
        object = json[1].getObject(1);
        assertEquals("jbr:shulchanaruch-baerheytev-1-1-1", object.get(URI));
        assertEquals("jbr:shulchanaruch-1-1-1", object.get(JBO_EXPLAINS));
        //assertEquals("jbr:shulchanaruch-baerheytev", object.get(JBO_SEFER));
        //assertEquals("shulchanaruch-1", object.get(JBO_CHELEK));
        //assertEquals("jbr:shulchanaruch-halachot-1-1", object.get(JBO_HALACHOT));
        //assertEquals("jbr:shulchanaruch-1-1", object.get(JBO_SIMAN));
        assertEquals("1", object.get(JBO_POSITION_IN_SIMAN));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("פירוש אורח חיים הלכות הנהגת אדם בבוקר א א", object.get(RDFS_LABEL));
        text = "(א) כארי. עיין בהלק''ט ח''ב ס''ג ובספר בני חייא: (ב) השחר. בשל''ה כתב סוד לחבר יום ולילה בתורה או בתפלה הן בבקר הן בערב. ומיד כשניעור משנתו יטול ידיו ובזה ינצל מהוצאת ש''ז. ומכ''ש אם אינו רוצה לישן אף שנשאר מושכב של''ה. כתב בספר תולעת יעקב איתא בזוהר ההולך שחרית ד' אמות ולא נטל ידיו חייב מיתה ע''כ. ותמה הב''ח על רבינו ב''י שלא כתבו. ונראה משום דרובא אין יכולין לזהר בכך ומוטב שיהיו שוגגין ולא יהיו מזידין מש''ה לא כתבו שיירי כנה''ג וכ''כ ד''א ועיין ביד אהרן סי' ד' בהגהת הטור: (ג) שויתי ה'. שיצייר שם הוי''ה תמיד נגד עיניו כזה יִהְוָה וזהו סוד שויתי ה' לנגדי וזה תועלת גדול לענין היראה האר''י ז''ל: (ד) המלעיגים. ועכ''פ לא יתקוטט עמהם. ב''י: (ה) בזריזות. ל''ד אלא ישהה מעט ולא יעמוד פתאום גיטין ד''ע. כתב בסדר היום ובקומו יאמר מודה אני לפניך מלך חי וקים שהחזרת בי נשמתי בחמלה רבה אמונתך וא''צ לזה נט''י אף שידיו מטונפות כי אין מזכיר השם ולא כינוי ומ''מ ללמוד נראה דאסור. אליהו זוטא. ואחר נטילת ידים יאמר שם של מ''ב אנא בכח:";
        assertEquals(text, object.get(JBO_TEXT));

        //sefer 1 siman 2 saif 3
        object = json[1].getObject(20);
        assertEquals("jbr:shulchanaruch-1-2-3", object.get(URI));
        //assertEquals("jbr:shulchanaruch", object.get(JBO_SEFER));
        //assertEquals("shulchanaruch-1", object.get(JBO_CHELEK));
        //assertEquals("jbr:shulchanaruch-halachot-1-1", object.get(JBO_HALACHOT));
        //assertEquals("jbr:shulchanaruch-1-2", object.get(JBO_SIMAN));
        assertEquals("3", object.get(JBO_POSITION_IN_SIMAN));
        assertEquals("12", object.get(JBO_POSITION));
        assertEquals("אורח חיים הלכות הנהגת אדם בבוקר ב ג", object.get(RDFS_LABEL));
        text_nikud = "יְדַקְדֵּק בַּחֲלוּקוֹ לְלָבְשׁוֹ כְּדַרְכּוֹ שֶׁלֹּא יַהֲפֹךְ הַפְּנִימִי (ב) לַחוּץ.";
        text = stripVowels(text_nikud);
        assertEquals(text_nikud, object.get(JBO_TEXT_NIKUD));
        assertEquals(text, object.get(JBO_TEXT));
        //perush
        object = json[1].getObject(21);
        assertEquals("jbr:shulchanaruch-baerheytev-1-2-3", object.get(URI));
        assertEquals("jbr:shulchanaruch-1-2-3", object.get(JBO_EXPLAINS));
        //assertEquals("jbr:shulchanaruch-baerheytev", object.get(JBO_SEFER));
        //assertEquals("shulchanaruch-1", object.get(JBO_CHELEK));
        //assertEquals("jbr:shulchanaruch-halachot-1-1", object.get(JBO_HALACHOT));
        //assertEquals("jbr:shulchanaruch-1-2", object.get(JBO_SIMAN));
        assertEquals("3", object.get(JBO_POSITION_IN_SIMAN));
        assertEquals("12", object.get(JBO_POSITION));
        assertEquals("פירוש אורח חיים הלכות הנהגת אדם בבוקר ב ג", object.get(RDFS_LABEL));
        text = "(ב) לחוץ. ויראו התפירות המגונות ויתגנה בעיני הבריות וכתב הט''ז שיש חילוק בין ת''ח לשאר אנשים דלת''ח אם לבש החלוק הפוכה צריך להפכה אבל שאר אינשי דעלמא יזהר לכתחלה שלא ללבשה הפוכה ע''ש. ויזהר מללבוש שני מלבושים יחד בפעם אחד כי קשה לשכחה. וישים ב' צידי המלבוש ביד ימינו וילבוש הימין ואח''כ השמאל ויכוין כי הכל נכלל בימין ומן הימין בא לשמאל. כתבים:";
        assertEquals(text, object.get(JBO_TEXT));

        //sefer 1 siman 697 saif 1
        object = json[1].getObject(json[1].subjects.size()-1);
        assertEquals("jbr:shulchanaruch-1-697-1", object.get(URI));
        //assertEquals("jbr:shulchanaruch", object.get(JBO_SEFER));
        //assertEquals("shulchanaruch-1", object.get(JBO_CHELEK));
        //assertEquals("jbr:shulchanaruch-halachot-1-39", object.get(JBO_HALACHOT));
        //assertEquals("jbr:shulchanaruch-1-697", object.get(JBO_SIMAN));
        assertEquals("1", object.get(JBO_POSITION_IN_SIMAN));
        assertEquals("4167", object.get(JBO_POSITION));
        assertEquals("אורח חיים הלכות מגילה ופורים תרצז א", object.get(RDFS_LABEL));
        text_nikud = "יוֹם י''ד וְט''ו שֶׁבַּאֲדָר רִאשׁוֹן אֵין נוֹפְלִים עַל פְּנֵיהֶם, וְאֵין אוֹמְרִים מִזְמוֹר יַעַנְךָ ה' בְּיוֹם צָרָה, וַאֲסוּרִים בְּהֶסְפֵּד (א) וְתַעֲנִית; אֲבָל שְׁאָר דְּבָרִים אֵין נוֹהֲגִים בָּהֶם; וְיֵשׁ אוֹמְרִים דְּאַף בְּהֶסְפֵּד וְתַעֲנִית מֻתָּרִים. הגה: וְהַמִּנְהָג כַּסְּבָרָא הָרִאשׁוֹנָה. יֵשׁ אוֹמְרִים שֶׁחַיָּב לְהַרְבּוֹת בְּמִשְׁתֶּה וְשִׂמְחָה (ב) בְּ''ד שֶׁבַּאֲדָר רִאשׁוֹן (טוּר בְּשֵׁם הָרִי''ף) וְאֵין נוֹהֲגִין כֵּן, מִכָּל מָקוֹם יַרְבֶּה קְצָת בִּסְעֻדָּה כְּדֵי לָצֵאת יְדֵי הַמַּחְמִירִים; (הַגָּהוֹת מַיְמוֹנִי בְּשֵׁם סְמַ''ק) וְטוֹב לֵב מִשְׁתֶּה (ג) תָּמִיד (מִשְׁלֵי ט''ו, ט''ו).";
        text = stripVowels(text_nikud);
        assertEquals(text_nikud, object.get(JBO_TEXT_NIKUD));
        assertEquals(text, object.get(JBO_TEXT));

        //sefer 2 siman 29 saif 1
        object = json[2].getObject(324);
        assertEquals("jbr:shulchanaruch-2-29-1", object.get(URI));
        //assertEquals("jbr:shulchanaruch", object.get(JBO_SEFER));
        //assertEquals("shulchanaruch-2", object.get(JBO_CHELEK));
        //assertEquals("jbr:shulchanaruch-halachot-2-2", object.get(JBO_HALACHOT));
        //assertEquals("jbr:shulchanaruch-2-29", object.get(JBO_SIMAN));
        assertEquals("1", object.get(JBO_POSITION_IN_SIMAN));
        assertEquals("177", object.get(JBO_POSITION));
        assertEquals("יורה דעה הלכות טרפות כט א", object.get(RDFS_LABEL));
        text_nikud = "שְׁמוֹנָה מִינֵי טְרֵפוֹת הֵן, וְסִימָנָם: דָּ''ן חָנַ''ק נֶפֶ''שׁ; (א) דְּרוּסָה, (ב) נְקוּבָה, (ג) חֲסֵרָה, (ד) נְטוּלָה, (ה) קְרוּעָה, (ו) נְפוּלָה, (ז) (ח) פְּסוּקָה, שְׁבוּרָה. (סִימָן זֶה הוּא בִּסְמַ''ג דַּף קמ''א ע''ב וּבֵית יוֹסֵף לֹא הֱבִיאוֹ) .";
        text = stripVowels(text_nikud);
        assertEquals(text_nikud, object.get(JBO_TEXT_NIKUD));
        assertEquals(text, object.get(JBO_TEXT));
        //perush
        object = json[2].getObject(325);
        assertEquals("jbr:shulchanaruch-baerheytev-2-29-1", object.get(URI));
        assertEquals("jbr:shulchanaruch-2-29-1", object.get(JBO_EXPLAINS));
        //assertEquals("jbr:shulchanaruch-baerheytev", object.get(JBO_SEFER));
        //assertEquals("shulchanaruch-2", object.get(JBO_CHELEK));
        //assertEquals("jbr:shulchanaruch-halachot-2-2", object.get(JBO_HALACHOT));
        //assertEquals("jbr:shulchanaruch-2-29", object.get(JBO_SIMAN));
        assertEquals("1", object.get(JBO_POSITION_IN_SIMAN));
        assertEquals("177", object.get(JBO_POSITION));
        assertEquals("פירוש יורה דעה הלכות טרפות כט א", object.get(RDFS_LABEL));
        text = "(א) דרוסה. פי' שדרסה ארי וכ''ה בסי' נ''ז: (ב) נקובה. פי' נקיבת קרום של מוח בסי' שאחר זה ותורבץ הוושט בסי' ל''ג ונקיבת הקנה בסימן ל''ד ונקיבת הריאה בסי' ל''ו ונקיבת המרה בסי' מ''ב ונקיבת הלב בסי' מ' ונקיבת הטחול בסי' מ''ג ונקיבת האם בסי' מ''ה ונקיבת הדקין בסי' מ''ו ונקיבת קיבה וכרס והמסס ובית הכוסות בסי' מ''ח ונקיבת הקורקבן בסימן מ''ט: (ג) חסרה. היינו בתחלת ברייתה והיינו חסרו האונות בסימן ל''ה: (ד) נטולה. היינו ניטל לחי התחתון והעליון בסימן ל''ג ונטולת הכבד בסימן מ''א וניטלת צומת הגידין בסי' נ''ו ובכלל נטולה הוא ג''כ גלודה שבסימן נ''ט: (ה) קרועה. פי' שנקרע רוב בשר החופה את הכרס בסימן מ''ח: (ו) נפולה. היינו נפולה ממש בסי' נ''ח: (ז) פסוקה. פי' פסוקת חוט השדרה בסי' ל''ב ופסוקת הגרגרת בסי' ל''ד: (ח) שבורה. פי' נשברה רוב צלעותיה שבסימן נ''ד וחביסת הגלגולת שבסי' שאחר זה ועקירת צלע בסי' נ''ד וכל הטריפות נכללין באלו השמונה ש''ך וכ' הרמב''ם אין לך בהדיא אלא דרוסה לכך החמירו בה וכל ספק שיסתפק בדרוסה אסור. ושאר מיני טריפות יש בהן ספיקן מותרין ותמה ב''י שלא מצא שום ספק טריפה שיהיה מותר ואני תמה דלפ''ז לא מקשה מידי גמרא דחולין דף מ''ג ע''ב ולעולא מ''ש ספק דרוסה ודו''ק וע''ש בתוס' וברא''ש. ועיין בפר''ח ובט''ז ועיין בב''ח שהקשה ג''כ בזה (מהרמ''ט):";
        assertEquals(text, object.get(JBO_TEXT));

        //sefer 2 siman 403 saif 9
        object = json[2].getObject(json[2].subjects.size()-4);
        assertEquals("jbr:shulchanaruch-2-403-9", object.get(URI));
        //assertEquals("jbr:shulchanaruch", object.get(JBO_SEFER));
        //assertEquals("shulchanaruch-2", object.get(JBO_CHELEK));
        //assertEquals("jbr:shulchanaruch-halachot-2-60", object.get(JBO_HALACHOT));
        //assertEquals("jbr:shulchanaruch-2-403", object.get(JBO_SIMAN));
        assertEquals("9", object.get(JBO_POSITION_IN_SIMAN));
        assertEquals("3493", object.get(JBO_POSITION));
        assertEquals("יורה דעה הלכות אבילות תג ט", object.get(RDFS_LABEL));
        text_nikud = "הַמְלַקֵט עֲצָמוֹת, וְהַמְשַׁמֵּר אֶת הַמֵּת, וְהַמּוֹלִיךְ אוֹתָם מִמָּקוֹם לְמָקוֹם, פָּטוּר מִקְרִיאַת שְׁמַע וּמִתְּפִלָה וּמֵהַתְּפִלִין, וּמִכָּל מִצְוֹת הָאֲמוּרוֹת בַּתּוֹרָה, בֵּין בְּחֹל בֵּין בְּשַׁבָּת, לֹא שְׁנָא עַצְמוֹת קְרוֹבִים לֹא שְׁנָא עַצְמוֹת רְחוֹקִים, בֵּין אִם הוּא בִּסְפִינָה אוֹ בַּדֶּרֶךְ, וַאֲפִלוּ אִם הֵם מְלַקְטִים (ג) רַבִּים. וְאִם רָצָה לְהַחְמִיר עַל עַצְמוֹ, לֹא יַחְמִיר, מִפְּנֵי כְּבוֹד עֲצָמוֹת.";
        text = stripVowels(text_nikud);
        assertEquals(text_nikud, object.get(JBO_TEXT_NIKUD));
        assertEquals(text, object.get(JBO_TEXT));
        //perush
        object = json[2].getObject(json[2].subjects.size()-3);
        assertEquals("jbr:shulchanaruch-baerheytev-2-403-9", object.get(URI));
        assertEquals("jbr:shulchanaruch-2-403-9", object.get(JBO_EXPLAINS));
        //assertEquals("jbr:shulchanaruch-baerheytev", object.get(JBO_SEFER));
        //assertEquals("shulchanaruch-2", object.get(JBO_CHELEK));
        //assertEquals("jbr:shulchanaruch-halachot-2-60", object.get(JBO_HALACHOT));
        //assertEquals("jbr:shulchanaruch-2-403", object.get(JBO_SIMAN));
        assertEquals("9", object.get(JBO_POSITION_IN_SIMAN));
        assertEquals("3493", object.get(JBO_POSITION));
        assertEquals("פירוש יורה דעה הלכות אבילות תג ט", object.get(RDFS_LABEL));
        text = "(ג) רבים. כתב הש''ך ודוקא מלקטים רבים אבל משמרים רבים זה משמר וזה קורא וכמ''ש בא''ח סי' ע''א ס''ד (ודין מקום הקבר שנלקטו ממנו העצמות אם מותר לקבור בו מתים אחרים ע''ל סי' שס''ג ושס''ד. וע' בתשו' ש''י שאלה פ''ז):";
        assertEquals(text, object.get(JBO_TEXT));

        //sefer 3 siman 1 saif 6
        object = json[3].getObject(9);
        assertEquals("jbr:shulchanaruch-3-1-6", object.get(URI));
        //assertEquals("jbr:shulchanaruch", object.get(JBO_SEFER));
        //assertEquals("shulchanaruch-3", object.get(JBO_CHELEK));
        //assertEquals("jbr:shulchanaruch-halachot-3-1", object.get(JBO_HALACHOT));
        //assertEquals("jbr:shulchanaruch-3-1", object.get(JBO_SIMAN));
        assertEquals("6", object.get(JBO_POSITION_IN_SIMAN));
        assertEquals("6", object.get(JBO_POSITION));
        assertEquals("אבן העזר הלכות פריה ורביה א ו", object.get(RDFS_LABEL));
        text_nikud = "נוֹלְדוּ לוֹ זָכָר (ט) וּנְקֵבָה, וּמֵתוּ וְהִנִּיחוּ בָּנִים, הֲרֵי זֶה קִיֵּם מִצְוַת פְּרִיָּה וּרְבִיָּה. בַּמֶּה דְּבָרִים אֲמוּרִים, כְּשֶׁהָיוּ בְּנֵי הַבָּנִים זָכָר וּנְקֵבָה, וְהָיוּ בָּאִים מִזָּכָר וּנְקֵבָה, אַף עַל פִּי שֶׁהַזָּכָר בֶּן בִּתּוֹ וְהַנְּקֵבָה בַּת בְּנוֹ, הוֹאִיל וּמִשְּׁנֵי בָּנָיו הֵם בָּאִים הֲרֵי קִיֵּם מִצְוַת פְּרִיָּה וּרְבִיָּה. אֲבָל אִם הָיוּ לוֹ בֵּן וּבַת, וּמֵתוּ, וְהִנִּיחַ אֶחָד מֵהֶם זָכָר וּנְקֵבָה, עֲדַיִן לֹא קִיֵּם מִצְוָה זוֹ. הגה: הָיָה הַבֵּן (י) מַמְזֵר, אוֹ חֵרֵשׁ שׁוֹטֶה (וְקָטָן), קִיֵּם הַמִּצְוָה. (ב''י בְּשֵׁם הָרַשְׁבָּ''א):";
        text = stripVowels(text_nikud);
        assertEquals(text_nikud, object.get(JBO_TEXT_NIKUD));
        assertEquals(text, object.get(JBO_TEXT));
        //perush
        object = json[3].getObject(10);
        assertEquals("jbr:shulchanaruch-baerheytev-3-1-6", object.get(URI));
        assertEquals("jbr:shulchanaruch-3-1-6", object.get(JBO_EXPLAINS));
        //assertEquals("jbr:shulchanaruch-baerheytev", object.get(JBO_SEFER));
        //assertEquals("shulchanaruch-3", object.get(JBO_CHELEK));
        //assertEquals("jbr:shulchanaruch-halachot-3-1", object.get(JBO_HALACHOT));
        //assertEquals("jbr:shulchanaruch-3-1", object.get(JBO_SIMAN));
        assertEquals("6", object.get(JBO_POSITION_IN_SIMAN));
        assertEquals("6", object.get(JBO_POSITION));
        assertEquals("פירוש אבן העזר הלכות פריה ורביה א ו", object.get(RDFS_LABEL));
        text = "(ט) ונקיבה. משמע שני זכרים ומכ''ש שתי נקבות לא מהני. אבל תוס' ביבמות דף ס''ב ע''ב ס''ל דבשני זכרים הבאים מבן ובת קיים פו''ר. ובש''ג הוסיף אפילו בשתי בנות הבאים מבן ובת קיים פו''ר. ואם היה לו ב' זכרים והם הניחו זכר ונקיבה. או שהיה לו ב' זכרים וא' מהן היה לו בת לא קיים פו''ר שצריך הוא שיוליד תחלה זכר ונקיבה ואם הבן קיים והבת מתה והניחה בת קיים פו''ר ואם הבן קיים ולא הוליד עדיין והבת מתה והניחה בן לא קיים פו''ר. יש להסתפק אשה שנתעברה באמבטי אם האב קיים פו''ר ואם נקרא בנו לכל דבר ח''מ. וב''ש הביא ראיה דהוי בנו לכל דבר: (י) ממזר. דוקא ממזר ידוע חי. אבל ממזר אינו ידוע אז אינו חי לא קיים פ''ו:";
        assertEquals(text, object.get(JBO_TEXT));

        //sefer 3 siman 177 saif 4
        object = json[3].getObject(json[3].subjects.size()-35);
        assertEquals("jbr:shulchanaruch-3-177-4", object.get(URI));
        //assertEquals("jbr:shulchanaruch", object.get(JBO_SEFER));
        //assertEquals("shulchanaruch-3", object.get(JBO_CHELEK));
        //assertEquals("jbr:shulchanaruch-halachot-3-8", object.get(JBO_HALACHOT));
        //assertEquals("jbr:shulchanaruch-3-177", object.get(JBO_SIMAN));
        assertEquals("4", object.get(JBO_POSITION_IN_SIMAN));
        assertEquals("1875", object.get(JBO_POSITION));
        assertEquals("אבן העזר הלכות חליצה קעז ד", object.get(RDFS_LABEL));
        text_nikud = "הָיְתָה אֲסוּרָה לוֹ, אֲפִלּוּ אִסוּר (ד) דְּרַבָּנָן, אֵינוֹ רַשַּׁאי לִשָּׂא אוֹתָהּ. וְכֵן אִם נְשָׂאָהּ וּמָצָא בָּהּ עֶרְוַת דָּבָר, יְגָרְשֶׁנָּה. הגה: יָצָא עָלֶיהָ שֵׁם רַע, כְּגוֹן שֶׁבָּאוּ שְׁנַיִם וְהֵעִידוּ עָלֶיהָ שֶׁתְּבָעָתוֹ לִזְנוּת, אֵין לָהּ קְנָס. אֲבָל מִשּׁוּם קוֹל בְּעָלְמָא עַל שֵׁם רַע שֶׁיָּצָא עָלֶיהָ, לֹא הִפְסִידָה הַקְּנָס (טוּר). בָּא עָלֶיהָ בְּאֹנֶס וְאַחַר כָּךְ פִּתָּה אוֹתָהּ, מִקְּרֵי מְאָנֵס, אַף עַל גַּב דְּאַחַר כָּךְ נִתְרַצֵּית לוֹ (מַהֲרִי''ק שֹׁרֶשׁ קכ''ט):";
        text = stripVowels(text_nikud);
        assertEquals(text_nikud, object.get(JBO_TEXT_NIKUD));
        assertEquals(text, object.get(JBO_TEXT));
        //perush
        object = json[3].getObject(json[3].subjects.size()-34);
        assertEquals("jbr:shulchanaruch-baerheytev-3-177-4", object.get(URI));
        assertEquals("jbr:shulchanaruch-3-177-4", object.get(JBO_EXPLAINS));
        //assertEquals("jbr:shulchanaruch-baerheytev", object.get(JBO_SEFER));
        //assertEquals("shulchanaruch-3", object.get(JBO_CHELEK));
        //assertEquals("jbr:shulchanaruch-halachot-3-8", object.get(JBO_HALACHOT));
        //assertEquals("jbr:shulchanaruch-3-177", object.get(JBO_SIMAN));
        assertEquals("4", object.get(JBO_POSITION_IN_SIMAN));
        assertEquals("1875", object.get(JBO_POSITION));
        assertEquals("פירוש אבן העזר הלכות חליצה קעז ד", object.get(RDFS_LABEL));
        text = "(ד) דרבנן. הרמב''ם פ''א מהלכות נערה בתולה דין ה'. ועיין מ''ש הכ''מ שם. לכאורה לא מקשה לפ''ז מידי הש''ס בכתובות דף ל''ז ע''א (שנה) [שניות] מד''ס כיון מדאוריי' חזיה ליה וכו' ע''ש. ולפי דברי הכ''מ ל''ק מידי דהוי כדאורייתא. ומצאתי בתשובת שבות יעקב ח''א סימן קל''א שמתרץ אף על פי דגזירה דרבנן הוי כדאורייתא מ''מ לענין קנס לא הוי כדאורייתא כדי שלא יהא חוטא נשכר ע''ש:";
        assertEquals(text, object.get(JBO_TEXT));

        //sefer 4 siman 1 saif 6
        object = json[4].getObject(10);
        assertEquals("jbr:shulchanaruch-4-1-6", object.get(URI));
        //assertEquals("jbr:shulchanaruch", object.get(JBO_SEFER));
        //assertEquals("shulchanaruch-4", object.get(JBO_CHELEK));
        //assertEquals("jbr:shulchanaruch-halachot-4-1", object.get(JBO_HALACHOT));
        //assertEquals("jbr:shulchanaruch-4-1", object.get(JBO_SIMAN));
        assertEquals("6", object.get(JBO_POSITION_IN_SIMAN));
        assertEquals("6", object.get(JBO_POSITION));
        assertEquals("חשן משפט הלכות דיינים א ו", object.get(RDFS_LABEL));
        text_nikud = "הַמְבַיֵּשׁ (כב) בִּדְבָרִים, מְנַדִּין אוֹתוֹ עַד שֶׁיְּפַיְסֶנּוּ כָּרָאוּי, לְפִי (כג) כְבוֹדוֹ. הגה: וְעַיֵן לְקַמָּן סִימָן ת''כ סָעִיף ל''ח וְעַיֵן לְקַמָּן סִימָן ב' אִם נִתְחַיֵּב לוֹ מַלְקוֹת, אִם יָכוֹל לִפְדּוֹת עַצְמוֹ בְּמָמוֹן:";
        text = stripVowels(text_nikud);
        assertEquals(text_nikud, object.get(JBO_TEXT_NIKUD));
        assertEquals(text, object.get(JBO_TEXT));
        //perush
        object = json[4].getObject(11);
        assertEquals("jbr:shulchanaruch-baerheytev-4-1-6", object.get(URI));
        assertEquals("jbr:shulchanaruch-4-1-6", object.get(JBO_EXPLAINS));
        //assertEquals("jbr:shulchanaruch-baerheytev", object.get(JBO_SEFER));
        //assertEquals("shulchanaruch-4", object.get(JBO_CHELEK));
        //assertEquals("jbr:shulchanaruch-halachot-4-1", object.get(JBO_HALACHOT));
        //assertEquals("jbr:shulchanaruch-4-1", object.get(JBO_SIMAN));
        assertEquals("6", object.get(JBO_POSITION_IN_SIMAN));
        assertEquals("6", object.get(JBO_POSITION));
        assertEquals("פירוש חשן משפט הלכות דיינים א ו", object.get(RDFS_LABEL));
        text = "(כב) בדברים. זהו מתקנת הגאונים אבל מדינא פטור בבושת דברים ואפי' דיינים סמוכים אין גובין אותו. סמ''ע: (כג) כבודו. בירושלמי הביאו הטור דמבייש את הזקן נותן ליטרא דדהבא וי''מ שהוא ל''ו זהובים עכ''ל סמ''ע וכ' הש''ך דבי''ד סי' רמ''ג ס''ב פסק הרמ''א שאין לנו בזה''ז דין ת''ח לענין ליטרא דדהבא אבל בתשובת ר''י מטראני החדשים ס''ס מ''ז חולק ע''ז ע''ש:";
        assertEquals(text, object.get(JBO_TEXT));

        //sefer 4 siman 425 saif 3
        object = json[4].getObject(json[4].subjects.size()-23);
        assertEquals("jbr:shulchanaruch-4-425-3", object.get(URI));
        //assertEquals("jbr:shulchanaruch", object.get(JBO_SEFER));
        //assertEquals("shulchanaruch-4", object.get(JBO_CHELEK));
        //assertEquals("jbr:shulchanaruch-halachot-4-41", object.get(JBO_HALACHOT));
        //assertEquals("jbr:shulchanaruch-4-425", object.get(JBO_SIMAN));
        assertEquals("3", object.get(JBO_POSITION_IN_SIMAN));
        assertEquals("3690", object.get(JBO_POSITION));
        assertEquals("חשן משפט הלכות חובל בחבירו תכה ג", object.get(RDFS_LABEL));
        text_nikud = "וְכֵן הָרוֹדֵף אַחַר הַזָּכָר אוֹ אַחַר אַחַת מִכָּל הָעֲרָיוֹת לְאָנְסָהּ, חוּץ (ד) מֵהַבְּהֵמָה, מַצִּילִין אוֹתוֹ אֲפִלּוּ בְּנֶפֶשׁ הָרוֹדֵף. וְאִם רָדַף אַחַר עֶרְוָה וְתָפַס וְשָׁכַב עִמָּהּ, כֵּיוָן שֶׁהֶעֱרָה בָּהּ, אַף עַל פִּי שֶׁלֹּא גָמַר בִּיאָתוֹ, אֵין מְמִיתִין אוֹתוֹ עַד עָמְדוֹ בַּדִּין.";
        text = stripVowels(text_nikud);
        assertEquals(text_nikud, object.get(JBO_TEXT_NIKUD));
        assertEquals(text, object.get(JBO_TEXT));
        //perush
        object = json[4].getObject(json[4].subjects.size()-22);
        assertEquals("jbr:shulchanaruch-baerheytev-4-425-3", object.get(URI));
        assertEquals("jbr:shulchanaruch-4-425-3", object.get(JBO_EXPLAINS));
        //assertEquals("jbr:shulchanaruch-baerheytev", object.get(JBO_SEFER));
        //assertEquals("shulchanaruch-4", object.get(JBO_CHELEK));
        //assertEquals("jbr:shulchanaruch-halachot-4-41", object.get(JBO_HALACHOT));
        //assertEquals("jbr:shulchanaruch-4-425", object.get(JBO_SIMAN));
        assertEquals("3", object.get(JBO_POSITION_IN_SIMAN));
        assertEquals("3690", object.get(JBO_POSITION));
        assertEquals("פירוש חשן משפט הלכות חובל בחבירו תכה ג", object.get(RDFS_LABEL));
        text = "(ד) מהבהמה. דאין ניתן להצילו בנפשו של רודף אלא ערוה שיש בה קלון ופגם לנרדף כן הוא בש''ס ס''פ בן סורר. שם:";
        assertEquals(text, object.get(JBO_TEXT));
    }

    @Test
    public void testSpecificPackages(){
        System.out.print("testSpecificPackages: ");
        Map<String, String> object;
        //shulchan aruch
        object = packageJson[1].getObject(0);
        assertEquals("jbr:shulchanaruch",object.get(URI));
        assertEquals("שלחן ערוך",object.get(RDFS_LABEL));

        //sefer 1
        object = packageJson[1].getObject(2);
        assertEquals("jbr:shulchanaruch-1",object.get(URI));
        assertEquals("שלחן ערוך - אורח חיים",object.get(RDFS_LABEL));
        assertEquals("1", object.get(JBO_POSITION));
        //sefer 1 halachot 39
        object = packageJson[1].getObject(packageJson[1].subjects.size()-13);
        assertEquals("jbr:shulchanaruch-halachot-1-39",object.get(URI));
        //assertEquals("shulchanaruch-1",object.get(JBO_CHELEK));
        assertEquals("הלכות מגילה ופורים",object.get(RDFS_LABEL));
        assertEquals("39", object.get(JBO_POSITION));
        //sefer 1 halachot 7 siman 130
        object = packageJson[1].getObject(139);
        assertEquals("jbr:shulchanaruch-1-130",object.get(URI));
        //assertEquals("shulchanaruch-1",object.get(JBO_CHELEK));
        assertEquals("רבונו של עולם שאומרים בשעת נשיאת כפים",object.get(RDFS_LABEL));
        assertEquals("130", object.get(JBO_POSITION));

        //sefer 2
        object = packageJson[2].getObject(2);
        assertEquals("jbr:shulchanaruch-2",object.get(URI));
        assertEquals("שלחן ערוך - יורה דעה",object.get(RDFS_LABEL));
        assertEquals("2", object.get(JBO_POSITION));
        //sefer 2 halachot 8
        object = packageJson[2].getObject(78);
        assertEquals("jbr:shulchanaruch-halachot-2-8",object.get(URI));
        //assertEquals("shulchanaruch-2",object.get(JBO_CHELEK));
        assertEquals("הלכות מליחה",object.get(RDFS_LABEL));
        assertEquals("8", object.get(JBO_POSITION));
        //sefer 2 halachot 16 siman 98
        object = packageJson[2].getObject(116);
        assertEquals("jbr:shulchanaruch-2-98",object.get(URI));
        //assertEquals("shulchanaruch-2",object.get(JBO_CHELEK));
        assertEquals("דין אסור שנתערב בהתר ואפן בטולו",object.get(RDFS_LABEL));
        assertEquals("98", object.get(JBO_POSITION));

        //sefer 3
        object = packageJson[3].getObject(2);
        assertEquals("jbr:shulchanaruch-3",object.get(URI));
        assertEquals("שלחן ערוך - אבן העזר",object.get(RDFS_LABEL));
        assertEquals("3", object.get(JBO_POSITION));
        //sefer 3 halachot 6
        object = packageJson[3].getObject(packageJson[3].subjects.size()-27);
        assertEquals("jbr:shulchanaruch-halachot-3-6",object.get(URI));
        //assertEquals("shulchanaruch-3",object.get(JBO_CHELEK));
        assertEquals("הלכות מאון",object.get(RDFS_LABEL));
        assertEquals("6", object.get(JBO_POSITION));
        //sefer 3 halachot 3 siman 26
        object = packageJson[3].getObject(31);
        assertEquals("jbr:shulchanaruch-3-26",object.get(URI));
        //assertEquals("shulchanaruch-3",object.get(JBO_CHELEK));
        assertEquals("שלא לקדש בביאה, ואסור ביאה על הפנויה אפלו אם יחדה",object.get(RDFS_LABEL));
        assertEquals("26", object.get(JBO_POSITION));

        //sefer 4
        object = packageJson[4].getObject(2);
        assertEquals("jbr:shulchanaruch-4",object.get(URI));
        assertEquals("שלחן ערוך - חשן משפט",object.get(RDFS_LABEL));
        assertEquals("4", object.get(JBO_POSITION));
        //sefer 4 halachot 2
        object = packageJson[4].getObject(31);
        assertEquals("jbr:shulchanaruch-halachot-4-2",object.get(URI));
        //assertEquals("shulchanaruch-4",object.get(JBO_CHELEK));
        assertEquals("הלכות עדות",object.get(RDFS_LABEL));
        assertEquals("2", object.get(JBO_POSITION));
        //sefer 4 halachot 41 siman 421
        object = packageJson[4].getObject(packageJson[4].subjects.size()-8);
        assertEquals("jbr:shulchanaruch-4-421",object.get(URI));
        //assertEquals("shulchanaruch-4",object.get(JBO_CHELEK));
        assertEquals("המביש חברו שלא בכונה, והחובל בחברו שלא בכונה",object.get(RDFS_LABEL));
        assertEquals("421", object.get(JBO_POSITION));

    }



    private int NumOfPsukimPerSefer(int seferNum) {
        int numOfPsukimPerSefer[] = {-1, 4802, 5978, 2959, 6530};
        return numOfPsukimPerSefer[seferNum];
    }

    private int NumOfObjectsPerSeferPackage(int seferNum) {
        int numOfHalachotPerSefer[] = {-1, 39, 60, 8, 42};
        int numOfSimanimPerSefer[] = {-1, 697, 403, 178, 427};
        return 3 + numOfHalachotPerSefer[seferNum] + numOfSimanimPerSefer[seferNum];
    }


}
