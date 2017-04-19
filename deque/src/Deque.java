/*
 * Corner cases.
 * Throw a java.lang.NullPointerException if the client attempts to add a null item;
 * throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque;
 * throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;
 * throw a java.util.NoSuchElementException if the client calls the next() method
 *      in the iterator and there are no more items to return.
 *
 * Performance requirements.
 * Your deque implementation must support each deque operation in constant worst-case time.
 * A deque containing n items must use at most 48n + 192 bytes of memory.
 * and use space proportional to the number of items currently in the deque.
 * Additionally, your iterator implementation must support each operation (including construction)
 * in constant worst-case time.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int dequeSize;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public Deque() {
        first = null;
        last = null;
        dequeSize = 0;
    }

    public boolean isEmpty() {
        return dequeSize == 0;
    }

    public int size() {
        return dequeSize;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("You can't add a null item to a Deque");
        }

        Node oldFirst = first;

        first = new Node();
        first.item = item;
        first.next = oldFirst;

        if (oldFirst != null) oldFirst.previous = first;
        if (last == null) last = first;

        dequeSize++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("You can't add a null item to a Deque");
        }

        Node oldLast = last;

        last = new Node();
        last.item = item;
        // last.next = null;
        last.previous = oldLast;
        if (oldLast != null) oldLast.next = last;
        if (first == null) first = last;

        dequeSize++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("No items in Deque");

        Item item = first.item;
        if (first.next != null) {
            first = first.next;
            first.previous = null;
        } else {
            first = null;
            last = null;
        }


        dequeSize--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("No items in Deque");

        Item item = last.item;
        if (last.previous != null) {
            last = last.previous;
            last.next = null;
        } else {
            last = null;
            first = last;
        }

        dequeSize--;
        return item;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    public static void main(String[] args) {
        System.out.println("Testing Deque: ");

        Deque<Integer> dq = new Deque<Integer>();

        dq.addFirst(0);

        System.out.println("tostr: " + dq.toString());
        System.out.println("last: " + dq.removeLast());
        System.out.println("tostr: " + dq.toString());

        dq.addFirst(2);

        System.out.println(dq.toString());

        System.out.println(dq.removeLast());
    }

}
