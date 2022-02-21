
/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING ANY
SOURCES OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. [Wali Khan, 2308097]
*/
import java.util.ArrayList;
import java.util.Collections;

public class QuickSelector<T extends Comparable<T>> {

    public T quickSelect(ArrayList<T> array, int index) {
        // your implementation goes here
        int n = array.size();
        if (n == 1)
            return array.get(0);
        T x = medianOfMedians(array);       // this is the pivot 
        ArrayList<T> l = new ArrayList<>();
        ArrayList<T> e = new ArrayList<>();
        ArrayList<T> g = new ArrayList<>();
        for (T i : array) {
            T cur = i;
            if (x.compareTo(cur) < 0) {
                g.add(cur);
            } else if (x.compareTo(cur) > 0) {
                l.add(cur);
            } else {
                e.add(cur);
            }
        }
        int k = l.size() + 1;
        if (index < k) {
            return quickSelect(l, index);
        } else if (index == k) {
            return x;
        } else {
            int z = index - l.size() - e.size();
            return quickSelect(g, z);
        }
    }

    private T medianOfMedians(ArrayList<T> array) {
        // your implementation goes here
        ArrayList<T> medians = new ArrayList<>();
        ArrayList<T> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(null);
        }
        int i = 0;
        int f = array.size() - (array.size() % 5);
        while (i < f) {
            for (int j = 0; j < 5; j++) {
                list.set(j, array.get(i));
                i++;
            }
            medians.add(median(list));
        }
        ArrayList<T> remaining = new ArrayList<>();
        int x = 0;
        if (f != array.size()) {
            while (i < array.size()) {
                remaining.add(x, array.get(i));
                i++;
            }
            medians.add(median(remaining));
        }
        T answer = median(medians);
        return answer;
    }

    private T median(ArrayList<T> array) {
        ArrayList<T> copy = new ArrayList<>();
        for (T i : array) {
            copy.add(i);
        }
        int size = copy.size();
        sort(copy);
        return copy.get(size / 2);
    }

    private void sort(ArrayList<T> array) {
        int n = array.size();
        for (int k = 1; k < n; k++) {
            T cur = array.get(k);
            int j = k;
            while (j > 0 && cur.compareTo(array.get(j - 1)) < 0) {
                swap(array, j - 1, j);
                j--;
            }
            array.set(j, cur);
        }

    }

    private void swap(ArrayList<T> array, int i, int j) {
        Collections.swap(array, i, j);
    }

}
