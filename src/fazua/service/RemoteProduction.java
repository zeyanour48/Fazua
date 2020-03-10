package fazua.service;

import fazua.model.Remote;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class RemoteProduction implements Runnable {
    private final List<ProductionLineListener> listeners = new ArrayList<>();
    private Remote remote;
    private Integer producedItems;
    protected BlockingQueue<Remote> queue;

    public Integer getProducedItems() {
        return producedItems;
    }

    public void setRemote(Remote remote) {
        this.remote = remote;
    }

    public RemoteProduction(BlockingQueue<Remote> queue) {
        this.queue = queue;
        producedItems = 0;
    }

    public void run() {
        if (remote != null) {
            try {
                queue.put(remote);
                System.out.println(" Remote production is running");
                this.remote = null;
                fireRemoteProductionChangeEvent();
                producedItems++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void fireRemoteProductionChangeEvent() {
        for (ProductionLineListener listener : listeners) {
            listener.onReadingChange();
        }
    }

    public void addListener(ProductionLineListener listener) {
        listeners.add(listener);
    }
}