package com.zl.fund.pojo;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
/**
 * 个人定投表 personal_invest
 * @author 
 *
 */
@Component
@Scope("prototype")
public class PersonalInvest {
    private Integer id;

    private Integer orderid;

    private Fund fund;

    private User user;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date investdate;

    private Integer eachpay;

    private String cardid;

    private InvestState investstate;

    private String whichday;

    private String bb;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getInvestdate() {
        return investdate;
    }

    public void setInvestdate(Date investdate) {
        this.investdate = investdate;
    }

    public Integer getEachpay() {
        return eachpay;
    }

    public void setEachpay(Integer eachpay) {
        this.eachpay = eachpay;
    }
    
    public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	
    public InvestState getInveststate() {
		return investstate;
	}

	public void setInveststate(InvestState investstate) {
		this.investstate = investstate;
	}

	public String getWhichday() {
		return whichday;
	}

	public void setWhichday(String whichday) {
		this.whichday = whichday;
	}

	public String getBb() {
        return bb;
    }

    public void setBb(String bb) {
        this.bb = bb == null ? null : bb.trim();
    }
}