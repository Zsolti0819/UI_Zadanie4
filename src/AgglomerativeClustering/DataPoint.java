package AgglomerativeClustering;

public class DataPoint {

	private double X;
	public double getX() {
		return X;
	}
	public void setX(double x) {
		X = x;
	}

	private double Y;
	public double getY() {
		return Y;
	}
	public void setY(double y) {
		Y = y;
	}

	private int clusterNumber;
	public int getClusterNumber() {
		return clusterNumber;
	}
	public void setClusterNumber(int clusterNumber) {
		this.clusterNumber = clusterNumber;
	}

}