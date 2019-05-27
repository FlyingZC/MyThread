package com.zc.concurrent;
// debug src demo
public class T06ThreadLocalSrc {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    private static ThreadLocal<String> threadLocal2 = new ThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("s");
        threadLocal = null;

        new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal2.set("s2");
                threadLocal2.remove();
            }
        }).start();
    }
}
