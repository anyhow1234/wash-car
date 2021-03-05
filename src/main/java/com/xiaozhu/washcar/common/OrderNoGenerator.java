package com.xiaozhu.washcar.common;

import com.sun.org.apache.bcel.internal.util.ClassPath;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.DateFormat;
import java.util.UUID;

/**
 * @Description:
 * @Author hans
 * @Date 2020/12/6 16:07
 * @Version 1.0
 */
public class OrderNoGenerator {
    private static final String WORKERID_PATH = ClassPath.getClassPath() + "/workerId";
    private static String lastNo = "";
    private long workerId;
    private long sequence;
    private final long workerIdBits;
    private final long maxWorkerId;
    private final long sequenceBits;
    private final long workerIdShift;
    private final long datacenterIdShift;
    private final long sequenceMask;
    private final long timestampLeftShift;
    private long lastTimestamp;

    private OrderNoGenerator() {
        this.workerId = 1L;
        this.sequence = 1L;
        this.workerIdBits = 10L;
        this.maxWorkerId = 1023L;
        this.sequenceBits = 12L;
        this.workerIdShift = 12L;
        this.datacenterIdShift = 22L;
        this.sequenceMask = 4095L;
        this.timestampLeftShift = 22L;
        this.lastTimestamp = -1L;
    }

    public static void main(String[] args) {
        System.out.println(OrderNoGenerator.getInstance().create());
    }

    public static OrderNoGenerator getInstance() {
        return OrderNoGenerator.OrderNoUtilsHolder.instance;
    }

    public String create() {
        return this.nextNo();
    }

    private String nextNo() {
        return this.nextNo(this.workerId);
    }

    private synchronized String nextNo(long workerId) {
        long timestamp = this.timeGen();
        if (timestamp < this.lastTimestamp) {
            throw new IllegalStateException("Clock moved backwards.");
        } else {
            if (this.lastTimestamp == timestamp) {
                this.sequence = this.sequence + 1L & 4095L;
                if (this.sequence == 0L) {
                    timestamp = this.tilNextMillis(this.lastTimestamp);
                }
            } else {
                this.sequence = 0L;
            }

            this.lastTimestamp = timestamp;
            long id = timestamp % 1000L << 22 | workerId << 12 | this.sequence;
            String timestampStr = DateFormatUtils.format(timestamp, "yyyyMMddHHmmss");
            return timestampStr + String.format("%010d", id);
        }
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp;
        for(timestamp = this.timeGen(); timestamp <= lastTimestamp; timestamp = this.timeGen()) {
        }

        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static String genId(String machineId) throws InterruptedException {
        String orderId = machineId + System.nanoTime();

        for(orderId = String.valueOf(orderId.hashCode()); lastNo.equals(orderId); orderId = machineId + (System.currentTimeMillis() + "").substring(1) + '-' + (System.nanoTime() + "").substring(7, 10)) {
        }

        lastNo = orderId;
        return orderId;
    }

    public static String getOrderIdByUUId(String machineId) {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }

        String orderId = machineId + String.format("%015d", hashCodeV);
        return orderId;
    }

    private static class OrderNoUtilsHolder {
        private static OrderNoGenerator instance = new OrderNoGenerator();

        private OrderNoUtilsHolder() {
        }
    }
}
