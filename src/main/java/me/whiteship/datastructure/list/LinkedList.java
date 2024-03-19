package me.whiteship.datastructure.list;

import java.util.*;

public class LinkedList<E> extends AbstractSequentialList<E> implements List<E>, Deque<E>, Cloneable, java.io.Serializable {

    transient int size = 0;
    transient Node<E> first;
    transient Node<E> last;

    public LinkedList() {
    }

    public LinkedList(Collection<? extends E> c) {
        this();
        addAll(c);
    }

    private void linkFirst(E e) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(null, e, f);
        first = newNode;
        if(f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
        modCount++;
    }

    private void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if(l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
        modCount++;
    }

    void linkBefore(E e, Node<E> succ) {
        final Node<E> prev = succ.prev;
        final Node<E> newNode = new Node<>(prev, e, succ);
        succ.prev = newNode;
        if(prev == null)
            first = newNode;
        else
            prev.next = newNode;
        size++;
        modCount++;
    }

    private E unlinkFirst(Node<E> f) {
        final E element = f.item;
        final Node<E> next = f.next;
        f.item = null;
        f.next = null;
        first = next;
        if(next == null)
            last = null;
        else
            next.prev = null;
        size--;
        modCount++;
        return element;
    }

    private E unlinkLast(Node<E> l) {
        final E element = l.item;
        final Node<E> prev = l.prev;
        l.item = null;
        l.prev = null;
        last = prev;
        if(prev == null)
            first = null;
        else
            prev.next = null;
        size--;
        modCount++;
        return element;
    }

    E unlink(Node<E> x) {
        final E element = x.item;
        final Node<E> prev = x.prev;
        final Node<E> next = x.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if(next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        modCount++;
        return element;
    }

    public E getFirst() {
        final Node<E> f = first;
        if(f == null)
            throw new NoSuchElementException();
        return f.item;
    }

    public E getLast() {
        final Node<E> l = last;
        if(l == null)
            throw new NoSuchElementException();
        return l.item;
    }

    public E removeFirst() {
        final Node<E> f = first;
        if(f == null)
            throw new NoSuchElementException();
        return unlinkFirst(f);
    }

    public E removeLast() {
        final Node<E> l = last;
        if(l == null)
            throw new NoSuchElementException();
        return unlinkLast(l);
    }

    public void addFirst(E e) { linkFirst(e); }

    public void addLast(E e) { linkLast(e); }

    public boolean contains(Object o) { return indexOf(o) >= 0; }

    public int size() { return size; }

    public boolean add(E e) {
        linkFirst(e);
        return true;
    }

    public boolean remove(Object o) {
        if(o == null) {
            for(Node<E> x = first; x != null; x = x.next) {
                if(x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for(Node<E> x = first; x != null; x = x.next) {
                if(o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    private boolean isElementIndex(int index) { return index >= 0 && index < size; }
    private boolean isPositionIndex(int index) { return index >= 0 && index < size; }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private void checkElementIndex(int index) {
        if(!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
    public void checkPositionIndex(int index) {
        if(!isPositionIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    Node<E> node(int index) {
        Node<E> x;
        if(index < (size >> 1)) {
            x  = first;
            for(int i = 0; i < index; i++)
                x = x.next;
        } else {
            x = last;
            for(int i = size - 1; i > index; i--)
                x= x.prev;
        }
        return x;
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        checkPositionIndex(index);

        Object[] a = c.toArray();
        int numNew = a.length;
        if(numNew == 0)
            return false;

        Node<E> pred, succ;
        if(index == size) {
            succ = null;
            pred = last;
        } else {
            succ = node(index);
            pred = succ.prev;
        }

        for(Object o: a) {
            @SuppressWarnings("unchecked") E e = (E) o;
            Node<E> newNode = new Node<>(pred, e, null);
            if(pred == null)
                first = newNode;
            else
                pred.next = newNode;
            pred = newNode;
        }

        if(succ == null) {
            last = pred;
        } else {
            pred.next = succ;
            succ.prev = pred;
        }

        size += numNew;
        modCount++;
        return true;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return false;
    }

    @Override
    public E pollFirst() {
        return null;
    }

    @Override
    public E pollLast() {
        return null;
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E element() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
