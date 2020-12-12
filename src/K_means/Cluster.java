package K_means;

public class Cluster {

	Cluster(int clusterNumber, double X_Centroid, double Y_Centroid) {
		super();
		this.clusterNumber = clusterNumber;
		this.X_Centroid = X_Centroid;
		this.Y_Centroid = Y_Centroid;
	}

	private double X_Centroid;
	public double getX_Centroid() {
		return X_Centroid;
	}
	public void setX_Centroid(double x_Centroid) {
		this.X_Centroid = x_Centroid;
	}

	private double Y_Centroid;
	public double getY_Centroid() {
		return Y_Centroid;
	}
	public void setY_Centroid(double y_Centroid) {
		this.Y_Centroid = y_Centroid;
	}

	private final int clusterNumber;
	public int getClusterNumber() {
		return clusterNumber;
	}

	public double calculateDistance(DataPoint dataPoint) {
		return Math.sqrt(Math.pow((getX_Centroid() - dataPoint.getX()), 2) + Math.pow((getY_Centroid() - dataPoint.getY()),2));
	}

	public void updateCentroid(DataPoint dataPoint) {
		setX_Centroid((getX_Centroid()+ dataPoint.getX())/2);
		setY_Centroid((getY_Centroid()+ dataPoint.getY())/2);
	}

}
