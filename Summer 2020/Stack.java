public interface Stack<E> {
    //returns the number of elements in the stack
    int size();

    //tests whether the stack is empty
    boolean isEmpty();

    //inserts an element at the top of the stack
    void push(E e);

    //returns without removing the element at the top of the stack
    E top();

    //returns and removes the element at the top of the stack

    E pop();
}