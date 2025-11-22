package com.interviewPrep.CustomThread;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomThreadPoolExecutor{

    private BlockingQueue<MyRunnable> bq;
    private Worker[] workers;
    public AtomicBoolean isShutDown = new AtomicBoolean(true);

    public CustomThreadPoolExecutor(int capacity){
        bq = new LinkedBlockingQueue<>();
        workers  = new Worker[capacity];
        isShutDown.set(false);

        for(int i=0;i<capacity;i++){
            workers[i] = new Worker("worker"+i);
            workers[i].start();
        }
    }



    public void submit(MyRunnable task){
        if(!isShutDown.get())  bq.offer(task);
        else throw new IllegalStateException("ThreadPool is shutting down...");

    }

    public void shutdown(){
        isShutDown.set(true);
        Arrays.stream(workers).forEach(Thread::interrupt);
    }

    public class Worker extends Thread{

        public Worker(String name){
            super(name);
        }
        public void run(){
            while(!isShutDown.get() || !bq.isEmpty()){
                try {
                    MyRunnable task =  bq.take();
                    task.run();
                    System.out.println(Thread.currentThread().getName()+" is working");
                    Thread.sleep(8000);

                } catch (InterruptedException e) {

                }

            }
            System.out.println(getName() + " terminated.");

        }
    }
}
