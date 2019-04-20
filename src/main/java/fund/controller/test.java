package com.zl.fund.controller;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zl.fund.pojo.User;
import com.zl.fund.service.OrderService;
import com.zl.fund.service.UserService;



@Controller
@RequestMapping("testOrder")
public class test {
	@Autowired
	private OrderService os;
	@RequestMapping("question")
	public String toDetails() {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = null;
	        try {
				date = sdf.parse("2019-3-16");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println(os.queryOrderList(date));
		return "cjwt";
	}
	
}	

