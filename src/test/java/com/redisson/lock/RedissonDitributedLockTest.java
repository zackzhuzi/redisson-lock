package com.redisson.lock;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * RedissonDitributedLockTest
 * 
 * @author yuzhupeng
 *
 */
public class RedissonDitributedLockTest {
    private static final String lockName = "dragon";
    private static final int THREADCOUNT = 200;

    private int count = 200;

    @Before
    public void init() {
        System.out.println("init count=" + count);
    }

    @Test
    public void redissonDitributedLockTest() {
        TestRunnable[] testRunnables = new TestRunnable[THREADCOUNT];
        for (int i = 0; i < THREADCOUNT; i++) {
            testRunnables[i] = new JedisLockThread();
        }

        // 多线程测试
        MultiThreadedTestRunner multiThreadedTestRunner = new MultiThreadedTestRunner(
                testRunnables);
        try {
            multiThreadedTestRunner.runTestRunnables();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    class JedisLockThread extends TestRunnable {
        @Override
        public void runTest() {
            boolean tryLock = false;
            RedissonDitributedLock lock = new RedissonDitributedLock(lockName);
            try {
                tryLock = lock.tryLock();
                if (tryLock) {
                    System.out.println(Thread.currentThread().getName()
                            + "|lock success|" + lockName);
                    count--;
                } else {
                    System.out.println(Thread.currentThread().getName()
                            + "|lock failed|" + lockName);
                }
            } catch (Exception e) {
                tryLock = false;
                System.out.println(Thread.currentThread().getName()
                        + "|lock failed|" + lockName);
                e.printStackTrace();
            } finally {
                if (tryLock) {
                    lock.unlock();
                }
            }
        }
    }

    @After
    public void printCount() {
        System.out.println("final count=" + count);
    }
}
