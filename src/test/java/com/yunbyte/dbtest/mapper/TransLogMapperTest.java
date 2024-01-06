package com.yunbyte.dbtest.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yunbyte.dbtest.DbTestApplication;
import com.yunbyte.dbtest.dao.TransLog;

/**
 * 测试TranLog表相关操作
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DbTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransLogMapperTest {

    @Autowired
    private TransLogMapper transLogMapper;

    /**
     * 测试插入一条交易流水信息
     */
    @Test
    public void insertTest() {
        TransLog transLog = new TransLog();
        transLog.setRegion(1);
        transLog.setSoureCardNo(1L);
        transLog.setDestCardNo(2L);
        transLog.setBalance(10);
        transLog.setUsername("kdkk");
        transLogMapper.insert(transLog);
    }

    /**
     * 测试不输转出转入账号全查数据
     */
    @Test
    public void selectLogAllTest() {
        List<TransLog> transLogs = transLogMapper.selectLogAll(null, null);
        for (TransLog transLog : transLogs) {
            System.out.println(transLog);
        }
    }

    /**
     * 测试输入转出账号，不输入转入账号查询
     */
    @Test
    public void selectLogByScardNoTest1() {
        List<TransLog> transLogs = transLogMapper.selectLogAll(1L, null);
        for (TransLog transLog : transLogs) {
            System.out.println(transLog);
        }
    }

    /**
     * 测试输入转出账号为0，不输入转入账号查询
     */
    @Test
    public void selectLogByScardNoTest2() {
        List<TransLog> transLogs = transLogMapper.selectLogAll(0L, null);
        for (TransLog transLog : transLogs) {
            System.out.println(transLog);
        }
    }

    /**
     * 测试不输入转出账号，输入转入账号查询
     */
    @Test
    public void selectLogByDcardNoTest() {
        List<TransLog> transLogs = transLogMapper.selectLogAll(null, 2L);
        for (TransLog transLog : transLogs) {
            System.out.println(transLog);
        }
    }

    /**
     * 测试输入转出转入账号查询
     */
    @Test
    public void selectLogBycardNoTest() {
        List<TransLog> transLogs = transLogMapper.selectLogAll(1L, 2L);
        for (TransLog transLog : transLogs) {
            System.out.println(transLog);
        }
    }

}
