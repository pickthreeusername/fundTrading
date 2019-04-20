package com.zl.fund.pojo;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
/**
 * 用户消息表 tb_message
 * @author 
 *
 */
@Component
@Scope("prototype")
public class Message {
    private Integer id;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date messagedate;

    private User user;

    private String messagecontent;

    private Short state;

    private String aa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getMessagedate() {
        return messagedate;
    }

    public void setMessagedate(Date messagedate) {
        this.messagedate = messagedate;
    }

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessagecontent() {
        return messagecontent;
    }

    public void setMessagecontent(String messagecontent) {
        this.messagecontent = messagecontent == null ? null : messagecontent.trim();
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public String getAa() {
        return aa;
    }

    public void setAa(String aa) {
        this.aa = aa == null ? null : aa.trim();
    }
}