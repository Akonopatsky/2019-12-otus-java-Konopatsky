package ru.otus.hw15;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DigitSequence {
    public static final Logger logger = LoggerFactory.getLogger(DigitSequence.class);
    public boolean isOddStep = true;

    public void setOddStep(boolean oddStep) {
        isOddStep = oddStep;
    }

    private synchronized void action() {
        boolean isOddThread = isOddStep;
        Thread.currentThread().setName(isOddThread ? "first" : "second");
        checkStep(isOddThread);

        for (int i = 1; i <= 10; i++) {
            checkStep(isOddThread);
            logger.info("{}", i);
        }
        for (int i = 9; i > 0; i--) {
            checkStep(isOddThread);
            logger.info("{}", i);
        }
    }

    private void checkStep(boolean isOddThread) {
        try {
            while (isOddThread != isOddStep) {
                this.wait();
            }
            setOddStep(!isOddThread);
            notifyAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        DigitSequence digitSequence = new DigitSequence();
        new Thread(() -> digitSequence.action()).start();
        new Thread(() -> digitSequence.action()).start();
    }

    private static class myInterruptedException extends RuntimeException {
        myInterruptedException(InterruptedException ex) {
            super(ex);
        }
    }
}
