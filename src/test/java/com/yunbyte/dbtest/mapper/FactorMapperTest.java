package com.yunbyte.dbtest.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.test.context.junit4.SpringRunner;

import com.yunbyte.dbtest.DbTestApplication;
import com.yunbyte.dbtest.dao.Factor1;

/**
 * 测试Fator表相关操作
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DbTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FactorMapperTest {

    @Autowired
    private FactorMapper factorMapper;

    /**
     * 测试插入一条数据
     */
    @Test
    public void insertTest() {
        Factor1 factor = new Factor1();
        factor.setCardNo(100002L);
        factor.setRegion(1);
        factor.setBalance(10.0);
        factor.setUsername("dbtest-test");
        try {
            factorMapper.insert(factor);
        } catch (UncategorizedSQLException | DuplicateKeyException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试根据卡号查分区
     */
    @Test
    public void selectRegionByCardNoTest() {
        int region = factorMapper.selectRegionByCardNo(1);
        System.out.println("balance=" + region);
    }

    /**
     * 测试根据卡号查分区，账号不存在的情况
     */
    @Test
    public void selectRegionByCardNoTest1() {
        Integer region = null;
        try {
            region = factorMapper.selectRegionByCardNo(10000003);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        System.out.println("balance=" + region);
    }

    /**
     * 测试根据卡号查余额
     */
    @Test
    public void selectBalByCardNoTest() {
        double balance = factorMapper.selectBalByCardNo(1);
        System.out.println("balance=" + balance);
    }

    /**
     * 测试根据卡号查余额，账号不存在的情况
     */
    @Test
    public void selectBalByCardNoTest1() {
        Double balance = null;
        try {
            balance = factorMapper.selectBalByCardNo(10000003);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        System.out.println("balance=" + balance);
    }

    /**
     * 测试根据卡号查余额，账号不存在的情况
     */
    @Test
    public void selectBalByCardNoTest2() {
        double balance = factorMapper.selectBalByCardNo(0);
        System.out.println("balance=" + balance);
    }

    /**
     * 测试修改账户余额
     */
    @Test
    public void updateBalanceTest() {
        factorMapper.updateBalance(10, 1, 1);
    }

}
