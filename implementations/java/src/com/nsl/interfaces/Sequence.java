package com.nsl.interfaces;

import java.util.List;

/**
 * Interface for sequence data structure.
 * This is a sequence interface.
 * The range of index: [0, |L| - 1] where L is a sequence data structure.
 * 
 * Duplicate of elements is allowed.
 */
public interface Sequence<E> {
    /**
     * Add an element at idx.
     * This is a modifier.
     * 
     * @param e an element to add to the sequence data structure. e is non-null.
     * @param idx an index of the sequence data structure where e will be added.
     *            0 <= idx <= this.size() - 1
     * @return true if succeeded to insert e, otherwise false. If idx is out of the required range, throws a runtime exception.
     */
    boolean insertAt(E e, int idx);
    
    /**
     * Delete an element at idx.
     * This is a modifier.
     *
     * @param idx an index of the sequence data structure where L[idx] is supposed to be removed.
     *            0 <= idx <= this.size() - 1
     * @return removed element. If this sequence data structure is empty, throws a runtime exception.
     *         If idx is out of the required range, throws a runtime exception.
     */
    E deleteAt(int idx);

    /**
     * Add an element at the first of the sequence data structure.
     * This is a modifier.
     * 
     * @param e an element to add. e is non-null.
     * @return true if succeeded to insert e, otherwise false.
     */
    boolean insertFirst(E e);

    /**
     * Delete an element at the start of the sequence data structure.
     * This is a modifier.
     *
     * @return removed element. If this sequence data structure is empty, throws a runtime exception.
     */
    E deleteFirst();

    /**
     * Add an element at the end of the sequence data structure.
     * This is a modifier.
     *
     * @param e an element to add. e is non-null.
     * @return true if succeeded to insert e, otherwise false.
     */
    boolean insertLast(E e);

    /**
     * Delete an element at the end of the sequence data structure.
     * This is a modifier.
     *
     * @return removed element. If this sequence data structure is empty, throws a runtime exception.
     */
    E deleteLast();

    /**
     * Return size of this sequence data structure. i.e. the number of elements this sequence data structure contains. |L| where L is a sequence data structure.
     *
     * @return size of this sequence data structure. If this sequence data structure has just instantiated, it would return 0.
     */
    int size();

    /**
     * Check if this sequence data structure is empty.
     *
     * @return true is this.size() == 0, otherwise false.
     */
    boolean isEmpty();

    /**
     * Get an element which has an index of idx in this sequence data structure.
     *
     * @param idx an index of the element to get. 0 <= idx <= this.size() - 1
     * @return the element at idx. If this sequence data structure is empty, return null.
     *         If idx is out of the required range, throws a runtime exception.
     */
    E getAt(int idx);

    /**
     * Get an element at the first of the sequence data structure.
     * 
     * @return the element at the first of the sequence data structure. If this sequence data structure is empty, return null.
     */
    E getFirst();

    /**
     * Check if this sequence data structure contains e.
     * 
     * @param e the element to check if it exists.
     * @return true if there is e in this sequence data structure, otherwise false.
     *         Searching start at which index = 0, such that if e has been inserted twice before,
     *         return the element whose index is lower.
     */
    boolean contains(E e);

    /**
     * Check if this sequence data structure contains e.
     * 
     * @param e the element to check if it exists.
     * @param startIdx the index at which searching starts.
     * @return true if there is e in this sequence data structure, otherwise false.
     *         Searching start at which index = 0, such that if e has been inserted twice before,
     *         return the element whose index is lower.
     */
    boolean contains(E e, int startIdx);

    /**
     * Replace the element at the index idx with e.
     * 
     * @param e the element to change with.
     * @param idx an index of the element to set. 0 <= idx <= this.size() - 1
     * @return the previous element.
     */
    E setAt(E e, int idx);

    /**
     * Return an array containing all elements.
     * 
     * @return an array containing all elements. Return object is non-null, so if the data structure empty, return an empty array.
     */
    List<E> getAll();
}