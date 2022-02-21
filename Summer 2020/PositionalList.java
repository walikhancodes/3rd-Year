
public interface PositionalList<E>{

    //returns the number of elements in the list
    int size();

    //tests whether the list is emptp
    boolean isEmpty();

    //returns the first Position in the list (null if empty)
    Position<E> first();

    //returns the last Position in the list (null if empty)
    Position<E> last();

    //returns the Position immediately before Position p (or null if p is first)
    Position<E> before(Position<E> p) throws IllegalArgumentException;

    //returns the Position immediately after Position p(of null, if p is last)
    Position<E> after(Position<E> p) throws IllegalArgumentException;

    //inserts element e at the front of the list and returns its new Position
    Position<E> addFirst(E e);

    //inserts element e at the end of the list and returns its new Position
    Position<E> addLast(E e);

    //inserts element e right before Position p and returns its new Position
    Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException;

    //inserts element e right after Position p and returns its new Position
    Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException;

    //replaces the element stores at Position p and returns the replace element
    E set(Position<E> p, E e) throws IllegalArgumentException;

    //removes the element stores at Position p and returns it 
    E remove(Position<E> p) throws IllegalArgumentException; 
}