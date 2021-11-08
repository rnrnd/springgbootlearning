package com.example.demo.thread;

import lombok.SneakyThrows;

public class ThreadLocalUnsafeCase implements Runnable{
    public static Number staNumber = new Number(0);
    public Number number = new Number(0);
    public static ThreadLocal<Number> threadLocal = new ThreadLocal<>();
    public static ThreadLocal<Number> initializedThreadLocal = ThreadLocal.withInitial(() -> new Number(10));
    @SneakyThrows
    @Override
    public void run() {
        staNumber.setNum(staNumber.getNum() + 1);
        number.setNum(number.getNum() + 1);
        //threadLocal.set(staNumber);
        threadLocal.set(number);
        Number initializedNumber = initializedThreadLocal.get();
        initializedNumber.setNum(initializedNumber.getNum() +1);
        initializedThreadLocal.set(initializedNumber);
        Thread.sleep(100);
        System.out.println(Thread.currentThread().getName() + " threadLocal = " + threadLocal.get().getNum());
        System.out.println(Thread.currentThread().getName() + " initializeThreadLocal = " + initializedThreadLocal.get().getNum());
    }
    /**
     * 总结：如果把定义成static的成员变量，放进ThreadLocal，并不能达到预期的线程各自持有自己的变量副本的效果
     * 创建ThreadLocal对象时直接初始化进去也是可以的，本质上跟后续set是一回事
     */


    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new ThreadLocalUnsafeCase()).start();
        }
    }

    public static class Number{
        int num;
        public Number(int num) {
            this.num = num;
        }
        public int getNum() {
            return num;
        }
        public void setNum(int num) {
            this.num = num;
        }
    }


}
