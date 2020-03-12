package fazua.validation;

import javafx.scene.control.Control;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

public class PositiveNumber16BitValidator implements Validator<String> {
    private Short validValue;
    private String fieldName;

    @Override
    public ValidationResult apply(Control control, String value) {
        boolean condition =
                value != null
                        ? !value
                        .matches(
                                "[0-9]+")
                        : value == null;
        if (!condition) {
            Short v = null;
            try {
                v = Short.parseShort(value);
                validValue = v;
            } catch (NumberFormatException ex) {
                System.out.println("NumberFormatException");
                validValue = null;
                condition = true;
            }
        }
        return ValidationResult.fromMessageIf(control, this.fieldName + " is not a positive number max 16 bit", Severity.ERROR, condition);

    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Short getValidValue() {
        return validValue;
    }
}