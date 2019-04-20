package com.zl.fund.pojo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 银行表 tb_bankcard
 * @author 
 *
 */
@Component
@Scope("prototype")
public class BankCard {
    private Integer id;

    private String bankname;

    private Integer timequota;

    private Integer dayquota;
    
    private String logo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname == null ? null : bankname.trim();
    }

    public Integer getTimequota() {
        return timequota;
    }

    public void setTimequota(Integer timequota) {
        this.timequota = timequota;
    }

    public Integer getDayquota() {
        return dayquota;
    }

    public void setDayquota(Integer dayquota) {
        this.dayquota = dayquota;
    }
    
    

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "BankCard [id=" + id + ", bankname=" + bankname + ", timequota=" + timequota + ", dayquota=" + dayquota
				+ ", logo=" + logo + "]";
	}

	
    
    
}