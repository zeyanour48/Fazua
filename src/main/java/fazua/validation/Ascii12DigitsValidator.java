package fazua.validation;

import javafx.scene.control.Control;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

public class Ascii12DigitsValidator implements Validator<String> {
    private String validValue;
private String fieldName;


    @Override
    public ValidationResult apply(Control control,  String value) {
        boolean condition =
                value != null
                        ? !value
                        .matches(
                                "\\d{1,12}" )
                        : value == null;

        System.out.println( value );
        System.out.println( condition );

        if (!condition)
          validValue=value;
          else
              validValue=null;


        return ValidationResult.fromMessageIf( control, this.fieldName+" is not a max 12 ASCII digits. Invalid format!", Severity.ERROR, condition );

    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getValidValue() {
        return validValue;
    }
}
