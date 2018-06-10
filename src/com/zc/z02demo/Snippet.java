package com.zc.z02demo;

import java.util.HashMap;
import java.util.UUID;

public class Snippet
{
    public static void main(String[] args)
    {

        final HashMap<String, String> map = new HashMap<String, String>(2);

        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 0; i < 10000; i++)
                {
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            map.put(UUID.randomUUID().toString(), "");
                        }
                    }, "ftf" + i).start();
                }
            }
        }, "ftf");
        t.start();
        try
        {
            t.join();
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
