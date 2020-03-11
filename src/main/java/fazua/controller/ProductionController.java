package fazua.controller;

import fazua.model.BottomBracket;
import fazua.model.Drivepack;
import fazua.model.EvationDriveSystem;
import fazua.model.Remote;
import fazua.service.*;
import fazua.validation.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductionController implements Initializable {
    private final ExecutorService service1 = Executors.newCachedThreadPool();
    private final ExecutorService service2 = Executors.newCachedThreadPool();
    private final ExecutorService service3 = Executors.newCachedThreadPool();

    @FXML
    private Text drivePackItems;
    @FXML
    private Text producedDrivePackItems;

    @FXML
    private Text bottomBracketItems;
    @FXML
    private Text producedBottomBracketItems;

    @FXML
    private Text remoteItems;
    @FXML
    private Text producedRemoteItems;

    @FXML
    private Text assembledItems;
    @FXML
    private Text producedEvationDriveSystemItems;

    @FXML
    private Text testedItems;
    @FXML
    private Text passedItems;
    @FXML
    private Text failedItems;
    @FXML
    private Text currentMotorOutputValue;
    @FXML
    private Text movingMotorLabel;
    @FXML
    private TextField drivePackSerialNumber;
    @FXML
    private TextField drivePackSoftwareVersion;
    @FXML
    private TextField drivePackMotorSerialNumber;


    @FXML
    private TextField bottomBracketSerialNumber;
    @FXML
    private TextField bottomBracketTorqueSensorSerialNumber;

    @FXML
    private TextField remoteSerialNumber;
    @FXML
    private TextField remoteHMIBoardSerialNumber;

    @FXML
    private ProgressBar testMotorProgressBar;
    @FXML
    Label validationErrors1;
    @FXML
    Label validationErrors2;
    @FXML
    Label validationErrors3;

    ValidationSupport drivepackValidationSupport = new ValidationSupport();
    Validator<String> validator1;
    Validator<String> validator2;
    Validator<String> validator3;

    ValidationSupport bottomBracketValidationSupport = new ValidationSupport();
    Validator<String> validator4;
    Validator<String> validator5;

    ValidationSupport remoteValidationSupport = new ValidationSupport();
    Validator<String> validator6;
    Validator<String> validator7;

    DrivepackProduction drivepackProduction;
    BottomBracketProduction bottomBracketProduction;
    RemoteProduction remoteProduction;
    EvationDriveSystemProduction evationDriveSystemProduction;
    MotorTesterTask motorTesterTask;

    BlockingQueue<Drivepack> queue1;
    BlockingQueue<BottomBracket> queue2;
    BlockingQueue<Remote> queue3;
    BlockingQueue<EvationDriveSystem> queue4;

    Thread evationDriveSystemProductionThread;
    Thread motorTesterThread;

    private void drivePackProductionInit() {

        queue1 = new ArrayBlockingQueue(10);
        drivepackProduction = new DrivepackProduction(queue1);
        drivepackProduction.addListener(new ProductionLineListener() {
            @Override
            public void onReadingChange() {
                updateDrivepackView();
            }
        });
    }

    private void bottomBracketProductionInit() {

        queue2 = new ArrayBlockingQueue(10);
        bottomBracketProduction = new BottomBracketProduction(queue2);
        bottomBracketProduction.addListener(new ProductionLineListener() {
            @Override
            public void onReadingChange() {
                updateBottomBracketView();
            }
        });
    }

    private void remoteProductionInit() {
        queue3 = new ArrayBlockingQueue(10);
        remoteProduction = new RemoteProduction(queue3);
        remoteProduction.addListener(new ProductionLineListener() {
            @Override
            public void onReadingChange() {
                updateRemoteView();
            }
        });
    }

    private void evationDriveSystemProductionInit() {

        queue4 = new ArrayBlockingQueue(10);
        evationDriveSystemProduction = new EvationDriveSystemProduction(queue1, queue2, queue3, queue4);
        evationDriveSystemProductionThread = new Thread(evationDriveSystemProduction);

        evationDriveSystemProduction.addListener(new ProductionLineListener() {
            @Override
            public void onReadingChange() {
                updateEvationDriveSystemView();
            }
        });
        evationDriveSystemProductionThread.start();
    }

    private void motorTesterInit() {
        motorTesterTask = new MotorTesterTask(queue4);
        motorTesterTask.setProgressBar(testMotorProgressBar);
        motorTesterTask.addListener(new ProductionLineListener() {
            @Override
            public void onReadingChange() {
                updateMotorTesterTaskView();
            }
        });
        motorTesterTask.messageProperty().addListener(new ChangeListener<String>() {

            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
            }
        });
        motorTesterThread = new Thread(motorTesterTask);
        motorTesterThread.start();


    }

    public ProductionController() {
        drivePackProductionInit();
        bottomBracketProductionInit();
        remoteProductionInit();
        evationDriveSystemProductionInit();
        motorTesterInit();
    }

    private void updateDrivepackView() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                drivePackItems.setText(String.valueOf(queue1.size()));
                producedDrivePackItems.setText(String.valueOf(drivepackProduction.getProducedItems()));
            }
        });
    }

    private void updateBottomBracketView() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                bottomBracketItems.setText(String.valueOf(queue2.size()));
                producedBottomBracketItems.setText(String.valueOf(bottomBracketProduction.getProducedItems()));
            }
        });
    }

    private void updateRemoteView() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                remoteItems.setText(String.valueOf(queue3.size()));
                producedRemoteItems.setText(String.valueOf(remoteProduction.getProducedItems()));
            }
        });
    }

    private void updateEvationDriveSystemView() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                drivePackItems.setText(String.valueOf(queue1.size()));
                bottomBracketItems.setText(String.valueOf(queue2.size()));
                remoteItems.setText(String.valueOf(queue3.size()));

                assembledItems.setText(String.valueOf(queue4.size()));
                producedEvationDriveSystemItems.setText(String.valueOf(evationDriveSystemProduction.getProducedItems()));

                bindProgressBar();
                testMotorProgressBar.setVisible(true);
                currentMotorOutputValue.setText("");
                movingMotorLabel.setVisible(true);

            }
        });
    }

    private void updateMotorTesterTaskView() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                assembledItems.setText(String.valueOf(queue4.size()));
                testedItems.setText(String.valueOf(motorTesterTask.getTestedItems()));
                passedItems.setText(String.valueOf(motorTesterTask.getPassedItems()));
                failedItems.setText(String.valueOf(motorTesterTask.getFailedItems()));
                testMotorProgressBar.setVisible(false);
                currentMotorOutputValue.setText(String.valueOf(  motorTesterTask.getCurrentMotorOtputValue()));
                movingMotorLabel.setVisible(false);

            }
        });
    }


    public void shutdown() {

        motorTesterTask.shutdown();
        motorTesterThread.interrupt();

        evationDriveSystemProduction.shutdown();
        evationDriveSystemProductionThread.interrupt();


        service1.shutdown();
        service2.shutdown();
        service3.shutdown();
    }

    @FXML
    private void addDrivePackEvent(ActionEvent event) {
        drivepackValidationSupport.setErrorDecorationEnabled(true);
        if (drivepackValidationSupport.isInvalid()) {
            System.out.println(drivepackValidationSupport.getValidationResult().getErrors().toString());
              } else {
            Integer sn = ((PositiveNumber32BitValidator) validator1).getValidValue();
            Short swn = ((PositiveDoubleToShortValidator) validator2).getValidValue();
            Short msn = ((PositiveNumber16BitValidator) validator3).getValidValue();
            Drivepack drivepack;
            try {
                drivepack = new Drivepack(sn, swn, msn);
                drivepackProduction.setDrivepack(drivepack);
                   service1.submit(drivepackProduction);
            } catch (Exception e) {
                System.out.println(e.getSuppressed());
                String msg = "";
                for (int i = 0; i < e.getSuppressed().length; i++) {
                    msg += e.getSuppressed()[i] + "\n";
                }
                validationErrors1.setText(msg);
                validationErrors1.setVisible(true);
            }
        }
    }

    @FXML
    public void addBottomBracketEvent(ActionEvent actionEvent) {
        bottomBracketValidationSupport.setErrorDecorationEnabled(true);
        if (bottomBracketValidationSupport.isInvalid()) {
            System.out.println(bottomBracketValidationSupport.getValidationResult().getErrors().toString());
        } else {
            Integer sn = ((PositiveNumber32BitValidator) validator4).getValidValue();
            String tssn = ((Ascii12DigitsValidator) validator5).getValidValue();
            BottomBracket bottomBracket;
            try {
                bottomBracket = new BottomBracket(sn, tssn);
                bottomBracketProduction.setBottomBracket(bottomBracket);
                service2.submit(bottomBracketProduction);
            } catch (Exception e) {
                System.out.println(e.getSuppressed());
                String msg = "";
                for (int i = 0; i < e.getSuppressed().length; i++) {
                    msg += e.getSuppressed()[i] + "\n";
                }
                validationErrors2.setText(msg);
                validationErrors2.setVisible(true);
            }
        }
    }
    @FXML
    public void addRemoteEvent(ActionEvent actionEvent) {
        remoteValidationSupport.setErrorDecorationEnabled(true);
        if (remoteValidationSupport.isInvalid()) {
            System.out.println(remoteValidationSupport.getValidationResult().getErrors().toString());
        } else {
            Integer sn = ((PositiveNumber32BitValidator) validator6).getValidValue();
            Short tssn = ((PositiveNumber16BitValidator) validator7).getValidValue();
            Remote remote;
            try {
                remote = new Remote(sn, tssn);
                remoteProduction.setRemote(remote);
                service3.submit(remoteProduction);
            } catch (Exception e) {
                System.out.println(e.getSuppressed());
                String msg = "";
                for (int i = 0; i < e.getSuppressed().length; i++) {
                    msg += e.getSuppressed()[i] + "\n";
                }
                validationErrors3.setText(msg);
                validationErrors3.setVisible(true);
            }
        }
    }

    public void bindProgressBar() {
        testMotorProgressBar.progressProperty().unbind();
        testMotorProgressBar.setProgress(0);
        testMotorProgressBar.progressProperty().bind(motorTesterTask.progressProperty());
        testMotorProgressBar.setVisible(false);
        movingMotorLabel.setVisible(false);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
initializeDrivepackValidators();
        initializeBottomBracketValidators();
        initializeRemoteValidators();
          }

    private void initializeRemoteValidators() {
        validator6 = new PositiveNumber32BitValidator();
        validator7= new PositiveNumber16BitValidator();

        remoteValidationSupport.registerValidator(remoteSerialNumber, true,   validator6);
        remoteValidationSupport.registerValidator(remoteHMIBoardSerialNumber, true,validator7);

    }

    private void initializeBottomBracketValidators() {
        validator4 = new PositiveNumber32BitValidator();
        validator5 = new Ascii12DigitsValidator();
        bottomBracketValidationSupport.registerValidator(bottomBracketSerialNumber, true,   validator4);
        bottomBracketValidationSupport.registerValidator(bottomBracketTorqueSensorSerialNumber, true,validator5);


    }

    private void initializeDrivepackValidators() {
        validator1 = new PositiveNumber32BitValidator();
        validator2 = new PositiveDoubleToShortValidator();
        validator3 = new PositiveNumber16BitValidator();
        drivepackValidationSupport.registerValidator(drivePackSerialNumber, true,validator1);
        drivepackValidationSupport.registerValidator(drivePackSoftwareVersion, true, validator2);
        drivepackValidationSupport.registerValidator(drivePackMotorSerialNumber, true,validator3);

    }

    @FXML
    public void clearDrivePackEvent(ActionEvent actionEvent) {
        drivePackSerialNumber.clear();
        drivePackSoftwareVersion.clear();
        drivePackMotorSerialNumber.clear();
    }
    @FXML
    public void clearBottomBracketEvent(ActionEvent actionEvent) {
        bottomBracketSerialNumber.clear();
        bottomBracketTorqueSensorSerialNumber.clear();
    }
    @FXML
    public void clearRemoteEvent(ActionEvent actionEvent) {
        remoteSerialNumber.clear();
        remoteHMIBoardSerialNumber.clear();
    }
}
