package com.yunbyte.dbtest.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yunbyte.dbtest.dao.Factor1;

/**
 * dbtest数据库测试程序持久层接口--账户表接口
 * 
 * @author Rongkai Wang
 * @Date: 2020-9-3
 */
@Repository
public interface FactorMapper {
    /**
     * 向数据库插入一条账户信息
     * 
     * @param factor 账户信息对象
     *               insert into factor values
     *               (cardno,regin,balance,sysdate,sysdate,'aaa');
     */
    void insert(Factor1 factor);

    /**
     * 根据卡号查余额
     * 
     * @param cardNo 卡号
     * @return 分区
     *
     *         select balance from factor where card_No=#{cardNo} and
     *         region=#{region}
     */
    Integer selectRegionByCardNo(@Param("cardNo") long cardNo);

    /**
     * 根据卡号查余额
     * 
     * @param cardNo 卡号
     * @return 余额
     *
     *         select balance from factor where card_No=#{cardNo} and
     *         region=#{region}
     */
    Double selectBalByCardNo(@Param("cardNo") long cardNo);

    /**
     * 修改账户余额
     * 
     * @param balance 新余额
     * @param cardNo  卡号
     * @param region  分区
     *
     *                update factor1 set balance=#{balance} where card_no=#{cardNo}
     *                and region=#{region}
     */
    void updateBalance(@Param("balance") double balance, @Param("cardNo") long cardNo, @Param("region") int region);

}
