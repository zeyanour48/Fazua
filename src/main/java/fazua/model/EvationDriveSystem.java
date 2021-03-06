package fazua.model;

import fazua.helper.Util;

import java.util.ArrayList;
import java.util.List;

public class EvationDriveSystem {
    Drivepack drivePack;
    BottomBracket bottomBracket;
    Remote remote;

    public EvationDriveSystem(Drivepack drivePack, BottomBracket bottomBracket, Remote remote) throws Exception {
            this.drivePack = drivePack;
            this.bottomBracket = bottomBracket;
            this.remote = remote;
            validate();
    }
    private void validate() throws Exception {
        List<String> errors = new ArrayList<>();
        Util.ensureNotNull(drivePack, "Drivepack field is null", errors);
        Util.ensureNotNull(bottomBracket, "Bottom bracket field is null", errors);
        Util.ensureNotNull(remote, "Remote field is null", errors);
        if (!errors.isEmpty()) {
            Exception ex = new Exception();
            for(String error: errors) {
                ex.addSuppressed(new Exception(error));
            }
            throw ex;
        }
    }
}
