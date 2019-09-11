package com.sz.water.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import sun.misc.BASE64Decoder;

public class AppUtils {

	/** 获取参数列表 */
	public static Map<String, Object> getMap(Object... values) {
		Map<String, Object> map = new HashMap<>();
		for (int i = 0; i < values.length; i += 2)
			map.put((String) values[i], values[i + 1]);
		return map;
	}

	/** 以指定格式获取当前时间格式字符串as */
	public static String getDate(String format) {
		if (format == null)
			format = "yyyy-MM-dd";
		Date date = new Date();
		SimpleDateFormat simple = new SimpleDateFormat(format);
		return simple.format(date);
	}

	/** 创建图片序列码 */
	public static String UUIDCode() {
		UUID uuid = UUID.randomUUID();
		String code = uuid.toString();
		return code.toUpperCase();
	}

	/** 获取session */
	public static HttpSession getHttpSession() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
		HttpSession session = request.getSession(true);
		return session;
	}

	/** 添加数据至session */
	public static HttpSession pushMap(String key, Object value) {
		HttpSession session = getHttpSession();
		session.setMaxInactiveInterval(18000);
		session.setAttribute(key, value);
		return session;
	}

	/** 从session获取数据 */
	public static Object findMap(String key) {
		HttpSession session = getHttpSession();
		return session.getAttribute(key);
	}

	/** 从session移除数据 */
	public static void removeSession(String key) {
		HttpSession session = getHttpSession();
		session.removeAttribute(key);
	}

	public static void saveImage(String data, String path, String name) {
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] bytes = decoder.decodeBuffer(data);
			for (int i = 0; i < bytes.length; i++)
				bytes[i] = bytes[i] < 0 ? bytes[i] += 256 : bytes[i];
			OutputStream output = new FileOutputStream(path + name + ".png");
			System.out.println(path + name + ".png");
			output.write(bytes);
			output.flush();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * <p>
	 * Title: getNumber
	 * </p>
	 * <p>
	 * Description:随机生成6个字符串
	 * </p>
	 * 
	 * @return
	 */
	public static String getNumber() {
		String str = "0123456789";
		String code = "";
		for (int i = 0; i < 6; i++) {
			int index = (int) (Math.random() * str.length());
			code += str.charAt(index);
		}
		return code;
	}

	/**
	 * 
	 * @Title: longTimeToDay
	 * @Description: 计算两个时间差
	 * @param startTime  开始时间
	 * @param endTime    结束时间
	 * @param resultType 返回day，hour，min
	 * @return
	 */
	public static long longTimeToDay(Date startTime, Date endTime, String resultType) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;

		// 获得两个时间的毫秒时间差异
		long diff = endTime.getTime() - startTime.getTime();

		switch (resultType) {
		case "day":
			long day = diff / nd;
			return day;
		case "hour":
			long hour = diff % nd / nh;
			return hour;
		case "min":
			long min = diff % nd % nh / nm;
			return min;
		default:
			return diff;
		}
	}

	/**
	 * 是否含有英文
	 * 
	 */
	public static boolean isExisLetter(String str) {
		if (str == null)
			return false;
		String regex = ".*[a-zA-z].*";
		return str.matches(regex);
	}

	// 判定是否为數字
	public static boolean isNumeric(String string) {
		if (string == null)
			return false;
		Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
		return pattern.matcher(string).matches();
	}

	/**
	 * 是否含有数字
	 * 
	 */
	public static boolean isExisNumber(String str) {
		if (str == null)
			return false;
		String regex = ".*[0-9].*";
		return str.matches(regex);
	}

	/**
	 * 只含有英文或者字符
	 */
	public boolean isNumberAndLetter(String str) {
		if (str == null)
			return false;
		return str.matches("[a-zA-Z0-9]+");
	}

	// 两个相加
	public static double add(double... v) {
		BigDecimal b = new BigDecimal(Double.toString(v[0]));
		for (int i = 1; i < v.length; i++) {
			BigDecimal b2 = new BigDecimal(Double.toString(v[i]));
			b = b.add(b2);
		}
		return b.doubleValue();
	}

	// 两个相减
	public static Double subDouble(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.subtract(b2).doubleValue();
	}

	public static boolean IsEmptyString(String str) {
		if (str == null || str.trim().length() == 0)
			return true;
		else
			return false;
	}

	public static Map<String, Object> convertBeanToMap(Object bean)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

	/**
	 * @Title: 类型转换
	 * @Description:
	 * @param fdepth
	 * @return
	 */
	public static double toDouble(String value) {
		try {
			return Double.parseDouble(value);
		} catch (Exception e) {
			return 0;
		}
	}

	public static int strToInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return 0;
		}
	}

	public static boolean strToBoolean(String value) {
		try {
			return Boolean.parseBoolean(value);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @param formatStr
	 * @return date
	 */
	public static Date StrToDate(String str, String formatStr) {

		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			date = new Date();
		}
		return date;
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date, String formatStr) {

		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		String str = format.format(date);
		return str;
	}

	public static String excelDate(Cell cell) {
		String guarantee_time = null;
		try {
			double ins = toDouble(cell.getStringCellValue());
			guarantee_time = DateToStr((HSSFDateUtil.getJavaDate(ins)), "yyyy-MM-dd");

		} catch (Exception e) {
			guarantee_time = getDate(null);
		}

		return guarantee_time;
	}

	/**
	 * 替换
	 * 
	 * @Title: reDateStr
	 * @Description:
	 * @param date
	 * @return
	 */
	public static String reDateStr(String date) {
		if (date == null) {
			return null;
		}
		return date.replaceAll("/", "-");
	}

	public static boolean isMSBrowser(HttpServletRequest request) {
		String[] IEBrowserSignals = { "MSIE", "Trident", "Edge" };
		String userAgent = request.getHeader("User-Agent");
		for (String signal : IEBrowserSignals) {
			if (userAgent.contains(signal)) {
				return true;
			}
		}
		return false;
	}
}
