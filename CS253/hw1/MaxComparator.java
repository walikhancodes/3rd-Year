/*THIS CODE WAS MY OWN WORK, 
IT WAS WRITTEN WITHOUT CONSULTING ANYSOURCES 
OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. 
[Wali Khan, 2308097]*/
import java.util.Comparator;
public class MaxComparator<K> implements Comparator<K> {
    @SuppressWarnings("unchecked")
    public int compare(K a, K b) throws ClassCastException {
        return ((Comparable<K>)b).compareTo(a);
    }
}
