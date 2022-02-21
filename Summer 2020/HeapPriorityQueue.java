import java.util.ArrayList;
import java.util.Comparator;;
//** An implementation of a PQ using an array-based heap */ 
public class HeapPriorityQueue<K,V> extends AbstractPriorityQueue<K,V>{
    
    //** primary collection of PQ entries */
    protected ArrayList<Entry<K,V>> heap = new ArrayList<>();

    //** Creates an empty PQ based on natural ordering of its keys */
    public HeapPriorityQueue(){super();}

    //** Creates an empty PQ based on a comparator to order its keys */
    public HeapPriorityQueue(Comparator<K> comp){super(comp);}

    //protected utilities
    protected int parent(int j){return ((j-1)/2);}
    protected int left(int j){return ((2*j)+1);}
    protected int right(int j){return ((2*j)+2);}
    protected boolean hasLeft(int j) { return left(j) < heap.size();}
    protected boolean hasRight(int j){ return right(j) < heap.size();}

    //** Exchanges the entries at indices ia nd j of the array list */
    protected void swap(int i, int j){
        Entry<K,V> temp = heap.remove(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    //** Moves the entry at index j higher, if necessary, to restore heap property */
    protected void upheap(int j){
        while(j>0){
            int p = parent(j);
            if(compare(heap.get(j), heap.get(p)) >= 0) break; // heap property verified 
            swap(j,p);
            j = p;  //continue from the parents location 
        }
    }

    //** Moves the entry at index j lower if necessary, to restore the heap property */
    protected void downheap(int j){
        while(hasLeft(j)){
            int leftIndex = left(j);
            int smallChildIndex = leftIndex;    //although right may be smaller
            if(hasLeft(j)){
                int rightIndex = right(j);
                if(compare(heap.get(leftIndex), heap.get(rightIndex)) > 0){
                    smallChildIndex = rightIndex;
                }
            }
            if(compare(heap.get(smallChildIndex), heap.get(j)) >= 0)break;
            swap(j,smallChildIndex);
            j = smallChildIndex;
        }
    }

    //public utility mehtods
    //** Returns the number of items in the PQ */
    public int size(){return heap.size();}

    //** Returns (but does not remove) the entry with the minimal key */
    public Entry<K,V> min(){
        if(heap.isEmpty())return null;
        return heap.get(0);
    }

    //**Inserts a key-value pair and returns the entry created */
    public Entry<K,V> insert(K key, V value)throws IllegalArgumentException{
        checkKey(key);
        Entry<K,V> newest = new PQEntry<>(key, value);
        heap.add(newest);
        upheap(heap.size() -1);
        return newest;
    }  
    
    public Entry<K,V> removeMin(){
        if(heap.isEmpty())return null;
        Entry<K,V> result = heap.get(0);
        swap(0,heap.size()-1);
        heap.remove(heap.size()-1);
        downheap(0);
        return result;
    }
    //** Sorts seqence S, using initially empty priority queue  */

    public static <E> void pqSort(PositionalList<E> S, PriorityQueue<E,?> P){
        int n = S.size();
        for(int j = 0; j < n; j++){
            E element = S.remove(S.first());
            P.insert(element, null);   //element if key; and null is the value
        }
        for(int j = 0; j < n; j++){
            E element = P.removeMin().getKey();
            S.addLast(element);
        }
    }



}