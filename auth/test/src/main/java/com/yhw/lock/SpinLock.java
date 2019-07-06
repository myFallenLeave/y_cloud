package com.yhw.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁  -保证原子性 短期等待的性能优化
 * 自旋锁是尝试获取锁的线程不会立即阻塞，采用循环的方式去获取锁，
 * 好处是减少了上下文切换，缺点是消耗cpu
 * reference：http://ifeve.com/java_lock_see1/
 */
public class SpinLock implements Lock{

    private AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock(){
        Thread thread = Thread.currentThread();
        //compareAndSet 以原子方式 更新值
        while (!atomicReference.compareAndSet(null,thread)){
            System.out.println(Thread.currentThread().getName());
        }
    }

    public void unLock(){
        Thread thread = Thread.currentThread();
        //如果当前状态值等于预期值，则以原子方式将同步状态设置为给定的更新值。
        atomicReference.compareAndSet(thread,null);
    }

    /*public static void main(String[] args){
        SpinLock spinLock = new SpinLock();

        //期望值为 null,新值为当前线程(线程1进入,赋值成功,为true不进入轮询,
        // 线程2进入就不是null了,需要线程1给atomicReference赋值为null才能跳出轮询)
        for (int i = 0; i < 2; i++){
            final int number = i+1;
            Thread thread = new Thread(()->{

                spinLock.lock();
                System.out.println(number+"持有锁");
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                spinLock.unLock();
                System.out.println(number+"释放锁");
            },String.valueOf(number));
            thread.start();
        }

    }*/
}
