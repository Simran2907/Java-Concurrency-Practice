package com.interviewPrep.CustomThread;

public class MyThread {

    private Thread worker;
    private volatile boolean running = true;

    public MyThread(Runnable task, String threadName) {
        this.worker = new Thread(() -> {
            running = true;
            task.run();
            running = false;
        }, threadName);
        worker.start();
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        running = false;
        worker.interrupt();
    }

    public  void start(){
        worker.start();
    }
}
