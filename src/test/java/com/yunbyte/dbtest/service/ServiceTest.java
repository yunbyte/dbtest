package com.yunbyte.dbtest.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yunbyte.dbtest.DbTestApplication;
import com.yunbyte.dbtest.dao.TransLog;
import com.yunbyte.dbtest.dto.CreateCardDTO;
import com.yunbyte.dbtest.dto.ResponseInfo;
import com.yunbyte.dbtest.dto.TransLoopDTO;
import com.yunbyte.dbtest.dto.TransferDTO;

/**
 * 测试业务处理层相关操作
 * 
 * @author Rongkai Wang
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DbTestApplication.class)
public class ServiceTest {

    @Autowired
    private IDbTestService demoService;

    /**
     * 测试转出账户不存在，事务回滚
     */
    @Test
    public void transTest1() {
        System.out.println("======================测试转出账户不存在==================");
        Long[] destCardNos = { 2L };
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setSoureCardNo(1000000000002L);
        transferDTO.setDestCardNos(destCardNos);
        transferDTO.setAmount(100.0);
        ResponseInfo trans = demoService.trans(transferDTO);
        System.out.println(trans.getCode() + ":" + trans.getMessage());
    }

    /**
     * 测试转出账户余额不足，事务回滚
     */
    @Test
    public void transTest2() {
        System.out.println("======================测试转出账户余额不足==================");
        Long[] destCardNos = { 2L };
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setSoureCardNo(1L);
        transferDTO.setDestCardNos(destCardNos);
        transferDTO.setAmount(1000000000.0);
        ResponseInfo trans = demoService.trans(transferDTO);
        System.out.println(trans.getCode() + ":" + trans.getMessage());
    }

    /**
     * 测试转入账户不存在，事务回滚
     */
    @Test
    public void transTest3() {
        System.out.println("======================测试转入账户不存在==================");
        Long[] destCardNos = { 10022222222L };
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setSoureCardNo(1L);
        transferDTO.setDestCardNos(destCardNos);
        transferDTO.setAmount(10.0);
        ResponseInfo trans = demoService.trans(transferDTO);
        System.out.println(trans.getCode() + ":" + trans.getMessage());
    }

    /**
     * 测试转出转入账号相同校验，抛出运行时异常
     */
    @Test
    public void transTest4() {
        System.out.println("======================测试转出转入账号相同==================");
        Long[] destCardNos = { 1L };
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setSoureCardNo(1L);
        transferDTO.setDestCardNos(destCardNos);
        transferDTO.setAmount(100.0);
        ResponseInfo trans = demoService.trans(transferDTO);
        System.out.println(trans.getCode() + ":" + trans.getMessage());
    }

    /**
     * 测试卡对卡正常转账，事务提交
     */
    @Test
    public void transTest5() {
        System.out.println("======================测试卡2向卡1正常转账100==================");
        Long[] destCardNos = { 1L };
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setSoureCardNo(2L);
        transferDTO.setDestCardNos(destCardNos);
        transferDTO.setAmount(100.0);
        ResponseInfo trans = demoService.trans(transferDTO);
        System.out.println(trans.getCode() + ":" + trans.getMessage());
    }

    /**
     * 测试一卡向两卡正常转账，事务提交
     */
    @Test
    public void transTest6() {
        System.out.println("======================测试卡1向卡2卡3正常转账100==================");
        Long[] destCardNos = { 2L, 3L };
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setSoureCardNo(1L);
        transferDTO.setDestCardNos(destCardNos);
        transferDTO.setAmount(100.0);
        ResponseInfo trans = demoService.trans(transferDTO);
        System.out.println(trans.getCode() + ":" + trans.getMessage());
    }

    /**
     * 测试一卡向多卡正常转账，事务提交
     */
    @Test
    public void transTest7() {
        System.out.println("======================测试卡1向卡2卡3卡3正常转账100==================");
        Long[] destCardNos = { 2L, 3L, 4L };
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setSoureCardNo(1L);
        transferDTO.setDestCardNos(destCardNos);
        transferDTO.setAmount(100.0);
        ResponseInfo trans = demoService.trans(transferDTO);
        System.out.println(trans.getCode() + ":" + trans.getMessage());
    }

    /**
     * 测试一卡同时转两卡时转出账户不存在，事务回滚
     */
    @Test
    public void transTest8() {
        System.out.println("======================测试一卡同时转两卡时转出账户不存在情况==================");
        Long[] destCardNos = { 2L, 3L };
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setSoureCardNo(1000000008L);
        transferDTO.setDestCardNos(destCardNos);
        transferDTO.setAmount(100.0);
        ResponseInfo trans = demoService.trans(transferDTO);
        System.out.println(trans.getCode() + ":" + trans.getMessage());
    }

    /**
     * 测试一卡同时转两卡时其中一个转入账户不存在，事务回滚
     */
    @Test
    public void transTest9() {
        System.out.println("======================测试一卡同时转两卡时其中一个转入账户不存在导致事务回滚==================");
        Long[] destCardNos = { 2L, 300000000L };
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setSoureCardNo(1L);
        transferDTO.setDestCardNos(destCardNos);
        transferDTO.setAmount(100.0);
        ResponseInfo trans = demoService.trans(transferDTO);
        System.out.println(trans.getCode() + ":" + trans.getMessage());
    }

    /**
     * 测试批量转账
     * 模拟场景：
     * 起始卡号为1，转账200次，转账金额满足，转入账号不存在1次，转出卡号不存在情况99次
     *
     */
    @Test
    public void transloopTest1() {
        TransLoopDTO transLoopDTO = new TransLoopDTO(200, 1L, 100.0);
        ResponseInfo transloop = demoService.transloop(transLoopDTO);
        System.out.println(transloop.getCode() + ":" + transloop.getMessage());
    }

    /**
     * 测试批量生成账号
     */
    @Test
    public void createCardLoopTest() {
        TransLoopDTO transLoopDTO = new TransLoopDTO(20, 20000L, 200.0);
        ResponseInfo cardLoop = demoService.createCardLoop(transLoopDTO);
        System.out.println(cardLoop.getCode() + ":" + cardLoop.getMessage());
    }

    /**
     * 测试批量生成账号
     * 起始账户已存在
     */
    @Test
    public void createCardLoopTest1() {
        TransLoopDTO transLoopDTO = new TransLoopDTO(20, 20019L, 200.0);
        ResponseInfo cardLoop = demoService.createCardLoop(transLoopDTO);
        System.out.println(cardLoop.getCode() + ":" + cardLoop.getMessage());
    }

    /**
     * 测试批量生成账号
     * 有一个账户已存在
     */
    @Test
    public void createCardLoopTest2() {
        TransLoopDTO transLoopDTO = new TransLoopDTO(20, 20020L, 200.0);
        ResponseInfo cardLoop = demoService.createCardLoop(transLoopDTO);
        System.out.println(cardLoop.getCode() + ":" + cardLoop.getMessage());
    }

    /**
     * 测试生成一条账户信息
     * 该账户已存在
     */
    @Test
    public void createCardTest1() {
        CreateCardDTO createCardDTO = new CreateCardDTO(10L, 100.0);
        ResponseInfo card = demoService.createCard(createCardDTO);
        System.out.println(card.getCode() + ":" + card.getMessage());
    }

    /**
     * 测试生成一条账户信息
     * 该账户不存在
     */
    @Test
    public void createCardTest2() {
        CreateCardDTO createCardDTO = new CreateCardDTO(10039208L, 100.0);
        ResponseInfo card = demoService.createCard(createCardDTO);
        System.out.println(card.getCode() + ":" + card.getMessage());
    }

    /**
     * 测试批量转账
     * 模拟场景：
     * 起始卡号为100，转账100次，转账金额满足，转入卡号不存在100次，转出卡号不存在情况99次
     *
     */
    @Test
    public void transloopTest2() {
        TransLoopDTO transLoopDTO = new TransLoopDTO(100, 100L, 100.0);
        ResponseInfo transloop = demoService.transloop(transLoopDTO);
        System.out.println(transloop.getCode() + ":" + transloop.getMessage());
    }

    /**
     * 测试批量转账
     * 模拟场景：
     * 起始卡号为200，转账100次，转入卡号不存在100次，转出卡号不存在情况100次
     *
     */
    @Test
    public void transloopTest3() {
        TransLoopDTO transLoopDTO = new TransLoopDTO(100, 200L, 100.0);
        ResponseInfo transloop = demoService.transloop(transLoopDTO);
        System.out.println(transloop.getCode() + ":" + transloop.getMessage());
    }

    /**
     * 测试查询交易流水
     * 1.根据输出账号查
     * 2.根据输入账号查
     * 3.不输账号，全量查
     */
    @Test
    public void selectLogTest() {
        ResponseInfo transLogPageInfo = demoService.selectLog(1L, null, 0, 0);

        for (TransLog transLog : transLogPageInfo.getList()) {
            System.out.println(transLog);
        }
        System.out.println(transLogPageInfo.getCode() + ":" + transLogPageInfo.getMessage());

    }

    /**
     * 测试查询交易流水分页显示
     */
    @Test
    public void selectLogPageTest() {
        ResponseInfo transLogPageInfo = demoService.selectLog(null, null, 100, 10);
        for (TransLog transLog : transLogPageInfo.getList()) {
            System.out.println(transLog);
        }
        System.out.println(transLogPageInfo.getList().size());
        System.out.println(transLogPageInfo.getCode() + ":" + transLogPageInfo.getMessage());
    }

    /**
     * 测试查询交易流水分页显示
     */
    @Test
    public void selectLogPageTest2() {
        ResponseInfo transLogPageInfo = demoService.selectLog(1L, 2L, 10, 10);
        for (TransLog transLog : transLogPageInfo.getList()) {
            System.out.println(transLog);
        }
        System.out.println(transLogPageInfo.getList().size());
        System.out.println(transLogPageInfo.getCode() + ":" + transLogPageInfo.getMessage());
    }

}
