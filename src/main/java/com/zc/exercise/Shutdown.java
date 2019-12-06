package com.zc.exercise;

import java.io.IOException;
//关机
public class Shutdown {
    public static void main(String[] args) throws IOException {
        Runtime.getRuntime().exec(System.getenv("windir")+"\\system32\\shutdown.exe -s -f");
    }
}
