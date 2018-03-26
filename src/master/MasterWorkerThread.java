package master;

import java.util.Stack;

public class MasterWorkerThread extends Thread {

	int[][] matrixA;
	int[][] matrixB;
	int[][] matrixC;
	Stack<int[]> work;
	int[] reiheSpalte = new int[2];

	public MasterWorkerThread(int[][] matrixA, int[][] matrixB, int[][] matrixC, Stack<int[]> work) {

		this.matrixA = matrixA;
		this.matrixB = matrixB;
		this.matrixC = matrixC;
		this.work = work;
		start();
	}

	public void run() {

		while (true) {

			if (!work.empty()) {

				reiheSpalte = work.pop();
				int x = reiheSpalte[0];
				int y = reiheSpalte[1];

				for (int z = 0; z < 5; z++) {

					matrixC[x][y] += matrixA[x][z] * matrixB[z][y];

				}

			}

			else if (work.empty()) {
				return;
			}

		}

	}
}
