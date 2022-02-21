//** An implementation of an adaptable PQ using an array based heap */
import java.util.Comparator;;
public class HeapAdaptablePriorityQueue<K,V> extends HeapPriorityQueue<K,V>  implements AdaptablePriorityQueue<K,V>{

    //** Nested AdaptablePQEntry class */
    // extension of the PQEntry class to include location infomation
    protected static class AdaptablePQEntry<K,V> extends PQEntry<K,V>{
        private int index;
        public AdaptablePQEntry(K key, V value, int j){
            super(key, value);
            index = j;
        }
        public int getIndex(){return index;}
        public void setIndex(int j){ index = j;}
    }
    //End of nested AdaptablePQEntry class

    //** Creates an empty adaptable PQ using the natural order of keys */
    public HeapAdaptablePriorityQueue(){super();}

    //** Creates an empty adaptable PQ with given comparator to order the keys  */
    public HeapAdaptablePriorityQueue(Comparator<K> comp){super(comp);}

    //protected utilities
    //** Validates an entry to ensure it is location aware*/
    protected AdaptablePQEntry<K,V> validate(Entry<K,V> entry) throws IllegalArgumentException{
        if(!(entry instanceof AdaptablePQEntry)) throw new IllegalArgumentException("Invalid etnry");
        AdaptablePQEntry<K,V> locator = (AdaptablePQEntry<K,V>) entry; 
        int j = locator.getIndex();
        if( j >= heap.size() || heap.get(j) != locator) throw new IllegalArgumentException("Invalid entry");
        return locator;
    }

    //** Exchanges the entries at indices i and j of the array list */
    protected void swap(int i, int j){
        super.swap(i,j);
        ((AdaptablePQEntry<K,V>)heap.get(i)).setIndex(i);   //reset the entry's index instance variable
        ((AdaptablePQEntry<K,V>)heap.get(j)).setIndex(j); 
    }

    //** Restores the heap property by moving the entry at index j upward/downward  */
    protected void bubble(int j){
        if(j > 0 && compare(heap.get(j), heap.get(parent(j))) < 0){
            upheap(j);
        } else{
            downheap(j);
        }
    }

    //** Inserts a key-value pair and returns the entry created */
    public Entry<K,V> insert(K key, V value)throws IllegalArgumentException{
        checkKey(key);
        Entry<K,V> newest =  new AdaptablePQEntry<>(key,value,heap.size());
        heap.add(newest);
        upheap(heap.size() -1);
        return newest;
    }

    //** Removes the given entry from the PQ */
    public void remove(Entry<K,V> entry) throws IllegalArgumentException{
        AdaptablePQEntry<K,V> locator = validate(entry);
        int j = locator.getIndex();
        if(j == heap.size() -1) {   //if the entry to be removed is the last position in the heap
            heap.remove(heap.size() -1);        //simple remove
        } else {
            swap(j,heap.size() -1);     //move to last position
            heap.remove(heap.size() -1); //remove it 
            bubble(j);
        }
    }

    //** Replaces the key of an entry */
    public void replaceKey(Entry<K, V> entry, K key) throws IllegalArgumentException{
        AdaptablePQEntry<K,V> locator = validate(entry);
        checkKey(key);
        locator.setKey(key);
        bubble(locator.getIndex());
    }

    //** Replaces the value of an entry */
    public void replaceValue(Entry<K,V> entry, V value) throws IllegalArgumentException{
        AdaptablePQEntry<K,V> locator = validate(entry);
        locator.setValue(value);
    }


}