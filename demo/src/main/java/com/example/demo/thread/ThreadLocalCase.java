package com.example.demo.thread;

public class ThreadLocalCase {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };
    public static class RunnableImpl implements Runnable{
        int order;
        public RunnableImpl(int order) {
            this.order = order;
        }
        @Override
        public void run() {
            Integer integer = threadLocal.get();
            integer = integer + order;
            threadLocal.set(integer);
            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
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
        new ThreadLocalCase().startThreadArray();
    }
}
