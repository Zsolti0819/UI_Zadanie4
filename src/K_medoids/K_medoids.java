package K_medoids;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Main.*;

public class K_medoids {

    List<Medoid> medoids = new ArrayList<>();
    List<Medoid> finalMedoids = new ArrayList<>();
    List<DataPoint> dataPoints;

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

    public void run() {

        dataPoints = generatePoints();

        extractRandomMedoids(dataPoints, finalMedoids);
        calculateCost(dataPoints, finalMedoids);

        while (true) {
            extractRandomMedoids(dataPoints, medoids);
            calculateCost(dataPoints, medoids);

            double sum = 0;
            double sumfinal = 0;
            for (Medoid medoid : medoids)
                sum += medoid.getCost();

            for (Medoid finalMedoid : finalMedoids)
                sumfinal += finalMedoid.getCost();

            if (sum >= sumfinal)
                break;

            finalMedoids = medoids;
            finalMedoidsSetFalse(medoids);
        }
    }

    public void printToPNG() throws IOException {
        final BufferedImage image = new BufferedImage ( 10000, 10000, BufferedImage.TYPE_INT_ARGB );
        final Graphics2D graphics2D = image.createGraphics ();
        graphics2D.setPaint (Color.BLACK);
        graphics2D.fillRect (0,0,10000,10000);

        Random random = new Random();

        for (Medoid finalMedoid : finalMedoids) {
            Color c = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            graphics2D.setPaint(c);
            graphics2D.drawOval((int) finalMedoid.getCenterOfMedoid().getX() + 5000, (int) finalMedoid.getCenterOfMedoid().getY() + 5000, 50, 50);
            graphics2D.fillOval((int) finalMedoid.getCenterOfMedoid().getX() + 5000, (int) finalMedoid.getCenterOfMedoid().getY() + 5000, 50, 50);
            for (int j = 0; j < finalMedoid.getDataPoints().size(); j++) {
                graphics2D.drawRect((int) finalMedoid.getDataPoints().get(j).getX() + 5000, (int) finalMedoid.getDataPoints().get(j).getY() + 5000, 5, 5);
            }
        }

        graphics2D.dispose ();
        ImageIO.write ( image, "png", new File( "k_medoids.png"));
    }

    private void extractRandomMedoids (List<DataPoint> dataSet, List<Medoid> medoids) {
        Random random = new Random();

        for (int i = 0; i < Main.CLUSTERCOUNT; i++) {
            int index = random.nextInt(dataSet.size());
            DataPoint item = dataSet.get(index);
            if (!(item.isMedoid())) {
                item.setMedoids(true);
                Medoid medoid = new Medoid(item, 0);

                if (medoids.size() == Main.CLUSTERCOUNT)
                    medoids.set(random.nextInt(Main.CLUSTERCOUNT), medoid);

                else
                    medoids.add(medoid);

            }
        }
    }

    private void calculateCost(List<DataPoint> dataPoints, List<Medoid> medoids) {

        for (DataPoint dataPoint : dataPoints) {
            double minDistance = Double.MAX_VALUE;
            int indexMin = 0;
            for (int j = 0; j < medoids.size(); j++) {
                double distance = EuclidenDistance(medoids.get(j), dataPoint);
                if (distance < minDistance) {
                    minDistance = distance;
                    indexMin = j;
                }
            }
            medoids.get(indexMin).getDataPoints().add(dataPoint);
            double oldCost = medoids.get(indexMin).getCost();
            medoids.get(indexMin).setCost(oldCost + minDistance);
        }
    }

    private double EuclidenDistance(Medoid medoid, DataPoint dataPoint) {
        return Math.sqrt(Math.pow(Math.abs(dataPoint.getX() - medoid.getCenterOfMedoid().getX()), 2) + Math.pow(Math.abs(dataPoint.getY() - medoid.getCenterOfMedoid().getY()), 2));
    }

    private void finalMedoidsSetFalse(List<Medoid> finalMedoids) {
        for (Medoid medoid : finalMedoids) {
            medoid.getCenterOfMedoid().setMedoids(false);
        }
    }

}
