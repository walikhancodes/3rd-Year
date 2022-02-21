import java.util.Comparator;
//** An implementation of a sorted map using an AVL tree */
public class AVLTreeMap<K,V> extends TreeMap<K,V> {
    //** Constructs an empty map using the natural ordering of keys */
    public AVLTreeMap(){super();}
    //**Constructs an empty map using the given comparator to order keys */
    public AVLTreeMap(Comparator<K> comp){super(comp);}
    //** Returns the height of the given tree position */
    protected int height(Position<Entry<K,V>> p){
        return tree.getAux(p);
    }
    //** Recomputes the height of the given position bsed on its childrens heights */
    protected void recomputeHeight(Position<Entry<K,V>> p){
        tree.setAux(p, 1 + Math.max(height(left(p)), height(right(p))));
    }
    //** Returns whether a position has balance factor between -1 and 1 inclusive  */
    protected boolean isBalanced(Position<Entry<K,V>> p){
        return Math.abs(height(left(p)) - height(right(p))) <= 1;
    }

    //** Returns a child of p with height no smaller than that of the other child */
    protected Position<Entry<K,V>> tallerChild(Position<Entry<K,V>> p){
        if(height(left(p)) > height(right(p))) return left(p);
        if(height(left(p)) < height(right(p))) return right(p);
        //equal heights break tie while matchin parent's orientation
        if(isRoot(p)){return left(p);}
        if(p == left(parent(p))) return left(p);    //return alligned child
        else return right(p);
    }

    //** Utilities used to rebalacne after an insert of removal operation. This traverses the path upward from p
    //   performing a trinode restructuring when imbalance is found continuing until balance is restores */
    protected void rebalance(Position<Entry<K,V>> p){
        int oldHeight, newHeight;
        do{
            oldHeight = height(p);     //not yet recalculates if internal
            if(!isBalanced(p)){ //imbalance detected
                //perform trinode restructuring setting p to resulting root 
                //and recompute the new local heights after the restructuring
                p = restructure(tallerChild(tallerChild(p)));
                recomputeHeight(left(p));
                recomputeHeight(right(p));

            }
            recomputeHeight(p);
            newHeight = height(p);
            p = parent(p);
        } while (oldHeight != newHeight && p != null);
    }

    //** Overrides the TreeMap rebalancing hook that is called after an insertion */
    protected void rebalanceInsert(Position<Entry<K,V>> p){
        rebalance(p);
    }

    //** Overrides the TreeMap rebelancing hook that is called after a deletion */
    protected void rebalanceDelete(Position<Entry<K,V>> p){
        if(!isRoot(p)){
            rebalance(parent(p));
        }
    }

    public static void main(String[] args) {
        Integer[] keys = {1,5,9,41,54};
        String[] values = {"x", "yy", "zzz", "saidssdcsdcsscdcdcw", "kdd81f"};
        AVLTreeMap<Integer,String> test1 = new AVLTreeMap<>();
        for(int i = 0; i < keys.length; i++){
            test1.put(keys[i], values[i]);
            
        }
        
        for(int i = 0; i < keys.length; i++){
            System.out.println(test1.remove(keys[(keys.length -1) -i]));
            
        }
    }

}