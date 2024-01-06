package com.yunbyte.dbtest.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 创建账户请求实体封装类
 * 
 * @author Rongkai Wang
 * @Date: 2020-9-9
 */
public class CreateCardDTO {

    @Min(0)
    @NotNull(message = "cardNo 不能为空")
    private Long cardNo;

    @Min(0)
    @NotNull(message = "amount 不能为空")
    private Double balance;

    public CreateCardDTO() {
    }

    public CreateCardDTO(Long cardNo, Double balance) {
        this.cardNo = cardNo;
        this.balance = balance;
    }

    public Long getCardNo() {
        return cardNo;
    }

    public void setCardNo(Long cardNo) {
        this.cardNo = cardNo;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "CreateCardDTO{" +
                "cardNo=" + cardNo +
                ", balance=" + balance +
                '}';
    }
}
