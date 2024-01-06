package com.yunbyte.dbtest.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 批量操作请求实体封装类
 * 
 * @author Rongkai Wang
 * @Date: 2020-9-5
 */
public class TransLoopDTO {

    @Min(1)
    @NotNull(message = "batchNum 不能为空")
    private Integer batchNum;

    @Min(0)
    @NotNull(message = "startCardNo 不能为空")
    private Long startCardNo;

    @Min(0)
    @NotNull(message = "amount 不能为空")
    private Double amount;

    public TransLoopDTO() {
    }

    public TransLoopDTO(Integer batchNum, Long startCardNo, Double amount) {
        this.batchNum = batchNum;
        this.startCardNo = startCardNo;
        this.amount = amount;
    }

    public Integer getBatchNum() {
        return batchNum;
    }

    public Long getStartCardNo() {
        return startCardNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setBatchNum(Integer batchNum) {
        this.batchNum = batchNum;
    }

    public void setStartCardNo(Long startCardNo) {
        this.startCardNo = startCardNo;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransLoopDTO{" +
                "batchNum=" + batchNum +
                ", startCardNo=" + startCardNo +
                ", amount=" + amount +
                '}';
    }
}
