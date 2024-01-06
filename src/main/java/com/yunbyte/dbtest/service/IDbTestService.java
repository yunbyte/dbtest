package com.yunbyte.dbtest.service;

import com.yunbyte.dbtest.dto.CreateCardDTO;
import com.yunbyte.dbtest.dto.ResponseInfo;
import com.yunbyte.dbtest.dto.TransLoopDTO;
import com.yunbyte.dbtest.dto.TransferDTO;

/**
 * dbtest数据库测试程序业务层接口
 * 
 * @author Rongkai Wang
 * @Date: 2020-9-3
 */
public interface IDbTestService {

    /**
     * 卡对卡转账
     * 
     * @param transferDTO 转账实体类对象
     * @return 转账操作响应实体对象
     */
    ResponseInfo trans(TransferDTO transferDTO);

    /**
     * 批量转账
     * 
     * @param transLoopDTO 批量操作实体类对象
     * @return 转账操作响应实体对象
     */
    ResponseInfo transloop(TransLoopDTO transLoopDTO);

    /**
     * 批量生成账号
     * 
     * @param transLoopDTO 批量操作实体类对象
     * @return 转账操作响应实体对象
     */
    ResponseInfo createCardLoop(TransLoopDTO transLoopDTO);

    /**
     * 生成一条账户信息
     * 
     * @param createCardDTO 创建账号请求实体
     * @return 转账操作响应实体对象
     */
    ResponseInfo createCard(CreateCardDTO createCardDTO);

    /**
     * 查询交易流水
     * 
     * @param soureCardNo 转出账号
     * @param destCardNo  转入账号
     * @param pageSize    数据页大小
     * @param pageNum     分页个数
     * @return 转账操作响应实体对象
     */
    ResponseInfo selectLog(Long soureCardNo, Long destCardNo, Integer pageSize, Integer pageNum);

}
