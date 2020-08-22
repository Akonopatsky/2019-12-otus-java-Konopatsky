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
        logger.info("set isOddThread: {}", isOddThread);
        Thread.currentThread().setName(isOddThread ? "first" : " second");
        checkStep(isOddThread);
        notifyAll();
        System.out.println("ffffffffffffff");
        for (int i = 1; i <= 10; i++) {
            logger.info("{}: {}", isOddThread ? "first" : " second", i);
            checkStep(isOddThread);

        }
        for (int i = 9; i > 0; i--) {
            logger.info("{}", i);
            checkStep(isOddThread);
        }
        logger.debug("{} end", Thread.currentThread().getName());

    }

    private void checkStep(boolean isOddThread) {
        setOddStep(!isOddThread);
        try {
            logger.debug("меняем поток isOddThread: {} isOddStep: {}", isOddThread, isOddStep);
            while (isOddThread != isOddStep) {
                logger.debug("wait", isOddThread, isOddStep);
                this.wait();
            }
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
