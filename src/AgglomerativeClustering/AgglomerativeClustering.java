package AgglomerativeClustering;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Main.*;

public class AgglomerativeClustering {

    List<DataPoint> centroids = new ArrayList<>();

    public void run () {
        int count = 0;
        for (DataPoint point : Main.dataPoints) {
            count++;
            point.setChecked(false);
            point.setClusterNumber(count);
        }

        int inThisCluster = 0;
        double distance;
        for (int i = 0; i < Main.dataPoints.size(); i++) {
            if (!Main.dataPoints.get(i).isChecked()) {
                DataPoint centroid = new DataPoint();
                centroid.setX(Main.dataPoints.get(i).getX());
                centroid.setY(Main.dataPoints.get(i).getY());
                centroid.setClusterNumber(i);
                for (DataPoint dataPoint : Main.dataPoints) {
                    distance = EuclideanDistance(centroid, dataPoint);
                    if (distance <= Main.MAXOFFSETBETWEENCLUSTERS && !dataPoint.isChecked()) {
                        dataPoint.setClusterNumber(i);
                        dataPoint.setChecked(true);
                        updateCentroid(centroid, dataPoint);
                        inThisCluster++;
                    }
                }
                if (inThisCluster > 1) centroids.add(centroid);
                else Main.dataPoints.get(i).setChecked(false);
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

        int count = 0;
        for (DataPoint centroid : centroids) {
            Color c = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            graphics2D.setPaint(c);
            for (DataPoint dataPoint : Main.dataPoints)
                if (dataPoint.getClusterNumber() == centroid.getClusterNumber()) {
                    count++;
                    graphics2D.drawOval((int) dataPoint.getX() + 5000, (int) dataPoint.getY() + 5000, 5, 5);
                    graphics2D.fillOval((int) dataPoint.getX() + 5000, (int) dataPoint.getY() + 5000, 5, 5);

                }
                graphics2D.drawOval((int) centroid.getX() + 5000, (int) centroid.getY() + 5000, 60, 60);
                graphics2D.fillOval((int) centroid.getX() + 5000, (int) centroid.getY() + 5000, 60, 60);
            }

        graphics2D.dispose ();
        ImageIO.write ( image, "png", new File( "agglomerative_clustering.png" ) );

    }

}
