package com.interviewPrep;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class OddEven_LockCondition {

    private static ReentrantLock lock = new ReentrantLock();
    private static Condition oddCondition = lock.newCondition();
    private static Condition evenCondition = lock.newCondition();
    private static final int LIMIT = 10;
    private static int count =0;

    public static void main(String[] args) throws InterruptedException {

        OddEven_LockCondition oddEvenLockCondition = new OddEven_LockCondition();
        Thread t1 = Thread.startVirtualThread(() -> {
            try {
                oddEvenLockCondition.printEven();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = Thread.startVirtualThread(() -> {
            try {
                oddEvenLockCondition.printOdd();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });



        t1.join();
        t2.join();

    }

    public void printEven() throws InterruptedException {
            lock.lock();
            try {
                while (count <= LIMIT) {
                    if (count % 2 == 0) {
                        System.out.println(count++);
                        oddCondition.signal();
                    } else evenCondition.await();

                    if (count > LIMIT) {
                        oddCondition.signal();
                        break;
                    }
                }
            }finally {
                lock.unlock();
            }
    }

    public void printOdd() throws InterruptedException {
            lock.lock();
        try {
            while (count <= LIMIT) {
                if (count % 2 != 0) {
                    System.out.println(count++);
                    evenCondition.signal();
                } else oddCondition.await();

                if (count > LIMIT) {
                    evenCondition.signal();
                    break;
                }
            }
        }finally {
            lock.unlock();
        }
    }
}
