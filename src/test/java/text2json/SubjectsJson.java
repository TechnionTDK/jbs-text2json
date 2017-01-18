package text2json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by omishali on 17/01/2017.
 */
public class SubjectsJson {
    List<Object> subjects = new ArrayList<Object>();

    public Map<String, String> getObject(int num) {
        return (Map<String, String>) subjects.get(num);
    }
}
