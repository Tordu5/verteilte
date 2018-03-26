package Aufgabe1_ReferenceUebergabe;

import java.util.ArrayList;

public class CustomThread extends Thread {
    private ArrayList<Thread> dependencies = new ArrayList<>();

    public CustomThread(String name){
        super(name);
        //start();
    }

    public CustomThread(String name,Thread... dependency){
        super(name);
        for (Thread depend:dependency){
            this.dependencies.add(depend);
        }
    }

    private void activity(){
        System.out.println(getName() + " is running");
    }

    public void run() {
        if(dependencies!=null) {
            dependencies.parallelStream().forEach(dependency -> {
                try {
                    dependency.join();
                    System.out.println(dependency.getName() + " finished");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        activity();
    }

}
