public interface Position<E> {

    //returns the element stores at this Position
    E getElement() throws IllegalStateException;
}