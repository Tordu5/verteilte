package Aufgabe3;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        int n = 20;
        int k = 15;


        new Main().calculate(n,k);
    }

    public void calculate(int n,int k){

        Calculation rechner = new Calculation(n, k);

        try {
            rechner.start();
            rechner.join();
            System.out.println(rechner.getResult());
        } catch (InterruptedException e) {

        }
    }


    public class Calculation extends Thread{
        int n;
        int k;
        Calculation a;
        Calculation b;
        int result;

        public Calculation (int n,int k) {
            this.n=n;
            this.k=k;
        }

        public int getResult(){
            return this.result;
        }

        @Override
        public void run() {
            if (k == 0 || k == n) {
                result = 1;
            } else if (k == 1 || k == (n - 1)) {
                result = n;
            } else {
                if (k > n / 2) {
                    k = n - k;
                }

                a = new Calculation(n - 1, k - 1);
                b = new Calculation(n - 1, k);
                a.start();
                b.start();

                try {
                    a.join();
                    b.join();
                } catch (InterruptedException e) {

                }

                result = a.getResult() + b.getResult();
            }
        }

    }
}
