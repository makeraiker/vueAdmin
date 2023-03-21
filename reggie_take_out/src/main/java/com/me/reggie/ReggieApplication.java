package com.me.reggie;

import lombok.extern.slf4j.Slf4j;           //lombok免去get set方法
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j                  //之后可以输出日志
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class ReggieApplication {
    public static void main(String[] args){
        SpringApplication.run(ReggieApplication.class,args);
        log.info("项目启动成功——————————————————");
    }
}
