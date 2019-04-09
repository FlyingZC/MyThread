package com.zc.concurrent.frameworkuse;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class T01Timer {
    Timer timer;

    public static void main(String[] args) {
        T01Timer demo = new T01Timer();
        demo.start0();
    }

    public void start0() {
        if (PurgeTaskStatus.STARTED == purgeTaskStatus) {// 防止重复启动
            // LOG.warn("Purge task is already running.");
            return;
        }
        // Don't schedule the purge task with zero or negative purge interval.
        long purgeInterval = 1000;
        if (purgeInterval <= 0) {// 校验
            // LOG.info("Purge task is not scheduled.");
            return;
        }

        Timer timer = new Timer("PurgeTask", true);
        TimerTask task = new PurgeTask("", "", 1000);
        timer.scheduleAtFixedRate(task, 0, TimeUnit.HOURS.toMillis(purgeInterval));// TODO ???

        purgeTaskStatus = PurgeTaskStatus.STARTED;
    }

    public void shutdown0() {
        if (PurgeTaskStatus.STARTED == purgeTaskStatus) {
            timer.cancel();
            purgeTaskStatus = PurgeTaskStatus.COMPLETED;
        } else {
            //
        }
    }

    public enum PurgeTaskStatus {
        NOT_STARTED, STARTED, COMPLETED;
    }

    private PurgeTaskStatus purgeTaskStatus = PurgeTaskStatus.NOT_STARTED;

    static class PurgeTask extends TimerTask {
        private String logsDir;
        private String snapsDir;
        private int snapRetainCount;

        public PurgeTask(String dataDir, String snapDir, int count) {
            logsDir = dataDir;
            snapsDir = snapDir;
            snapRetainCount = count;
        }

        @Override
        public void run() {
            //
            System.out.println("-- didi --");
        }
    }
}
