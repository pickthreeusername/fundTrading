package com.zl.fund.pojo;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
/**
 * 账户资产记录表 tb_userasset
 * @author 
 *
 */
@Component
@Scope("prototype")
public class UserAsset {
    private Integer id;

    private Integer userid;

    private User user;
    
    private Short operation;

    private BigDecimal inmoney;

    private BigDecimal outmoney;

    private Short status;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date tradetime;

    private String remark;

    private String aa;

    private String bb;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Short getOperation() {
        return operation;
    }

    public void setOperation(Short operation) {
        this.operation = operation;
    }

    public BigDecimal getInmoney() {
        return inmoney;
    }

    public void setInmoney(BigDecimal inmoney) {
        this.inmoney = inmoney;
    }

    public BigDecimal getOutmoney() {
        return outmoney;
    }

    public void setOutmoney(BigDecimal outmoney) {
        this.outmoney = outmoney;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getTradetime() {
        return tradetime;
    }

    public void setTradetime(Date tradetime) {
        this.tradetime = tradetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAa() {
        return aa;
    }

    public void setAa(String aa) {
        this.aa = aa == null ? null : aa.trim();
    }

    public String getBb() {
        return bb;
    }

    public void setBb(String bb) {
        this.bb = bb == null ? null : bb.trim();
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}