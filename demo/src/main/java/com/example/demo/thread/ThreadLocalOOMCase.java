package com.example.demo.thread;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalOOMCase {
    private static final int TASK_LOOP_SIZE = 500;

    final static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 1,  TimeUnit.MINUTES, new LinkedBlockingDeque<>());

    static class LocalVariable {
        private byte[] bytes = new byte[1024*1024*5];
    }

    final static ThreadLocal<LocalVariable> localVariable = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < TASK_LOOP_SIZE; i++){
            poolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    /**
                     * LocalVariable有一个内置的大小为5M的数组，使用5个线程的线程池不停地创建LocalVariable对象
                     * 在Java VisualVM中监视堆内存的占用发现只是略大于25M
                     * 而使用ThreadLocal不停地将LocalVariable对象set进去的时候，发现内存占用比之前多出三倍左右、这就是ThreadLocal使用不当产生的内存泄漏问题，
                     * 每个线程实例都对应一个TheadLocalMap实例，我们可以在同一个线程里实例化很多个ThreadLocal来存储很多种类型的值，这些ThreadLocal实例分别作为key，对应各自的value
                     * ThreadLocal本身并不是一个容器，我们存取的value实际上存储在ThreadLocalMap中，ThreadLocal只是作为TheadLocalMap的key
                     * ThreadLocalMap中定义了Entry类用来存储，key是ThreadLocal，value是变量副本的值，Entry与ThreadLocal之间是弱引用
                     * 由于ThreadLocalMap是以弱引用的方式引用着ThreadLocal，换句话说，就是ThreadLocal是被ThreadLocalMap以弱引用的方式关联着，
                     * 因此如果ThreadLocal没有被ThreadLocalMap以外的对象引用，则在下一次GC的时候，ThreadLocal实例就会被回收，
                     * 那么此时ThreadLocalMap里的一组KV的K就是null了，因此在没有额外操作的情况下，此处的V便不会被外部访问到，
                     * 而且只要Thread实例一直存在，Thread实例就强引用着ThreadLocalMap，因此ThreadLocalMap就不会被回收，那么这里K为null的V就一直占用着内存。
                     *
                     * 综上，发生内存泄露的条件是
                     * 1.ThreadLocal实例没有被外部强引用，比如我们假设在提交到线程池的task中实例化的ThreadLocal对象，当task结束时，ThreadLocal的强引用也就结束了
                     * 2.ThreadLocal实例被回收，但是在ThreadLocalMap中的V没有被任何清理机制有效清理
                     * 3.当前Thread实例一直存在，则会一直强引用着ThreadLocalMap，也就是说ThreadLocalMap也不会被GC
                     *
                     * 也就是说，如果Thread实例还在，但是ThreadLocal实例却不在了，则ThreadLocal实例作为key所关联的value无法被外部访问，却还被强引用着，因此出现了内存泄露。
                     *
                     */
                    //new LocalVariable();
                    localVariable.set(new LocalVariable());
                    System.out.println("use local variable...");
                    System.gc();
                    localVariable.remove();
                }
            });
            Thread.sleep(100);
        }
        System.out.println("thread is finished!");
    }
}
