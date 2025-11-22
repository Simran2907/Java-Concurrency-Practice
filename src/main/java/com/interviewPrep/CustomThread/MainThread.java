package com.interviewPrep.CustomThread;

public class MainThread {

    public static void main(String[] args) {
        CustomThreadPoolExecutor customThreadPoolExecutor = new CustomThreadPoolExecutor(3);

        MyRunnable task1 = () -> System.out.println("working 1");
        customThreadPoolExecutor.submit(task1);
        MyRunnable task2 = () -> System.out.println("working 2");
        customThreadPoolExecutor.submit(task2);
        MyRunnable task3 = () -> System.out.println("working 3");
        customThreadPoolExecutor.submit(task3);
        MyRunnable task4 = () -> System.out.println("working 4");
        customThreadPoolExecutor.submit(task4);
        MyRunnable task5 = () -> System.out.println("working 5");
        customThreadPoolExecutor.submit(task5);
        MyRunnable task6 = () -> System.out.println("working 6");
        customThreadPoolExecutor.submit(task6);

        customThreadPoolExecutor.shutdown();
    }
}
