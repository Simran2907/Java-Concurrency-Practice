package com.interviewPrep.CustomThread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWritelock<K,V> {

    private Map<K,V> map = new HashMap<>();
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        ReadWritelock<Integer,Integer> readWritelock = new ReadWritelock<>();
        Thread t1  = Thread.startVirtualThread(() ->{
            readWritelock.put(1,1);
            readWritelock.put(2,2);
            System.out.println(readWritelock.get(1));
            System.out.println(readWritelock.get(2));
            System.out.println("compute "+readWritelock.computeIfAbsent(3,3));
            System.out.println("compute "+readWritelock.computeIfAbsent(4,4));

        });

        Thread t2  = Thread.startVirtualThread(() ->{
            System.out.println(readWritelock.get(3));
            System.out.println(readWritelock.get(4));
            System.out.println("compute "+readWritelock.computeIfAbsent(3,4));
        });
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public V get(K key){
        readWriteLock.readLock().lock();
        try {
            return map.get(key);
        }finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void put(K key, V value){
        readWriteLock.writeLock().lock();
        try{
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+" is adding "+ value);
        } finally {
            readWriteLock.writeLock().unlock();
        }

    }

    public V computeIfAbsent(K key, V value){
        readWriteLock.readLock().lock();
        try {
            V val = map.get(key);
            System.out.println(Thread.currentThread().getName() + " is compute reading " + val);
            if (val != null) return val;
        }finally {
            readWriteLock.readLock().unlock();
        }

        readWriteLock.writeLock().lock();
        try {
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + " is compute adding " + value);
            return map.get(key);
        }finally {
            readWriteLock.writeLock().unlock();
        }


    }
}
