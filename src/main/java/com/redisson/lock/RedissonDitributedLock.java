package com.redisson.lock;

import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.core.RLock;

/**
 * Redisson分布式锁
 * 
 * @author yuzhupeng
 *
 */
public class RedissonDitributedLock {
    private static final String PREFIX = "redisson.lock.";
    private Redisson redisson = RedissonFactory.getRedisson();

    // 超时时间:默认5s
    private static int waitTime = 5;
    // 锁的过期时间：默认1分钟
    private static int leaseTime = 60;

    private RLock rLock;

    public RedissonDitributedLock(String lockName) {
        this.rLock = redisson.getLock(PREFIX + lockName);
    }

    public boolean tryLock() throws InterruptedException {
        return tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
    }

    /**
     * 获取锁
     * 
     * @param lockName
     * @param waitTime
     * @param leaseTime
     * @param unit
     * @return
     * @throws InterruptedException
     */
    public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit)
            throws InterruptedException {
        return rLock.tryLock(waitTime, leaseTime, unit);
    }

    public void unlock() {
        rLock.unlock();
    }
}
