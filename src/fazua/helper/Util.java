package fazua.helper;

import java.util.List;

public class Util {
    static public boolean ensureNotNull(Object field, String errorMsg, List<String> errors) {
        boolean result = true;
        if (field == null) {
            errors.add(errorMsg);
            result = false;
        }
        return result;
    }
}
