package com.xiaozhu.washcar.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;

/**
 * @Description:
 * @Author hans
 * @Date 2020/12/6 16:04
 * @Version 1.0
 */
public class IDGenerate {
    private static final Logger log= LoggerFactory.getLogger(IDGenerate.class);
    private static int worker_id=1;
    private static int datacenter_id=1;

    public static IdWorker w = null;
    static {
        w = new IdWorker(worker_id,datacenter_id);
    }

    public static void main(String[] args) {
        log.info(getUniqueIdStr());
    }

    /**
     * 获取主键ID
     * 需要初始化配置worker_id、datacenter_id以保证及期间的唯一性
     */
    public static long getUniqueID(){
        return w.nextId();
    }
    public static String getUniqueIdStr(){
        long i=getUniqueID();
        return String.valueOf(i);
    }
    public static String getUniqueIdStr(long worker_id){
        IdWorker w = new IdWorker(worker_id,datacenter_id);
        long i=w.nextId();
        return String.valueOf(i);
    }

}

/**
 * 64位ID (42(毫秒)+5(机器ID)+5(业务编码)+12(重复累加))
 *
 * @author Polim
 */
class IdWorker {

    private static final Logger log= LoggerFactory.getLogger(IdWorker.class);

    private final static long twepoch = 1288834974657L;
    // 机器标识位数
    private final static long workerIdBits = 5L;
    // 数据中心标识位数
    private final static long datacenterIdBits = 5L;
    // 机器ID最大值
    private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
    // 数据中心ID最大值
    private final static long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    // 毫秒内自增位
    private final static long sequenceBits = 12L;
    // 机器ID偏左移12位
    private final static long workerIdShift = sequenceBits;
    // 数据中心ID左移17位
    private final static long datacenterIdShift = sequenceBits + workerIdBits;
    // 时间毫秒左移22位
    private final static long timestampLeftShift = sequenceBits + workerIdBits+ datacenterIdBits;

    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

    private static long lastTimestamp = -1L;

    private long sequence = 0L;
    private final long workerId;
    private final long datacenterId;

    public IdWorker(long workerId, long datacenterId) {
        datacenterId=getRandom();
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("worker Id can't be greater than %d or less than 0");
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException("datacenter Id can't be greater than %d or less than 0");
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            try {
                throw new Exception("Clock moved backwards.  Refusing to generate id for "+ (lastTimestamp - timestamp) + " milliseconds");
            } catch (Exception e) {
                log.error("",e);
            }
        }

        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID
        long nextId = ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;

        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }
    /**
     * 生成1-31之间的随机数
     *
     * @return
     */
    private static long getRandom() {
        int max = (int) (maxDatacenterId);
        int min = 1;
        Random random = new Random();
        long result = random.nextInt(max - min) + min;
        return result;
    }
    private long timeGen() {
        return System.currentTimeMillis();
    }
}
