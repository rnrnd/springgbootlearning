package com.example.demo.vola;

public class VolatileCase {

    private static volatile boolean ready;
    private static int number;

    private static class PrintThread extends Thread {
        @Override
        public void run() {
            super.run();
            System.out.println("PrintThread is running......");
            while (!ready);
            System.out.println("number = " + number);
        }
    }

    /**
     * Java语言提供了一种稍弱的同步机制，即volatile变量，用来确保将变量的更新操作通知到其他线程。<br/>
     * 当把变量声明为volatile类型后，编译器与运行时都会注意到这个变量是共享的，因此不会将该变量上的操作与其他内存操作一起重排序。<br/>
     * volatile变量不会被缓存在寄存器或者对其他处理器不可见的地方，因此在读取volatile类型的变量时总会返回最新写入的值。<br/>
     * 在访问volatile变量时不会执行加锁操作，因此也就不会使执行线程阻塞，因此volatile变量是一种比sychronized关键字更轻量级的同步机制。<br/>
     * 当对非 volatile 变量进行读写的时候，每个线程先从内存拷贝变量到CPU缓存中。如果计算机有多个CPU，每个线程可能在不同的CPU上被处理，这意味着每个线程可以拷贝到不同的 CPU cache 中。<br/>
     * 而声明变量是 volatile 的，JVM 保证了每次读变量都从内存中读，跳过 CPU cache 这一步。<br/>
     * 当一个变量定义为 volatile 之后，将具备两种特性：<br/>
     * 　　1.保证此变量对所有的线程的可见性，这里的“可见性”，如本文开头所述，当一个线程修改了这个变量的值，volatile 保证了新值能立即同步到主内存，以及每次使用前立即从主内存刷新。<br/>
     *      但普通变量做不到这点，普通变量的值在线程间传递均需要通过主内存（详见：Java内存模型）来完成。<br/>
     *
     * 　　2.禁止指令重排序优化。有volatile修饰的变量，赋值后多执行了一个“load addl $0x0, (%esp)”操作，这个操作相当于一个内存屏障（指令重排序时不能把后面的指令重排序到内存屏障之前的位置），<br/>
     *      只有一个CPU访问内存时，并不需要内存屏障；（什么是指令重排序：是指CPU采用了允许将多条指令不按程序规定的顺序分开发送给各相应电路单元处理）。<br/>
     *
     * volatile 性能：<br/>
     * 　　volatile 的读性能消耗与普通变量几乎相同，但是写操作稍慢，因为它需要在本地代码中插入许多内存屏障指令来保证处理器不发生乱序执行。<br/>
     */
    public static void main(String[] args) throws InterruptedException {
        PrintThread printThread = new PrintThread();
        printThread.start();
        printThread.sleep(1000);
        ready = true;
        number = 51;
        printThread.sleep(5000);
        System.out.println("main is ended!");
    }
}
