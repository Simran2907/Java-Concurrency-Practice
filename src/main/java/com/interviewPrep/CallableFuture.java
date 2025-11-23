package com.interviewPrep;

import java.util.concurrent.*;

public class CallableFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // callable -> innterface like runnable but return result
        int a =9,b=10;
        // 1. create executor service
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //2. create callable
        Callable<Integer> task = () -> a+b ;
        //3. submit callable -> get future
        Future<Integer> future = executorService.submit(task);
        //4. print result by using future.get
        System.out.println(future.get());
        //5. shutting down executor service
        executorService.shutdown();
    }
}
