package com.example.demo.vola;

import java.util.concurrent.CountDownLatch;

public class NotSafe {

    private volatile int count = 0;
    CountDownLatch countDownLatch;
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    //volatitle只能保证线程之间的可见，不能保证操作的原子性，因此需要加锁才能保证结果的正确
    public void incCount(){
        synchronized (this) {
            count++;
        }
    }

    private static class CountThread extends Thread{
        private NotSafe sample;
        public CountThread(NotSafe sample){
            this.sample = sample;
        }
        @Override
        public void run() {
            super.run();
            for (int i = 0; i < 10000; i++) {
                sample.incCount();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NotSafe sample = new NotSafe();
        CountThread countThread1 = new CountThread(sample);
        CountThread countThread2 = new CountThread(sample);
        countThread1.start();
        countThread2.start();
        Thread.sleep(1000);
        System.out.println(sample.count);
    }
}
