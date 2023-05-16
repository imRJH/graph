package ch.rjh.matrice;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author francesc.termine
 */
import java.util.HashMap;
import java.util.Map;

public class BiHashMap<K1, K2, V> {
    private final Map<K1, Map<K2, V>> mMap;

    public BiHashMap() {
        mMap = new HashMap<K1, Map<K2, V>>();
    }

    public void addNode(K1 node) {
        mMap.put(node, new HashMap<>());
    }

    public void addEdge(K1 src, K2 dest, V value) {
        put(src, dest, value);
    }

    public void adjacentNode(K1 node) {
        System.out.println(lineContent(node));
    }

    public void deleteNode(K1 key) {
        removeColumn((K2) key);
        removeLine(key);
    }

    /**
     * Associates the specified value with the specified keys in this map (optional operation). If the map previously
     * contained a mapping for the key, the old value is replaced by the specified value.
     *
     * @param key1
     *            the first key
     * @param key2
     *            the second key
     * @param value
     *            the value to be set
     * @return the value previously associated with (key1,key2), or <code>null</code> if none
     * @see Map#put(Object, Object)
     */
    public V put(K1 key1, K2 key2, V value) {
        Map<K2, V> map;
        if (mMap.containsKey(key1)) {
            map = mMap.get(key1);
        } else {
            map = new HashMap<K2, V>();
            mMap.put(key1, map);
        }
        return map.put(key2, value);
    }

    /**
     * Returns the value to which the specified key is mapped, or <code>null</code> if this map contains no mapping for
     * the key.
     *
     * @param key1
     *            the first key whose associated value is to be returned
     * @param key2
     *            the second key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or <code>null</code> if this map contains no mapping for
     *         the key
     * @see Map#get(Object)
     */
    public V get(K1 key1, K2 key2) {
        if (mMap.containsKey(key1)) {
            return mMap.get(key1).get(key2);
        } else {
            return null;
        }
    }

    /**
     * Returns <code>true</code> if this map contains a mapping for the specified key
     *
     * @param key1
     *            the first key whose presence in this map is to be tested
     * @param key2
     *            the second key whose presence in this map is to be tested
     * @return Returns true if this map contains a mapping for the specified key
     * @see Map#containsKey(Object)
     */
    public boolean containsKeys(K1 key1, K2 key2) {
        return mMap.containsKey(key1) && mMap.get(key1).containsKey(key2);
    }

    public void clear() {
        mMap.clear();
    }

    public Map<K2, V> lineContent(K1 key1){
        if (mMap.containsKey(key1)) {
            return mMap.get(key1);
        } else {
            return null;
        }
    }

    public Map<K1, V> columnContent(K2 key2){
        Map<K1, V> cMap = new HashMap<K1, V>();
        for(K1 key1 : mMap.keySet()){
            cMap.put(key1, mMap.get(key1).get(key2));
        }
        return cMap;
    }

    public BiHashMap transpose(){
        BiHashMap<K2,K1,V> rMap = new BiHashMap<>();
        for(K1 key1 : mMap.keySet()){
            for(K2 key2 : mMap.get(key1).keySet()){
                rMap.put(key2, key1, mMap.get(key1).get(key2));
            }
        }
        return rMap;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("impression de la matrice :\n");
        for(K1 key1 : mMap.keySet()){
            for(K2 key2 : mMap.get(key1).keySet()){
                sb.append(this.get(key1,key2) + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String convertToString( Map<K1, V> map){
        StringBuilder sb = new StringBuilder();
        for(K1 key1 : map.keySet()){
            sb.append("k:").append(key1.toString()).append(",v:").append (map.get(key1).toString()).append(" ");
        }
        return sb.toString();
    }

    public void removeLine(K1 key){
        if(this.mMap.containsKey(key)){
            this.mMap.remove(key);
        }
    }

    public void removeColumn(K2 key2){
        for(K1 key1 : mMap.keySet()){
            if(this.mMap.get(key1).containsKey(key2)){
                this.mMap.get(key1).remove(key2);
            }
        }
    }

}

