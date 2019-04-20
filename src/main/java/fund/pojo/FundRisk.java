package com.zl.fund.pojo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 基金风险等级表  tb_fundrisk
 * @author 
 *
 */
@Component
@Scope("prototype")
public class FundRisk {
    private Integer id;

    private String fundriskname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFundriskname() {
        return fundriskname;
    }

    public void setFundriskname(String fundriskname) {
        this.fundriskname = fundriskname == null ? null : fundriskname.trim();
    }
}