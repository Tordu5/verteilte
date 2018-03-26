package parallel;

import java.util.Stack;

public class ResultatsParallelismusThread extends Thread {
	int festeGroesse;
	Stack<int[]> work;
	int[] reiheSpalte = new int[2];
	int[][] matrixA;
	int[][] matrixB;
	int[][] matrixC = new int[5][5];

	public ResultatsParallelismusThread(int[][] matrixA, int[][] matrixB, int[][] matrixC, Stack<int[]> work,
			int festeGroesse) {
		this.matrixA = matrixA;
		this.matrixB = matrixB;
		this.matrixC = matrixC;
		this.work = work;
		this.festeGroesse = festeGroesse;

	}

	public void run() {
		for (int z = 0; z < festeGroesse; z++) {

			reiheSpalte = work.pop();
			int x = reiheSpalte[0];
			int y = reiheSpalte[1];
			
			int sum = 0;
			
			for (int w = 0; w < matrixC.length; w++) {

				sum += matrixA[x][w] * matrixB[w][y];

			}
			
			matrixC[x][y] = sum;

		}
	}

}

