package Aufgabe2;

import java.util.ArrayList;
import java.util.Arrays;

public class Master {

    int[][] A = {{1,-2,3,4,-1},{-2,3,0,1,2},{4,-1,2,1,-2},{-2,1,3,-1,3},{0,2,-1,2,4}};
    int[][] B = {{2,-4,-1,1,-2},{-1,1,-2,2,1},{5,0,3,-2,-4},{1,-2,1,0,2},{2,3,-3,0,0}};
    int[][] C= new int[5][5];
    int threads = 25;
    ArrayList<Runnable> runnables = new ArrayList<>();
    
    public void createCalculationList(){

        for (int step=0; step< A.length*B[1].length;step+=threads){

            for (int count = 0 ; count < threads && step+count < A.length*B.length ; count++){
                runnables.add(new Calculation(A,B,C,(step+count)));
            }
            System.out.println("All "+ threads +" Threads starting");

            runnables.parallelStream().forEach( runnable -> {
                Thread thread = new Thread(runnable);
                thread.start();
                System.out.println("thread started");
                try {
                    thread.join();
                    System.out.println("Thread finished");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            System.out.println("All "+ threads +" threads finished");
            runnables.clear();

        }

        System.out.println(Arrays.toString(C[0]));
        System.out.println(Arrays.toString(C[1]));
        System.out.println(Arrays.toString(C[2]));
        System.out.println(Arrays.toString(C[3]));
        System.out.println(Arrays.toString(C[4]));

    }

    private class Calculation implements Runnable{
        int[][] A;
        int[][] B;
        int[][] result;
        int step;

        public Calculation(int[][] a, int[][] b, int[][] result, int step) {
            A = a;
            B = b;
            this.result = result;
            this.step = step;
        }

        @Override
        public void run() {
            int i = step/A.length;
            int j = step%B.length;

            int calculation = 0;
            for (int n=0 ; n<A.length && n<B[i].length ;n++){
                calculation += (A[i][n] * B[n][j]);
            }
            result[i][j]=calculation;
        }
    }

    public static void main(String[] args){
        new Master().createCalculationList();
    }
}