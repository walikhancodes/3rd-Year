/*THIS CODE WAS MY OWN WORK, 
IT WAS WRITTEN WITHOUT CONSULTING ANYSOURCES 
OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. 
[Wali Khan, 2308097]*/

import java.util.*;
import java.util.Comparator;

public class BinaryHeap<K extends Comparable<K>> extends Heap<K> {
    private List<K> array;
    private Comparator<K> comparator;
    public BinaryHeap(){
        this(new DefaultComparator<>());
    }
    public BinaryHeap(Comparator<K> comparator){
         this.comparator = comparator;
         array = new ArrayList<K>();
        
    }
    
    private void swap(int i, int j){
        Collections.swap(array, i, j);
    }
    // private int root(){
    
    // }
    public int size(){
        return array.size();
    }
    private int left(int i){
        return 2*i +1;
    }
    private boolean hasLeft(int i){
        return left(i) < array.size();
    }
    private boolean hasRight(int i){
        return right(i) < array.size();
    }
    private int right(int i){
        return 2*i +2;
    }
    private int parent(int i){
        return (i-1)/2;
    }
    private void sink(int i){
        while(hasLeft(i)){
            int indexOfLeft = left(i);
            int indexOfSmallChild = indexOfLeft;
            int indexOfRight = right(i);
            if(hasRight(i)){
                if(comparator.compare(array.get(indexOfLeft), array.get(indexOfRight)) > 0){
                    indexOfSmallChild = indexOfRight;
                }
            }
            if(comparator.compare(array.get(indexOfSmallChild), array.get(i)) >= 0){
                break;
            }
            swap(i, indexOfSmallChild);
            i = indexOfSmallChild;
        }
    
    }
    private void swim(int i){
        while (i > 0){
            int p = parent(i);
            if(comparator.compare(array.get(i), array.get(p)) >= 0) break;
            swap(i, p);
            i = p;

        }
    }
    
    public void insert(K k){
        array.add(k);
        swim(array.size() - 1);
    }
    public K removeMin(){
        if(array.isEmpty()) return null;
        swap(0, array.size() - 1);
        K answer = array.remove(array.size()-1);
        sink(0);
        return answer;
    }
    public K min(){
        if(array.isEmpty()){
            return null;
        }
        return array.get(0);
        
    }

 
}
