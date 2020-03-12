package fazua.model;

import fazua.helper.Util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Drivepack {
    Integer serialNumber;
    Short softwareVersion;
    Short motorSerialNumber;
    public Drivepack(Integer serialNumber, Short softwareVersion, Short motorSerialNumber) throws Exception {
        this.serialNumber=serialNumber;
        this.softwareVersion=softwareVersion;
        this.motorSerialNumber=motorSerialNumber;
        validate();
    }

    private void validate() throws Exception {
        List<String> errors = new ArrayList<>();
        Boolean passes=Util.ensureNotNull(serialNumber, "Serial number is null", errors);
        if (passes) {
            if (serialNumber < 0) {
                errors.add("Serial number is not a positive integer number" ) ;
            }
        }

        passes=Util.ensureNotNull(softwareVersion, "Software version is null", errors);
        if (passes) {
            if (softwareVersion < 0) {
                errors.add("Invalid software version format " ) ;
            }
        }

        passes= Util.ensureNotNull(motorSerialNumber, "Motor serial number is null", errors);
        if (passes) {
            if (motorSerialNumber < 0) {
                errors.add("Motor serial number Is not a positive Short Number: " ) ;
            }
        }

        if (!errors.isEmpty()) {
            Exception ex = new Exception();
            for(String error: errors) {
                ex.addSuppressed(new Exception(error));
            }
            throw ex;
        }
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }
    public Short getSoftwareVersion() {
        return softwareVersion;
    }
    public String getSoftwareVersiontoString(Short softwareVersion){
    String bs= String.format("%016d",new BigInteger(Integer.toBinaryString(Short.toUnsignedInt(softwareVersion))));
    String bs1=bs.substring(0,8);
    String bs2=bs.substring(8,16);

    Byte bb1= Byte.parseByte(bs1,2);
    Byte bb2= Byte.parseByte(bs2,2);

    return (bb2+"."+   bb1);
}
    public Short getMotorSerialNumber() {
        return motorSerialNumber;
    }
  }

