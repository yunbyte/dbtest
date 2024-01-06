package com.yunbyte.dbtest.dao;

/**
 * dbtest数据库测试程序持久层实体类--账户表
 * 
 * @author Rongkai Wang
 * @Date: 2020-9-3
 */
public class Factor1 {

    private static final long serialVersionUID = 1L;

    private Long cardNo;
    private Integer region;
    private Double balance;
    private String startDate;
    private String changeDate;
    private String username;

    public Long getCardNo() {
        return cardNo;
    }

    public void setCardNo(Long cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getRegion() {
        return region;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
