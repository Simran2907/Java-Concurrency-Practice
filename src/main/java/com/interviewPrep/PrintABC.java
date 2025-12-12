package com.interviewPrep;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABC {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condA = lock.newCondition();
    private static Condition condB = lock.newCondition();
    private static Condition condC = lock.newCondition();

    private static int turn =0;
    private static final int LIMIT= 30;
    private static int count = 1;

    public static void main(String[] args) throws InterruptedException {
        PrintABC  printABC = new PrintABC();
        Thread t1 = Thread.startVirtualThread( () -> {
            try {
                printABC.printABC(0,"A",condA,condB);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread t2 = Thread.startVirtualThread( () -> {
            try {
                printABC.printABC(1,"B",condB,condC);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread t3 = Thread.startVirtualThread( () -> {
            try {
                printABC.printABC(2,"C",condC,condA);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t1.join();
        t2.join();
        t3.join();


    }

    public void printABC(int threadNo, String value,Condition curCond, Condition nextCond) throws InterruptedException {
            lock.lock();
            try {
                while (count <= LIMIT) {
                    while (turn != threadNo) {
                        curCond.await();
                    }
                    if (count > LIMIT) {
                        nextCond.signal();
                        break;
                    }
                    System.out.print(value);
                    count++;
                    turn = (turn + 1) % 3;
                    nextCond.signal();
                }
            }finally {
                lock.unlock();
            }
    }
}
