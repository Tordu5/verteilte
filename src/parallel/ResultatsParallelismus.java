package parallel;

import java.util.ArrayList;
import java.util.Stack;

public class ResultatsParallelismus {

	static int[][] matrixA = { { 1, -2, 3, 4, -1 }, { -2, 3, 0, 1, 2 }, { 4, -1, 2, 1, -2 }, { -2, 1, 3, -1, 3 },
			{ 0, 2, -1, 2, 4 } };

	static int[][] matrixB = { { 2, -4, -1, 1, -2 }, { -1, 1, -2, 2, 1 }, { 5, 0, 3, -2, -4 }, { 1, -2, 1, 0, 2 },
			{ 2, 3, -3, 0, 0 } };

	static int[][] matrixC = new int[5][5];

	static Stack<int[]> workList = new Stack<int[]>();
	static ArrayList<Thread> threads = new ArrayList<>();
	static int anzahlThreads;
	static int rest;
	static int arbeitsUmfang;

	public void main(int args) {
		
		anzahlThreads = args; // aus main
		
		rest = (matrixC[0].length*matrixC[1].length) % anzahlThreads;
		arbeitsUmfang = ((matrixC[0].length*matrixC[1].length) - rest) / anzahlThreads;

		System.out.println("Anzahl Threads: " + anzahlThreads);
		System.out.println("Rest: " + rest);
		System.out.println("Arbeitsumfang: " + arbeitsUmfang);

		fillWorkList();

		aufgabenTeilen();

		generateThreads();

		System.out.println();

		getMatrixC();

	}

	public static void fillWorkList() {

		for (int i = 0; i < matrixC.length; i++) {
			for (int j = 0; j < matrixC.length; j++) {
				int[] reiheSpalte = new int[2];
				reiheSpalte[0] = i;
				reiheSpalte[1] = j;
				workList.push(reiheSpalte);
			}
		}

	}

	public static void generateThreads() {
		for (int i = 0; i < threads.size(); i++) {
			try {
				threads.get(i).join();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void aufgabenTeilen() {
		if (rest == 0) {
			for (int q = 0; q < anzahlThreads; q++) {
				ResultatsParallelismusThread t = new ResultatsParallelismusThread(matrixA, matrixB, matrixC,workList, arbeitsUmfang);
				threads.add(t);
				t.start();

			}
		} else {
			for (int q = 0; q < anzahlThreads - rest; q++) {
				ResultatsParallelismusThread t = new ResultatsParallelismusThread(matrixA, matrixB, matrixC,workList, arbeitsUmfang);
				threads.add(t);
				t.start();
			}
			

			for (int q = 0; q < rest; q++) {
				ResultatsParallelismusThread t = new ResultatsParallelismusThread(matrixA, matrixB, matrixC,workList, arbeitsUmfang + 1);
				threads.add(t);
				t.start();

			}

		}
	}

	public static void getMatrixC() {

		for (int i = 0; i < matrixC[0].length; i++) {
			for (int j = 0; j < matrixC[1].length; j++) {
				System.out.print(matrixC[i][j] + "\t");
			}
			System.out.println();
		}

	}

}

