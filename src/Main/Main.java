package Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import AgglomerativeClustering.AgglomerativeClustering;
import DivisiveClustering.*;
import K_means.*;
import K_medoids.*;

public class Main {
	public static int MAXOFFSETBETWEENCLUSTERS = 750;
	public static int CLUSTERCOUNT = 20;
	public static int POINTCOUNT = 40020;

	/*
	private static ArrayList<DataPoint> generatePoints() {
		ArrayList<DataPoint> dataSet = new ArrayList<>(20);
		int max = 5000;
		int maxOffset = 100;

		Random random = new Random();
		for (int i = 0; i < 20; i++) {
			int randomX = (random.nextInt(2*max)-max);
			int randomY = (random.nextInt(2*max)-max);
			DataPoint dataPoint = new DataPoint();
			dataPoint.setX(randomX);
			dataPoint.setY(randomY);
			if (!dataSet.contains(dataPoint))
				dataSet.add(dataPoint);

		}

		while (dataSet.size() != Main.POINTCOUNT) {
			DataPoint randomPoint = dataSet.get(random.nextInt(dataSet.size()));
			int X_offset = (random.nextInt(2*maxOffset)-maxOffset);
			int Y_offset = (random.nextInt(2*maxOffset)-maxOffset);
			double randomPointX = randomPoint.getX()+X_offset;
			double randomPointY = randomPoint.getY()+Y_offset;
			DataPoint dataPoint = new DataPoint();
			dataPoint.setX(randomPointX);
			dataPoint.setY(randomPointY);
			dataSet.add(dataPoint);
		}
		return dataSet;
	}

	 */

	public static void main(String[] args) throws IOException {

		Scanner scanner = new Scanner(System.in);
		int choice = -1;

		while (choice != 0) {
			System.out.println("Vyberte jeden z moznosti: ");
			System.out.println("0 - Skoncit program");
			System.out.println("1 - K Means zhlukovanie");
			System.out.println("2 - K Medoids zhlukovanie");
			System.out.println("3 - Aglomerativne zhlukovanie");
			System.out.println("4 - Divizivne zhlukovanie");

			choice = scanner.nextInt();

			if (choice == 1) {
				long startTime = System.nanoTime();
				System.out.println("K Means zhlukovanie");
				K_means k_means = new K_means();
				k_means.run();
				k_means.printToPNG();
				long endTime = System.nanoTime();
				long totalTime = (endTime - startTime) / 1000000;
				System.out.println("Beh programu: "+ totalTime+" ms\n");
			}

			if (choice == 2) {
				long startTime = System.nanoTime();
				System.out.println("K Medoids zhlukovanie");
				K_medoids clustering = new K_medoids();
				clustering.run();
				clustering.printToPNG();
				long endTime = System.nanoTime();
				long totalTime = (endTime - startTime) / 1000000;
				System.out.println("Beh programu: "+ totalTime+" ms\n");
			}

			if (choice == 3) {
				long startTime = System.nanoTime();
				System.out.println("Aglomerativne zhlukovanie");
				AgglomerativeClustering agglomerativeClustering = new AgglomerativeClustering();
				agglomerativeClustering.run();
				agglomerativeClustering.printToPNG();
				long endTime = System.nanoTime();
				long totalTime = (endTime - startTime) / 1000000;
				System.out.println("Beh programu: "+ totalTime+" ms\n");
			}

			if (choice == 4) {
				long startTime = System.nanoTime();
				System.out.println("Divizivne zhlukovanie");
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
