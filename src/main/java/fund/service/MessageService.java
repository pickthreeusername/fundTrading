package com.zl.fund.service;

import com.github.pagehelper.PageInfo;
import com.zl.fund.pojo.Message;

public interface MessageService {
	/**
	 * 消息列表
	 * @param userid
	 * @return
	 */
	PageInfo<Message> queryAllMessageByUserId(Integer userid,Integer pageNum);
	/**
	 * 逻辑删除消息
	 * @param id
	 * @return
	 */
	int deleteMessage(Integer id);
}
