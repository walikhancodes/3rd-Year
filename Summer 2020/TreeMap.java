import java.util.Comparator;
import java.util.ArrayList;
//** An implementation of a sorted map using a binary search tree */
public class TreeMap<K,V> extends AbstractSortedMap<K,V>{
    // To represent the underlying tree structure, we use a specialized subclass of the 
    // LinkedBinaryTree class that we name BalanaceableBinaryTree
    protected BalanceableBinaryTree<K,V> tree = new BalanceableBinaryTree<>();

    //** Constructs an empty map using the natural ordering of keys */
    public TreeMap(){
        super(); // the AbstractSortedMap constructor 
        tree.addRoot(null); //create a sentinel lead as root
    }

    public TreeMap(Comparator<K> comp){
        super(comp);
        tree.addRoot(null);
    }

    //returns the number of entries in the map
    public int size(){
        return (tree.size() -1) /2; //only internal nodes have entries 
    }
    //** Utility used when inserting a new entry at a leaf of the tree */
    private void expandExternal(Position<Entry<K,V>> p, Entry<K,V> entry){
        tree.set(p,entry);      //store new entry at p
        tree.addLeft(p, null);  //set leaves to null
        tree.addRight(p, null);
    }
    
    // Some notational shorthands for brevity (yet not efficiency)
    protected Position<Entry<K,V>> root() { return tree.root(); }
    protected Position<Entry<K,V>> parent(Position<Entry<K,V>> p) { return tree.parent(p); }
    protected Position<Entry<K,V>> left(Position<Entry<K,V>> p) { return tree.left(p); }
    protected Position<Entry<K,V>> right(Position<Entry<K,V>> p) { return tree.right(p); }
    protected Position<Entry<K,V>> sibling(Position<Entry<K,V>> p) { return tree.sibling(p); }
    protected boolean isRoot(Position<Entry<K,V>> p) { return tree.isRoot(p); }
    protected boolean isExternal(Position<Entry<K,V>> p) { return tree.isExternal(p); }
    protected boolean isInternal(Position<Entry<K,V>> p) { return tree.isInternal(p); }
    protected void set(Position<Entry<K,V>> p, Entry<K,V> e) { tree.set(p, e); }
    protected Entry<K,V> remove(Position<Entry<K,V>> p) { return tree.remove(p); }
    protected void rotate(Position<Entry<K,V>> p) { tree.rotate(p); }
    protected Position<Entry<K,V>> restructure(Position<Entry<K,V>> x) { return tree.restructure(x); }


    //** Returns the Position in p's subtree having given key (or else the terminal leaf) */
    private Position<Entry<K,V>> treeSearch(Position<Entry<K,V>> p, K key){
        if(isExternal(p)){
            return p;   //no subtree key not found return final leaf
        }
        int comp = compare(key,p.getElement());
        if(comp == 0){
            return p;   //key found return its Position
        } else if (comp < 0){
            return treeSearch(left(p), key);   //searches left subtree
        } else{
            return treeSearch(right(p), key);
        }
    }

    //** Returns the value associated with the specific key(or else null) */
    public V get(K key) throws IllegalArgumentException{
        checkKey(key);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        rebalanceAccess(p);
        if(isExternal(p)){return null;} //unncessful search 
        return p.getElement().getValue();
    }

    //** Associates the given value with the given key, returning any overridden value */
    public V put(K key, V value) throws IllegalArgumentException{
        checkKey(key);
        Entry<K,V> newEntry = new MapEntry<>(key, value);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if(isExternal(p)){  //key is new 
            expandExternal(p, newEntry);
            rebalanceInsert(p);
            return null;
        } else{     //replacing existing key
            V old = p.getElement().getValue();
            set(p,newEntry);
            rebalanceAccess(p);
            return old;
        }
    }

    public V remove(K key) throws IllegalArgumentException{
        checkKey(key);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if(isExternal(p)){      //key not found
            rebalanceAccess(p); //
            return null;
        } else{
            V old = p.getElement().getValue();
            if(isInternal(left(p)) && isInternal(right(p))){    //both children are internal
                Position<Entry<K,V>> replacement = treeMax(left(p));
                set(p, replacement.getElement());
                p = replacement;
            }// p now has at most one child athat is an internal node
            Position<Entry<K,V>> leaf = (isExternal(left(p)) ? left(p) : right(p));
            Position<Entry<K,V>> sib = sibling(leaf);
            remove(leaf);
            remove(p);
            rebalanceDelete(sib);
            return old;
        }
    }
    //Returns position with the minimal key in the subtree rooted at Position p.
    protected Position<Entry<K,V>> treeMin(Position<Entry<K,V>> p){
        Position<Entry<K,V>> walk = p;
        while(isInternal(walk)){
            walk = left(walk);
        }
        return parent(walk);
    }

    //Returns position with the max key in the subtree rooted at Position p.
    protected Position<Entry<K,V>> treeMax(Position<Entry<K,V>> p){
        Position<Entry<K,V>> walk = p;
        while(isInternal(walk)){
            walk = right(walk);
        }
        return parent(walk);
    }

    //** Returns the entry having the greatest key (or null if map is empty) */
    public Entry<K,V> lastEntry(){
        if(isEmpty()){return null;}
        return treeMax(root()).getElement();
    }

    //** Returns the entry having the least key (or null if map is empty) */
    public Entry<K,V> firstEntry(){
        if(isEmpty()){return null;}
        return treeMin(root()).getElement();
    }

    
   //Returns the entry with least key greater than or equal to given key
   //(or null if no such key exists).
    public Entry<K,V> ceilingEntry(K key) throws IllegalArgumentException {
        checkKey(key);                              // may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isInternal(p)) return p.getElement();   // exact match
        while (!isRoot(p)) {
            if (p == left(parent(p))){
                return parent(p).getElement();  // parent has next greater key
            } else{
                p = parent(p);
            }
        }
        return null;                                // no such ceiling exists
    }

    //** Returns the entry with greatest key less than or equal to given key (if any)*/
    public Entry<K,V> floorEntry(K key) throws IllegalArgumentException{
        checkKey(key);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if(isInternal(p)) return p.getElement(); //exact match 
        while(!isRoot(p)){
            if(p == right(parent(p))){
                return parent(p).getElement();
            } else{
                p = parent(p);
            }
        }
        return null;
    }

    //** Returns the entry with thhe greatest key strictly less than given key (if any) */
    public Entry<K,V> lowerEntry(K key) throws IllegalArgumentException{
        checkKey(key);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if(isInternal(p) && isInternal(left(p))){
            return treeMax(left(p)).getElement();   //this is the predecessor to p
            //otherwise we had failed search, or match with no left child
        }
        while(!isRoot(p)){
            if(p == right(parent(p))){
                return parent(p).getElement(); //parent has next lesser key
            } else {
                p = parent(p);
            }
        }
        return null;    // no such key exists 
    }

    //** Returns the entry with the least key strictly greater than given key (if any) */
    public Entry<K,V> higherEntry(K key) throws IllegalArgumentException{
        checkKey(key);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if(isInternal(p) && isInternal(right(p))){
            return treeMin(right(p)).getElement();   //this is the predecessor to p
            //otherwise we had failed search, or match with no left child
        }
        while(!isRoot(p)){
            if(p == left(parent(p))){
                return parent(p).getElement(); //parent has next lesser key
            } else {
                p = parent(p);
            }
        }
        return null;    // no such key exists 
    }

    //** Returns an interable collection of all key-value entries of the map */
    public Iterable<Entry<K,V>> entrySet(){
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(size());
        for(Position<Entry<K,V>> p : tree.inorder()){
            if(isInternal(p)) buffer.add(p.getElement());
        }
        return buffer;
    }

    //** Returns an iterable of entries with keys in range from [k1,k2] */
    public Iterable<Entry<K,V>> subMap(K fromKey, K toKey){
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(size());
        if(compare(fromKey, toKey) < 0){ //ensures that fromKey < toKey
            subMapRecurse(fromKey, toKey, root(), buffer);
        }     
        return buffer;
    }

    protected void subMapRecurse(K fromKey, K toKey, Position<Entry<K,V>> p, ArrayList<Entry<K,V>> buffer){
        if(isInternal(p)){
            if(compare(p.getElement(), fromKey) < 0){// p's keys is less than fromKey so any relevent entries are to the right 
                subMapRecurse(fromKey, toKey, right(p), buffer);
            } else {
                subMapRecurse(fromKey, toKey, left(p), buffer); //first consider left subtree
                if(compare(p.getElement(), toKey) < 0){     // p is within range 
                    buffer.add(p.getElement()); //so add it to buffer and consider right subtree as well
                    subMapRecurse(fromKey, toKey, right(p), buffer);
                }
            }
            
        }
    }
    protected void rebalanceInsert(Position<Entry<K,V>> p){} 
    protected void rebalanceDelete(Position<Entry<K,V>> p){}
    protected void rebalanceAccess(Position<Entry<K,V>> p){}
    //** A specialized version of LinkedBinaryTree with support for balancing */
    protected static class BalanceableBinaryTree<K,V> extends LinkedBinaryTree<Entry<K,V>>{
        //this extends the inherited LinkedBinaryTree.Node class
        protected static class BSTNode<E> extends Node<E> {
            int aux = 0;
            public BSTNode(E e, Node<E> parent, Node<E> leftChild, Node<E> rightChild){
                super(e, parent, leftChild, rightChild);
            }
            public int getAux(){
                return aux;
            }
            public void setAux(int value){ aux = value;}
        }
      // End of nested Node class 
        //positional- based methods related to aux field 
        public int getAux(Position<Entry<K,V>> p){
            return ((BSTNode<Entry<K,V>>) p).getAux();
        }

        public  void setAux(Position<Entry<K,V>> p, int value){
            ((BSTNode<Entry<K,V>>) p).setAux(value);
        }

        protected Node<Entry<K,V>> createNode(Entry<K,V> e, Node<Entry<K,V>> parent, Node<Entry<K,V>> left, Node<Entry<K,V>> right){
            return new BSTNode<>(e,parent, left,right);
        }
        //** Relinks a parent node with its oriented child node */
        private void relink(Node<Entry<K,V>> parent, Node<Entry<K,V>> child, boolean makeLeftChild){
            child.setParent(parent);
            if(makeLeftChild){
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }

        //** Rotates Position p above its parent */
        public void rotate(Position<Entry<K,V>> p){
            Node<Entry<K,V>> x = validate(p);
            Node<Entry<K,V>> y = x.getParent(); // we assume this exists 
            Node<Entry<K,V>> z = y.getParent(); // grandparent possibly null
            if(z==null){
                root = x;
                x.setParent(null);
            } else{
                relink(z,x,y == z.getLeft());   // x becomes direct child of z 
            }//now rotate x and y, including transfer of middle subtree
            if(x == y.getLeft()){
                relink(y, x.getRight(),true);       //x's right child becomes y's left
                relink(x,y,false);                  // y becomes x's right child
            } else {
                relink(y,x.getLeft(),false);   //x's left child becomes y's right 
                relink(x,y,true);       //y becomes left child of x
            }
        } 
        
        //** Performs a trinode restructuring of Position x with its parent/grandparent */
        public Position<Entry<K,V>> restructure(Position<Entry<K,V>> x){
            Position<Entry<K,V>> y = parent(x);
            Position<Entry<K,V>> z = parent(y);
            if((x == right(y)) == (y == right(z))){     //matching alligments 
                rotate(y);              //single rotation of y
                return(y);              //y is the new subtree root 
            } else {
                rotate(x);
                rotate(x);
                return x;
            }
        }
    }
}