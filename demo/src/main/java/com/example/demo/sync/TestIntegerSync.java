package com.example.demo.sync;

public class TestIntegerSync {

    public static class Worker implements Runnable {
        private Integer i;
        private Object o = new Object();
        public Worker(Integer i) {
            this.i = i;
        }
        @Override
        public void run() {
            synchronized (o) {
                Thread t = Thread.currentThread();
                System.out.println(t.getName() + "---" + i + "--@" + System.identityHashCode(i));
                int i = this.i.intValue();
                this.i++;
                System.out.println(t.getName() + "---" + this.i + "--@" + System.identityHashCode(this.i));
                try {
                    t.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static class WorkerThread extends Thread {
        private Integer i;
        private Object o;
        public WorkerThread(Integer i, Object o) {
            this.i = i;
            this.o = o;
        }
        @Override
        public void run() {
            super.run();
            synchronized (o) {
                Thread t = Thread.currentThread();
                System.out.println(t.getName() + "---" + i + "--@" + System.identityHashCode(i));
                int i = this.i.intValue();
                this.i++;
                System.out.println(t.getName() + "---" + this.i + "--@" + System.identityHashCode(this.i));
                try {
                    t.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        Worker worker = new Worker(0);
        Object o = new Object();
        Integer integer = Integer.valueOf(128);
        for (int i = 0; i < 5; i++) {
            //new Thread(worker).start();
            new WorkerThread(integer, o).start();
        }
    }
}
