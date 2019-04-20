package com.zl.fund.pojo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 基金类型表 tb_fundtype
 * @author 
 *
 */
@Component
@Scope("prototype")
public class FundType {
    private Integer id;

    private String fundtypename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFundtypename() {
        return fundtypename;
    }

    public void setFundtypename(String fundtypename) {
        this.fundtypename = fundtypename == null ? null : fundtypename.trim();
    }
}