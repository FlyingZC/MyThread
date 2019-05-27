package com.zc.concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class T14ConcurrentHashMap {
    private static ConcurrentMap<String, String> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        map.putIfAbsent("1", "a");
        map.get("1");
    }
}
