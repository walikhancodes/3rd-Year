import java.util.Comparator;
public abstract class AbstractPriorityQueue<K,V> implements PriorityQueue<K,V>{

    //** Nested PQEntry class  */
    protected static class PQEntry<K,V> implements Entry<K,V>{
        private K k; //key
        private V v; //value
        public PQEntry(K key, V value){
            k = key;
            v = value;
        } 
        //methods of the Entry interface
        public K getKey(){ return k;}
        public V getValue(){return v;}
        //utilities not exposed as part of the Entry interface
        protected void setKey(K key){k = key;}
        protected void setValue(V value){v = value;}
    } //end of nested PQEntry class

    //instance variables for an AbstractPriorityQueue
    //** THe comparator defing the ordering of keys in the priority queue */
    private Comparator<K> comp;
    
    //** Creates an empty priority queue using the given comparator to order keys */
    protected AbstractPriorityQueue(Comparator<K> c) {comp = c;}

    //** Creates an empty priority queue using the natural ordering of its keys  */
    protected AbstractPriorityQueue(){this(new DefaultComparator<K>());}

    //** Method for comparing two entries according to key */
    protected int compare(Entry<K,V> a, Entry<K,V> b){
        return comp.compare(a.getKey(), b.getKey());
    }

    //** Determines whether a key is valid */
    protected boolean checkKey(K key)throws IllegalArgumentException{
        try{
            return (comp.compare(key,key) == 0);    //see if key can be compared to itself
        } catch (ClassCastException e){
            throw new IllegalArgumentException("Incompatible key");
        }
    }

    //**Tests whether the priorty queue is empty */
    public boolean isEmpty(){return size() == 0;}

    
}