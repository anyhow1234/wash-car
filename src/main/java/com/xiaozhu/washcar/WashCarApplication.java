package com.xiaozhu.washcar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description:
 * @Author hans
 * @Date 2020/12/5 11:17
 * @Version 1.0
 */
@SpringBootApplication(scanBasePackages = "com.xiaozhu.washcar")
public class WashCarApplication {
    public static void main(String[] args) {
        SpringApplication.run(WashCarApplication.class);
    }
}
