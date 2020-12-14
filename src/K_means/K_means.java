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

    List<Cluster> clusters = new ArrayList<>();
    Map<Cluster, List<DataPoint>> clusterRecords = new LinkedHashMap<>();

    public void run() {
        int counter = 1;

        Iterator<DataPoint> iterator = Main.dataPoints.iterator();

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
                    if(minDistance > distance && !dataPoint.isChecked()) {
                        minDistance = distance;
                        whichCluster = cluster;
                    }
                }

                assert whichCluster != null;
                dataPoint.setChecked(true);
                dataPoint.setClusterNumber(whichCluster.getClusterNumber());
                whichCluster.updateCentroid(dataPoint);
                clusterRecords.get(whichCluster).add(dataPoint);

            }
        }
    }

    private void initializeCluster(int clusterNumber, DataPoint dataPoint) {

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

        int fail = 0;
        for (Map.Entry<Cluster, List<DataPoint>> entry : clusterRecords.entrySet()) {
            Color c = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            graphics2D.setPaint(c);
            graphics2D.drawOval((int) entry.getKey().getX_Centroid()+5000, (int) entry.getKey().getY_Centroid()+5000, 60, 60);
            graphics2D.fillOval((int) entry.getKey().getX_Centroid()+5000, (int) entry.getKey().getY_Centroid()+5000, 60, 60);
            int value = entry.getKey().getClusterNumber();
            for (DataPoint datum : Main.dataPoints)
                if (datum.getClusterNumber() == value) {
                    graphics2D.drawOval((int) datum.getX() + 5000, (int) datum.getY() + 5000, 5, 5);
                    graphics2D.fillOval((int) datum.getX() + 5000, (int) datum.getY() + 5000, 5, 5);
                    if (Math.sqrt(Math.pow((entry.getKey().getX_Centroid() - datum.getX()), 2) + Math.pow((entry.getKey().getY_Centroid() - datum.getY()),2)) > Main.MAXOFFSETBETWEENCLUSTERS)
                        fail++;
                }
        }

        if (fail > 0) {
            System.out.println("Aspon jeden bod mal vacsiu vzdialenost od centroidu ako " + Main.MAXOFFSETBETWEENCLUSTERS + "-> Neuspesne zhlukovanie");
            System.out.println("Pocet takych bodov: "+fail);
        }

        graphics2D.dispose ();
        ImageIO.write ( image, "png", new File( "k_means.png" ) );

    }
}
