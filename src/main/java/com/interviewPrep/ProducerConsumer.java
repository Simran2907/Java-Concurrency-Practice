package com.interviewPrep;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumer {
    private static BlockingQueue<Integer> bq = new ArrayBlockingQueue<>(5);

    public static void main(String[] args) {
        ProducerConsumer producerConsumer = new ProducerConsumer();
        Thread produce = new Thread(() -> {
            for (int i=1;i < 10;i++){
                producerConsumer.produce(i);
            }
        });
        Thread consume = new Thread(() -> {
            for (int i=1;i < 10;i++){
                producerConsumer.consume();
            }
        });

        produce.start();
        consume.start();

    }

    private void consume(){
        try {
            int c = bq.take();
            System.out.println("Consuming "+c);
            Thread.sleep(800);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void produce(int i){
        try {
            bq.put(i);
            System.out.println("Producing "+i);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
