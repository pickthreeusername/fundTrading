package com.zl.fund.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zl.fund.mapper.MessageMapper;
import com.zl.fund.pojo.Message;
import com.zl.fund.service.MessageService;
@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	private MessageMapper mm;
	@Override
	public PageInfo<Message> queryAllMessageByUserId(Integer userid, Integer pageNum) {
		//PageHelper为分页对象
		PageHelper.startPage(pageNum,5);
		List<Message> msgList = mm.queryAllMessageByUserId(userid);
		PageInfo<Message> pageInfo = new PageInfo<Message>(msgList);
		return pageInfo;
	}

	@Override
	public int deleteMessage(Integer id) {
		
		return mm.deleteMessage(id);
	}

}
