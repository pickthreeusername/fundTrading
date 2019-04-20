package com.zl.fund.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class ExternalService {
	/**
	 * 发送验证码
	 * @param phone
	 * @param code
	 * @return "00000"发送成功 ，其他：失败
	 */
	public static String sendVerifyCode(String phone, String code) {
		String host = "http://dingxin.market.alicloudapi.com";
	    String path = "/dx/sendSms";
	    String method = "POST";
	    String appcode = "e15b1756d7ec4398ba32c42de84ccbb0";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("mobile", phone);
	    querys.put("param", "code:" + code);
	    querys.put("tpl_id", "TP1711063");
	    Map<String, String> bodys = new HashMap<String, String>();
	    String returnCode = "";
	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
	    	//System.out.println(response.toString());
	    	//获取response的body
	    	String jsonStr = EntityUtils.toString(response.getEntity());
	    	System.out.println("---------jsonStr:" + jsonStr);
	    	JSONObject jsonObj = JSONObject.parseObject(jsonStr);
	    	returnCode = (String) jsonObj.get("return_code");
	    	System.out.println("-------------------returncode:" + jsonObj.get("return_code"));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return returnCode;
	}

	/**
	 * 实名认证
	 * @param idCard
	 * @param name
	 * @return  "01":通过   "02":不通过
	 */
	 public static String checkIdentityCode(String idCard, String name) {
		 String host = "https://idcardcert.market.alicloudapi.com";
	        String path = "/idCardCert";
	        String appcode = "e15b1756d7ec4398ba32c42de84ccbb0";
	        String urlSend = host + path + "?idCard=" + idCard + "&name=" + name;
			
	        URL url;
	        String status = "";
			try {
				url = new URL(urlSend);
				HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		        httpURLConnection.setRequestProperty("Authorization", "APPCODE " + appcode);//格式Authorization:APPCODE (中间是英文空格)
		        int httpCode = httpURLConnection.getResponseCode();
		        String json = read(httpURLConnection.getInputStream());
		        JSONObject jsonObj = JSONObject.parseObject(json);
		        status = (String) jsonObj.get("status");
		        //System.out.println("/* 获取服务器响应状态码 200 正常；400 权限错误 ； 403 次数用完； */ ");
		        System.out.println(httpCode);
		        System.out.println("/* 获取返回的json   */ ");
		        System.out.print(json);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return status;
	        
	 }
	 private static String read(InputStream is) throws IOException {
	        StringBuffer sb = new StringBuffer();
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        String line = null;
	        while ((line = br.readLine()) != null) {
	            line = new String(line.getBytes(), "utf-8");
	            sb.append(line);
	        }
	        br.close();
	        return sb.toString();
	    }
}
