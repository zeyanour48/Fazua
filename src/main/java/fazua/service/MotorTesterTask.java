package fazua.service;

import fazua.model.EvationDriveSystem;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class MotorTesterTask extends Task<Void>{
    private ProgressBar progressBar;
    private final List<ProductionLineListener> listeners = new ArrayList<>();
    private Boolean bExit=false;
    protected BlockingQueue<EvationDriveSystem> queue1;

    private Integer testedItems;
    private Integer passedItems;
    private Integer failedItems;

    public Integer getCurrentMotorOtputValue() {
        return currentMotorOtputValue;
    }

    private Integer currentMotorOtputValue;
    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public Integer getTestedItems() {
        return testedItems;
    }

    public Integer getPassedItems() {
        return passedItems;
    }

    public Integer getFailedItems() {
        return failedItems;
    }
    public MotorTesterTask(BlockingQueue<EvationDriveSystem> queue1) {
        this.queue1 = queue1;
        testedItems=0;
        passedItems=0;
        failedItems=0;

    }
    //Generate Random Motor Value
    public Boolean motorTest() {
        Integer motorOutput = Math.toIntExact(Math.round(Math.floor(80 + ((150 - 80 + 1) * Math.random()))));
        currentMotorOtputValue=motorOutput;
        if ((motorOutput > 85) && (motorOutput < 140))
            return true;
        else
            return false;
    }
    @Override
    protected Void call() throws Exception {
       while (!bExit) {
            try {
                EvationDriveSystem eds=queue1.take();
                if(eds!=null) {
                    for(int i=0; i<400; i++){
                        updateProgress(i, 400);
                            Thread.sleep(10);
                    }
                    Boolean isPassed= motorTest();
                    testedItems++;
                    if(isPassed)
                        passedItems++;
                    else
                        failedItems++;
                    System.out.println("new MotorTest");
                    fireMotorTesterChangeEvent();
                }
            } catch (InterruptedException e) {
            }
      }
        return null;
    }
    private void fireMotorTesterChangeEvent() {
        for (ProductionLineListener listener : listeners) {
            listener.onReadingChange();
        }
    }

    public void addListener(ProductionLineListener listener) {

        listeners.add(listener);
    }

    public void shutdown() {
        this.bExit = true;
    }
}