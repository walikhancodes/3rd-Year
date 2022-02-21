import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedPositionalList<E> implements PositionalList<E>{

    //nested Node class which implements the Position interface 
    private static class Node<E> implements Position<E>{
        private E element;
        private Node<E> prev;
        private Node<E> next;
        public Node(E e, Node<E> p, Node<E> n){
            element = e;
            prev = p;
            next = n;
        }
        public E getElement() throws IllegalStateException{
            if(next == null){
                throw new IllegalStateException("Position is no longer valid");
            }
            return element;
        }

        public Node<E> getPrev(){
            return prev;
        }
        public Node<E> getNext(){
            return next;
        }
        public void setElement(E e ){
            element = e;
        }
        public void setPrev(Node<E> p){
            prev = p;
        }
        public void setNext(Node<E> n){
            next = n;
        }
    }

    private Node<E> header;
    private Node<E> trailer;
    private int size = 0;

    //constructs a new empty list
    public LinkedPositionalList(){
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, header, null);
        header.setNext(trailer);
    }

    //private utilities
    //validates the position and returns it as a node
    private Node<E> validate(Position<E> p) throws IllegalArgumentException{
        if(!(p instanceof Node))throw new IllegalArgumentException("Invalid p");
        Node<E> node = (Node<E>) p;
        if(node.getNext() == null){
            throw new IllegalArgumentException("p is no longer in the list");
        }
        return node;
    }

    private Position<E> position(Node<E> node){
        if(node == header || node == trailer){
            return null;
        }
        return node;
    }

    //public accesor methods
    public int size(){return size;}
    public boolean isEmpty(){return size ==0;}

    //returns the first Position in the linked list of null if empty
    public Position<E> first(){
        return position(header.getNext());
    }
    //returns the positoon of the last Position in the linked list or null if empty 
    public Position<E> last(){
        return position(trailer.getPrev());
    }

    //returns the Position immediately before Position p (or null if p is first)
    public Position<E> before(Position<E> p) throws IllegalArgumentException{
        Node<E> node = validate(p);
        return position(node.getPrev());
    }
    public Position<E> after(Position<E> p)throws IllegalArgumentException{
        Node<E> node = validate(p);
        return position(node.getNext());
    }

    //private utilities
    private Position<E> addBetween(E e, Node<E> predecessor, Node<E> successor){
        Node<E> newest = new Node<>(e, predecessor, successor);
        predecessor.setNext(newest);
        successor.setPrev(newest);
        size++;
        return newest;
    }

    //public update methods
    public Position<E> addFirst(E e){
        return  addBetween(e, header, header.getNext());
    }
    public Position<E> addLast(E e){
        return addBetween(e, trailer.getPrev(), trailer);
    }


    public Position<E> addBefore(Position<E> p, E e)throws IllegalArgumentException{
        Node<E> node = validate(p);
        return addBetween(e, node.getPrev(), node);
    }
    public Position<E> addAfter(Position<E> p, E e)throws IllegalArgumentException{
        Node<E> node = validate(p);
        return addBetween(e, node, node.getNext());
    }

    //replaces the element stores at Position p and returns the replaced element
    public E set(Position<E> p, E e)throws IllegalArgumentException{
        Node<E> node = validate(p);
        E answer = node.getElement();
        node.setElement(e);
        return answer;
    }

    //removes the element stores at Position p and returns it (invalidating p)
    public E remove(Position<E>p)throws IllegalArgumentException{
        Node<E> node = validate(p);
        Node<E> predecessor = node.getPrev();
        Node<E> successor = node.getNext();
        predecessor.setNext(successor);
        successor.setPrev(predecessor);
        size--;
        E answer = node.getElement();
        node.setElement(null);
        node.setNext(null);
        node.setPrev(null);
        return answer;
    }
    //Nested Iterator class
    private class PositionIterator implements Iterator<Position<E>>{
        private Position<E> cursor = first(); //position of the next element to report
        private Position<E> recent = null; //position of the last reported element 
        //tests whether the iterator has a next object
        public boolean hasNext(){
            return (cursor != null);
        }
        //returns the next position in the iterator 
        public Position<E> next() throws NoSuchElementException{
            if(cursor == null) throw new NoSuchElementException("nothing left");
            recent = cursor;
            cursor = after(cursor);
            return recent;
        }
        //removes the elemt returned by the most recent call to next
        public void remove() throws IllegalStateException{
            if(recent == null)throw new IllegalStateException("nothing to remove");
            LinkedPositionalList.this.remove(recent); //remove from outer list 
            recent = null; //do not allow remove again until next is called
        }
    }

    private class PositionIterable implements Iterable<Position<E>>{
        public Iterator<Position<E>> iterator(){ return new PositionIterator();}
    }

    //returns an iterable reprsentation of the list's positions
    public Iterable<Position<E>> positions(){
        return new PositionIterable();
    }

    private class ElementIterator implements Iterator<E>{
        Iterator<Position<E>> posIterator = new PositionIterator();
        public boolean hasNext(){ return posIterator.hasNext();}
        public E next(){return posIterator.next().getElement();}
        public void remove(){posIterator.remove();}
    }
    public Iterator<E> iterator(){ return new ElementIterator();}

    public static void insertionSort(LinkedPositionalList<Integer> list){
        Position<Integer> marker = list.first();
        while(marker != list.last()){
            Position<Integer> pivot = list.after(marker);
            int value = pivot.getElement();
            if(value > marker.getElement()){
                marker = pivot; 
            } else {
                Position<Integer> walk = marker;
                while(walk != list.first() && list.before(walk).getElement() > value){
                    walk = list.before(walk);
                }
                list.remove(pivot);
                list.addBefore(walk, value);
            }
        }

    }


    public static void main(String[] args) {
        LinkedPositionalList<Integer> x = new LinkedPositionalList<>();
        LinkedPositionalList<String> z = new LinkedPositionalList<>();
        Integer[] test1 = {10,12,53,2,11};
        String[] test2 = {"alampanaya", "fag", "wali", "roxas", "geed"};
        for(int i = 0; i < test1.length; i++){
           x.addLast(test1[i]);
        }
        System.out.println("test1:" + x.size());
        while(x.size() != 0){
            Node<Integer> current = (Node<Integer>) x.first();
            System.out.println(x.remove(current));
            System.out.println(x.size());
        }
        for(int i = 0; i < test2.length; i++){
            z.addLast(test2[i]);
         }
        System.out.println("test2:" + z.size());
        while(z.size() != 0){
            Node<String> current = (Node<String>) z.first();
            System.out.println(z.remove(current));
            System.out.println(z.size());
        }
      
    }

}