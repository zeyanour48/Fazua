package fazua.service;

import fazua.model.Drivepack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class DrivepackProduction implements Runnable{
    private final List<ProductionLineListener> listeners = new ArrayList<>();
    private Integer producedItems;
    protected BlockingQueue<Drivepack> queue;
    private Drivepack drivepack;

    public void setDrivepack(Drivepack drivepack) {
        this.drivepack = drivepack;
    }
    public Integer getProducedItems() {
        return producedItems;
    }

    public DrivepackProduction(BlockingQueue<Drivepack> queue) {
        this.queue = queue;producedItems=0;
    }
    public void run() {
            if(drivepack!=null) {
                try {
                    queue.put(drivepack);
                    System.out.println(" Drivepack production is running: "+queue.size());
                    this.drivepack=null;
                    fireDrivepackProductionChangeEvent();
                    producedItems++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    private void fireDrivepackProductionChangeEvent() {
        for (ProductionLineListener listener : listeners) {
            listener.onReadingChange();
        }
    }

    public void addListener(ProductionLineListener listener) {

        listeners.add(listener);
    }
}