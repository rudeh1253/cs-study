package com.nsl.interfaces;

/**
 * An interface of set data structure.
 * 
 * Duplicate of elements is not allowed, so It is needed to define equality of items in set data structure.
 * If items are of mutable type, it is not required to implement equality.
 * If items are of immutable type, it is necessary to define equality by overriding
 * equals() method and hashCode() method.
 */
public interface Set<K, V> {
    /**
     * Insert an element with key.
     * If you try inserting an element which the data structure already contains (according to
     * equality you defined or defined by Object superclass), it will fail to insert, but
     * not throw an exception.
     * 
     * @param key key of the element. Every key must have a definition of equality according to the
     *            abstract space.
     * @param val value of the item. Duplicate of value is allowed. val must not be null.
     * @return true if there does not exists the same key so that succeded to insert the element, otherwise false.
     */
    boolean insert(K key, V val);

    /**
     * Delete the element with corresponding key, and return its value.
     * 
     * @param key key of the element. Every key must have a definition of equality according to the
     *            abstract space.
     * @return the value mapped to key. If there does not exists such key, return null.
     */
    V delete(K key);

    /**
     * Find the element using key.
     * Return object is nullable, so clients must be careful.
     * 
     * @param key of the element you are finding.
     * @return the value mapped to key. If there does not exist such key, return null.
     */
    V find(K key);

    /**
     * Check whether the set contains an element keyed the parameter key.
     * 
     * @param key of the element you want to find.
     * @return true if there is an element with corresponding key, otherwise false.
     */
    boolean contains(K key);

    /**
     * Return the stored item with largest key.
     * 
     * @return the stored item with largest key. If this set is empty, throws a runtime exception.
     */
    K findMax();

    /**
     * Return the stored item with smallest key.
     * 
     * @return the stored item with smallest key. If this set is empty, throws a runtime exception.
     */
    K findMin();

    /**
     * Return the stored item with smallest key larger than previousKey.
     * 
     * @param previousKey you are finding the key that is preceded by previousKey.
     * @return the stored item with smalllest key larger than previousKey. If there is not previousKey, throws a runtime exception.
     */
    K findNext(K previousKey);

    /**
     * Return the stroed item with largest key smaller than nextKey.
     * 
     * @param nextKey you are finding the key that is followd by nextKey.
     * @return the stroed item with largest key smaller than nextKey. If there is not nextKey, throws a runtime exception.
     */
    K findPrev(K nextKey);

    /**
     * Return an array containing all keys.
     * 
     * @return an array containing all keys. Return object is non-null, so if the data structure empty, return an empty array.
     */
    K[] getAll();
}