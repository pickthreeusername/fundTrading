package com.zl.fund.pojo;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
/**
 * 基金表 tb_fund
 * @author 
 *
 */
@Component
@Scope("prototype")
public class Fund {
    private Integer id;

    private Integer fundid;

    private String fundname;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date establishdate;

    private BigDecimal totalscale;

    private FundType fundtype;

    private Short fundstate;

    private BigDecimal unitvalue;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date unitvaluedate;

    private BigDecimal dayupdown;

    private BigDecimal totalupdown;

    private BigDecimal traderate;

    private BigDecimal managerate;

    private Short startpoint;

    private String advice;

    private FundRisk fundrisk;

    private String fundmanager;

    private String riskbearid;

    private String fundgoal;

    private String aaa;

    private String bbb;

    private String ccc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFundid() {
        return fundid;
    }

    public void setFundid(Integer fundid) {
        this.fundid = fundid;
    }

    public String getFundname() {
        return fundname;
    }

    public void setFundname(String fundname) {
        this.fundname = fundname == null ? null : fundname.trim();
    }

    public Date getEstablishdate() {
        return establishdate;
    }

    public void setEstablishdate(Date establishdate) {
        this.establishdate = establishdate;
    }

    public BigDecimal getTotalscale() {
        return totalscale;
    }

    public void setTotalscale(BigDecimal totalscale) {
        this.totalscale = totalscale;
    }

    public FundType getFundtype() {
		return fundtype;
	}

	public void setFundtype(FundType fundtype) {
		this.fundtype = fundtype;
	}

	public Short getFundstate() {
        return fundstate;
    }

    public void setFundstate(Short fundstate) {
        this.fundstate = fundstate;
    }

    public BigDecimal getUnitvalue() {
        return unitvalue;
    }

    public void setUnitvalue(BigDecimal unitvalue) {
        this.unitvalue = unitvalue;
    }

    public Date getUnitvaluedate() {
        return unitvaluedate;
    }

    public void setUnitvaluedate(Date unitvaluedate) {
        this.unitvaluedate = unitvaluedate;
    }

    public BigDecimal getDayupdown() {
        return dayupdown;
    }

    public void setDayupdown(BigDecimal dayupdown) {
        this.dayupdown = dayupdown;
    }

    public BigDecimal getTotalupdown() {
        return totalupdown;
    }

    public void setTotalupdown(BigDecimal totalupdown) {
        this.totalupdown = totalupdown;
    }

    public BigDecimal getTraderate() {
        return traderate;
    }

    public void setTraderate(BigDecimal traderate) {
        this.traderate = traderate;
    }

    public BigDecimal getManagerate() {
        return managerate;
    }

    public void setManagerate(BigDecimal managerate) {
        this.managerate = managerate;
    }

    public Short getStartpoint() {
        return startpoint;
    }

    public void setStartpoint(Short startpoint) {
        this.startpoint = startpoint;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice == null ? null : advice.trim();
    }

    public FundRisk getFundrisk() {
		return fundrisk;
	}

	public void setFundrisk(FundRisk fundrisk) {
		this.fundrisk = fundrisk;
	}

	public String getFundmanager() {
        return fundmanager;
    }

    public void setFundmanager(String fundmanager) {
        this.fundmanager = fundmanager == null ? null : fundmanager.trim();
    }

    public String getRiskbearid() {
        return riskbearid;
    }

    public void setRiskbearid(String riskbearid) {
        this.riskbearid = riskbearid == null ? null : riskbearid.trim();
    }

    public String getFundgoal() {
        return fundgoal;
    }

    public void setFundgoal(String fundgoal) {
        this.fundgoal = fundgoal == null ? null : fundgoal.trim();
    }

    public String getAaa() {
        return aaa;
    }

    public void setAaa(String aaa) {
        this.aaa = aaa == null ? null : aaa.trim();
    }

    public String getBbb() {
        return bbb;
    }

    public void setBbb(String bbb) {
        this.bbb = bbb == null ? null : bbb.trim();
    }

    public String getCcc() {
        return ccc;
    }

    public void setCcc(String ccc) {
        this.ccc = ccc == null ? null : ccc.trim();
    }
}