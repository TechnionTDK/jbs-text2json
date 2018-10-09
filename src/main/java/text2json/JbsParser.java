package text2json;

import java.io.IOException;

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

    protected void addTextUri(String value) {jsonObject().add(URI, JBR_TEXT + value);}
    protected void addTextInterprets(String value) { jsonObject().add(JBO_INTERPRETS, JBR_TEXT + value);}
    protected void addTextUri(JsonObject obj, String value) {
        obj.add(URI, JBR_TEXT + value);
    }

    protected void addLabel(String label) {
        jsonObject().add(RDFS_LABEL, label);
    }
    protected void addLabel(JsonObject obj, String label) {
        obj.add(RDFS_LABEL, label);
    }

    protected void addWithin(String value) {
        jsonObject().addToArray(JBO_WITHIN, JBR_SECTION + value);
    }
    protected void addWithin(JsonObject obj, String value) {
        obj.addToArray(JBO_WITHIN, JBR_SECTION + value);
    }

    protected void addPackageUri(String value) { packagesJsonObject().add(URI, JBR_SECTION + value); }

    protected int toInt(String num) { return Integer.parseInt(num); }

    protected void addText(String text) {jsonObject().add(JBO_TEXT, text); }
    protected void addName(String name) {jsonObject().add(JBO_NAME, name); }
    protected void appendText(String text) {jsonObject().append(JBO_TEXT, text);}
    protected void appendNikudText(String text) {jsonObject().append(JBO_TEXT_NIKUD, text);
}
}
