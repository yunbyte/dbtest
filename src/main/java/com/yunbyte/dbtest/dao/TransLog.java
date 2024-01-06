package com.yunbyte.dbtest.dao;

/**
 * dbtest数据库测试程序持久层实体类--交易流水表
 * 
 * @author Rongkai Wang
 * @Date: 2020-9-3
 */
public class TransLog {
    private static final long serialVersionUID = 1L;

    private Integer region;
    private Long soureCardNo;
    private Long destCardNo;
    private double balance;
    private String changeDate;
    private String username;

    public TransLog() {
    }

    public Integer getRegion() {
        return region;
    }

    public TransLog setRegion(Integer region) {
        this.region = region;
        return this;
    }

    public TransLog(Integer region, Long soureCardNo, Long destCardNo, double balance, String username) {
        this.region = region;
        this.soureCardNo = soureCardNo;
        this.destCardNo = destCardNo;
        this.balance = balance;
        this.username = username;
    }

    public Long getSoureCardNo() {
        return soureCardNo;
    }

    public TransLog setSoureCardNo(Long soureCardNo) {
        this.soureCardNo = soureCardNo;
        return this;
    }

    public Long getDestCardNo() {
        return destCardNo;
    }

    public TransLog setDestCardNo(Long destCardNo) {
        this.destCardNo = destCardNo;
        return this;
    }

    public double getBalance() {
        return balance;
    }

    public TransLog setBalance(double balance) {
        this.balance = balance;
        return this;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public TransLog setChangeDate(String changeDate) {
        this.changeDate = changeDate;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public TransLog setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String toString() {
        return "TransLog:{" +
                "region=" + region +
                ", soureCardNo=" + soureCardNo +
                ", destCardNo=" + destCardNo +
                ", balance=" + balance +
                ", changeDate=" + changeDate +
                ", username='" + username + '\'' +
                '}';
    }
}
