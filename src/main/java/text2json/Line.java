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

    public String getLine() { return line; }

    public boolean beginsWith(String s) {
        return line.startsWith(s);
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
        int firstIndex;
        int lastIndex;

        if(first == " ") firstIndex = 0;
        else firstIndex = line.indexOf(first) + first.length();
        if(last == " ") lastIndex = line.length();
        else lastIndex = line.indexOf(last, firstIndex);

        return line.substring(firstIndex, lastIndex);
    }

    public boolean is(String s) {
        return line.equals(s);
    }
}
