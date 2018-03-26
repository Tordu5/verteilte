package Aufgabe2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class masterWorker {

    int[][] A = {{1,-2,3,4,-1},{-2,3,0,1,2},{4,-1,2,1,-2},{-2,1,3,-1,3},{0,2,-1,2,4}};
    int[][] B = {{2,-4,-1,1,-2},{-1,1,-2,2,1},{5,0,3,-2,-4},{1,-2,1,0,2},{2,3,-3,0,0}};
    int[][] C= new int[5][5];
    Queue<Calculation> work = new LinkedList<>();
    int threads = 5;

    public void createCalculationList(){

        createList();
        createAndStartWorker(threads);
        System.out.println(Arrays.toString(C[0]));
        System.out.println(Arrays.toString(C[1]));
        System.out.println(Arrays.toString(C[2]));
        System.out.println(Arrays.toString(C[3]));
        System.out.println(Arrays.toString(C[4]));

    }

    public void createList(){
        for (int x=0;x<A.length;x++){
            for (int y=0;y<A.length;y++){
                int[][] result= C;
                int[] aim= new int[]{x,y};

                for (int i=0;i<A.length;i++){
                    int[] variables = new int[] {
                            A[x][i],
                            B[i][y]
                    };
                    work.offer(new Calculation(result,aim,variables));
                }

            }
        }
    }

    public void createAndStartWorker(int threads){
        ArrayList<Worker> workers = new ArrayList<>();

        for (int i=0;i<threads; i++){
            workers.add(new Worker("Worker"+i,work));
            System.out.println("worker"+i+ " created!");
        }

        for (Thread worker:workers){
            worker.start();
        }

        workers.forEach(worker -> {
            try {
                worker.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private class Worker extends Thread {
        Queue<Calculation> work;
        Calculation calculation;
        String name;

        public Worker(String name,Queue<Calculation> work){
            this.name=name;
            this.work=work;
        }

        @Override
        public void run() {
            while (!work.isEmpty()){
                calculation=work.poll();
                System.out.print(name + " calculated =>");
                calculate();
                try {
                    sleep((int)Math.random() *1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void calculate(){
            int[][] result= calculation.getResult();
            int[] aim=calculation.getAim();
            int[] variables=calculation.getVariables();
            System.out.println(" Result["+aim[0]+"]["+aim[1]+"] += "+variables[0]+"*"+variables[1]);
            result[aim[0]][aim[1]] += variables[0]*variables[1];
        }
    }

    private class Calculation {
        int[][] result;
        int[] aim;
        int[] variables;

        public Calculation(int[][] result, int[] aim, int[] variables) {
            this.result = result;
            this.aim = aim;
            this.variables = variables;
        }

        public int[][] getResult() {
            return result;
        }

        public int[] getAim() {
            return aim;
        }

        public int[] getVariables() {
            return variables;
        }
    }

    public static void main(String[] args){
        new masterWorker().createCalculationList();
    }
}