package Main;

import java.io.IOException;
import java.util.Scanner;

import AgglomerativeClustering.AgglomerativeClustering;
import DivisiveClustering.*;
import K_means.*;
import K_medoids.*;

public class Main {
	public static int MAXOFFSETBETWEENCLUSTERS = 750;
	public static int CLUSTERCOUNT = 20;
	public static int POINTCOUNT = 40020;

	public static void main(String[] args) throws IOException {

		Scanner scanner = new Scanner(System.in);
		int choice = -1;

		while (choice != 0) {
			System.out.println("Vyberte jeden z moznosti: ");
			System.out.println("0 - Skoncit program");
			System.out.println("1 - K Means klastrovanie");
			System.out.println("2 - K Medoids klastrovanie");
			System.out.println("3 - Aglomerativne klastrovanie");
			System.out.println("4 - Divizivne klastrovanie");

			choice = scanner.nextInt();

			if (choice == 1) {
				long startTime = System.nanoTime();
				System.out.println("K Means klastrovanie");
				K_means k_means = new K_means();
				k_means.run();
				k_means.printToPNG();
				long endTime = System.nanoTime();
				long totalTime = (endTime - startTime) / 1000000;
				System.out.println("Beh programu: "+ totalTime+" ms\n");
			}

			if (choice == 2) {
				long startTime = System.nanoTime();
				System.out.println("K Medoids klastrovanie");
				K_medoids clustering = new K_medoids();
				clustering.run();
				clustering.printToPNG();
				long endTime = System.nanoTime();
				long totalTime = (endTime - startTime) / 1000000;
				System.out.println("Beh programu: "+ totalTime+" ms\n");
			}

			if (choice == 3) {
				long startTime = System.nanoTime();
				System.out.println("Aglomerativne klastrovanie");
				AgglomerativeClustering agglomerativeClustering = new AgglomerativeClustering();
				agglomerativeClustering.run();
				agglomerativeClustering.printToPNG();
				long endTime = System.nanoTime();
				long totalTime = (endTime - startTime) / 1000000;
				System.out.println("Beh programu: "+ totalTime+" ms\n");
			}

			if (choice == 4) {
				long startTime = System.nanoTime();
				System.out.println("Divizivne klastrovanie");
				DivisiveClustering divisiveClustering = new DivisiveClustering();
				divisiveClustering.run();
				divisiveClustering.printToPNG();
				long endTime = System.nanoTime();
				long totalTime = (endTime - startTime) / 1000000;
				System.out.println("Beh programu: "+ totalTime+" ms\n");
			}
		}
	}
}
