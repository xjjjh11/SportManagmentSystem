package com.sms;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@SpringBootTest
class SportMgmtSysApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {
        log.info("获取数据连接："+dataSource.getConnection());
        String year = new SimpleDateFormat("yyyy").format(new Date());
        String month = new SimpleDateFormat("MM").format(new Date());
        String day = new SimpleDateFormat("dd").format(new Date());
        log.info("当前时间："+ year+month+day);
    }

    @Test
    void generateBrcPwd(){

        System.out.println(new BCryptPasswordEncoder().encode("asd123"));
    }

}
