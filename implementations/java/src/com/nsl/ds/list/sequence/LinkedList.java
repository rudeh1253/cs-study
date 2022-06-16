package com.nsl.ds.list.sequence;

import java.util.ArrayList;
import java.util.List;

import com.nsl.interfaces.Sequence;

/**
 * Linked list as a sequence data structure.
 * Doubly linked list.
 */
public class LinkedList<E> implements Sequence<E> {
    private final Node<E> head;
    private final Node<E> tail;
    private int size = 0;

    /**
     * Build method of a sequence linked list data structure.
     * 
     * @param s an array containing elements to build as a linked list.
     *          If you want to build an empty linked list, just hand over an empty array
     *          as an argument.
     */
    public LinkedList(E[] s) {
        // O(n)-time
        head = new Node<>();
        tail = new Node<>();
        head.next = tail;
        tail.prev = head;

        for (E e : s) {
            this.insertLast(e);
        }
    }

    /**
     * Build method of a sequence linked list data structure.
     * 
     * @param collection an iterable object containing elements to build as a linked list.
     *                   If you want to build an empty linked list, just hand over an empty object(not null)
     *                   as an argument.
     */
    public LinkedList(Iterable<E> collection) {
        this.head = new Node<>();
        this.tail = new Node<>();
        head.next = tail;
        tail.prev = head;

        for (E e : collection) {
            this.insertLast(e);
        }
    }

    // Given two nodes which should be inserted nearby,
    // link the nodes.
    // order: newNode -> prev
    private void addNodeToNext(Node<E> newNode, Node<E> p) {
        Node<E> n = p.next;
        linkTwoNode(p, newNode);
        linkTwoNode(newNode, n);
    }

    // Link p and n.
    private void linkTwoNode(Node<E> p, Node<E> n) {
        p.next = n;
        n.prev = p;
    }

    // Create a node containing the given element value and return the node.
    private Node<E> createNewNode(E e) {
        Node<E> newNode = new Node<>();
        newNode.val = e;
        return newNode;
    }

    @Override
    public boolean insertAt(E e, int idx) {
        checkIndexInvariant(idx);
        Node<E> cur = getCurAt(idx);
        addNodeToNext(createNewNode(e), cur);
        this.size++;
        return true;
    }

    // Get a cursor pointing to idx-th node from the first of list.
    private Node<E> getCurAt(int idx) {
        Node<E> cur = head;
        for (int i = 0; i < idx + 1; i++) {
            cur = cur.next;
        }
        return cur;
    }

    // If index is out of bound, throws a runtime exception.
    private void checkIndexInvariant(int idx) {
        if (!(0 <= idx && idx <= this.size - 1)) {
            throw new RuntimeException("Index out of bound.\nRequired is 0 <= idx <= " + (this.size() - 1) + ", but actually the argument is " + idx + ".");
        }
    }

    @Override
    public E deleteAt(int idx) {
        if (this.isEmpty()) {
            return null;
        }
        checkIndexInvariant(idx);
        Node<E> cur = getCurAt(idx);
        this.size--;
        return deleteNode(cur);
    }

    private E deleteNode(Node<E> node) {
        E e = node.val;
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.next = null;
        node.prev = null;
        node.val = null;
        return e;
    }

    @Override
    public boolean insertFirst(E e) {
        Node<E> newNode = createNewNode(e);
        addNodeToNext(newNode, this.head);
        this.size++;
        return true;
    }

    @Override
    public E deleteFirst() {
        if (this.isEmpty()) {
            return null;
        }
        this.size--;
        return deleteNode(this.head.next);
    }

    @Override
    public boolean insertLast(E e) {
        Node<E> newNode = createNewNode(e);
        linkTwoNode(this.tail.prev, newNode);
        linkTwoNode(newNode, this.tail);
        this.size++;
        return true;
    }

    @Override
    public E deleteLast() {
        if (this.isEmpty()) {
            return null;
        }
        this.size--;
        return deleteNode(this.tail.prev);
    }

    @Override
    public int size() { return this.size; }

    @Override
    public boolean isEmpty() { return this.size() == 0; }

    @Override
    public E getAt(int idx) {
        if (this.isEmpty()) {
            return null;
        }
        checkIndexInvariant(idx);
        return getCurAt(idx).val;
    }

    @Override
    public E getFirst() {
        if (this.isEmpty()) {
            return null;
        }
        return this.head.next.val;
    }

    @Override
    public boolean contains(E e) {
        return exeContains(e, this.head.next);
    }

    @Override
    public boolean contains(E e, int startIdx) {
        return exeContains(e, getCurAt(startIdx));
    }
    
    // Implement contains methods above.
    // Checking is executed starting at curNode. After checking
    // curNode, set curNode = curNode.next and so on.
    // When it encounters this.tail(dummy node), escape the loop and return false,
    // which means there is not the item you are finidng.
    private boolean exeContains(E finding, Node<E> curNode) {
        while (curNode != this.tail) {
            if (finding.equals(curNode.val)) {
                return true;
            }
            curNode = curNode.next;
        }
        return false;
    }

    @Override
    public E setAt(E e, int idx) {
        checkIndexInvariant(idx);
        Node<E> n = getCurAt(idx);
        E previousVal = n.val;
        n.val = e;
        return previousVal;
    }

    @Override
    public List<E> getAll() {
        List<E> list = new ArrayList<>();
        Node<E> cur = this.head.next;
        int i = 0;
        while (cur != this.tail) {
            list.add(i++, cur.val);
            cur = cur.next;
        }
        return list;
    }

    /**
     * A definition of data structure to simply store information of a node.
     * Node object should never be exposed.
     * This class can be accessed within only com.nsl.ds.list.sequence.LinkedList class which is outer class.
     */
    private static class Node<E> {
        public Node<E> prev = null;
        public Node<E> next = null;
        public E val = null;

        public Node() { }
    }
}