package fazua.helper;

import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationSupport;

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
    static public String formatValidationSupportMsg(ValidationSupport validationSupport)
    {
        String v="";
        for (ValidationMessage s:validationSupport .getValidationResult().getErrors()
        ) {
            v+=s.getText()+"\n";


        }
        return  v;
    }
}
