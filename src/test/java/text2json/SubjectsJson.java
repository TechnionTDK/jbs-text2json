package text2json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static text2json.JbsOntology.URI;

/**
 * Created by omishali on 17/01/2017.
 */
public class SubjectsJson {
    List<Object> subjects = new ArrayList<Object>();

    public Map<String, String> getObject(int num) {
        return (Map<String, String>) subjects.get(num);
    }

    /**
     * Note: o(N) complexitiy, prefer to get objects from the beginning of the file
     * @param uri
     * @return
     */
    public Map<String, String> getObject(String uri) {
        for (Object e : subjects) {
            String eUri = ((Map<String, String>)e).get(URI);
            if (eUri.equals(uri))
                return (Map<String, String>)e;
        }
        return null;
    }
}
