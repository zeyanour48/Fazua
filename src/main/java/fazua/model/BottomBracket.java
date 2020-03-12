package fazua.model;

import fazua.helper.Util;

import java.util.ArrayList;
import java.util.List;

public class BottomBracket {
    Integer serialNumber;
    String  torqueSensorSerialNumber;

    public BottomBracket(Integer serialNumber, String torqueSensorSerialNumber) throws Exception {
       this. serialNumber=serialNumber;
        this.torqueSensorSerialNumber=torqueSensorSerialNumber;
        validate();
    }
    private void validate() throws Exception {
        List<String> errors = new ArrayList<>();
        Boolean passes= Util.ensureNotNull(serialNumber, "Serial number is null", errors);
        if (passes) {
            if (serialNumber < 0) {
                errors.add("Serial number is not a positive integer number: " ) ;
            }
        }

        passes=Util.ensureNotNull(torqueSensorSerialNumber, "Torque sensor serial number is null", errors);
        if (passes) {
            if (!torqueSensorSerialNumber.matches("\\d{1,12}")) {
                errors.add("Invalid torque sensor serial number format  " ) ;
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
    public String getTorqueSensorSerialNumber() {
        return torqueSensorSerialNumber;
    }
}
