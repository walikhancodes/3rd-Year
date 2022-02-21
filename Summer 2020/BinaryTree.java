

public interface BinaryTree<E> extends Tree<E> {
    //returns the Position of p's left child or null if no child exists
    Position<E> left(Position<E> p) throws IllegalArgumentException;

    //returns the Positon of p's right child or null if no child exits
    Position<E> right(Position<E> p) throws IllegalArgumentException;

    //returns the Position of p's sibling or null if no sibling exists
    Position<E> sibling(Position<E> p) throws IllegalArgumentException;
}