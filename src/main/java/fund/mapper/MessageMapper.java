package com.zl.fund.mapper;

import java.util.List;

import com.zl.fund.pojo.Message;

public interface MessageMapper {
	/**
	 * 消息列表
	 * @param userid
	 * @return
	 */
	List<Message> queryAllMessageByUserId(Integer userid);
	/**
	 * 逻辑删除消息
	 * @param id
	 * @return
	 */
	int deleteMessage(Integer id);
}