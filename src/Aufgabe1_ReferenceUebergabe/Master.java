package Aufgabe1_ReferenceUebergabe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class Master {

    public static void main(String args[]) throws InterruptedException {

        Thread thread1 = new CustomThread("thread 1");
        Thread thread2 = new CustomThread("thread 2",thread1);
        Thread thread3 = new CustomThread("thread 3",thread1);
        Thread thread4 = new CustomThread("thread 4",thread3);
        Thread thread5 = new CustomThread("thread 5",thread2,thread3);
        Thread thread6 = new CustomThread("thread 6",thread3);
        Thread thread7 = new CustomThread("thread 7",thread4,thread5,thread6);

        thread1.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread4.start();
        thread3.start();
        thread2.start();

    }
}
