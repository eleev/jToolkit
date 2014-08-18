package jToolkit4FixedPipeline.terrain.utils;

import jToolkit4FixedPipeline.vector.Vector3f;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 06.06.13
 * Time: 9:22
 * To change this template use File | Settings | File Templates.
 */
public class DistanceCalculator {
    private List<Vector3f> points;
    private List<Float> distances;

    public DistanceCalculator () {
        distances = new LinkedList<Float>();
        points = new LinkedList<Vector3f>();
    }

    public void addElem (final Vector3f point) {
        points.add(point);
        calculator();
    }

    private void calculator () {
        if (points.size() > 1) {
            for (int i = points.size() - 2; i > -1 ; i--) {
                distances.add(points.get(points.size() - 1).distance(points.get(i)));
            }
        }
    }

    public List<Float> getDistances() {
        return distances;
    }

    public List<Vector3f> getPoints () {
        return points;
    }

    @Override
    public String toString () {
        return "points : " + points.toString() + "\ndistance: " + distances.toString();
    }
}
