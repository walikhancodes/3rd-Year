import java.util.Arrays;

public class LinkedStack<E> implements Stack<E>{

    private SinglyLinkedList<E> list = new SinglyLinkedList<>();//empty list
    public LinkedStack(){}; //new stack relies on empty list
    public int size(){return list.size();}
    public boolean isEmpty(){return list.isEmpty();}
    public void push(E element){list.addFirst(element);}
    public E top(){return list.first();}
    public E pop(){
       return list.removeFirst();
    }

    public static <E> void reverse(E[] a){
        LinkedStack<E> buffer = new LinkedStack<>();
        for(int i = 0; i < a.length; i++){
            buffer.push(a[i]);
        }
        for(int i = 0; i < a.length; i++){
            a[i] = buffer.pop();
        }

    }

    public static void main(String[] args) {
        LinkedStack<LinkedStack> x = new LinkedStack<>();
        Integer [] y = {4, 8, 15, 16, 23, 42};
        System.out.println("y = " + Arrays.toString(y));
        String [] z = {"Jack", "Kate", "Hurley", "Jin", "Michael"};
        System.out.print("z = [");
        for(int i = 0; i <z.length -1; i++){

            System.out.print("" + z[i] + ", ");
        }
        System.out.print("Michael]");
        System.out.println();
        System.out.println("Reversing");
        x.reverse(y);
        x.reverse(z);
        System.out.println("y = " + Arrays.toString(y));
        System.out.println("z = " + Arrays.toString(z));





        
    }



}