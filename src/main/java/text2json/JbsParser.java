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

    protected void addLabel(String label) {
        jsonObject().add(RDFS_LABEL, label);
    }
    protected void addLabel(JsonObject obj, String label) {
        obj.add(RDFS_LABEL, label);
    }

    protected void addWithin(String wi) {
        jsonObject().addToArray(JBO_WITHIN, JBR_SECTION + wi);
    }
    protected void addWithin(JsonObject obj, String wi) {
        obj.addToArray(JBO_WITHIN, JBR_SECTION + wi);
    }

    protected void addPackageUri(String pUri) { packagesJsonObject().add(URI, JBR_SECTION + pUri); }

    protected int toInt(String num) { return Integer.parseInt(num); }

    protected void addText(String text) {jsonObject().add(JBO_TEXT, text); }
    protected void appendText(String text) {jsonObject().append(JBO_TEXT, text);}
    protected void appendNikudText(String text) {jsonObject().append(JBO_TEXT_NIKUD, text);
    }
}
