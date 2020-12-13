package Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

	public static List<DataPoint> dataPoints = new ArrayList<>();

	public static ArrayList<DataPoint> generatePoints() {
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

	public static void main(String[] args) throws IOException {

		dataPoints = generatePoints();

		Scanner scanner = new Scanner(System.in);
		int choice;

		System.out.println("Vyberte jeden z moznosti: ");
		System.out.println("1 - K Means zhlukovanie");
		System.out.println("2 - K Medoids zhlukovanie");
		System.out.println("3 - Aglomerativne zhlukovanie");
		System.out.println("4 - Divizivne zhlukovanie");
		System.out.println("5 - Spustit vsetky algoritmy");

		choice = scanner.nextInt();

		if (choice == 1) {
			long startTime = System.nanoTime();
			System.out.println("K Means zhlukovanie");
			K_means k_means = new K_means();
			k_means.run();
			System.out.println("Algoritmus prebehlo.\nVykreslenie ...");
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
			System.out.println("Algoritmus prebehlo.\nVykreslenie ...");
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
			System.out.println("Algoritmus prebehlo.\nVykreslenie ...");
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
			System.out.println("Algoritmus prebehlo.\nVykreslenie ...");
			divisiveClustering.printToPNG();
			long endTime = System.nanoTime();
			long totalTime = (endTime - startTime) / 1000000;
			System.out.println("Beh programu: "+ totalTime+" ms\n");
		}
		if (choice == 5) {
			long startTime = System.nanoTime();
			System.out.println("K Means zhlukovanie");
			K_means k_means = new K_means();
			k_means.run();
			System.out.println("Algoritmus prebehlo.\nVykreslenie ...");
			k_means.printToPNG();
			long endTime = System.nanoTime();
			long totalTime = (endTime - startTime) / 1000000;
			System.out.println("Beh programu: "+ totalTime+" ms\n");

			long startTime2 = System.nanoTime();
			System.out.println("K Medoids zhlukovanie");
			K_medoids clustering = new K_medoids();
			clustering.run();
			System.out.println("Algoritmus prebehlo.\nVykreslenie ...");
			clustering.printToPNG();
			long endTime2 = System.nanoTime();
			long totalTime2 = (endTime2 - startTime2) / 1000000;
			System.out.println("Beh programu: "+ totalTime2+" ms\n");

			long startTime3 = System.nanoTime();
			System.out.println("Aglomerativne zhlukovanie");
			AgglomerativeClustering agglomerativeClustering = new AgglomerativeClustering();
			agglomerativeClustering.run();
			System.out.println("Algoritmus prebehlo.\nVykreslenie ...");
			agglomerativeClustering.printToPNG();
			long endTime3 = System.nanoTime();
			long totalTime3 = (endTime3 - startTime3) / 1000000;
			System.out.println("Beh programu: "+ totalTime3+" ms\n");


			long startTime4 = System.nanoTime();
			System.out.println("Divizivne zhlukovanie");
			DivisiveClustering divisiveClustering = new DivisiveClustering();
			divisiveClustering.run();
			System.out.println("Algoritmus prebehlo.\nVykreslenie ...");
			divisiveClustering.printToPNG();
			long endTime4 = System.nanoTime();
			long totalTime4 = (endTime4 - startTime4) / 1000000;
			System.out.println("Beh programu: "+ totalTime4+" ms\n");
		}

	}
}
