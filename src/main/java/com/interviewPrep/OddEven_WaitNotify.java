package com.interviewPrep;

public class OddEven_WaitNotify {

    private static final int LIMIT = 10;
    private static int COUNT = 0;

    public static void main(String[] args) {
        OddEven_WaitNotify oddEvenWaitNotify = new OddEven_WaitNotify();
        Thread t1 = new Thread(oddEvenWaitNotify::printEven);
        Thread t2 = new Thread(oddEvenWaitNotify::printOdd);

        t1.start();
        t2.start();

    }

    private synchronized void printEven(){
        while(COUNT<=LIMIT){
                if (COUNT % 2 == 0) {
                    System.out.println(COUNT++);
                    notifyAll();
                }else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        }
    }


    private synchronized void printOdd(){
        while(COUNT<=LIMIT){
                if (COUNT % 2 != 0) {
                    System.out.println(COUNT++);
                    notifyAll();
                }else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        }
    }
}
