package Aufgabe1;

import java.util.concurrent.Semaphore;

public class Thread_6 extends Thread{

    private Semaphore[] sems;

    public Thread_6(Semaphore[] sems, String name){
        super(name);
        this.sems=sems;
        start();
    }

    private void Activity_6() {
        System.out.println("Activity_6 running");
    }

    public void run(){
        try {
            sems[5].acquire();
        } catch (InterruptedException e){
            System.out.println(e.toString());
        }
        Activity_6();
        sems[8].release();
    }
}
