package com.redisson.lock;

import java.io.InputStream;
import java.util.Properties;

import org.redisson.Config;
import org.redisson.Redisson;

/**
 * Redisson生成工厂
 * 
 * @author yuzhupeng
 *
 */
public class RedissonFactory {
    private static final String REDISCONFIGFILE = "redis.properties";
    private static Redisson redisson;
    static {
        try {
            Properties pps = new Properties();
            InputStream inputStream = RedissonFactory.class.getClassLoader()
                    .getResourceAsStream(REDISCONFIGFILE);
            pps.load(inputStream);
            String host = pps.getProperty("redis.host");
            String port = pps.getProperty("redis.port");
            String address = host + ":" + port;
            int timeout = Integer.parseInt(pps
                    .getProperty("redis.connect.timeout"));
            Config config = new Config();
            config.useSingleServer().setAddress(address).setTimeout(timeout);
            redisson = Redisson.create(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Redisson getRedisson() {
        return redisson;
    }

}
