/*THIS CODE WAS MY OWN WORK, 
IT WAS WRITTEN WITHOUT CONSULTING ANYSOURCES 
OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. 
[Wali Khan, 2308097]*/
import java.util.*;
@SuppressWarnings("unchecked")
public class MedianHeap<K extends Comparable<K>> implements MedianQueue<K>{

    private BinaryHeap<K> maxHeap;
    private BinaryHeap<K> minHeap;
    

    MaxComparator<K> maxComparator = new MaxComparator<K>();

    DefaultComparator<K> minComparator = new DefaultComparator<K>();

    public MedianHeap(){
        maxHeap = new BinaryHeap<K>(minComparator);
        minHeap = new BinaryHeap<K>(maxComparator);
    }

    public boolean isEmpty(){
       return maxHeap.size() == 0 && minHeap.size() == 0;
    }
    public int size(){
        return maxHeap.size() + minHeap.size();
    }
    
    private void rebalance(){
        //perhaps you'd use a helper method like this...
        if(Math.abs(maxHeap.size() - minHeap.size()) > 1){
            if(maxHeap.size() > minHeap.size()){
                minHeap.insert(maxHeap.removeMin()); 
            }
            else{
                maxHeap.insert(minHeap.removeMin()); 
            }
        }
    }
    
    public void insert(K k){
        K median = median();
        if(isEmpty() || median==null ){
            minHeap.insert(k);
            
        }else {
    
            if(minComparator.compare(k, median)>0){
                maxHeap.insert(k);
            } 
            else{
                minHeap.insert(k);
            }}
            rebalance();
    }
    public K removeMedian(){

        
        return (maxHeap.size()<minHeap.size())? 
        minHeap.removeMin():maxHeap.removeMin();
        }
    
    public K median(){
        return (maxHeap.size()<minHeap.size())? 
        minHeap.min():maxHeap.min();
        
    }

    
}
