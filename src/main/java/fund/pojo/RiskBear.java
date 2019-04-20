package com.zl.fund.pojo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 风险承受等级表 tb_riskbear
 * @author 
 *
 */
@Component
@Scope("prototype")
public class RiskBear {
    private Integer id;

    private Integer bearlevel;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBearlevel() {
        return bearlevel;
    }

    public void setBearlevel(Integer bearlevel) {
        this.bearlevel = bearlevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}