package com.example.demo.thread;

public class NoThreadLocalCase {

    private static Integer integer = 1;

    public static class RunnableImpl implements Runnable{
        int order;
        public RunnableImpl(int order) {
            this.order = order;
        }
        @Override
        public void run() {
            integer = integer + order;
            System.out.println(Thread.currentThread().getName() + ":" + integer);
        }
    }
    public void startThreadArray() throws InterruptedException {
        Thread[] threads = new Thread[3];
        for(int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new RunnableImpl(i), "Thread " +i );
            threads[i].start();
            Thread.sleep(1000);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new NoThreadLocalCase().startThreadArray();
    }
}
