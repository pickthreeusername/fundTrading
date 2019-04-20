package com.zl.fund.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zl.fund.pojo.Message;
import com.zl.fund.pojo.User;
import com.zl.fund.service.MessageService;

@Controller
@RequestMapping("messageList")
public class MessageListController {
	@Autowired
	private MessageService ms;
	
	@RequestMapping("toMessage.do")
	public String toMessage(Model model,HttpSession session,@RequestParam(value="pageNum",defaultValue="1")Integer pageNum) {
		User user = (User) session.getAttribute("loginUser");
		PageInfo<Message> pageInfo = ms.queryAllMessageByUserId(user.getId(), pageNum);
		model.addAttribute("pageInfo", pageInfo);
		
		return "MessageList";
	}
	
	@RequestMapping("deleteMsg.do")
	@ResponseBody
	public Map<String,Object> deleteMsg(Integer id) {
		Map<String,Object> json=new HashMap<String,Object>();
		int num = ms.deleteMessage(id);
		if(num==0) {
			json.put("flag", false);
		}else {
			json.put("flag", true);
		}
		return json;
	}
}
