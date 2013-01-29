package com.scurab.gwt.rlw.server.util;

import java.util.*;

public class DoubleHashMap<K1, K2, V> {

    private HashMap<K1, HashMap<K2, V>> mData = new HashMap<K1, HashMap<K2, V>>();

    public void clear(){
        for(K1 key : mData.keySet()){
            mData.get(key).clear();
        }
    }

    public V put(K1 key1, K2 key2, V value){
        V oldValue = null;

        HashMap<K2, V> mSub = mData.get(key1);
        if(mSub == null){
            mSub = new HashMap<K2, V>();
            mData.put(key1, mSub);
        }

        oldValue = mSub.get(key2);
        mSub.put(key2, value);
        return oldValue;
    }

    public boolean containsKey(K1 key1, K2 key2){
        return mData.containsKey(key1) && mData.get(key1).containsKey(key2);
    }

    public boolean containsValue(V v){
        for(K1 key : mData.keySet()){
            if(mData.get(key).containsValue(v)){
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty(){
        for(K1 key : mData.keySet()){
            if(mData.get(key).size() > 0){
                return false;
            }
        }
        return true;
    }

    public int size(){
        int result = 0;
        for(K1 key : mData.keySet()){
            result += mData.get(key).size();
        }
        return result;
    }

    public V remove(K1 key1, K2 key2){
        HashMap<K2, V> sub = mData.get(key1);
        V removed = null;
        if(sub != null){
            removed = sub.remove(key2);
        }
        return removed;
    }

    public V get(K1 key1, K2 key2){
        HashMap<K2, V> sub = mData.get(key1);
        V v = null;
        if(sub != null){
            v = sub.get(key2);
        }
        return v;
    }

    public Collection<V> values(){
        ArrayList<V> result = new ArrayList<V>();
        for(K1 key : mData.keySet()){
            result.addAll(mData.get(key).values());
        }
        return result;
    }

    public Set<KeyWrapper<K1, K2>> keySet() {
        Set<KeyWrapper<K1, K2>> set = new HashSet<KeyWrapper<K1, K2>>();
        for(K1 key : mData.keySet()){
            HashMap<K2, V> sub = mData.get(key);
            if(sub != null){
                for(K2 key2 : sub.keySet()){
                    KeyWrapper<K1, K2> v = new KeyWrapper<K1, K2>(key, key2);
                    set.add(v);
                }
            }
        }
        return set;
    }


    public static class KeyWrapper<K1, K2> {
        private final K1 mKey1;
        private final K2 mKey2;

        private KeyWrapper(K1 k1, K2 k2) {
            mKey1 = k1;
            mKey2 = k2;
        }

        public K1 getKey1() {
            return mKey1;
        }

        public K2 getKey2() {
            return mKey2;
        }
    }

}