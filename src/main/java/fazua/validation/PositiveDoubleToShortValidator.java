package fazua.validation;

import javafx.scene.control.Control;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

import java.math.BigInteger;

public class PositiveDoubleToShortValidator implements Validator<String> {
    private Short validValue;
    private String fieldName;


    @Override
    public ValidationResult apply(Control control,  String value) {
        boolean condition =
                value != null
                        ? !value
                        .matches(
                                "[0-9]+([\\.][0-9]+)?" )
                        : value == null;

        System.out.println( value );
        System.out.println( condition );

        if (!condition){
            String[] arrOfStr=new String[]{"",""};
             arrOfStr = value.split("[.]");
            Integer p1 =null, p2 = null;
            Byte b1 = null, b2 = null;
            Short sv = null;
//            System.out.println(arrOfStr[0]);
//            System.out.println(arrOfStr[1]);

            try {
                b1=Byte.parseByte(arrOfStr[0]);
                //p1 = Integer.parseInt(arrOfStr[0]);

            } catch (NumberFormatException ex1) {
                //  p1 = -1;
            }
            System.out.println("b1: " + b1);
            if(arrOfStr.length==1)
                if(b1!=null)
                arrOfStr=new String[]{b1.toString(),"0"};
                else
                    arrOfStr=new String[]{"0","0"};
            try {
                b2=Byte.parseByte(arrOfStr[1]);

                //   p2 = Integer.parseInt(arrOfStr[1]);
            } catch (NumberFormatException ex2) {
//            p2 = -1;
            }
            System.out.println("b2: " + b2);


            if ((b1 !=null) && (b2!=null)) {

                sv = (short) ((b2 * 256) + b1);
//4898
                System.out.println(String.format("%016d",new BigInteger(Integer.toBinaryString(Short.toUnsignedInt(sv)))));
                String bs= String.format("%016d",new BigInteger(Integer.toBinaryString(Short.toUnsignedInt(sv))));
                String bs1=bs.substring(0,8);
                String bs2=bs.substring(8,16);
                System.out.println(    bs1);
                System.out.println(    bs2);

                Byte bb1= Byte.parseByte(bs1,2);
                Byte bb2= Byte.parseByte(bs2,2);
                System.out.println(    "hghg");

                System.out.println(bb2+"."+   bb1);





            } else {
                sv = null;
                condition=true;
                System.out.println("error3");
            }

            validValue=sv;
//    Short v = null;
//        try {
//            v = Short.parseShort(value);
//            validValue=v;
//        } catch (NumberFormatException ex) {
//            System.out.println("NumberFormatException");
//            validValue=null;
//            condition=true;
//        }
       }
        return ValidationResult.fromMessageIf( control, this.fieldName+" is not a positive Double Number. Invalid format!", Severity.ERROR, condition );

    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Short getValidValue() {
        return validValue;
    }
}
