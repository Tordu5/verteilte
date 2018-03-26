package Aufgabe1;

import java.util.concurrent.Semaphore;

public class Thread_7 extends Thread{

    private Semaphore[] sems;

    public Thread_7(Semaphore[] sems, String name){
        super(name);
        this.sems=sems;
        start();
    }

    private void Activity_7() {
        System.out.println("Activity_7 running");
    }

    public void run(){
        try {
            sems[6].acquire();
            sems[7].acquire();
            sems[8].acquire();
        } catch (InterruptedException e){
            System.out.println(e.toString());
        }
        Activity_7();
    }
}
