package com.zl.fund.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.zl.fund.pojo.UserBankCard;

public abstract class TimeNum {
	//得到订单编号  例:2019/3/3 --190303XXXX
	public static Integer timeNum() {
		long time=System.currentTimeMillis();
		Date date=new Date(time);
		String ma="yyyyMMdd";
		SimpleDateFormat forma=new SimpleDateFormat(ma);
		String nwdate=forma.format(date);
		nwdate = nwdate.substring(2,nwdate.length());
		StringBuffer sb=new StringBuffer();
		sb.append(nwdate);
		for(int i =0;i<4;i++) {
        sb.append(String.valueOf
                (new Random().nextInt(10)));
        }
		Integer x=Integer.parseInt(sb.toString());
		return x;
	}
	//隐藏银行卡前几位数 用"*"表示
	public static List<String> yinHang(List<UserBankCard> UserBankCards) {
		List<String> cardids = new ArrayList<String>();
		StringBuffer sb=new StringBuffer();
		for (UserBankCard userBankCard : UserBankCards) {
			String cardid = userBankCard.getCardid();
			String x = cardid.substring(cardid.length()-4,cardid.length());
			for(int i =0;i<4;i++) {
		        sb.append("*");
		        }
			sb.append(x);
			cardids.add(sb.toString());
		}

		return cardids;
	}
	//得到今天的日期
	public static String today() {
		long time=System.currentTimeMillis();
		Date date=new Date(time);
		String ma="dd";
		SimpleDateFormat forma=new SimpleDateFormat(ma);
		String nwdate=forma.format(date);
		return nwdate;
	}


}
