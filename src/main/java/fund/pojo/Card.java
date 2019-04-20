package com.zl.fund.pojo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Card {
	private Integer userid;
	private String cardid;
	private Integer portion;
	private Integer fundid;
	private Double unitValue;
	
	

	public Card() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Card(Integer userid, String cardid, Integer portion, Integer fundid, Double unitValue) {
		super();
		this.userid = userid;
		this.cardid = cardid;
		this.portion = portion;
		this.fundid = fundid;
		this.unitValue = unitValue;
	}


	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	public Integer getPortion() {
		return portion;
	}

	public void setPortion(Integer portion) {
		this.portion = portion;
	}

	public Integer getFundid() {
		return fundid;
	}

	public void setFundid(Integer fundid) {
		this.fundid = fundid;
	}

	

	public Double getUnitValue() {
		return unitValue;
	}


	public void setUnitValue(Double unitValue) {
		this.unitValue = unitValue;
	}


	@Override
	public String toString() {
		return "Card [userid=" + userid + ", cardid=" + cardid + ", portion=" + portion + ", fundid=" + fundid
				+ ", unitValue=" + unitValue + "]";
	}
	
	
	

}
