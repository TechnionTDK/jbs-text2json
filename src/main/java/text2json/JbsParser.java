package text2json;

import static text2json.JbsOntology.*;


/**
 * Created by omishali on 15/08/2017.
 */
public abstract class JbsParser extends Parser {
    protected void addBook(String bookId) {
        jsonObject().add(JBO_BOOK, JBR_BOOK + bookId);
    }
    protected void addBook(JsonObject obj, String bookId) {
        obj.add(JBO_BOOK, JBR_BOOK + bookId);
    }

    protected void addPosition(int position) {jsonObject().add(JBO_POSITION, position); }
    protected void addPosition(JsonObject obj, int position) {
        obj.add(JBO_POSITION, position);
    }

    protected void addUri(String uri) {
        jsonObject().add(URI, JBR_TEXT + uri);
    }
    protected void addUri(JsonObject obj, String uri) {
        obj.add(URI, JBR_TEXT + uri);
    }

    protected void addRdfs(String rdfs) {
        jsonObject().add(RDFS_LABEL, rdfs);
    }
    protected void addRdfs(JsonObject obj, String rdfs) {
        obj.add(RDFS_LABEL, rdfs);
    }

    protected void addWithin(String wi) {
        jsonObject().addToArray(JBO_WITHIN, JBR_SECTION + wi);
    }

    protected void addWithin(JsonObject obj, String wi) {
        obj.addToArray(JBO_WITHIN, JBR_SECTION + wi);
    }

    protected void addPackageUri(String pUri) { packagesJsonObject().add(URI, JBR_SECTION + pUri); }

    protected int toInt(String num) { return Integer.parseInt(num); }


    protected String numberToHebrew(int num) {
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
    }
}
