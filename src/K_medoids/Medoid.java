package K_medoids;

import java.util.ArrayList;
import java.util.List;
import Main.*;

public class Medoid {

    Medoid(DataPoint centerOfMedoid, double cost) {
        this.centerOfMedoid = centerOfMedoid;
        this.cost = cost;
    }

    private final List<DataPoint> dataPoints = new ArrayList<>();
    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    private final DataPoint centerOfMedoid;
    public DataPoint getCenterOfMedoid() {
        return centerOfMedoid;
    }

    private double cost;
    double getCost() {
        return cost;
    }
    void setCost(double cost) {
        this.cost = cost;
    }

}
