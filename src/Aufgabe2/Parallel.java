package Aufgabe2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class Parallel {

    int[][] A = {{1,-2,3,4,-1},{-2,3,0,1,2},{4,-1,2,1,-2},{-2,1,3,-1,3},{0,2,-1,2,4}};
    int[][] B = {{2,-4,-1,1,-2},{-1,1,-2,2,1},{5,0,3,-2,-4},{1,-2,1,0,2},{2,3,-3,0,0}};
    int[][] revB = getReversedB();
    int[][] C= new int[5][5];
    int threads = 10;
    Semaphore sema = new Semaphore(threads);

    public static void main(String[] args){
        new Parallel().calculate();
    }

    public void calculate(){
        Thread thread = new Thread(new Tier1Calculation(A,revB,C,sema));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println(Arrays.toString(C[0]));
        System.out.println(Arrays.toString(C[1]));
        System.out.println(Arrays.toString(C[2]));
        System.out.println(Arrays.toString(C[3]));
        System.out.println(Arrays.toString(C[4]));
    }

    private int[][] getReversedB(){
        int[][] revB = new int[B.length][B[1].length];
        for (int i=0; i<B.length;i++){
            for (int j=0; j<B.length;j++){
                revB[j][i] = B[i][j];
            }
        }
        return revB;
    }

    public class Tier1Calculation implements Runnable{
        int[][] A;
        int[][] revB;
        int[][] result;
        int[] aim;
        Semaphore sema;
        ArrayList<Thread> tier1Calculations = new ArrayList<>();

        public Tier1Calculation(int[][] a, int[][] revB, int[][] result, Semaphore sema) {
            A = a;
            this.revB = revB;
            this.result = result;
            this.sema = sema;
        }

        @Override
        public void run() {
            for (int i=0; i<A.length;i++){
                for (int j = 0; j< B.length; j++){
                    aim=new int[]{i,j};
                    tier1Calculations.add(new Thread(new Tier2Calculation(A[i],revB[j],result,aim,sema)));
                }
            }

            tier1Calculations.parallelStream().forEach(thread -> {
                try {
                    thread.start();
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public class Tier2Calculation implements Runnable{
        int[] A;
        int[] revB;
        int[][] result;
        int[] aim;
        Semaphore sema;
        ArrayList<Thread> tier2Calculations = new ArrayList<>();

        public Tier2Calculation(int[] a, int[] revB, int[][] result,int[] aim, Semaphore sema) {
            A = a;
            this.revB = revB;
            this.result = result;
            this.aim = aim;
            this.sema = sema;
        }

        @Override
        public void run() {
            for (int i= 0;i<A.length;i++){
                tier2Calculations.add(new Thread(new Tier3Calculation(A[i],revB[i],result,aim,sema)));
            }

            tier2Calculations.parallelStream().forEach(thread -> {
                try {
                    thread.start();
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public class Tier3Calculation implements Runnable{
        int A;
        int revB;
        int[][] result;
        int[] aim;
        Semaphore sema;

        public Tier3Calculation(int a, int revB, int[][] result,int[] aim, Semaphore sema) {
            A = a;
            this.revB = revB;
            this.result = result;
            this.aim=aim;
            this.sema = sema;
        }

        @Override
        public void run() {
            try {
                sema.acquire();
                this.result[aim[0]][aim[1]] += (A * revB);
                System.out.println("Calculated position ["+aim[0]+"] ["+aim[1]+"] !");
                sema.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
