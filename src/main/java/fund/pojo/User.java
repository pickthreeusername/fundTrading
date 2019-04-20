package com.zl.fund.pojo;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
/**
 * 用户表 tb_user
 * @author 
 *
 */
@Component
@Scope("prototype")
public class User {
    private Integer id;

    private Integer userId;

    private String password;

    private String realName;

    private String mobilePhone;

    private Integer padPay;//支付密码

    private String idCard;

    private String address;

    private String email;

    private RiskBear riskBear;//风险承受等级

    private String sex;

    private String profession;//职业
    
    private Integer ifSuccess;//是否注册成功
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date cardDate;//身份证有效日期
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date regDate;//注册日期

    private String remark;//备注

    private String aa;

    private String bb;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer getPadPay() {
		return padPay;
	}

	public void setPadPay(Integer padPay) {
		this.padPay = padPay;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public RiskBear getRiskBear() {
		return riskBear;
	}

	public void setRiskBear(RiskBear riskBear) {
		this.riskBear = riskBear;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public Date getCardDate() {
		return cardDate;
	}

	public void setCardDate(Date cardDate) {
		this.cardDate = cardDate;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAa() {
		return aa;
	}

	public void setAa(String aa) {
		this.aa = aa;
	}

	public String getBb() {
		return bb;
	}

	public void setBb(String bb) {
		this.bb = bb;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", password=" + password + ", realName=" + realName
				+ ", mobilePhone=" + mobilePhone + ", padPay=" + padPay + ", idCard=" + idCard + ", address=" + address
				+ ", email=" + email + ", riskBear=" + riskBear + ", sex=" + sex + ", profession=" + profession
				+ ", cardDate=" + cardDate + ", regDate=" + regDate + ", remark=" + remark + ", aa=" + aa + ", bb=" + bb
				+ "]";
	}

	public Integer getIfSuccess() {
		return ifSuccess;
	}

	public void setIfSuccess(Integer ifSuccess) {
		this.ifSuccess = ifSuccess;
	}
    
    
}