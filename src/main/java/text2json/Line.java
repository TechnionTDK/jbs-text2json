package text2json;

/**
 * Created by omishali on 12/12/2016.
 */
public class Line {
    private String line;
    private boolean lineMatched;

    Line(String line){ this.line = line;
                       this.lineMatched = false;}

    public void lineMatched(){lineMatched = true; }

    public boolean isLineMatched(){ return lineMatched; }

    public String getLine() { return line.trim(); }

    /**
     *
     * @param position Starting from 0
     * @return
     */
    public String getWord(int position) {
        return getLine().split("\\s+")[position];
    }

    public boolean beginsWith(String s) {
        return line.startsWith(s);
    }

    /**
     * Whether the line begins with one of the elements in arr
     * @param arr
     * @return
     */
    public boolean beginsWith(String[] arr) {
        for (String s : arr)
            if (line.startsWith(s))
                return true;

        return false;
    }

    public boolean endsWith(String[] stringArr) {
        for(String s : stringArr){
            if (line.endsWith(": (" + s + ")")) return true;
        }
        return false;
    }

    public boolean contains(String s) {
        return line.contains(s);
    }

    public int wordCount() {
        return line.trim().split("\\s+").length;
    }

    /**
     * Returns the text in between first and last.
     * If first or last are an empty space refer to them as beginning/end of line accordingly.
     * @param
     * @return
     */
    public String extract(String first, String last) {
        int firstIndex = 0;
        int lastIndex = line.length();

        if(first != " ") {
            firstIndex = line.indexOf(first) + first.length();
        }

        if(last != " ") {
            lastIndex = line.indexOf(last, firstIndex);
        }

        return line.substring(firstIndex, lastIndex).trim();
    }

    public boolean is(String s) {
        return line.equals(s);
    }

    public boolean endsWith(String s) {
        return line.endsWith(s);
    }
}
