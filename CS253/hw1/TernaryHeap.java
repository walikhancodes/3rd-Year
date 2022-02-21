/*THIS CODE WAS MY OWN WORK, 
IT WAS WRITTEN WITHOUT CONSULTING ANYSOURCES 
OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. 
[Wali Khan, 2308097]*/

import java.util.ArrayList;
import java.util.*;
import java.util.Comparator;
public class TernaryHeap<K extends Comparable<K>> extends Heap<K> {
    private List<K> array;
    private Comparator<K> comp;
    public TernaryHeap(){
        this(Comparator.naturalOrder());
    }
    public TernaryHeap(Comparator<K> comp){
        this.comp = comp;
        array = new ArrayList<K>();
        
    }
    
    private void swap(int i, int j){
        Collections.swap(array, i, j);
    }
    // private int root(){

    // }
    private int left(int i){
        return 3*i + 1;
    }
    private int middle(int i){
        return 3*i + 2;
    }
    private int right(int i){
        return 3*i + 3;
    }
    private int parent(int i){
        return (i-1)/3;
    }
    private boolean hasLeft(int i){
        return left(i) < array.size();
    }
    private boolean hasRight(int i){
        return right(i) < array.size();
    }
    private  boolean hasMiddle(int i){
        return middle(i) < array.size();
    }
    private void sink(int i){
        while(hasMiddle(i) && hasLeft(i)){
            int indexOfLeft = left(i);
            int indexOfSmallChild = indexOfLeft;
            int indexOfMiddle = middle(i);
            int indexOfRight = right(i);
            if(hasMiddle(i)){
                if(comp.compare(array.get(indexOfLeft), array.get(indexOfMiddle)) > 0){
                    indexOfSmallChild = indexOfMiddle;
                }
            }
            if(hasRight(i)){
                if(comp.compare(array.get(indexOfMiddle), array.get(indexOfRight)) > 0 && comp.compare(array.get(indexOfLeft), array.get(indexOfRight)) > 0){
                    indexOfSmallChild = indexOfRight;
                }
            }
            if(comp.compare(array.get(indexOfSmallChild), array.get(i)) >= 0){
                break;
            }
            swap(i, indexOfSmallChild);
            i = indexOfSmallChild;
        }
    }
    private void swim(int i){
        while(i > 0){
            int p = parent (i); 
            if(comp.compare(array.get(i), array.get(p)) >=0){
                break;
            }
            swap(i,p);
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
