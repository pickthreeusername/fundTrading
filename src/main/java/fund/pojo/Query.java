package com.zl.fund.pojo;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class Query {
	//交易时间固定条件
	private Integer queryTradeDate;
	//交易时间自定义条件(从)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date queryTradeDateStart;
	//交易时间自定义条件(到)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date queryTradeDateEnd;
	//交易类型条件
	private Integer queryTradeType;
	//交易状态条件
	private Integer queryTradeState;
	//哪个用户
	private Integer queryUserId;
	public Integer getQueryTradeDate() {
		return queryTradeDate;
	}
	public void setQueryTradeDate(Integer queryTradeDate) {
		this.queryTradeDate = queryTradeDate;
	}
	public Date getQueryTradeDateStart() {
		return queryTradeDateStart;
	}
	public void setQueryTradeDateStart(Date queryTradeDateStart) {
		this.queryTradeDateStart = queryTradeDateStart;
	}
	public Date getQueryTradeDateEnd() {
		return queryTradeDateEnd;
	}
	public void setQueryTradeDateEnd(Date queryTradeDateEnd) {
		this.queryTradeDateEnd = queryTradeDateEnd;
	}
	public Integer getQueryTradeType() {
		return queryTradeType;
	}
	public void setQueryTradeType(Integer queryTradeType) {
		this.queryTradeType = queryTradeType;
	}
	public Integer getQueryTradeState() {
		return queryTradeState;
	}
	public void setQueryTradeState(Integer queryTradeState) {
		this.queryTradeState = queryTradeState;
	}
	public Integer getQueryUserId() {
		return queryUserId;
	}
	public void setQueryUserId(Integer queryUserId) {
		this.queryUserId = queryUserId;
	}
	

}
