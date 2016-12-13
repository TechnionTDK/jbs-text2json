package text2json;

/**
 * Created by omishali on 12/12/2016.
 */
public interface LineMatcher {
    /**
     * @return the type of the matcher, e.g., PARASHA_LINE
     */
    public String type();

    /**
     * Whether the provided line matches the criteria defined by the LineMatcher
     * @param line
     * @return
     */
    public boolean match(Line line);
}

