package fazua.validation;

import javafx.scene.control.Control;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

public class PositiveNumber16BitValidator implements Validator<String> {
    private Short validValue;



    @Override
    public ValidationResult apply(Control control,  String value) {
        boolean condition =
                value != null
                        ? !value
                        .matches(
                                "[0-9]+" )
                        : value == null;

        System.out.println( value );
        System.out.println( condition );

        if (!condition){
    Short v = null;
        try {
            v = Short.parseShort(value);
            validValue=v;
        } catch (NumberFormatException ex) {
            System.out.println("NumberFormatException");
            validValue=null;
            condition=true;
        }
       }
        return ValidationResult.fromMessageIf( control, "not a positive number max 16 bit", Severity.ERROR, condition );

    }


    public Short getValidValue() {
        return validValue;
    }
}
