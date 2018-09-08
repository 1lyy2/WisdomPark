package com.zykj.carfigure.utils;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 字符串辅助类
 * 
 */
public class StrUtil {
    public static boolean isEmpty(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回以某个符号分隔的字符串的第几项 从0开始
     * 
     * @param str 原字符串
     * @param num 要取得的字符在字符串中的序号，以0开始
     * @param character 分隔符
     * @return String 返回类型
     */
    public static String getSingleSplitStr(String str, int num, String character) {
        if (isEmpty(str) || !str.contains(character)) {
            return str;
        }
        String[] strs = str.split(character);
        if (strs.length < num + 1) {
            return "";
        }
        try {
            return strs[num];
        } catch (Exception e) {
            return "";
        }
    }

	public static String star(int month, int day) {
		String star = "";
		if (month == 1 && day >= 20 || month == 2 && day <= 18) {
			star = "水瓶座";
		}
		if (month == 2 && day >= 19 || month == 3 && day <= 20) {
			star = "双鱼座";
		}
		if (month == 3 && day >= 21 || month == 4 && day <= 19) {
			star = "白羊座";
		}
		if (month == 4 && day >= 20 || month == 5 && day <= 20) {
			star = "金牛座";
		}
		if (month == 5 && day >= 21 || month == 6 && day <= 21) {
			star = "双子座";
		}
		if (month == 6 && day >= 22 || month == 7 && day <= 22) {
			star = "巨蟹座";
		}
		if (month == 7 && day >= 23 || month == 8 && day <= 22) {
			star = "狮子座";
		}
		if (month == 8 && day >= 23 || month == 9 && day <= 22) {
			star = "处女座";
		}
		if (month == 9 && day >= 23 || month == 10 && day <= 22) {
			star = "天秤座";
		}
		if (month == 10 && day >= 23 || month == 11 && day <= 21) {
			star = "天蝎座";
		}
		if (month == 11 && day >= 22 || month == 12 && day <= 21) {
			star = "射手座";
		}
		if (month == 12 && day >= 22 || month == 1 && day <= 19) {
			star = "摩羯座";
		}
		return star;
	}
	//出生日期字符串转化成Date对象  
    public static Date parse(String strDate) throws ParseException {  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        return sdf.parse(strDate);  
    }  
    
	// 由出生日期获得年龄
	public static int getAge(Date birthDay) throws IllegalArgumentException {
		Calendar cal = Calendar.getInstance();

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthDay);

		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		if (cal.after(now)) {
			throw new IllegalArgumentException("Can't be born in the future");
		}

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth)
					age--;
			} else {
				age--;
			}
		}
		return age;
	}
	

    /**
     * 隐藏手机中间4位号码
     * 130****0000
     *
     * @param mobile_phone 手机号码
     * @return 130****0000
     */
    public static String hideMobilePhone4(String mobile_phone) {
        return mobile_phone.substring(0, 3) + "****" + mobile_phone.substring(7, 11);
    }
    
	/**
	 * 将实际地理距离转换为屏幕像素值
	 * 
	 * @param distance
	 *            实际距离,单位为米
	 * @param currScale
	 *            当前地图尺寸
	 * @param context
	 * @return
	 */
	public static double metreToScreenPixel(double distance, double currScale,
			Context context) {
		float dpi = context.getResources().getDisplayMetrics().densityDpi;
		// 当前地图范围内1像素代表多少地图单位的实际距离
		double resolution = (25.39999918 / dpi)
				* currScale / 1000;
		return distance / resolution;
	}

	/**
	 * 将屏幕上对应的像素距离转换为当前显示地图上的地理距离(米)
	 * 
	 * @param pxlength
	 * @param currScale
	 * @param context
	 * @return
	 */
	public static double screenPixelToMetre(double pxlength, double currScale,
			Context context) {
		float dpi = context.getResources().getDisplayMetrics().densityDpi;
		double resolution = (25.39999918 / dpi)
				* currScale / 1000;
		return pxlength * resolution;
	}

	private static final double EARTH_RADIUS = 6378137.0;

	// 返回单位是米
	public static double getDistance(double longitude1, double latitude1,
			double longitude2, double latitude2) {
		double Lat1 = rad(latitude1);
		double Lat2 = rad(latitude2);
		double a = Lat1 - Lat2;
		double b = rad(longitude1) - rad(longitude2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(Lat1) * Math.cos(Lat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

}
