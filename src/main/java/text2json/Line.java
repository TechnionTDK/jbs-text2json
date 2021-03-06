package text2json;

/**
 * Created by omishali on 12/12/2016.
 */
public class Line {
    private String line;
    private boolean lineMatched;

    Line(String line){ this.line = line.trim();
                       this.lineMatched = false;}

    public void lineMatched(){lineMatched = true; }

    public boolean isLineMatched(){ return lineMatched; }

    public String getLine() { return line; }

    /**
     *
     * @param position Starting from 0
     * @return
     */
    public String getWord(int position) {
        return getLine().split("\\s+")[position];
    }

    public boolean beginsWith(String s) {
        return getLine().startsWith(s);
    }

    /**
     * Whether the line begins with (e + suffix) (e is one of the elements in arr)
     * @param arr
     * @return
     */
    public boolean beginsWith(String[] arr, String suffix) {
        for (String s : arr)
            if (getLine().startsWith(s + suffix))
                return true;

        return false;
    }

    public boolean endsWith(String s) {
        return line.endsWith(s);
    }

    public boolean endsWith(String[] stringArr) {
        for(String s : stringArr){
            if (getLine().endsWith(": (" + s + ")")) return true;
        }
        return false;
    }

    public boolean beginsWith(String[] stringArr) {
        for(String s : stringArr){
            if (line.startsWith(s)) return true;
        }
        return false;
    }

    public boolean contains(String s) {
        return getLine().contains(s);
    }

    public int wordCount() {
        return getLine().trim().split("\\s+").length;
    }

    /**
     * Returns the text in between first and last.
     * If first or last are an empty space refer to them as beginning/end of line accordingly.
     * @param
     * @return
     */
    public String extract(String first, String last) {
        int firstIndex = 0;
        int lastIndex = getLine().length();
        if(first != " ") {
            firstIndex = getLine().indexOf(first) + first.length();
        }
        if(last != " ") {
            lastIndex = getLine().indexOf(last, firstIndex);
        }
        return getLine().substring(firstIndex, lastIndex).trim();
    }

    public String getFirstWord(){
        if (wordCount() == 1){
            return getLine();
        }
        return getLine().substring(0, getLine().indexOf(' '));
    }

    /**
     * Assume the separator between words is a space (one or more)
     * @return
     */
    public Line removeFirstWord() {
        int firstSpace = line.indexOf(" ");
        line = line.substring(firstSpace).trim();
        return this;
    }

    /**
     * Removes extra spaces between words (keeps one space)
     * @return
     */
    public Line removeExtraSpaces() {
        String[] words = line.split("\\s+");
        StringBuffer result = new StringBuffer();
        for (String word : words)
            result.append(word + " ");

        line = result.toString().trim();
        return this;
    }

    public boolean is(String s) {
        return getLine().equals(s);
    }
}
