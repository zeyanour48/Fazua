package fazua.service;

import fazua.model.BottomBracket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class BottomBracketProduction implements Runnable {
    private final List<ProductionLineListener> listeners = new ArrayList<>();
    private Integer producedItems;
    private BottomBracket bottomBracket;
    protected BlockingQueue<BottomBracket> queue;
    public BottomBracketProduction(BlockingQueue<BottomBracket> queue) {
        this.queue = queue;
        producedItems = 0;
    }

    public void setBottomBracket(BottomBracket bottomBracket) {
        this.bottomBracket = bottomBracket;
    }
    public Integer getProducedItems() {
        return producedItems;
    }

    public void run()
    {
        if (bottomBracket != null) {
            try {
                queue.put(bottomBracket);
                System.out.println(" Bottom Bracket production is running");
                this.bottomBracket = null;
                fireBottomBracketProductionChangeEvent();
                producedItems++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void fireBottomBracketProductionChangeEvent() {
        for (ProductionLineListener listener : listeners) {
            listener.onReadingChange();
        }
    }

    public void addListener(ProductionLineListener listener) {

        listeners.add(listener);
    }
}