package edu.miracosta.cs113;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class HashTableChain<K, V> implements Map<K,V> {
    private static final int INITIAL_CAPACITY = 105;
    private static final double LOAD_THRESHOLD = 0.75;
    private LinkedList<Entry<K,V>>[] table;
    private int numKeys;

    public HashTableChain(){
        table = new LinkedList[INITIAL_CAPACITY];
        numKeys = 0;
    }

    public V put(K key, V value) {
        int index = key.hashCode() % table.length;

        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }else {
            for (Entry<K,V> entry : table[index]) {
                if (entry.key.equals(key)) {
                    V oldValue = entry.getValue();
                    entry.setValue(value);
                    return oldValue;
                }
            }
        }
        table[index].addFirst(new Entry<>(key,value));
        numKeys ++;
        if (numKeys > (LOAD_THRESHOLD * table.length)) {
            rehash();
        }
        return null;
    }
    private void rehash() {
        numKeys = 0;
        LinkedList<Entry<K, V>>[] original = table;
        table = new LinkedList[(original.length * 2) % 2 == 0 ? original.length * 2 : (original.length * 2) + 1];
        for (int i = 0; i < original.length; i ++) {
            if (original[i] != null) {
                for (Entry entry : original[i]) {
                    this.put((K)entry.getKey(), (V)entry.getValue());
                }
            }
        }
    }

    @Override
    public int size() {
        return numKeys;
    }

    @Override
    public boolean isEmpty() {
        return numKeys == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int index = getIndexOfKey(key);
        if (table[index] == null) {
            return false;
        } else {
            for (Entry entry: table[index]) {
                if (entry.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < table.length; i ++) {
            if (table[i] != null) {
                for (Entry entry: table[i]) {
                    if (entry.value.equals(value)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public V get(Object key) {
        int index = getIndexOfKey(key);
        if (table[index] == null) {
            return null;
        }else{
            for (Entry<K,V> entry : table[index]) {
                if (entry.key.equals(key)){
                    return entry.getValue();
                }
            }
            return null;
        }
    }

    @Override
    public V remove(Object key) {
        V removedValue = null;
        int index = getIndexOfKey(key);
        if (table[index] == null) {
            return null;
        } else {
            for (Entry<K,V> entry : table[index]) {
                if (entry.key.equals(key)){
                    removedValue = entry.value;
                    table[index].remove(entry);
                }
            }
            if (table[index].isEmpty()) {
                table[index] = null;
            }
            return removedValue;
        }
    }

    @Override
    public void clear() {
        table = new LinkedList[INITIAL_CAPACITY];
        numKeys = 0;
    }

    @Override
    public Set<K> keySet() {
        return new KeySet();
    }

    @Override
    public Collection<V> values() {
        //Not Needed
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        //Not Needed
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    public int getIndexOfKey(Object key) {
        int temp = key.hashCode() % table.length;
        if (temp < 0) {
            temp += table.length;
        }
        return temp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < table.length; i ++) {
            if (table[i] != null) {
                sb.append("Current Index in Table: " + i + "\n");
                for (Entry entry: table[i]) {
                    sb.append("\t" + entry + "\n");
                }
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Map)) {
            return false;
        } else {
            Set temp = ((Map)other).entrySet();
            if (temp.size() != this.size()) {
                return false;
            }

            Iterator iterator = temp.iterator();
            Map.Entry entry;

            while(iterator.hasNext()) {
                entry = (Map.Entry)iterator.next();
                if (this.get(entry.getKey()) == null || !this.get(entry.getKey()).equals(entry.getValue())) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public int hashCode() {
        Iterator iterator = (new EntrySet()).iterator();
        int hashCode = 0;
        while (iterator.hasNext()) {
            hashCode += iterator.next().hashCode();
        }
        return hashCode;
    }

    private static class Entry<K, V> implements Map.Entry<K,V>{
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Map.Entry)) {
                return false;
            } else {
                Map.Entry otherEntry = (Map.Entry)other;
                return otherEntry.getKey().equals(this.getKey()) && otherEntry.getValue().equals(this.getValue());
            }
        }

        @Override
        public int hashCode() {
            return  (key==null ? 0 : key.hashCode()) ^ (value==null ? 0 : value.hashCode());
        }
    }
    private class KeySet extends AbstractSet<K> {

        @Override
        public int size() {
            return numKeys;
        }

        @Override
        public Iterator<K> iterator() {
            return new KeyIterator();
        }
        private class KeyIterator implements Iterator<K> {
            private int index = 0; //Next key to be accessed
            private int tableIndex = 0; //Index of current LinkedList (Iterator)
            private Map.Entry<K,V> lastItemReturned;
            private Iterator<Entry<K,V>> chainIterator; //Iterator for current Linked List

            @Override
            public boolean hasNext() {
                return index < numKeys;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (chainIterator == null || !chainIterator.hasNext()) {
                    chainIterator = getNextChainIterator();
                }
                lastItemReturned = chainIterator.next();
                index ++;
                return lastItemReturned.getKey();
            }
            public Iterator<Entry<K,V>> getNextChainIterator() {
                if (tableIndex != 0) {
                    tableIndex ++;
                }
                while (tableIndex < table.length) {
                    if (table[tableIndex] != null && !table[tableIndex].isEmpty()) {
                        return table[tableIndex].iterator();
                    }
                    tableIndex ++;
                }
                return null;
            }


            @Override
            public void remove() {
                if (lastItemReturned == null) {
                    throw new NoSuchElementException();
                } else {
                    KeySet.this.remove(lastItemReturned);
                    lastItemReturned = null;
                }
            }
        }
    }
    private class EntrySet extends AbstractSet<Map.Entry<K,V>> {

        @Override
        public int size() {
            return numKeys;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new SetIterator();
        }

        private class SetIterator implements Iterator<Map.Entry<K,V>> {
            private int index = 0; //Next key to be accessed
            private int tableIndex = 0; //Index of current LinkedList (Iterator)
            private Map.Entry<K,V> lastItemReturned;
            private Iterator<Entry<K,V>> chainIterator; //Iterator for current Linked List

            @Override
            public boolean hasNext() {
                return index < numKeys;
            }

            @Override
            public Map.Entry<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (chainIterator == null || !chainIterator.hasNext()) {
                    chainIterator = getNextChainIterator();
                }
                lastItemReturned = chainIterator.next();
                index ++;
                return lastItemReturned;
            }
            public Iterator<Entry<K,V>> getNextChainIterator() {
                if (tableIndex != 0) {
                    tableIndex ++;
                }
                while (tableIndex < table.length) {
                    if (table[tableIndex] != null && !table[tableIndex].isEmpty()) {
                        return table[tableIndex].iterator();
                    }
                    tableIndex ++;
                }
                return null;
            }

            @Override
            public void remove() {
                if (lastItemReturned == null) {
                    throw new NoSuchElementException();
                } else {
                    EntrySet.this.remove(lastItemReturned);
                    lastItemReturned = null;
                }
            }
        }
    }
}