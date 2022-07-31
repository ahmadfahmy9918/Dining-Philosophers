package Lab9;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Semaphore {

    private int value;
    private int ID;

    /**
     * Create a semaphore.
     *
     * @param value The initial value of the Semaphore ( must be &ge; 0).
     */
    public Semaphore(int value) {
        this.value = value;
    }

    /**
     * Increment the number of available resources. This method never blocks. It
     * may wakeup a Thread waiting for the Semaphore.
     */
    public synchronized void up() {
        value++;
        if (value >= 0) {
            //wake up a thread if it was suspended
            this.notify();
        }
    }

    /**
     * Request a resource. If no resources are available, the calling Thread
     * block until a resource controlled by the Semaphore becomes available.
     */
    public synchronized void down() {
        value--;
        if (value < 0) {
            try {
                //suspend a thread until another thread calls notify()
                this.wait();
            } catch (InterruptedException e) {
                Logger.getLogger(Semaphore.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    }
}