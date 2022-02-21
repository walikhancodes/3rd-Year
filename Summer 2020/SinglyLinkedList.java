public class SinglyLinkedList<E> {
    //Nested Node class within t he SinglyLinkedList class
    
    private static class Node<E>{
        //instance variables in the Node class
        private E element;
        private Node<E> next;
        //constructor for the node class
        public Node(E e, Node<E> n){
            element = e;
            next = n;
        }
        //access methods for the node class
        public E getElement(){return element;}
        public Node<E> getNext(){return next;}
        //upadate method that updates the next node 
        public void setNext(Node<E> n){next = n;}

    }

    //instance variables of the SinglyLinkedList
    private Node<E> head = null; //the head
    private Node<E> tail = null; // the tail
    private int size = 0; // keeps track of size
    //constructs an initial empty list
    public SinglyLinkedList(){}
    //access methods 
    public int size(){return size;}
    public boolean isEmpty(){return size == 0;}
    public E first(){
        if(isEmpty()){
            return null;
        }
        return head.getElement();
    }
    public E last(){
        if(isEmpty()){
            return null;
        }
        return tail.getElement();
    }
    public void addFirst(E e){
        head = new Node<>(e, head);
        if(size == 0){
            tail = head;
        }
        size++;
    }

    public void addLast(E e){
        Node<E> newest = new Node<>(e,null);
        if(isEmpty()){
            head = newest;
        }
        else{
            tail.setNext(newest);
        }
        tail = newest;
        size++;
    }

    public E removeFirst(){
        if(isEmpty()){return null;}
        E answer = head.getElement();
        head = head.getNext();
        size--;
        if(size == 0){
            tail = null;
        }
        return answer;
    }

    public static void main(String[] args) {
        // long startTime = System.currentTimeMillis();
        Integer[] a = new Integer[10];
        Integer[] b = new Integer[10];
        for(int i = 0; i<a.length; i++){
            a[i] = i;
        }
        for(int i = 0; i<b.length; i++){
            b[i] = i;
        }
        SinglyLinkedList<Integer> test = new SinglyLinkedList<>();
        SinglyLinkedList<Integer> test1 = new SinglyLinkedList<>();
        for(int i = 0; i<a.length; i++){
            test.addFirst(a[i]);
        }
        for(int i = 0; i<b.length; i++){
            test1.addFirst(b[i]);
        }
        System.out.println(test.equals(test1));
        for(int i = 0; i < a.length; i++){
            System.out.println("" + test.removeFirst());
        }
        //long endTime = System.currentTimeMillis();
        // long elapsed = endTime - startTime;
        // System.out.println(elapsed);
    }

    public boolean equals(Object o){
        if(o == null){return false;}
        if(getClass() != o.getClass()){return false;}
        SinglyLinkedList other = (SinglyLinkedList) o;
        if(size != other.size){return false;}
        Node a = head;
        Node b = other.head;
        while(a != null){
            if(!a.getElement().equals(b.getElement())){return false;}
            a = a.getNext();
            b = b.getNext();
        }
        return true;
    }

}