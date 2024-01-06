package com.yunbyte.dbtest.service;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.yunbyte.dbtest.conf.ReaderValueConfig;
import com.yunbyte.dbtest.dao.Factor1;
import com.yunbyte.dbtest.dao.TransLog;
import com.yunbyte.dbtest.dto.CreateCardDTO;
import com.yunbyte.dbtest.dto.ResponseInfo;
import com.yunbyte.dbtest.dto.TransLoopDTO;
import com.yunbyte.dbtest.dto.TransferDTO;
import com.yunbyte.dbtest.exception.TransException;
import com.yunbyte.dbtest.mapper.FactorMapper;
import com.yunbyte.dbtest.mapper.TransLogMapper;

/**
 * dbtest数据库测试程序业务层实现类
 * 
 * @author Rongkai Wang
 * @Date: 2020-9-3
 */
@Transactional(rollbackFor = RuntimeException.class)
@Service
public class DbTestServiceImpl implements IDbTestService {

    private static Logger LOG = LoggerFactory.getLogger(DbTestServiceImpl.class);

    @Autowired
    private FactorMapper factorMapper;
    @Autowired
    private TransLogMapper transLogMapper;
    @Autowired
    private ReaderValueConfig readerValueConfig;

    /**
     * 转账操作业务处理方法
     * 
     * @param transferDTO 转账实体类对象
     * @return 转账操作响应实体类对象
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = RuntimeException.class)
    public ResponseInfo trans(TransferDTO transferDTO) {
        TransLog[] transLogs = null;
        LOG.info("转账事务开启 ...");
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        try {
            transLogs = transferAccounts(transferDTO);
        } catch (TransException e) {
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            LOG.error("错误转账事务回滚！", e);
            transLogs = e.getTransLog();
            return new ResponseInfo(e.getCode(), e.getMessage());
        } finally {
            if (transLogs != null) {
                writeTransLog(transLogs);
            }
        }
        LOG.info("正常转账事务提交");
        return new ResponseInfo("SUCCESS-200", "转账成功");
    }

    /**
     * 批量转账逻辑
     * 1.根据页面输入次数和金额执行转账批处理
     * 2.转出卡号为页面输入起始卡号依次递增，转入卡号为转出卡号加一
     * 3.每笔转账各自执行自身转账事务成功时提交和失败时回滚
     * 
     * @param transLoopDTO 批量转账实体类对象
     * @return 响应实体对象
     */
    @Override
    public ResponseInfo transloop(TransLoopDTO transLoopDTO) {
        TransferDTO transferDTO = new TransferDTO();
        Long startCardNo = transLoopDTO.getStartCardNo();
        Double amount = transLoopDTO.getAmount();
        for (int i = 0; i < transLoopDTO.getBatchNum(); i++) {
            transferDTO.setSoureCardNo(startCardNo);
            Long[] destCardNos = {};
            destCardNos = ArrayUtils.add(destCardNos, startCardNo + 1);
            transferDTO.setDestCardNos(destCardNos);
            transferDTO.setAmount(amount);
            trans(transferDTO);
            startCardNo++;
        }
        return new ResponseInfo("SUCCESS-200", "操作完成");
    }

    /**
     * 批量生成账号
     * 
     * @param transLoopDTO 批量操作实体类对象
     * @return 响应实体对象
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = RuntimeException.class)
    public ResponseInfo createCardLoop(TransLoopDTO transLoopDTO) {
        LOG.info("批量生成账户事务开启 ...");
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        Long cardNo = transLoopDTO.getStartCardNo();
        int nodeCount = readerValueConfig.nodeCount;
        for (int i = 0; i < transLoopDTO.getBatchNum(); i++) {
            try {
                int region = (int) (cardNo % nodeCount);
                createCard(cardNo, region, transLoopDTO.getAmount());
            } catch (TransException e) {
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                LOG.error("批量生成账户事务回滚！", e);
                return new ResponseInfo(e.getCode(), e.getMessage());
            }
            cardNo++;
        }
        LOG.info("批量生成账户事务提交！");
        return new ResponseInfo("SUCCESS-200", "创建完成，初始余额：" + transLoopDTO.getAmount());
    }

    /**
     * 生成一条账户信息
     * 
     * @param createCardDTO 创建账户请求实体
     * @return 响应实体对象
     */
    @Override
    public ResponseInfo createCard(CreateCardDTO createCardDTO) {
        int nodeCount = readerValueConfig.nodeCount;
        int region = (int) (createCardDTO.getCardNo() % nodeCount);
        try {
            createCard(createCardDTO.getCardNo(), region, createCardDTO.getBalance());
        } catch (TransException e) {
            return new ResponseInfo(e.getCode(), e.getMessage());
        }
        return new ResponseInfo("SUCCESS-200", "创建完成，初始余额：" + createCardDTO.getBalance());
    }

    /**
     * 生成一条账户信息
     * 
     * @param cardNo  卡号
     * @param region  分区
     * @param balance 金额
     */
    private void createCard(Long cardNo, Integer region, Double balance) throws TransException {
        Factor1 factor = new Factor1();
        factor.setCardNo(cardNo);
        factor.setRegion(region);
        factor.setBalance(balance);
        factor.setUsername("dbtest");
        try {
            factorMapper.insert(factor);
        } catch (UncategorizedSQLException | DuplicateKeyException e) {
            throw new TransException("ERROR-1009", "账户" + cardNo + "已存在");
        }
    }

    /**
     * 查询交易流水
     * 1.如果前端未输入所查账号，则查询所有交易流水
     * 2.如果只输入转出账号则查询该账号转出数据
     * 3.如果只输入转入账号则查询该账号转入数据
     * 4.如果转出转入账号都输入则查询对应账号下的交易数据
     * 
     * @param soureCardNo 转出账号
     * @param destCardNo  转入账号
     * @param pageSize    数据页大小
     * @param pageNum     分页个数
     * @return 响应实体（交易流水数据集合）
     */
    @Override
    public ResponseInfo selectLog(Long soureCardNo, Long destCardNo, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<TransLog> transLogs = transLogMapper.selectLogAll(soureCardNo, destCardNo);
        return new ResponseInfo("SUCCESS-200", "操作完成", transLogs);
    }

    /**
     * 实现将从一个账户转到多个账户的事务--参数为转账实体对象
     * 
     * @param transferDTO 转账操作实体类对象
     * @return 转账操作日志数组
     */
    private TransLog[] transferAccounts(TransferDTO transferDTO) {
        return this.transferAccounts(transferDTO.getSoureCardNo(), transferDTO.getAmount(),
                transferDTO.getDestCardNos());
    }

    /**
     * 实现将从一个账户转到多个账户的事务--参数为转出转入卡号和金额
     * 
     * @param soureCardNo 转出账户
     * @param amount      转账金额
     * @param destCardNos 转入账户
     */
    private TransLog[] transferAccounts(Long soureCardNo, Double amount, Long... destCardNos) throws TransException {
        List<TransLog> transLogs = new LinkedList<>();
        // 1.转入账户校验
        if (destCardNos.length < 1) {
            throw new TransException("ERROR-1008", "非法参数异常！转入账户为空");
        }
        if (ArrayUtils.contains(destCardNos, soureCardNo)) {
            throw new TransException("ERROR-1000", "转出账户和转入账户不能相同");
        }
        // 2.转出账户不存在
        // 转入账户region
        Integer sourcdRegion = factorMapper.selectRegionByCardNo(soureCardNo);
        int nodeCount = readerValueConfig.nodeCount;
        int region;
        if (sourcdRegion == null) {
            region = (int) (soureCardNo % nodeCount);
            transLogs.add(new TransLog(region, soureCardNo, destCardNos[0], amount,
                    "ERROR: not found card in source" + soureCardNo));
            throw new TransException("ERROR-1001", "转出账户" + soureCardNo + "不存在", transLogs.toArray(new TransLog[0]));
        }
        // 3.检验余额
        Double balance = factorMapper.selectBalByCardNo(soureCardNo);
        if (balance < amount * destCardNos.length) {
            transLogs.add(new TransLog(sourcdRegion, soureCardNo, destCardNos[0], amount,
                    "ERROR: " + soureCardNo + " Balance not enough"));
            throw new TransException("ERROR-1002", "转出账户" + soureCardNo + "余额不足", transLogs.toArray(new TransLog[0]));
        }

        for (long destCordNo : destCardNos) {
            // 4.检验对方账户
            // 转入账户region
            Integer destRegion = factorMapper.selectRegionByCardNo(destCordNo);
            if (destRegion == null) {
                region = (int) (destCordNo % nodeCount);
                transLogs.add(new TransLog(region, soureCardNo, destCordNo, amount,
                        "ERROR: not found card in dest" + destCordNo));
                throw new TransException("ERROR-1003", "转入账户" + destCordNo + "不存在", transLogs.toArray(new TransLog[0]));
            }
            Double destBalance = factorMapper.selectBalByCardNo(destCordNo);
            if (balance - amount < 0) {
                transLogs.add(new TransLog(sourcdRegion, soureCardNo, destCordNo, amount,
                        "ERROR: " + soureCardNo + " Balance not enough"));
                throw new TransException("ERROR-1004", "转出账户" + soureCardNo + "余额不足",
                        transLogs.toArray(new TransLog[0]));
            }
            // 5.实现转账
            factorMapper.updateBalance(balance - amount, soureCardNo, sourcdRegion);
            writeTransLog(sourcdRegion, soureCardNo, destCordNo, amount, "I am source - factor");
            factorMapper.updateBalance(destBalance + amount, destCordNo, destRegion);
            writeTransLog(destRegion, soureCardNo, destCordNo, amount, "I am source dist - factor");
            balance = factorMapper.selectBalByCardNo(soureCardNo);
        }
        return transLogs.toArray(new TransLog[0]);
    }

    /**
     * 批量写入转账日志
     * 
     * @param transLogs 转账日志对象
     */
    private void writeTransLog(TransLog... transLogs) {
        if (transLogs == null || transLogs.length == 0) {
            return;
        }
        for (TransLog transLog : transLogs) {
            LOG.info(transLog.toString());
            transLogMapper.insert(transLog);
        }
    }

    /**
     * 写入单条转账日志
     * 
     * @param region       节点数据
     * @param sourceCardNo 转出卡号
     * @param destCordNo   转入卡号
     * @param balance      转账金额
     * @param username     操作用户
     */
    private void writeTransLog(Integer region, Long sourceCardNo, Long destCordNo, double balance, String username) {
        transLogMapper.insert(new TransLog()
                .setRegion(region)
                .setSoureCardNo(sourceCardNo)
                .setDestCardNo(destCordNo)
                .setBalance(balance)
                .setUsername(username));
    }

}
