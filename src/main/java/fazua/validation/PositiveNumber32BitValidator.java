package fazua.validation;

import javafx.scene.control.Control;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

public class PositiveNumber32BitValidator implements Validator<String> {
    private Integer validValue;



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
    Integer sn = null;
        try {
            sn = Integer.parseInt(value);
            validValue=sn;
        } catch (NumberFormatException ex) {
            System.out.println("NumberFormatException");
            validValue=null;
            condition=true;
        }
       }
        return ValidationResult.fromMessageIf( control, "not a positive number max 32 bit", Severity.ERROR, condition );

    }


    public Integer getValidValue() {
        return validValue;
    }
}
