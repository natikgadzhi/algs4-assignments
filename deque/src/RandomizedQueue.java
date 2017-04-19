/*
 * Corner cases.
 * The order of two or more iterators to the same randomized queue must be mutually independent;
 * each iterator must maintain its own random order.
 * Throw a java.lang.NullPointerException if the client attempts to add a null item;
 * throw a java.util.NoSuchElementException if the client attempts to sample or dequeue an item
 *   from an empty randomized queue;
 * throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;
 * throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are
 *   no more items to return.
 *
 * Performance requirements. Your randomized queue implementation must support each randomized queue operation
 *   (besides creating an iterator) in constant amortized time. That is, any sequence of m randomized queue operations
 *     (starting from an empty queue) should take at most cm steps in the worst case, for some constant c.
 *     A randomized queue containing n items must use at most 48n + 192 bytes of memory.
 *     Additionally, your iterator implementation
 *     must support operations next() and hasNext() in constant worst-case time;
 *     and construction in linear time;
 *     you may (and will need to) use a linear amount of extra memory per iterator.
 */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a;
    private int n;

    public RandomizedQueue() {
        n = 0;
        a = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) throw new java.lang.NullPointerException("Can't insert null value");
        if (n == a.length) resize(n*2);
        a[n++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");

        int rnd = StdRandom.uniform(n);

        Item item = a[rnd];

        a[rnd] = a[n-1];
        a[n-1] = null;
        n--;

        if (n > 0 && n == a.length/4) resize(a.length/2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return a[StdRandom.uniform(n)];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }


    // FIXME this works, but uses a bit more memory than I'd want.
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int served = 0;
        private int[] idx;

        public RandomizedQueueIterator() {
            for (int i = 0; i < n; i++) {
                idx[i] = i;
            }

            StdRandom.shuffle(idx);
        }

        public boolean hasNext() {
            return served < n-1;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[idx[served++]];
        }
    }

    private void resize(int capacity) {
        assert capacity >= n;
        a = java.util.Arrays.copyOf(a, capacity);
    }

    public static void main(String[] args) {
        System.out.print("RandomizedQueue Unit Tests:");
    }
}