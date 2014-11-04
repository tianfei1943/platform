package com.smf.study.lang;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateOfLang {
	
	private static final Logger logger = LoggerFactory.getLogger(DateOfLang.class);
	
	public static void main(String[] args) throws ParseException {
		
		String dateStr = "2010/01/01 11:22:33";
		// 生成Date对象
		Date date = DateUtils.parseDate(dateStr, new String[]{"yyyy/MM/dd HH:mm:ss"});
		logger.info("当前日期="+dateStr);
		
		// 10天后
		Date tenDaysAfter = DateUtils.addDays(date, 10); // => 2010/01/11 11:22:33
		logger.info("10天之后="+DateFormatUtils.format(tenDaysAfter, "yyyy/MM/dd HH:mm:ss"));
		 
		// 前一个月
		Date prevMonth = DateUtils.addMonths(date, -1); // => 2009/12/01 11:22:33
		logger.info("前一个月="+DateFormatUtils.format(prevMonth, "yyyy/MM/dd HH:mm:ss"));
		 
		// 判断是否是同一天
		Date date1 = DateUtils.parseDate("2010/01/01 11:22:33", new String[]{"yyyy/MM/dd HH:mm:ss"});
		Date date2 = DateUtils.parseDate("2010/01/01 22:33:44", new String[]{"yyyy/MM/dd HH:mm:ss"});
		if(DateUtils.isSameDay(date1, date2)){
			logger.info("==");// true
		}else{
			logger.info("!=");
		}
		
		// 日期格式化
		logger.info(DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss"));
		
		logger.error("error");

	}

}
