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

}
