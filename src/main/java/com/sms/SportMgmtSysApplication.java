package com.sms;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({
        "com.sms.usermgmt.mapper",
        "com.sms.placemgmt.mapper",
        "com.sms.moneymgmt.mapper",
        "com.sms.equipmgmt.mapper",
        "com.sms.contestmgmt.mapper",
        "com.sms.announmgmt.mapper"
})
public class SportMgmtSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportMgmtSysApplication.class, args);
    }

}
