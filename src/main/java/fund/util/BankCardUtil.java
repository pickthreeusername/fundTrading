package com.zl.fund.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
// /security/toSetTransCode.do
public class BankCardUtil {
	
	public static String bankCard(String cardid, String idcard,String name) {
		String host = "https://aliyuncardby4element.haoservice.com";
	    String path = "/creditop/BankCardQuery/QryBankCardBy3Element";
	    String method = "GET";
	    String appcode = "e51ce39322dc466884bfdb9533ee6dc7";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("accountNo",cardid);
	    querys.put("idCardCode",idcard);
	    querys.put("name",name);
	    String jsonstr ="";
	    String reason="";
	    try {
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	System.out.println("yyyyyy"+response.toString());
	    	//获取response的body	    	
	    	jsonstr =  EntityUtils.toString(response.getEntity());
	    	System.out.println("jsonstr=="+jsonstr);
	     	JSONObject obj = JSONObject.parseObject(jsonstr);
	     	reason = (String)obj.get("reason");
	     	System.out.println("reason=="+reason);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }	    	
	    return reason;
	  }
	/**
	 * 手机短信验证码
	 */
	public static String phoneMsg(String phone) {
		String host = "https://exempt.market.alicloudapi.com";
	    String path = "/exemptCode";
	    String method = "GET";
	    String appcode = "e51ce39322dc466884bfdb9533ee6dc7";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    Random random=new Random();
        Integer code=random.nextInt(899999)+100000;
	    querys.put("content", "【嘎嘎基金】您的验证码是："+code+"，请在5分钟内使用。请勿泄露。");
	    querys.put("phone",phone);
	    String info="";
	    try {
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	System.out.println(response.toString());
            //状态码: 200 正常；400 URL无效；401 appCode错误； 403 次数用完； 500 API网管错误
	    	//获取response的body
	    	String jsonstr = EntityUtils.toString(response.getEntity());
	    	System.out.println("jsonstr=="+jsonstr);
	    	JSONObject obj = JSONObject.parseObject(jsonstr);
	    	info = (String) obj.get("code");
	    	System.out.println("code=="+info);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return info;
	}
}
