package com.example.demo.sync;

public class TestIntegerSync {
    private static final Object obj = new Object();
    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker(0);
        Object o = new Object();
        Integer integer = Integer.valueOf(128);
        TestIntegerSync test = new TestIntegerSync();

        //for (int i = 0; i < 5; i++) {
            //new Thread(worker).start();
            //new WorkerThread(integer, o).start();
            new Thread(() -> {
                try {
                    test.b();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        //}
        //for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    test.a();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        //}
    }
    public void a() throws InterruptedException {
        synchronized (this){
            System.out.println("a start:" + Thread.currentThread().getName());
            Thread.sleep(1000);
            System.out.println("a end  :" + Thread.currentThread().getName());
        }
    }
    public void b() throws InterruptedException {
        synchronized (obj){
            System.out.println("b start:" + Thread.currentThread().getName());
            Thread.sleep(1000);
            System.out.println("b end  :" + Thread.currentThread().getName());
        }
    }
    public static class Worker implements Runnable {
        private Integer i;
        private static final Object o = new Object();
        public Worker(Integer i) {
            this.i = i;
        }
        @Override
        public void run() {
            synchronized (this) {
                Thread t = Thread.currentThread();
                System.out.println(t.getName() + "---" + i + "--@" + System.identityHashCode(i));
                //int i = this.i;
                this.i++;
                System.out.println(t.getName() + "---" + this.i + "--@" + System.identityHashCode(this.i));
                try {
                    Thread.sleep(1000);
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

}
