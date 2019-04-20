package com.zl.fund.pojo;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

/**
 * 个人基金表 personal_fund
 * @author 
 *
 */
@Component
@Scope("prototype")
public class PersonalFund {
    private Integer id;

    private Integer orderid;

    private Fund fund;

    private User user;

    private BigDecimal portion;

    private String cardid;

    private TradeType tradetype;

    private TradeState tradestate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date orderdate;

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

	public BigDecimal getPortion() {
		return portion;
	}

	public void setPortion(BigDecimal portion) {
		this.portion = portion;
	}
	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	public TradeType getTradetype() {
		return tradetype;
	}

	public void setTradetype(TradeType tradetype) {
		this.tradetype = tradetype;
	}

	public TradeState getTradestate() {
		return tradestate;
	}

	public void setTradestate(TradeState tradestate) {
		this.tradestate = tradestate;
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public String getBb() {
		return bb;
	}

	public void setBb(String bb) {
		this.bb = bb;
	}

	@Override
	public String toString() {
		return "PersonalFund [id=" + id + ", orderid=" + orderid + ", fund=" + fund + ", user=" + user + ", portion="
				+ portion + ", cardid=" + cardid + ", tradetype=" + tradetype + ", tradestate=" + tradestate
				+ ", orderdate=" + orderdate + ", bb=" + bb + "]";
	}
	
   
}