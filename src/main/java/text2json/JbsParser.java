package text2json;

import static text2json.JbsOntology.JBO_BOOK;
import static text2json.JbsOntology.JBO_POSITION;
import static text2json.JbsOntology.JBR_BOOK;

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
    protected void addPosition(int position) {
        jsonObject().add(JBO_POSITION, position);
    }
    protected void addPosition(JsonObject obj, int position) {
        obj.add(JBO_POSITION, position);
    }

}
