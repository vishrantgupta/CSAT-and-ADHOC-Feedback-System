/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vishrant.cf.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class CommonUtils {

    public static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> sortMapByValues(Map<K, V> map) {

        SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(
                new Comparator<Map.Entry<K, V>>() {
                    @Override
                    public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                        Object val1 = e1.getValue();
                        Object val2 = e2.getValue();
                        if ((val1 instanceof String) && (val2 instanceof String)) {
                            return val1.toString().toLowerCase().compareTo(val2.toString().toLowerCase());
                        } else {
                            int res = e1.getValue().compareTo(e2.getValue());
                            return res != 0 ? res : 1;
                        }
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
    
}