package fazua.model;

import fazua.helper.Util;

import java.util.ArrayList;
import java.util.List;

public class Remote {
    Integer serialNumber;
    Short hMIBoardSerialNumber;

    public Remote(Integer serialNumber, Short hMIBoardSerialNumber) throws Exception {
        this.serialNumber=serialNumber;
        this.hMIBoardSerialNumber=hMIBoardSerialNumber;
        validate();
    }
    private void validate() throws Exception {
        List<String> errors = new ArrayList<>();
        Boolean passes= Util.ensureNotNull(serialNumber, "Serial Number is null", errors);
        if (passes) {
            if (serialNumber < 0) {
                errors.add("Serial Number Is not a positive Integer Number: " ) ;
            }
        }


        passes= Util.ensureNotNull(hMIBoardSerialNumber, "HMI Board Serial Number is null", errors);
        if (passes) {
            if (hMIBoardSerialNumber < 0) {
                errors.add("HMI Board Serial Number Is not a positive Short Number: " ) ;
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
    public Short getHMIBoardSerialNumber() {
        return hMIBoardSerialNumber;
    }
}
