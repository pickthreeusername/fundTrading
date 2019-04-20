package com.zl.fund.pojo;

import java.math.BigDecimal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 用户银行卡表 tb_userbankcard
 * @author 
 *
 */
@Component
@Scope("prototype")
public class UserBankCard {
    private Integer id;

    private Integer userid;
    
    private User user;

    private BankCard bankCard;

    private String cardid;

    private String bankDeposit;

    private String tel;

    private Short state;

    private BigDecimal balances;

    public BigDecimal getBalances() {
		return balances;
	}

	public void setBalances(BigDecimal balances) {
		this.balances = balances;
	}

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

	public BankCard getBankCard() {
		return bankCard;
	}

	public void setBankCard(BankCard bankCard) {
		this.bankCard = bankCard;
	}

	public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid == null ? null : cardid.trim();
    }

    public String getBankDeposit() {
        return bankDeposit;
    }

    public void setBankDeposit(String bankDeposit) {
        this.bankDeposit = bankDeposit == null ? null : bankDeposit.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

	public String getBb() {
        return bb;
    }

    public void setBb(String bb) {
        this.bb = bb == null ? null : bb.trim();
    }

	@Override
	public String toString() {
		return "UserBankCard [id=" + id + ", userid=" + userid + ", bankCard=" + bankCard + ", cardid=" + cardid
				+ ", bankDeposit=" + bankDeposit + ", tel=" + tel + ", state=" + state + ", balances=" + balances
				+ ", bb=" + bb + "]";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	
    
    
}