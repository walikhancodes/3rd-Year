/*THIS CODE WAS MY OWN WORK, 
IT WAS WRITTEN WITHOUT CONSULTING ANYSOURCES 
OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. 
[Wali Khan, 2308097]*/
import java.util.ArrayList;
public class TSPNearestNeighbor {
    public static ArrayList<Line2D> computeNearestNeighbor(ArrayList<Point2D> points) {
        ArrayList<Point2D> p = new ArrayList<>();// first what we do is we create a copy of the points of the graph given
        for (Point2D point : points) {
            p.add(point);
        }
        ArrayList<Point2D> result = new ArrayList<>(); // this is where we will keep the order of points to convert to edges
        result.add(p.remove(0));// add the first point to the path
        Point2D v = result.get(0); // the vertice whose distance we are comparing to
        while (p.size() > 0) {// we will iterate through all the vertices
            int j = 0; // stores the index of the minimal element
            double min = v.distance(p.get(0)); // distance from v to first element in vertices list of given input graph as parameter 
            for (int k = 0; k < p.size(); k++) { // go thru each vertice
                double cur = v.distance(p.get(k));
                if (cur < min) { // if there is a smaller distance
                    j = k;
                    min = cur;
                }
            }
            result.add(p.remove(j)); // add the smallest distance to the path
            v = result.get(result.size() - 1); // update the next vertice
        }
        result.add(result.get(0)); // cycle
        ArrayList<Line2D> answer = new ArrayList<>();
        for (int i = 1; i < result.size(); i++) { // this is where we convert the points to lines
            Line2D addThis = new Line2D(result.get(i - 1), result.get(i));
            answer.add(addThis);
        }
        return answer;
        // return new ArrayList<>();
    }
}
