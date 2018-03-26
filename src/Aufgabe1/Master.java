package Aufgabe1;

import java.util.concurrent.Semaphore;

public class Master {

    public static void main(String args[]){

        Semaphore[] sems = new Semaphore[9];

        for(int i = 0; i<sems.length;i++){
            sems[i] = new Semaphore(0);
        }

        new Thread_1(sems, "Thread_1");
        new Thread_2(sems, "Thread_2");
        new Thread_3(sems, "Thread_3");
        new Thread_4(sems, "Thread_4");
        new Thread_5(sems, "Thread_5");
        new Thread_6(sems, "Thread_6");
        new Thread_7(sems, "Thread_7");
    }
}
