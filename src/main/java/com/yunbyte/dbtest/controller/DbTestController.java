package com.yunbyte.dbtest.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yunbyte.dbtest.dto.CreateCardDTO;
import com.yunbyte.dbtest.dto.ResponseInfo;
import com.yunbyte.dbtest.dto.TransLoopDTO;
import com.yunbyte.dbtest.dto.TransferDTO;
import com.yunbyte.dbtest.service.IDbTestService;

/**
 * dbtest数据库测试程序控制层
 * 
 * @author Rongkai Wang
 * @Date: 2020-9-3
 */
@Validated
@CrossOrigin
@RestController
@RequestMapping("/dbtest")
public class DbTestController {

    @Autowired
    private IDbTestService dbService;

    /**
     * 卡对卡转账操作
     * 
     * @param transferDTO 转账操作请求实体对象
     * @return 转账操作响应实体对象
     */
    @PostMapping(value = "/trans")
    public ResponseInfo trans(@RequestBody @Valid TransferDTO transferDTO) {
        return dbService.trans(transferDTO);
    }

    /**
     * 一卡同时向两卡转账操作
     * 
     * @param transferDTO 转账操作请求实体对象
     * @return 转账操作响应实体对象
     */
    @PostMapping(value = "/trans2")
    public ResponseInfo trans2(@RequestBody @Valid TransferDTO transferDTO) {
        return dbService.trans(transferDTO);
    }

    /**
     * 批量转账操作
     * 
     * @param transLoopDTO 批量操作请求实体对象
     * @return 转账操作响应实体对象
     */
    @PostMapping(value = "/transloop")
    public ResponseInfo transLoop(@RequestBody @Valid TransLoopDTO transLoopDTO) {
        return dbService.transloop(transLoopDTO);
    }

    /**
     * 批量生成账号
     * 
     * @param transLoopDTO 批量操作请求实体对象
     * @return 转账操作响应实体对象
     */
    @PostMapping(value = "/createcardloop")
    public ResponseInfo createCardLoop(@RequestBody @Valid TransLoopDTO transLoopDTO) {
        return dbService.createCardLoop(transLoopDTO);
    }

    /**
     * 生成一条账户信息
     * 
     * @param createCardDTO 创建账户请求实体
     * @return 转账操作响应实体对象
     */
    @PostMapping(value = "/createcard")
    public ResponseInfo createCard(@RequestBody @Valid CreateCardDTO createCardDTO) {
        return dbService.createCard(createCardDTO);
    }

    /**
     * 查询交易流水
     * 
     * @param soureCardNo 转出账号
     * @param destCardNo  转入账号
     * @param pageSize    数据页大小
     * @param pageNum     分页个数
     * @return 转账操作响应实体对象
     */
    @GetMapping("/selectlog")
    public ResponseInfo selectLog(@Min(0) Long soureCardNo, @Min(0) Long destCardNo,
            @RequestParam(value = "pageSize", defaultValue = "1000") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return dbService.selectLog(soureCardNo, destCardNo, pageSize, pageNum);

    }

}
