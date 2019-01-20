package hello;

import org.springframework.stereotype.Component;


@Component
public class Receiver {

    private final static Integer FACTOR = 2;

    private static Integer SLEEP_IN_SECS = 5000;

    public void receiveMessage(String message) throws InterruptedException {
        System.out.println("SLEEP TIME : " + SLEEP_IN_SECS);
        System.out.println("Received <" + message + ">");
        try {
            //mock throw exception to call restfull api;
            String str = null;
            str.contains(message);
        } catch (Exception e) {
            SLEEP_IN_SECS = (SLEEP_IN_SECS * FACTOR);
            Thread.sleep(SLEEP_IN_SECS);
            throw e;
        }
    }
}
