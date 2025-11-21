package com.interviewPrep;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreeThreadSequencePrinter {

    private static int turn = 0;
    private static final int LIMIT = 10;
    private static int COUNT =0;
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition conditionA = lock.newCondition();
    private static Condition conditionB = lock.newCondition();
    private static Condition conditionC = lock.newCondition();

    public static void main(String[] args) {
        ThreeThreadSequencePrinter threeThreadSequencePrinter = new ThreeThreadSequencePrinter();
        Thread t1 = new Thread( () -> threeThreadSequencePrinter.printSequence(0,conditionA,conditionB));
        Thread t2 = new Thread( () -> threeThreadSequencePrinter.printSequence(1,conditionB,conditionC));
        Thread t3 = new Thread( () -> threeThreadSequencePrinter.printSequence(2,conditionC,conditionA));
        t1.start();
        t2.start();
        t3.start();
    }

    private void printSequence(int threadNo, Condition condition, Condition conditionNxt){
        while (true){
            lock.lock();
            while (turn != threadNo){
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if(COUNT > LIMIT){
                conditionNxt.signal();
                break;
            }

            System.out.println(Thread.currentThread().getName() + " "+COUNT++);
            turn = (turn+1) % 3;
            conditionNxt.signal();
            lock.unlock();
        }
    }
}
