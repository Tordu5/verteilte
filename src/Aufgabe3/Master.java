package Aufgabe3;


import java.util.ArrayList;

public class Master {

    public static void main(String[] args){

        Master master = new Master();
        master.compare(13,7);



    }

    public void compare(int n, int k){
        final long timeStart = System.currentTimeMillis();
        this.calculate(n,k);
        final long timeEnd = System.currentTimeMillis();
        final long normalDuration = timeEnd-timeStart;
        System.out.println("Normal Duration =       "+ normalDuration);
        final long optitimeStart = System.currentTimeMillis();
        this.optimizedCalculate(n,k);
        final long optitimeEnd = System.currentTimeMillis();
        final long optiDuration = optitimeEnd-optitimeStart;
        System.out.println("Optimized Duration =    "+ optiDuration);
        System.out.println("With n =    "+n +" and k =  "+k);
    }

    public void calculate(int n, int k){
        Calculate calculate =new Calculate(n,k);
        Thread thread = new Thread(calculate);
        thread.start();
        try {
            thread.join();
            System.out.println("Result is =     "+ calculate.getResult());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void optimizedCalculate(int n, int k){
        OptimizedCalculate calculate =new OptimizedCalculate(n,k);
        Thread thread = new Thread(calculate);
        thread.start();
        try {
            thread.join();
            System.out.println("Optimized Result is =     "+ calculate.getResult());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class Calculate implements Runnable{
        int n;
        int k;
        int result=0;

        public Calculate(int n, int k) {
            this.n = n;
            this.k = k;
        }

        @Override
        public void run() {
            if (n == k || k == 0){
                result=1;
            } else if ( k == 1 || n==k+1){
                result = n;
            } else {
                if (k*2>n){
                    k=n-k;
                }
                ArrayList calculations =new ArrayList<Runnable>();
                calculations.add(new Calculate(n-1,k-1));
                calculations.add(new Calculate(n-1,k));

                calculations.parallelStream().forEach(calculation ->{
                    Thread thread =new Thread((Calculate)calculation);
                    thread.start();
                    try {
                        thread.join();
                        result += ((Calculate) calculation).getResult();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });


            }
        }

        public int getResult(){
            return this.result;
        }
    }

    public class OptimizedCalculate implements Runnable{
        int n;
        int k;
        int result=0;
        boolean optimisation = true;

        public OptimizedCalculate(int n, int k) {
            this.n = n;
            this.k = k;
        }

        @Override
        public void run() {
            if (n == k || k == 0){
                result=1;
            } else if ( k == 1 || n==k+1){
                result = n;
            } else {
                if (k*2>n){
                    k=n-k;
                }
                if (n==k*2 && optimisation){
                    OptimizedCalculate calculate = new OptimizedCalculate(n-1,k-1);
                    Thread thread =new Thread(calculate);
                    thread.start();
                    try {
                        thread.join();
                        result = calculate.getResult()*2;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    ArrayList calculations =new ArrayList<OptimizedCalculate>();
                    calculations.add(new OptimizedCalculate(n-1,k-1));
                    calculations.add(new OptimizedCalculate(n-1,k));

                    calculations.parallelStream().forEach(calculation ->{
                        Thread thread =new Thread((OptimizedCalculate)calculation);
                        thread.start();
                        try {
                            thread.join();
                            result += ((OptimizedCalculate) calculation).getResult();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });

                }
            }
        }

        public int getResult(){
            return this.result;
        }
    }
}
