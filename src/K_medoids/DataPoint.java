package K_medoids;

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

    private boolean medoid;
    public boolean isMedoid() {
        return medoid;
    }
    void setMedoid(boolean medoid) {
        this.medoid = medoid;
    }

}
