package com.yunbyte.dbtest.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yunbyte.dbtest.dao.TransLog;

/**
 * dbtest数据库测试程序持久层接口--交易流水接口
 * 
 * @author Rongkai Wang
 * @Date: 2020-9-3
 */
@Repository
public interface TransLogMapper {

    /**
     * 插入一条交易流水
     * 
     * @param transLog
     *                 insert into trans_log values
     *                 (region1,card_no1,card_no2,balance,sysdate,'I am source -
     *                 factor');
     */
    void insert(TransLog transLog);

    /**
     * 查询交易流水
     * 
     * @param soureCardNo 转出账户
     * @param desCardNo   转入账户
     * @return 交易流水数据集合
     */
    List<TransLog> selectLogAll(@Param("soureCardNo") Long soureCardNo, @Param("destCardNo") Long desCardNo);

}
