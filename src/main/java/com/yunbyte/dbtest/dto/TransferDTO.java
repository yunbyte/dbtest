package com.yunbyte.dbtest.dto;

import java.util.Arrays;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 转账操作请求实体封装类
 * 
 * @author Rongkai Wang
 * @Date: 2020-9-3
 */
public class TransferDTO {

    @Min(0)
    @NotNull(message = "soureCardNo 不能为空")
    private Long soureCardNo;

    @Min(0)
    @NotNull(message = "amount 不能为空")
    private Double amount;

    @Valid
    @NotEmpty(message = "账号数组不能为空")
    @NotNull(message = "destCardNos 不能为空")
    private Long[] destCardNos;

    public TransferDTO() {
    }

    public TransferDTO(Long soureCardNo, Double amount, Long[] destCardNos) {
        this.soureCardNo = soureCardNo;
        this.amount = amount;
        this.destCardNos = destCardNos;
    }

    public Long getSoureCardNo() {
        return soureCardNo;
    }

    public Double getAmount() {
        return amount;
    }

    public Long[] getDestCardNos() {
        return destCardNos;
    }

    public void setSoureCardNo(Long soureCardNo) {
        this.soureCardNo = soureCardNo;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDestCardNos(Long[] destCardNos) {
        this.destCardNos = destCardNos;
    }

    @Override
    public String toString() {
        return "TransferDTO :{" +
                "soureCardNo=" + soureCardNo +
                ", amount=" + amount +
                ", destCardNos=" + Arrays.toString(destCardNos) +
                '}';
    }
}
