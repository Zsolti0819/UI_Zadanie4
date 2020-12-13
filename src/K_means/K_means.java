package K_means;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;
import Main.*;

public class K_means {

    List<DataPoint> data = new ArrayList<>();
    List<Cluster> clusters = new ArrayList<>();
    Map<Cluster, List<DataPoint>> clusterRecords = new LinkedHashMap<>();

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
        int counter = 1;
        data = generatePoints();
        Iterator<DataPoint> iterator = data.iterator();

        DataPoint dataPoint;

        while(iterator.hasNext()) {
            dataPoint = iterator.next();
            if (counter <= Main.CLUSTERCOUNT) {
                dataPoint.setClusterNumber(counter);
                initializeCluster(counter, dataPoint);
                counter++;
            }
            else {

                double minDistance = Integer.MAX_VALUE;
                Cluster whichCluster = null;

                for(Cluster cluster : clusters) {
                    double distance = cluster.calculateDistance(dataPoint);
                    if(minDistance > distance) {
                        minDistance = distance;
                        whichCluster = cluster;
                    }
                }

                assert whichCluster != null;
                dataPoint.setClusterNumber(whichCluster.getClusterNumber());
                whichCluster.updateCentroid(dataPoint);
                clusterRecords.get(whichCluster).add(dataPoint);

            }
        }
    }

    public void initializeCluster(int clusterNumber, DataPoint dataPoint) {

        Cluster cluster = new Cluster(clusterNumber, dataPoint.getX(), dataPoint.getY());
        clusters.add(cluster);
        List<DataPoint> clusterDataPoint = new ArrayList<>();
        clusterDataPoint.add(dataPoint);
        clusterRecords.put(cluster, clusterDataPoint);

    }

    public void printToPNG() throws IOException {
        final BufferedImage image = new BufferedImage ( 10000, 10000, BufferedImage.TYPE_INT_ARGB );
        final Graphics2D graphics2D = image.createGraphics ();
        graphics2D.setPaint (Color.BLACK );
        graphics2D.fillRect (0,0,10000,10000);

        Random random = new Random();

        for (Map.Entry<Cluster, List<DataPoint>> entry : clusterRecords.entrySet()) {
            Color c = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            graphics2D.setPaint(c);
            graphics2D.drawOval((int) entry.getKey().getX_Centroid()+5000, (int) entry.getKey().getY_Centroid()+5000, 50, 50);
            graphics2D.fillOval((int) entry.getKey().getX_Centroid()+5000, (int) entry.getKey().getY_Centroid()+5000, 50, 50);
            int value = entry.getKey().getClusterNumber();
            for (DataPoint datum : data)
                if (datum.getClusterNumber() == value)
                    graphics2D.drawRect((int) datum.getX() + 5000, (int) datum.getY() + 5000, 10, 10);

        }

        graphics2D.dispose ();
        ImageIO.write ( image, "png", new File( "k_means.png" ) );

    }
}
