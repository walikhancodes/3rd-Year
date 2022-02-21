/*THIS CODE WAS MY OWN WORK, 
IT WAS WRITTEN WITHOUT CONSULTING ANYSOURCES 
OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. 
[Wali Khan, 2308097]*/
import java.util.ArrayList;
public class TSPMinimumSpanningTree {
    public static ArrayList<Line2D> computeMST(ArrayList<Point2D> points){
        ArrayList<Point2D> p = new ArrayList<>();// first what we do is we create a copy of the points of the graph given
        for (Point2D point : points) {
            p.add(point);
        }
        //this is where we store the points in the MST
        ArrayList<Point2D> mst = new ArrayList<>();  
        //this is where we will store the final tree
        ArrayList<Line2D> result = new ArrayList<>(); 
        //add any point MST this is the first point
        mst.add(p.remove(0)); 
        //goes through all reachable points given as parameter 
        while(p.size() > 0){    
            double min = mst.get(0).distance(p.get(0)); //distance between the mst point and the first element of vertices of the graph 
            Line2D edge = new Line2D(mst.get(0), p.get(0)); //the first edge is created 
            int j = 0; //holds the index of element with least weighted edge 
            for(int i = 0; i < mst.size(); i++){ //this is a nested for loop for finding the next minimal weight considering all outgoing edges of any vertex alrdy in the tree
                for(int k = 0; k < p.size(); k++){
                    //the current point in the MST 
                    Point2D current = mst.get(i); 
                    double cur = current.distance(p.get(k));   //the distance betweent the current MST point and remaining points outside the MST
                    //if a new minimun weight is found
                    if(cur < min){  
                        min = cur;  //the minimal distance is then updated as well 
                        //edge is updated 
                        edge = new Line2D(current, p.get(k)); 
                        j = k;  //the index containing the point for minimal edge is updated 
                    }
                }
            }
            mst.add(p.remove(j)); //the point is added to the MST 
            //edge is added to the tree 
            result.add(edge);   
        }
        return result;
        //return new ArrayList<>();
    }
    public static ArrayList<Line2D> computeDFSTour(ArrayList<Point2D> points, ArrayList<Line2D> mst){
        //first we create a copy of the ArrayList containing the mst edges 
        ArrayList<Line2D> tree = new ArrayList<>(); 
        for (Line2D edge : mst) {
            tree.add(edge);
        }
        //this will contain the tour pre conversion to lines 
        ArrayList<Point2D> tour = new ArrayList<>();
        //this is the first point we will add to the tour 
        Point2D addThis = tree.get(0).getP1();
        tour.add(addThis);
        while(tree.size() > 0){
            int j = 0;
            for(int i = 0; i < tour.size(); i++){
                j = find(tour.get(tour.size() -1 - i), tree);
                if (j != -1){
                    break;
                }
            }
            //add P2 of the found point at index to tour 
            tour.add(tree.remove(j).getP2());
        }
        tour.add(tour.get(0));
        ArrayList<Line2D> answer = new ArrayList<>();
        for (int i = 1; i < tour.size(); i++) { // this is where we convert the points to lines
            Line2D a = new Line2D(tour.get(i - 1), tour.get(i));
            answer.add(a);
        }
        return answer;
        //return new ArrayList<>();
    }

    //** Method to help find the index of the next point in the tour */
    public static int find(Point2D target, ArrayList<Line2D> tree){
        Point2D walk;
        int index = 0;
        while(index < tree.size()){
            walk = tree.get(index).getP1();
            if(walk == target){
                return index;
            }
            index++;
        }
        return -1;
    }
}
