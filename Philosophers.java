package Lab9;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Philosophers extends Thread {
    private static Semaphore[] sem = new Semaphore[5];
    private static Semaphore mutex = new Semaphore(4);
    private static int i = 0;
    private int id;
    private static int counter = 0;
    
    public Philosophers(int id) {
        this.id = id;
        sem[i] = new Semaphore(1);
        i++;
    }
    
    public synchronized void Waiting() throws InterruptedException {
        if(id>0)
        System.out.println("Philosopher " + (id) + " is Waiting for fork " + ((id + 1)) );

        Thread.sleep(100);
    }
    
    public synchronized void Complete_Dinner() throws InterruptedException {
        System.out.println("Philosopher " + (id + 1) + " completed his dinner");
        Thread.sleep(100);

    }
    
    public synchronized void Take_Forks() {
        mutex.down();
        sem[id].down();
        System.out.println("Fork " + (id + 1) + " taken by philosopher " + (id + 1));
        sem[(id + 1) % 5].down();
        System.out.println("Fork " + (((id + 1) % 5) + 1) + " taken by philosopher " + (id + 1));
    }
    
    public synchronized void Release_Forks() {
        sem[id].up();
        sem[(id + 1) % 5].up();
        System.out.println("Philosopher " + (id + 1) + " released fork " + (id + 1) + " and fork " + (((id + 1) % 5) + 1));
        mutex.up();
        counter++;
        System.out.println("Till now num of philosophers completed dinner are  " + counter);
    }
    
    @Override
    public void run() {
        try {
            Take_Forks();
            Waiting();
            Complete_Dinner();
            Release_Forks();
        } catch (InterruptedException ex) {
            Logger.getLogger(Philosophers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        int k, j, t, z;
        Philosophers arr[] = new Philosophers[5];
        k = 0;
        
        for(j=0; j<5; j++){
            arr[j] = new Philosophers(k);
            k++;
            }  

        for(t=0; t<5; t++){
        arr[t].start();
        }
        
        for(z=0; z<5; z++){
        arr[z].join();
        }
        
    }
    
}
