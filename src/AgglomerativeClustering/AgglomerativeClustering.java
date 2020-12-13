package AgglomerativeClustering;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Main.Main;

public class AgglomerativeClustering {

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

        int count = 0;
        for (DataPoint point : dataPoints) {
            count++;
            point.setChecked(false);
            point.setClusterNumber(count);
        }

        int inThisCluster = 0;
        double distance;
        for (int i = 0; i < dataPoints.size(); i++) {
            if (!dataPoints.get(i).isChecked()) {
                DataPoint centroid = new DataPoint();
                centroid.setX(dataPoints.get(i).getX());
                centroid.setY(dataPoints.get(i).getY());
                centroid.setClusterNumber(i);
                for (DataPoint dataPoint : dataPoints) {
                    distance = EuclideanDistance(centroid, dataPoint);
                    if (distance <= Main.MAXOFFSETBETWEENCLUSTERS && !dataPoint.isChecked()) {
                        dataPoint.setClusterNumber(i);
                        dataPoint.setChecked(true);
                        updateCentroid(centroid, dataPoint);
                        inThisCluster++;
                    }
                }
                if (inThisCluster > 0) centroids.add(centroid);
                else dataPoints.get(i).setChecked(false);
                inThisCluster = 0;
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
            Color c = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            graphics2D.setPaint(c);
            for (DataPoint dataPoint : dataPoints)
                if (dataPoint.getClusterNumber() == centroid.getClusterNumber())
                    graphics2D.drawRect((int) dataPoint.getX() + 5000, (int) dataPoint.getY() + 5000, 10, 10);
            graphics2D.drawOval((int) centroid.getX() + 5000, (int) centroid.getY() + 5000, 50, 50);
            graphics2D.fillOval((int) centroid.getX() + 5000, (int) centroid.getY() + 5000, 50, 50);
        }

        graphics2D.dispose ();
        ImageIO.write ( image, "png", new File( "agglomerative_clustering.png" ) );

    }

}
