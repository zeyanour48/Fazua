package fazua.service;

import fazua.model.BottomBracket;
import fazua.model.Drivepack;
import fazua.model.EvationDriveSystem;
import fazua.model.Remote;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class EvationDriveSystemProduction extends Task<Void> {
    private final List<ProductionLineListener> listeners = new ArrayList<>();
    private Integer producedItems;

    protected BlockingQueue<Drivepack>   queue1;
    protected BlockingQueue<BottomBracket> queue2;
    protected BlockingQueue<Remote> queue3;
    protected BlockingQueue<EvationDriveSystem> queue4;

    private Boolean bExit=false;
    public Integer getProducedItems() {
        return producedItems;
    }

    public EvationDriveSystemProduction(BlockingQueue<Drivepack> queue1, BlockingQueue<BottomBracket> queue2, BlockingQueue<Remote> queue3, BlockingQueue<EvationDriveSystem> queue4) {
        this.queue1 = queue1;
        this.queue2 = queue2;
        this.queue3 = queue3;
        this.queue4 = queue4;
        producedItems=0;
    }

    protected   Void call() throws Exception  {
       while (!bExit)
       {
           try {
               Thread.sleep(10000);
               if((queue1.size()>0)&&(queue2.size()>0)&&(queue3.size()>0)) {
                   Drivepack drivepack = queue1.take();
                   BottomBracket bottomBracket = queue2.take();
                   Remote remote = queue3.take();
                   EvationDriveSystem ev = new EvationDriveSystem(drivepack, bottomBracket, remote);
                   if (ev != null) {
                       queue4.put(ev);
                       System.out.println("new EvationDriveSystem");
                       fireEvationDriveSystemProductionChangeEvent();
                       producedItems++;
                   }
               }
           } catch (InterruptedException e) {
           }
       }
       return null;
    }
    private void fireEvationDriveSystemProductionChangeEvent() {
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
