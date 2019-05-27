package com.zc.concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 一个基于链接节点的无界线程安全队列。此队列按照 FIFO（先进先出）原则对元素进行排序。队列的头部 是队列中时间最长的元素。
 * 队列的尾部 是队列中时间最短的元素。新的元素插入到队列的尾部，队列获取操作从队列头部获得元素。
 * 当多个线程共享访问一个公共 collection 时，ConcurrentLinkedQueue 是一个恰当的选择。此队列不允许使用 null 元素。
 *
 * 此实现采用了有效的“无等待 (wait-free)”算法，
 * 该算法基于 Maged M. Michael 和 Michael L. Scott 合著的 Simple, Fast, and Practical Non-Blocking and Blocking Concurrent Queue Algorithms 中描述的算法。
 *
 * 需要小心的是，与大多数 collection 不同，size 方法不是 一个固定时间操作。由于这些队列的异步特性，确定当前元素的数量需要遍历这些元素。
 *
 * 此类及其迭代器实现了 Collection 和 Iterator 接口的所有可选 方法。
 *
 * 内存一致性效果：当存在其他并发 collection 时，将对象放入 ConcurrentLinkedQueue 之前的线程中的操作
 * happen-before 随后通过另一线程从 ConcurrentLinkedQueue 访问或移除该元素的操作。
 */
public class T07ConcurrentLinkedQueue {
    /**
     *  boolean	add(E e)
     *           将指定元素插入此队列的尾部。add()内部直接调的就是 offer(),两个方法一样的效果
     *  boolean	contains(Object o)
     *           如果此队列包含指定元素，则返回 true。
     *  boolean	isEmpty()
     *           如果此队列不包含任何元素，则返回 true。
     *  Iterator<E>	iterator()
     *           返回在此队列元素上以恰当顺序进行迭代的迭代器。
     *  boolean	offer(E e)
     *           将指定元素插入此队列的尾部。
     *  E	peek()
     *           获取但不移除此队列的头；如果此队列为空，则返回 null。
     *  E	poll()
     *           获取并移除此队列的头，如果此队列为空，则返回 null。
     *  boolean	remove(Object o)
     *           从队列中移除指定元素的单个实例（如果存在）。
     *  int	size()
     *           返回此队列中的元素数量。!!!需要遍历集合的,会有性能问题
     *  Object[]	toArray()
     *           返回以恰当顺序包含此队列所有元素的数组。
     * <T> T[]	toArray(T[] a)
     *           返回以恰当顺序包含此队列所有元素的数组；返回数组的运行时类型是指定数组的运行时类型。
     */

    private static ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                q.add("a1");
                q.add("a2");
                q.add("a3");
                q.add("a4");
                q.contains("a1");
                q.isEmpty();

                q.peek();
                q.poll();
                q.poll();
                q.poll();
                q.poll();

                q.size();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
//                q.add("b1");
//                q.add("b2");
//                q.add("b3");
//                q.add("b4");
//                q.contains("b1");
//                q.isEmpty();
//
//                q.peek();
//                q.poll();
//
//                q.size();
            }
        }).start();

    }
}
