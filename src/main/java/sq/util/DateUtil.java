package sq.util;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import sq.enumeration.CalendarType;
import org.apache.commons.beanutils.PropertyUtils;
import org.joda.time.DateTime;

public class DateUtil {

	public static int daysBetween(final Date date1, final Date date2) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		final long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		final long time2 = cal.getTimeInMillis();
		final long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	public static int hoursBetween(final Date start, final Date end) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		final long time1 = cal.getTimeInMillis();
		cal.setTime(end);
		final long time2 = cal.getTimeInMillis();
		final long between_hours = (time2 - time1) / (1000 * 3600);

		return Integer.parseInt(String.valueOf(between_hours));
	}

	public static int minutesBetween(final Date start, final Date end) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		final long time1 = cal.getTimeInMillis();
		cal.setTime(end);
		final long time2 = cal.getTimeInMillis();
		final long between_minutes = (time2 - time1) / (1000 * 60) % 60;

		return Integer.parseInt(String.valueOf(between_minutes));
	}

	public static int secondsBetween(final Date start, final Date end) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		final long time1 = cal.getTimeInMillis();
		cal.setTime(end);
		final long time2 = cal.getTimeInMillis();
		final long between_seconds = (time2 - time1) / 1000 % 60;

		return Integer.parseInt(String.valueOf(between_seconds));
	}

	public static Date getNewDate(final int days) {
		final Calendar cal = Calendar.getInstance();
		final Date date = new Date();
		cal.setTime(date);
		final long time1 = cal.getTimeInMillis();
		final long time_days = days * 1000 * 3600 * 24;
		final long time = time1 + time_days;
		return new Date(time);
	}

	/*
	 * 计算某一个日期后几小时的日期
	 *
	 * @Param date1
	 *
	 * @Param hours
	 *
	 * @return
	 */
	public static Date addHoursToDate(final Date date, final int hours) {

		return new DateTime(date).plusHours(hours).toDate();
	}

	public static Date addDaysToDate(final Date date, final int days) {

		return new DateTime(date).plusDays(days).toDate();
	}

	public static Date addMinuteToDate(final Date date, final int minutes) {

		return new DateTime(date).plusMinutes(minutes).toDate();
	}

	public static Date minusHoursToDate(final Date date, final int hours) {

		return new DateTime(date).minusHours(hours).toDate();
	}

	public static Date minusMinuteToDate(final Date date, final int minutes) {

		return new DateTime(date).minusMinutes(minutes).toDate();
	}

	public static void setUpdateDate(final Object obj) {
		try {
			PropertyUtils.setProperty(obj, "updateDate", new Date());
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		} catch (final NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 * @description: 按日期格式返回月份第一天.
	 * @author: liuxmi
	 * @Date:Sep 9, 2009
	 * @return:String
	 */
	public static Date getFirstDayOfMonth(final Date date) {
		if (date == null) {
			return date;
		}
		final Calendar now = Calendar.getInstance();
		now.setTime(date);
		final Calendar calendar = new GregorianCalendar(now.get(Calendar.YEAR),
				now.get(Calendar.MONTH), now.get(Calendar.DATE));
		calendar.set(Calendar.DATE, 1);
		return calendar.getTime();
	}

	/**
	 * @return
	 * @description: 按日期格式返回月份最后天.
	 * @author: liuxmi
	 * @Date:Sep 9, 2009
	 * @return:String
	 */
	public static Date getFirstDayOfNextMonth() {
		final Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	public static Date getStartDate(final Date date) {
		if (date == null) {
			return new Date();
		}
		final Calendar now = Calendar.getInstance();
		now.setTime(date);
		final Calendar calendar = new GregorianCalendar(now.get(Calendar.YEAR),
				now.get(Calendar.MONTH), now.get(Calendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	public static Date getEndDate(final Date date) {
		if (date == null) {
			return new Date();
		}
		final Calendar now = Calendar.getInstance();
		now.setTime(date);
		final Calendar calendar = new GregorianCalendar(now.get(Calendar.YEAR),
				now.get(Calendar.MONTH), now.get(Calendar.DATE) + 1, 0, 0, 0);
		return calendar.getTime();
	}

	public static Date parseStr2Date(final String dateStr) {
		return parseStr2Date(dateStr, null);
	}

	public static Date parseStr2Date(final String dateStr, String pattern) {
		if (dateStr == null) {
			return null;
		}
		if (pattern == null || pattern.equals("")) {
			pattern = "yyyy-MM-dd";
		}
		final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(dateStr, new ParsePosition(0));

	}

	public static Date parseStr2Date(final String dateStr, String pattern,
			Locale type) {
		if (dateStr == null) {
			return null;
		}
		if (pattern == null || pattern.equals("")) {
			pattern = "yyyy-MM-dd";
		}
		final SimpleDateFormat sdf = new SimpleDateFormat(pattern, type);

		return sdf.parse(dateStr, new ParsePosition(0));

	}

	public static String dateFormat(final Date date) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		return dateFormat.format(date);
	}

	public static String dateFormatGMT(final Date date) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		return dateFormat.format(date);
	}

	public static String dateFormatGMTLenglish(final Date date) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(
				"d MMM, yyyy h:m:s aa", Locale.ENGLISH);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		return dateFormat.format(date);
	}

	public static Date stringToDate(final String date) throws ParseException {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateFormat.parse(date);
	}

	public static String dateFormat(final Date date, final String format) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static String dateFormatYMD(final Date date) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		return dateFormat.format(date);
	}

	public static String dateFormatYMDchinese(final Date date) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		return dateFormat.format(date);
	}

	public static String showSpendTime(Long startTime, final String name) {
		final String result = "%s用时：" + (new Date().getTime() - startTime)
				+ "s";
		startTime = new Date().getTime();
		return String.format(result, name);
	}

	public static String afterOrBeforeNDay(String currentDate, int n) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(currentDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + n);
		String dayBefore = new SimpleDateFormat("yyyy-MM-dd")
				.format(c.getTime());
		return dayBefore;
	}

	// formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	// data Date类型的时间
	public static String dateToString(Date data, String formatType) {
		return new SimpleDateFormat(formatType).format(data);
	}

	// currentTime要转换的long类型的时间
	// formatType要转换的string类型的时间格式
	public static String longToString(long currentTime, String formatType)
			throws ParseException {
		Date date = longToDate(currentTime, formatType); // long类型转成Date类型
		String strTime = dateToString(date, formatType); // date类型转成String
		return strTime;
	}

	// currentTime要转换的long类型的时间
	// formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	public static Date longToDate(long currentTime, String formatType)
			throws ParseException {
		Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
		String sDateTime = dateToString(dateOld, formatType); //
		// 把date类型的时间转换为string
		Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
		return date;
	}

	// strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
	// HH时mm分ss秒，
	// strTime的时间格式必须要与formatType的时间格式相同
	public static Date stringToDate(String strTime, String formatType)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		Date date = null;
		date = formatter.parse(strTime);
		return date;
	}

	public static Date getMonthEndByStart(Date startDate) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(startDate);
		cl.add(Calendar.MONTH, 1);
		cl.add(Calendar.DAY_OF_MONTH, -1);
		return cl.getTime();
	}

	/**
	 * 根据某一天计算相差月份的1号
	 *
	 * @param startDate
	 * @return firstDay
	 */
	public static Date getFirstDayFromDateByMonthCalc(Date startDate,
			int calc) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(startDate);
		cl.add(Calendar.MONTH, calc);
		return cl.getTime();
	}

	public static Date getNextMonthStartByStart(Date startDate) {
		return getFirstDayFromDateByMonthCalc(startDate, 1);
	}

	public static Date getFirstDayFromDateByWeekCalc(Date startDate, int calc) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(startDate);
		cl.add(Calendar.WEEK_OF_YEAR, calc);
		return cl.getTime();
	}

	public static Date getNextDayStartByStart(Date startDate) {
		return getFirstDayFromDateByDayCalc(startDate, 1);
	}

	public static Date getFirstDayFromDateByDayCalc(Date startDate, int calc) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(startDate);
		cl.add(Calendar.DAY_OF_YEAR, calc);
		return cl.getTime();
	}

	public static Date getNextYearStartByStart(Date startDate) {
		return getFirstDayFromDateByYearCalc(startDate, 1);
	}

	public static Date getFirstDayFromDateByYearCalc(Date startDate, int calc) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(startDate);
		cl.add(Calendar.YEAR, calc);
		return cl.getTime();
	}

	public static Date getNextWeekStartByStart(Date startDate) {
		return getFirstDayFromDateByWeekCalc(startDate, 1);
	}

	public static String getWeek(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setMinimalDaysInFirstWeek(7);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		int year = calendar.get(Calendar.YEAR);
		if (month == 0 && day == 1 && week > 1) {
			year = year - 1;
		}
		return year + "年" + String.format("%02d", week) + "周";

	}

	public static String getMonth(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		return year + "-" + String.format("%02d", month);
	}

	public static String getYear(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		return year + "";
	}
	public static String getTimeKeyByCyc(CalendarType cyc, String key) {
		switch (cyc) {
			case WEEK:
				return DateUtil.getWeek(key);
			case MONTH:
				return DateUtil.getMonth(key);
			case YEAR:
				return DateUtil.getYear(key);
			default:
				return key;
		}
	}

	public static Date getFirstDayByNextCyc(CalendarType cyc, Date startDate) {
		switch (cyc) {
			case WEEK:
				return DateUtil.getNextWeekStartByStart(
						DateUtil.parseStr2Date(DateUtil.dateFormat(startDate)));
			case MONTH:
				return DateUtil.getNextMonthStartByStart(
						DateUtil.parseStr2Date(DateUtil.dateFormat(startDate)));
			case YEAR:
				return DateUtil.getNextYearStartByStart(
						DateUtil.parseStr2Date(DateUtil.dateFormat(startDate)));
			default:
				return DateUtil.getNextDayStartByStart(
						DateUtil.parseStr2Date(DateUtil.dateFormat(startDate)));
		}
	}

	public  static Date getFirstDayByPreCyc(CalendarType cyc, Date startDate) {
		switch (cyc) {
			case WEEK:
				return DateUtil.getFirstDayFromDateByWeekCalc(startDate, -1);
			case MONTH:
				return DateUtil.getFirstDayFromDateByMonthCalc(startDate, -1);
			case YEAR:
				return DateUtil.getFirstDayFromDateByYearCalc(startDate, -1);
			default:
				return DateUtil.getFirstDayFromDateByDayCalc(startDate, -1);
		}
	}
}
