package org.macalester.edu.comp124.hw5;

import java.util.*;
import java.util.List;

/**
 * Recognizer to recognize 2D gestures. Uses the $1 gesture recognition algorithm.
 */
public class Recognizer {

    /**
     * Constructs a recognizer object
     */
    public Recognizer(){
    }


    /**
     * Create a template to use for matching
     * @param name of the template
     * @param points in the template gesture's path
     */
    public void addTemplate(String name, List<Point> points){
        // TODO: process the points and add them as a template. Use Decomposition!
         points = resample(points, 64);

         double indicativeAngle = indicativeAngle(points);
         points = rotateBy(points, -indicativeAngle);
         //points = scaleTo(points);
    }

    //TODO: Add recognize and other processing methods here

    public List<Point> resample(List<Point> points, int n){
        double resampleInterval = pathLength(points) / (n-1);

        List<Point> resampledPoints = new ArrayList<>();
        resampledPoints.add(points.get(0));

        double accumulatedDistance = 0;
        double segmentDistance;

        for(int i = 1; i < points.size(); i++){
            Point p = points.get(i);
            Point previous = points.get(i-1);

            segmentDistance = p.distance(previous);

            if(accumulatedDistance + segmentDistance > resampleInterval){
                double alpha = (resampleInterval - accumulatedDistance) / segmentDistance;
                Point interpolated = Point.interpolate(previous, p, alpha);
                resampledPoints.add(interpolated);
                points.add(i, interpolated);
                accumulatedDistance = 0;
            }

            else{
                accumulatedDistance += segmentDistance;
            }
        }

        if(resampledPoints.size() < n){
            resampledPoints.add(points.get(points.size()-1));
        }

        return resampledPoints;
    }

    public double pathLength(List<Point> points){
        double totalDistance = 0;

        for(int i = 1; i < points.size(); i++){
            totalDistance += points.get(i).distance(points.get(i-1));
        }

        return totalDistance;
    }

    public List<Point> rotateBy(List<Point> points, double indicativeAngle){

        List<Point> rotatedPoints = new ArrayList<>();
        Point centroid = findCentroid(points);

        for(Point current : points){
            rotatedPoints.add(current.rotate(indicativeAngle, centroid));
        }

        return rotatedPoints;
    }

    public Point findCentroid(List<Point> points){
        double totalX = 0;
        double totalY = 0;

        for(int i = 0; i < points.size(); i++){
            totalX += points.get(i).getX();
            totalY += points.get(i).getY();
        }

        return new Point(totalX / points.size(), totalY / points.size());
    }

    public double indicativeAngle(List<Point> points){
        Point centroid = findCentroid(points);
        Point vector = centroid.subtract(points.get(0));
        return vector.angle();
    }

    public List<Point> scaleTo(List<Point> points){
        int size = 250;
        Point min = findMin(points);
        Point max = findMax(points);

        double width = max.getX() - min.getX();
        double height = max.getY() - min.getY();

        List<Point> scaledPoints = new ArrayList<>();

        for(Point current : points){
            scaledPoints.add(current.scale(size/width, size/height));
        }

        return scaledPoints;
    }

    public Point findMin(List<Point> points){
        Point min = points.get(0);

        for(Point current : points){
            min = Point.min(min, current);
        }

        return min;
    }

    public Point findMax(List<Point> points){
        Point max = points.get(0);

        for(Point current : points){
            max = Point.max(max, current);
        }

        return max;
    }

    /**
     * Uses a golden section search to calculate rotation that minimizes the distance between the gesture and the template points.
     * @param points
     * @param templatePoints
     * @return best distance
     */
    private double distanceAtBestAngle(List<Point> points, List<Point> templatePoints){
        double thetaA = -Math.toRadians(45);
        double thetaB = Math.toRadians(45);
        final double deltaTheta = Math.toRadians(2);
        double phi = 0.5*(-1.0 + Math.sqrt(5.0));// golden ratio
        double x1 = phi*thetaA + (1-phi)*thetaB;
        double f1 = distanceAtAngle(points, templatePoints, x1);
        double x2 = (1 - phi)*thetaA + phi*thetaB;
        double f2 = distanceAtAngle(points, templatePoints, x2);
        while(Math.abs(thetaB-thetaA) > deltaTheta){
            if (f1 < f2){
                thetaB = x2;
                x2 = x1;
                f2 = f1;
                x1 = phi*thetaA + (1-phi)*thetaB;
                f1 = distanceAtAngle(points, templatePoints, x1);
            }
            else{
                thetaA = x1;
                x1 = x2;
                f1 = f2;
                x2 = (1-phi)*thetaA + phi*thetaB;
                f2 = distanceAtAngle(points, templatePoints, x2);
            }
        }
        return Math.min(f1, f2);
    }

    private double distanceAtAngle(List<Point> points, List<Point> templatePoints, double theta){
        //TODO: Uncomment after rotate method is implemented
        List<Point> rotatedPoints = null;
        // rotatedPoints = rotateBy(points, theta);
        return pathDistance(rotatedPoints, templatePoints);
    }

    private double pathDistance(List<Point> a, List<Point> b){

        //TODO: implement the method and return the correct distance
        return 0;
    }






}