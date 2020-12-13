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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	private boolean checked;

	public boolean isCentroid() {
		return centroid;
	}

	public void setCentroid(boolean centroid) {
		this.centroid = centroid;
	}

	private boolean centroid;

}