package com.nsl.ds.hash;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.nsl.interfaces.Set;

/**
 * K must be immutable type and have defined equals, hashCode methods following the Object class contract.
 */
public class HashSet<K, V> implements Set<K, V> {
    private static final int DEFAULT_SIZE = 10;
    private static final int SIZE_INCREMENT = 10;

    private List<Item<K, V>>[] hashTable = (List<Item<K, V>>[])(new List[DEFAULT_SIZE]);

    public HashSet(Map<K, V> map) {
        setEmptyListFrom(0);
        for (K key : map.keySet()) {
            this.insert(key, map.get(key));
        }
    }

    @Override
    public boolean insert(K key, V val) {
        if (hashTable.length - 1 < key.hashCode()) {
            int s = this.hashTable.length;
            setNewTable(hashTable.length + SIZE_INCREMENT);
            setEmptyListFrom(s);
            return insert(key, val);
        }
        hashTable[key.hashCode()].add(new Item<>(key, val));
        reduceArraySize();
        return true;
    }

    private void setEmptyListFrom(int start) {
        for (int i = start; i < this.hashTable.length; i++) {
            this.hashTable[i] = new LinkedList<>();
        }
    }

    private void copyTable(List<Item<K, V>>[] to, int by) {
        for (int i = 0; i < by; i++) {
            to[i] = this.hashTable[i];
        }
    }

    @Override
    public V delete(K key) {
        Item<K, V> finding = new Item<K,V>(key, null);
        V returnVal = hashTable[key.hashCode()].get(hashTable[key.hashCode()].indexOf(finding)).val;
        if (hashTable[key.hashCode()].remove(finding)) {
            reduceArraySize();
            return returnVal;
        } else {
            return null;
        }
    }

    private void setNewTable(int sizeOfNewTable) {
        List<Item<K, V>>[] newTable = (List<Item<K, V>>[])(new List[sizeOfNewTable]);
        copyTable(newTable, newTable.length);
        this.hashTable = newTable;
    }

    private void reduceArraySize() {
        int numOfUnnecessary = 0;
        int idx = this.hashTable.length;
        while (!this.hashTable[--idx].isEmpty()) {
            numOfUnnecessary++;
        }
        setNewTable(this.hashTable.length - numOfUnnecessary);
    }

    @Override
    public V find(K key) {
        int code = key.hashCode();
        if (code > this.hashTable.length - 1) {
            return null;
        }

        Item<K, V> finding = new Item<K,V>(key, null);
        if (this.hashTable[code].contains(finding)) {
            return this.hashTable[code].get(this.hashTable[code].indexOf(finding)).val;
        } else {
            return null;
        }
    }

    @Override
    public boolean contains(K key) {
        try {
            return this.hashTable[key.hashCode()].contains(new Item<K,V>(key, null));
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override
    public K findMax() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public K findMin() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public K findNext(K previousKey) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public K findPrev(K nextKey) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public K[] getAll() {
        // TODO Auto-generated method stub
        return null;
    }
    
    private static class Item<K, V> {
        public K key;
        public V val;

        public Item(K k, V v) {
            this.key = k;
            this.val = v;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Item) {
                return this.key.equals(((Item<K, V>)obj).key);
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return this.key.hashCode();
        }
    }
}
