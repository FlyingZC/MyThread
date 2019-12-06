package com.zc.concurrent.frameworkuse;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class T02Wait {

    long epoch = -1;

    int total = 10;

    int half = total / 2;

    long initLimit = 1;

    long tickTime = 300;

    boolean waitingForNewEpoch = true;

    public static void main(String[] args) throws IOException, InterruptedException {
        T02Wait leader = new T02Wait();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        leader.getEpochToPropose(Long.valueOf(finalI), Long.valueOf(finalI));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    // VisibleForTesting
    protected Set<Long> connectingFollowers = new HashSet<Long>();

    public long getEpochToPropose(long sid, long lastAcceptedEpoch) throws InterruptedException, IOException { // follower的 sid 和 epoch
        synchronized (connectingFollowers) {
            System.out.println(sid);
            if (!waitingForNewEpoch) {
                return epoch;
            }
            if (lastAcceptedEpoch >= epoch) {
                epoch = lastAcceptedEpoch + 1; // 更新 newEcho
            }
            if (isParticipant(sid)) {
                connectingFollowers.add(sid);// 将自己加入连接集合中,方便后续判断leader是否有效
            }
            if (connectingFollowers.contains(this.getId()) && // 包含 leader
                    this.containsQuorum(connectingFollowers)) {// 集合中包含 leader 和 过半的 follower.若有足够多的follower进入，选举有效，则无需等待，并通知其他的等待线程，类似于Barrier
                waitingForNewEpoch = false;
                this.setAcceptedEpoch(epoch);
                connectingFollowers.notifyAll();// 唤醒阻塞在connectingFollowers这个同步监视器上的其他线程
            } else {// 若进入的follower不够,则进入等待,超时即为initLimit时间
                long start = Time.currentElapsedTime();
                long cur = start;
                long end = start + this.getInitLimit() * this.getTickTime();
                while (waitingForNewEpoch && cur < end) {
                    connectingFollowers.wait(end - cur);// wait期间,释放掉connectingFollowers锁,其他线程有机会执行本方法
                    cur = Time.currentElapsedTime();
                }
                if (waitingForNewEpoch) {// 超时了,退出lead过程,重新发起选举
                    throw new InterruptedException("Timeout while waiting for epoch from quorum");
                }
            }
            return epoch;
        }
    }

    private Long getId() {
        return 1L;
    }

    public boolean containsQuorum(Set<Long> set) {
        return (set.size() > half);
    }

    public void setAcceptedEpoch(long epoch) {
        System.out.println("accept epoch : " + epoch);
        this.epoch = epoch;
    }

    public long getInitLimit() {
        return initLimit;
    }

    public long getTickTime() {
        return tickTime;
    }

    private boolean isParticipant(long sid) {
        return true;
    }
}
