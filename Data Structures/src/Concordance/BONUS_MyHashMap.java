package Concordance;

import java.util.Arrays;

// Faster than BST at building but slower at printing/outputing (because outputing sorted)

public class BONUS_MyHashMap {
    static class Entry {
        String word; //key
        MyLinkedList lineNumbers; //value

        Entry(String _word, int lineNumber) {
            word = _word;
            lineNumbers = new MyLinkedList(lineNumber);
        }

        //O(n) when n is the size of the lineNumbers List
        @Override
        public String toString() {
            String separator = " -> ";
            return word + separator + lineNumbers.toString(word.length() + separator.length()) + '\n';
        }
    }

    private final Entry[] array;

    public BONUS_MyHashMap(int size) {
        array = new Entry[size];
    }

    private boolean insert(String word, int lineNumber, int hash) {
        if (array[hash] == null) {
            array[hash] = new Entry(word, lineNumber);
            return true;
        } else if (array[hash].word.equalsIgnoreCase(word)) {
            array[hash].lineNumbers.insert(lineNumber);
            return true;
        }
        return false;
    }

    public void insert(String word, int lineNumber) {
        int hash = hash(word);
        boolean inserted = false;
        while (!inserted) {
            inserted = insert(word, lineNumber, hash);
            hash++; //linear traversal
            if (hash == array.length) {
                hash = 0;
            }
        }
    }

    public String search(String word) {
        int hash = hash(word);
        Entry entry = array[hash];
        int accesses = 0;
        while (accesses < array.length) {
            if (entry != null && entry.word.equalsIgnoreCase(word))
                break; // found word

            hash++; //linear traversal
            if (hash == array.length) hash = 0;
            entry = array[hash];
            accesses++;
        }
        return accesses < array.length ? entry.toString() : "Word Not Found!";
    }

    private int hash(String word) {
        int hash = word.hashCode();
        return ((hash ^ hash >>> 16) & 0x7fffffff) % array.length;
    }

    public String getMostCommonWord() {
        Entry max = array[0];
        for (Entry entry : array) {
            if (entry != null && (max == null || entry.lineNumbers.size > max.lineNumbers.size))
                max = entry;
        }
        assert max != null;
        return max.toString();
    }

    public String toString() {
        Entry[] copy = new Entry[array.length];
        System.arraycopy(array, 0, copy, 0, array.length);
        int nonNulls = 0;
        for (Entry entry : copy) if (entry != null) nonNulls++;
        Entry[] finalCopy = new Entry[nonNulls];
        for (Entry entry : copy) {
            if (entry != null) finalCopy[--nonNulls] = entry;
        }
//        sort(finalCopy);
        Arrays.sort(finalCopy, (c1, c2) -> {
            if (c1 == null && c2 == null) return 0;
            if (c1 == null) return 1;
            if (c2 == null) return -1;
            return c1.word.compareTo(c2.word);
        });
        StringBuilder sb = new StringBuilder();
        for (Entry entry : finalCopy) {
            if (entry != null)
                sb.append(entry);
        }
        return sb.toString();
    }

    //------------------- Improved QuickSort Functions ---------------------//

    //O((nlgn) + O(20n)
    public void sort(Entry[] array) {
        improvedQuickSort(array, 0, array.length - 1);
        insertionSort(array);
    }

    private void improvedQuickSort(Entry[] array, int start, int end) {
        if (end - start < 20) return;
        int pi = improvedPartition(array, start, end);
        improvedQuickSort(array, start, pi - 1);
        improvedQuickSort(array, pi + 1, end);
    }

    /* This function takes last element as pivot,
    places the pivot element at its correct
    position in sorted array, and places all
    smaller (smaller than pivot) to left of
    pivot and all greater elements to right
    of pivot */
    //(Lomuto's approach)
    private int improvedPartition(Entry[] array, int start, int end) {
        swap(end, medianOfThreeIndex(array, start, (start + end) / 2, end));
        Entry pivot = array[end];
        int i = start - 1; // index of smaller element
        for (int j = start; j < end; j++) {
            // If current element is smaller than the pivot
            if (compare(array[j], pivot) < 0) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, end);

        return i + 1;
    }

    private void swap(int i, int j) {
        Entry temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static int compare(Entry c1, Entry c2) {
        if (c1 == null && c2 == null) return 0;
        if (c1 == null) return 1;
        if (c2 == null) return -1;
        return c1.word.compareTo(c2.word);
    }

    //Basically XOR
    private int medianOfThreeIndex(Entry[] array, int x, int y, int z) {
        if (compare(array[x], array[y]) > 0 != compare(array[x], array[z]) > 0) return x;
        if (compare(array[y], array[x]) > 0 != compare(array[y], array[z]) > 0) return y;
        return z;
    }

    public void insertionSort(Entry[] array) {
        for (int i = 1; i < array.length; ++i) {
            Entry key = array[i];
            int j = i - 1;

            while (j >= 0 && compare(array[j], key) > 0) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
    }

}
