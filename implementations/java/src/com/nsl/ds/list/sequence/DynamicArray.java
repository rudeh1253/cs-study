package com.nsl.ds.list.sequence;

import java.util.ArrayList;
import java.util.List;

import com.nsl.interfaces.Sequence;

public class DynamicArray<E> implements Sequence<E> {
    private final List<E> arr;

    public DynamicArray(E[] e) {
        arr = new ArrayList<>();
        for (E item : e) {
            this.insertLast(item);
        }
    }

    @Override
    public boolean insertAt(E e, int idx) {
        this.arr.add(idx, e);
        return true;
    }

    @Override
    public E deleteAt(int idx) {
        return this.arr.remove(idx);
    }

    @Override
    public boolean insertFirst(E e) {
        this.arr.add(0, e);
        return true;
    }

    @Override
    public E deleteFirst() {
        return this.arr.remove(0);
    }

    @Override
    public boolean insertLast(E e) {
        return this.arr.add(e);
    }

    @Override
    public E deleteLast() {
        return this.arr.remove(this.arr.size() - 1);
    }

    @Override
    public int size() {
        return this.arr.size();
    }

    @Override
    public boolean isEmpty() {
        return this.arr.isEmpty();
    }

    @Override
    public E getAt(int idx) {
        return this.arr.get(idx);
    }

    @Override
    public E getFirst() {
        return this.arr.get(0);
    }

    @Override
    public boolean contains(E e) {
        return this.arr.contains(e);
    }

    @Override
    public boolean contains(E e, int startIdx) {
        return this.arr.subList(startIdx, this.size() - 1).contains(e);
    }

    @Override
    public E setAt(E e, int idx) {
        return this.arr.set(idx, e);
    }

    @Override
    public List<E> getAll() {
        List<E> copy = new ArrayList<>();
        copy.addAll(this.arr);
        return copy;
    }
}