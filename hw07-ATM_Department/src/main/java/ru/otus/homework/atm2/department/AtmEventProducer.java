package ru.otus.homework.atm2.department;

import java.util.ArrayList;
import java.util.List;

public class AtmEventProducer {
    private final List<AtmListener> listeners = new ArrayList<>();

    void addListener(AtmListener listener) {
        listeners.add(listener);
    }

    void addListeners(List<? extends AtmListener> listeners) {
        this.listeners.addAll(listeners);
    }

    void removeListener(AtmListener listener) {
        listeners.remove(listener);
    }

    void restoreAllAtm() {
        listeners.forEach(AtmListener::restoreInitState);
    }

    long getAmount() {
        long sum = 0;
        for (AtmListener listener : listeners) {
            sum += listener.getAmount();
        }
        return sum;
    }
}
