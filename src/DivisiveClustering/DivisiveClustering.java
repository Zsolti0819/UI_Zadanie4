package DivisiveClustering;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Main.Main;

public class DivisiveClustering {

    List<DataPoint> dataPoints = new ArrayList<>();
    List<DataPoint> centroids = new ArrayList<>();

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

    public void run () {

        dataPoints = generatePoints();

        for (DataPoint point : dataPoints) {
            point.setChecked(false);
            point.setClusterNumber(0);
        }

        int count = 0;
        int inThisCluster = -1;
        double distance;
        for (int i = 0; i < dataPoints.size(); i++) {
            if (!dataPoints.get(i).isChecked()) {
                DataPoint centroid = new DataPoint();
                centroid.setX(dataPoints.get(i).getX());
                centroid.setY(dataPoints.get(i).getY());
                centroid.setClusterNumber(dataPoints.get(i).getClusterNumber());
                centroids.add(centroid);
                double minValue = Integer.MAX_VALUE;
                for (int j = 0; j < dataPoints.size(); j++) {
                    distance = EuclideanDistance(centroid, dataPoints.get(j));
                    /*
                    if (distance <= Main.MAXOFFSETBETWEENCLUSTERS && !dataPoint.isChecked()) {
                        dataPoint.setClusterNumber(i);
                        dataPoint.setChecked(true);
                        updateCentroid(centroid, dataPoint);
                        inThisCluster++;
                    }
                     */
                    if (distance < minValue && !dataPoints.get(j).isChecked()) {
                        minValue = distance;
                        inThisCluster = j;
                        count++;
                    }

                }

                if (inThisCluster != -1) {
                    dataPoints.get(inThisCluster).setChecked(true);
                    dataPoints.get(inThisCluster).setClusterNumber(dataPoints.get(i).getClusterNumber());
                    updateCentroid(centroid, dataPoints.get(inThisCluster));

                }

                count = 0;
                inThisCluster = -1;
                /*
                if (inThisCluster > 0)
                    centroids.add(centroid);
                else
                    dataPoints.get(i).setChecked(false);
                 */
            }
        }
    }


    private double EuclideanDistance (DataPoint centroid, DataPoint dataPoint) {
        return Math.sqrt(Math.pow((centroid.getX() - dataPoint.getX()), 2) + Math.pow((centroid.getY() - dataPoint.getY()), 2));
    }

    private void updateCentroid(DataPoint centroid, DataPoint dataPoint) {
        centroid.setX((centroid.getX() + dataPoint.getX())/2);
        centroid.setY((centroid.getY() + dataPoint.getY())/2);
    }

    public void printToPNG() throws IOException {
        final BufferedImage image = new BufferedImage ( 10000, 10000, BufferedImage.TYPE_INT_ARGB );
        final Graphics2D graphics2D = image.createGraphics ();
        graphics2D.setPaint (Color.BLACK );
        graphics2D.fillRect (0,0,10000,10000);

        Random random = new Random();

        for (DataPoint centroid : centroids) {
            System.out.println("Hello");
            Color c = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            graphics2D.setPaint(c);
            for (DataPoint dataPoint : dataPoints)
                if (dataPoint.getClusterNumber() == centroid.getClusterNumber())
                    graphics2D.drawRect((int) dataPoint.getX() + 5000, (int) dataPoint.getY() + 5000, 10, 10);
            graphics2D.drawOval((int) centroid.getX() + 5000, (int) centroid.getY() + 5000, 50, 50);
            graphics2D.fillOval((int) centroid.getX() + 5000, (int) centroid.getY() + 5000, 50, 50);
        }

        graphics2D.dispose ();
        ImageIO.write ( image, "png", new File( "divisive_clustering.png" ) );

    }

}
