package com.nsl.interfaces;

/**
 * Interface for list.
 * This is a sequence interface.
 * The range of index: [0, |L| - 1] where L is a list.
 */
public interface List<E> {
    /**
     * Add an element at idx.
     * 
     * @param e an element to add to the list. e is non-null.
     * @param idx an index of the list where e will be added.
     *            0 <= idx <= this.size() - 1
     * @return true if succeeded to insert e, otherwise false
     */
    boolean insertAt(E e, int idx);
    
    /**
     * Delete an element at idx.
     *
     * @param idx an index of the list where L[idx] is supposed to be removed.
     * @return removed element.
     */
    E deleteAt(int idx);

    /**
     * Add an element at the first of the list.
     * 
     * @param e an element to add. e is non-null.
     * @return true if succeeded to insert e, otherwise false.
     */
    boolean insertFirst(E e);

    /**
     * Delete an element at the start of the list.
     *
     * @return removed element.
     */
    E deleteFirst();

    /**
     * Add an element at the end of the list.
     *
     * @param e an element to add. e is non-null.
     * @return true if succeeded to insert e, otherwise false.
     */
    boolean insertLast(E e);

    /**
     * Delete an element at the end of the list.
     *
     * @return removed element.
     */
    E deleteLast();

    /**
     * Return size of this list. i.e. the number of elements this list contains. |L| where L is a list.
     *
     * @return size of this list. If this list has just instantiated, it would return 0.
     */
    int size();

    /**
     * Check if this list is empty.
     *
     * @return true is this.size() == 0, otherwise false.
     */
    boolean isEmpty();

    /**
     * Get an element which has an index of idx in this list.
     *
     * @param idx 
     */
    E getAt(int idx);
}
