package com.zc.concurrent;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

public class T15Atomic {

    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    /**
     * 原子更新基本类型
     * AtomicBoolean：以原子更新的方式更新 Boolean
     * AtomicInteger：以原子更新的方式更新 Integer
     * AtomicLong：以原子更新的方式更新 Long
     * <p>
     * addAndGet(int delta) ：以原子方式将输入的数值与实例中原本的值相加，并返回最后的结果
     * <p>
     * incrementAndGet() ：以原子的方式将实例中的原值进行加1操作，并返回最终相加后的结果
     * <p>
     * getAndSet(int newValue)：将实例中的值更新为新值，并返回旧值
     * <p>
     * getAndIncrement()：以原子的方式将实例中的原值加1，返回的是自增前的旧值
     */
    @Test
    public void testAtomicInteger() {
        System.out.println(atomicInteger.getAndIncrement());
        System.out.println(atomicInteger.get());
        System.out.println(atomicInteger.addAndGet(2));
    }

    private static int[] value = new int[]{1, 2, 3};
    // 使用 AtomicIntegerArray包装一个数组
    private static AtomicIntegerArray integerArray = new AtomicIntegerArray(value);

    /**
     * 原子更新数组类型
     * AtomicIntegerArray：原子更新整型数组中的元素
     * AtomicLongArray：原子更新长整型数组中的元素
     * AtomicReferenceArray：原子更新引用类型数组中的元素
     */
    @Test
    public void testAtomicIntegerArray() {
        // 对数组中索引为 1的位置的元素加 5
        int result = integerArray.getAndAdd(1, 5);
        System.out.println(integerArray.get(1));
        System.out.println(result);
    }

    private static AtomicReference<User> reference = new AtomicReference<>();

    /**
     * 原子更新引用类型
     * AtomicReference：原子更新引用类型
     * AtomicReferenceFieldUpdater：原子更新引用类型里的字段
     * AtomicMarkableReference：原子更新带有标记位的引用类型
     */
    @Test
    public void testAtomicReference() {
        User user1 = new User("a", 1);
        // 使用 AtomicReference包装一个引用类型的对象
        reference.set(user1);

        User user2 = new User("b", 2);
        // 原子式更新引用类型对象
        User user = reference.getAndSet(user2);// getAndSet()先返回旧值再设置新值

        System.out.println(user);
        System.out.println(reference.get());
    }

    // 用于原子更新 User类中的 age字段
    private static AtomicIntegerFieldUpdater updater = AtomicIntegerFieldUpdater.newUpdater(User.class,"age");
    /**
     * 原子更新字段类型.要原子更新的这个字段必须用 volatile修饰,且不能是 private修饰的,不然报错
     * AtomicIntegerFieldUpdater：原子更新整型字段类
     * AtomicLongFieldUpdater：原子更新长整型字段类
     * AtomicStampedReference：原子更新引用类型，这种更新方式会带有版本号。是为了解决CAS的ABA问题
     */
    @Test
    public void testAtomicIntegerFieldUpdater() {
        User user = new User("a", 1);
        int oldValue = updater.getAndAdd(user, 5);
        System.out.println(oldValue);
        System.out.println(updater.get(user));
    }

    static class User {
        private String userName;

        public volatile int age;

        public User(String userName, int age) {
            this.userName = userName;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" + "userName='" + userName + '\'' + ", age=" + age + '}';
        }
    }

}
