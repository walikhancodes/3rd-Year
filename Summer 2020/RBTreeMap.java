
import java.util.Comparator;
public class RBTreeMap<K,V> extends TreeMap<K,V> {
    //** Constructs an empty map using the natural ordering of keys */
    public RBTreeMap(){super();}
    //** Constructs an empty map using a speicific Comparator to order the keys */
    public RBTreeMap(Comparator<K> comp){super(comp);}
    //we use the inherited aux field with convention that black = 0 and red = 1
    // note that new leaves by default will be black as aux = 0
    private boolean isBlack(Position<Entry<K,V>> p){return tree.getAux(p) == 0;}
    private boolean isRed(Position<Entry<K,V>> p){return tree.getAux(p) == 1;}
    private void makeBlack(Position<Entry<K,V>> p) { tree.setAux(p, 0);}
    private void makeRed(Position<Entry<K,V>> p) { tree.setAux(p, 1);}
    private void setColor(Position<Entry<K,V>> p, boolean toRed){tree.setAux(p, toRed ? 1 : 0);}

    //** Overrides the TreeMap rebalancing hook that is called after an insert */
    protected void rebalanceInsert(Position<Entry<K,V>> p){
        if(!isRoot(p)){
            makeRed(p);     // the new internal node is red
            resolveRed(p);  //may case double red issue 
        }
    }

    //** Remedies potential double-red violations above red position p */
    private void resolveRed(Position<Entry<K,V>> p){
        Position<Entry<K,V>> parent, uncle, middle, grand;
        parent = parent(p);
        if(isRed(parent)){  // double red problem exists 
            uncle = sibling(parent);
            if(isBlack(uncle)){         // case 1 misshapen 4-node do trinode restructure 
                middle = restructure(p);
                makeBlack(middle);
                makeRed(left(middle));
                makeRed(right(middle));
            } else {                    // case 2 overfull 5-node perform recoloring 
                makeBlack(parent);
                makeBlack(uncle);
                grand = parent(parent);
                if(!isRoot(grand)){       //grandparent becomes red recur at red grandparent
                    makeRed(grand);
                    resolveRed(grand);
                }
            }
        }
    }

    //** Overrides the TreeMap rebalancing hook that is called after a deletion */
    protected void rebalanceDelete(Position<Entry<K,V>> p){
        if(isRed(p)){       // deleted parent was balck
            makeBlack(p);   // restores black depth 
        } else if (!isRoot(p)){
            Position<Entry<K,V>> sib = sibling(p);
            if(isInternal(sib) && (isBlack(sib) || isInternal(left(sib)))){
                remedyDoubleBlack(p);   //sib's subtree has non zero blackheight 
            }
        }
    }

    //** Remedies a presumed double black violation at the given nonroot position */
    private void remedyDoubleBlack(Position<Entry<K,V>> p){
        Position<Entry<K,V>> z = parent(p);
        Position<Entry<K,V>> y = sibling(p);
        if(isBlack(y)){
            if(isRed(left(y)) || isRed(right(y))){  // case 1 tri node re structuring 
                Position<Entry<K,V>> x = (isRed(left(y)) ? left(y) : right(y));
                Position<Entry<K,V>> middle = restructure(x);
                setColor(middle, isRed(z));
                makeBlack(left(middle));
                makeBlack(right(middle));
            } else {            //case 2 recoloring 
                makeRed(y);
                if(isRed(z)){   
                    makeBlack(z);       // problem solved 
                } else if(!isRoot(z)){
                    remedyDoubleBlack(z);       //propagate the problem 
                }
            } 
        } else{
            rotate(y);
            makeBlack(y);
            makeRed(z);
            remedyDoubleBlack(p);   //restart process at p
        }
    }
} 