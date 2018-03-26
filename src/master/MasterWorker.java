package master;

import java.util.ArrayList;
import java.util.Stack;

public class MasterWorker {

	private static int[][] matrixA = { { 1, -2, 3, 4, -1 }, { -2, 3, 0, 1, 2 }, { 4, -1, 2, 1, -2 },
			{ -2, 1, 3, -1, 3 }, { 0, 2, -1, 2, 4 } };
	private static int[][] matrixB = { { 2, -4, -1, 1, -2 }, { -1, 1, -2, 2, 1 }, { 5, 0, 3, -2, -4 },
			{ 1, -2, 1, 0, 2 }, { 2, 3, -3, 0, 0 } };
	private static int[][] matrixC = new int[5][5];

	private static int anzahlThreads;
	private static Stack<int[]> work;
	static ArrayList<Thread> threads;

	public void main(int args) {
		
		anzahlThreads = args;
		System.out.println("Anzahl Threads: " + anzahlThreads + "\n");
		setWorkFromMaster();

		generateThreads();

		getMatrixC();

	}


	public static void getMatrixC() {

		for (int i = 0; i < matrixC[0].length; i++) {
			for (int j = 0; j < matrixC[1].length; j++) {
				System.out.print(matrixC[i][j] + "\t");
			}
			System.out.println();
		}

	}

	public static void generateThreads() {

		threads = new ArrayList<>();
		

		for (int i = 0; i < anzahlThreads; i++) {

			
			MasterWorkerThread mw = new MasterWorkerThread(matrixA, matrixB, matrixC, work);
			threads.add(mw);

			try {
				
				threads.get(i).join();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}
	}

	public static void setWorkFromMaster() {
		work = new Stack<int[]>();

		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {

				int[] reiheSpalte = new int[2];
				reiheSpalte[0] = x;
				reiheSpalte[1] = y;
				work.push(reiheSpalte);
			}
		}
	}

}

