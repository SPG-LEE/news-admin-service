/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sq.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.json.JSONObject;

/**
 * 
 * @author Administrator
 */
public class FormatUtil {

	private final static int ENCODE_XORMASK = 0x5A;
	private final static char ENCODE_DELIMETER = '\002';
	private final static char ENCODE_CHAR_OFFSET1 = 'A';
	private final static char ENCODE_CHAR_OFFSET2 = 'h';

	private final static Pattern JSON_ARRAY_PATTERN = Pattern
			.compile("^\\[.*\\]$");
	private final static Pattern JSON_OBJECT_PATTERN = Pattern
			.compile("^\\{.*\\}$");

	public static boolean checkJsonObject(final String value) {
		final Matcher matcher = JSON_OBJECT_PATTERN.matcher(value);
		return matcher.matches();
	}

	public static boolean checkJsonArray(final String value) {
		final Matcher matcher = JSON_ARRAY_PATTERN.matcher(value);
		return matcher.matches();
	}

	public static float convertNumberToFloat(final Number value) {
		final DecimalFormat df = new DecimalFormat("########.#");
		return Float.parseFloat(df.format(value));
	}

	public static String formatFloat(final float value, final int digit) {

		try {
			final DecimalFormat nf = new DecimalFormat();
			nf.setDecimalSeparatorAlwaysShown(false);
			nf.setRoundingMode(RoundingMode.HALF_UP);
			nf.setGroupingUsed(false);
			nf.setMinimumFractionDigits(digit);
			nf.setMaximumFractionDigits(digit);
			return nf.format(value);
		} catch (final Exception e) {
			e.printStackTrace();
			return "0.0";
		}

	}

	public static String formatDouble(final double value, final int digit) {

		try {
			final DecimalFormat nf = new DecimalFormat();
			nf.setDecimalSeparatorAlwaysShown(false);
			nf.setRoundingMode(RoundingMode.HALF_UP);
			nf.setGroupingUsed(false);
			nf.setMinimumFractionDigits(digit);
			nf.setMaximumFractionDigits(digit);
			return nf.format(value);
		} catch (final Exception e) {
			e.printStackTrace();
			return "0.0";
		}

	}

	public static String formatFloat(final String str, final int digit) {
		final float value = toFloat(str);
		return formatFloat(value, digit);

	}

	public static String formatDouble(final String str, final int digit) {
		final double value = toDouble(str);
		return formatDouble(value, digit);

	}

	public static String formatNumber(final Number value) {
		if (value == null) {
			return "";
		}
		final DecimalFormat df = new DecimalFormat();
		df.setDecimalSeparatorAlwaysShown(false);
		df.setGroupingUsed(false);
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		df.setNegativePrefix("");
		return df.format(value);
	}

	public static String formatNumber(final Number value, final int i) {
		if (value == null) {
			return "";
		}
		final DecimalFormat df = new DecimalFormat();
		df.setDecimalSeparatorAlwaysShown(false);
		df.setGroupingUsed(false);
		df.setMinimumFractionDigits(i);
		df.setMaximumFractionDigits(i);
		df.setNegativePrefix("");
		return df.format(value);
	}

	public static String calculatePerimeter(final String width,
			final String height) {
		final double w = Double.valueOf(width);
		final double h = Double.valueOf(height);
		return FormatUtil.formatNumber((w + h) * 2);
	}

	public static int parseInt(final float value) {
		return (int) Math.rint(value - 0.01);
	}

	public static String formatDate(final Date date) {
		if (date == null) {
			return "未知日期";
		}
		final SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	public static Date addTime(final Date date, final Date date1) {

		final DateTime now = new DateTime();
		DateTime value = new DateTime(date1);
		value = value.withHourOfDay(now.getHourOfDay());
		value = value.withMinuteOfHour(now.getMinuteOfHour());
		value = value.withSecondOfMinute(now.getSecondOfMinute());

		return value.toDate();
	}

	public static String formatDateNoTime(final Date date) {
		if (date == null) {
			return "未知日期";
		}
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	public static Date parseDateNoTime(final String date) {
		if (date == null) {
			return null;
		}
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return dateFormat.parse(date);
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date toDate(final String date) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			return dateFormat.parse(date);
		} catch (final ParseException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Builds a cookie string containing a username and password.
	 * <p>
	 * 
	 * Note: with open source this is not really secure, but it prevents users
	 * from snooping the cookie file of others and by changing the XOR mask and
	 * character offsets, you can easily tweak results.
	 * 
	 * @param username
	 *            The username.
	 * @param password
	 *            The password.
	 * @return String encoding the input parameters, an empty string if one of
	 *         the arguments equals <code>null</code>.
	 */
	public static String encodePasswordCookie(final String username,
			final String password) {
		final StringBuffer buf = new StringBuffer();
		if (username != null && password != null) {
			final byte[] bytes = (username + ENCODE_DELIMETER + password)
					.getBytes();
			int b;

			for (int n = 0; n < bytes.length; n++) {
				b = bytes[n] ^ (ENCODE_XORMASK + n);
				buf.append((char) (ENCODE_CHAR_OFFSET1 + (b & 0x0F)));
				buf.append((char) (ENCODE_CHAR_OFFSET2 + ((b >> 4) & 0x0F)));
			}
		}
		return buf.toString();
	}

	public static String[] decodePasswordCookie(String cookieVal) {

		// check that the cookie value isn't null or zero-length
		if (cookieVal == null || cookieVal.length() <= 0) {
			return null;
		}

		// unrafel the cookie value
		final char[] chars = cookieVal.toCharArray();
		final byte[] bytes = new byte[chars.length / 2];
		int b;
		for (int n = 0, m = 0; n < bytes.length; n++) {
			b = chars[m++] - ENCODE_CHAR_OFFSET1;
			b |= (chars[m++] - ENCODE_CHAR_OFFSET2) << 4;
			bytes[n] = (byte) (b ^ (ENCODE_XORMASK + n));
		}
		cookieVal = new String(bytes);
		final int pos = cookieVal.indexOf(ENCODE_DELIMETER);
		final String username = (pos < 0) ? "" : cookieVal.substring(0, pos);
		final String password = (pos < 0) ? "" : cookieVal.substring(pos + 1);

		return new String[] { username, password };
	}

	public static boolean isEmpty(final Collection collection) {
		return collection == null ? true : collection.isEmpty();
	}

	public static boolean isEmpty(final String value) {
		return value == null ? true : value.trim().length() == 0;
	}

	public static List removeDuplicate(final List list) {
		return new ArrayList(new HashSet(list));
	}

	public static String formatMac(final String mac) {
		final char[] chars = mac.toCharArray();
		final StringBuffer sb = new StringBuffer();
		for (final char each : chars) {
			final int i = Integer.parseInt("" + each, 16);
			if (i < 10) {
				sb.append("8" + i);
			} else {
				sb.append(i);
			}
		}
		return sb.toString();
	}

	public static long convertStringToLong(final String value) {
		if (value == null || value.trim().isEmpty()) {
			return -1;
		}
		try {
			return Long.parseLong(value);
		} catch (final Exception e) {
			return -1;
		}
	}

	public static String toMac(final String mac) {
		final char[] macs = mac.toCharArray();
		final StringBuilder sb1 = new StringBuilder();
		int i = 0;
		while (i < macs.length) {
			final int first = Integer.parseInt("" + macs[i]);
			int result = 0;
			if (first == 8) {
				result = Integer.parseInt("" + macs[i + 1]);
			} else {
				result = Integer.valueOf("" + Integer.parseInt("" + macs[i])
						+ Integer.parseInt("" + macs[i + 1]));
			}
			sb1.append(Integer.toHexString(result));
			i += 2;
		}
		return sb1.toString().toUpperCase();
	}

	public static String formatTooltip(final String name, final String price) {
		final StringBuilder sb = new StringBuilder();
		sb.append("<html>").append("<br><center>").append(name)
				.append("</center><br>")
				.append("<center>&nbsp;&nbsp;&nbsp;&nbsp;").append(price)
				.append("&nbsp;&nbsp;&nbsp;&nbsp;</center><br>")
				.append("</html>");
		return sb.toString();
	}

	public static boolean isNullOrEmpty(final String... value) {
		for (final String each : value) {
			if (each != null && !each.trim().isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public static float toFloat(final String value) {
		if (isNullOrEmpty(value)) {
			return 0;
		} else {
			try {
				final DecimalFormat df = new DecimalFormat();
				df.setMinimumFractionDigits(2);
				df.setMaximumFractionDigits(2);
				final float returnValue = df.parse(value).floatValue();
				// if (returnValue.lastIndexOf(".00") > 0) {
				// return returnValue.substring(0, returnValue.length() - 3);
				// } else {
				return returnValue;
			} catch (final Exception e) {
				return 0;
			}
		}

	}

	public static double toDouble(final String value) {
		if (isNullOrEmpty(value)) {
			return 0;
		} else {
			try {
				final DecimalFormat df = new DecimalFormat();
				df.setMinimumFractionDigits(2);
				df.setMaximumFractionDigits(2);
				final double returnValue = df.parse(value).doubleValue();
				// if (returnValue.lastIndexOf(".00") > 0) {
				// return returnValue.substring(0, returnValue.length() - 3);
				// } else {
				return returnValue;
			} catch (final Exception e) {
				return 0;
			}
		}

	}

	public static String formatTooltip(final String name) {
		final StringBuilder sb = new StringBuilder();
		sb.append("<html>").append("<br><center>&nbsp;&nbsp;&nbsp;&nbsp;")
				.append(name).append("&nbsp;&nbsp;&nbsp;&nbsp;</center><br>")
				.append("</html>");
		return sb.toString();
	}

	public static String toFloatString(final float value) {

		try {
			final DecimalFormat nf = new DecimalFormat();
			nf.setDecimalSeparatorAlwaysShown(false);
			nf.setGroupingUsed(false);
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			return nf.format(value);
		} catch (final Exception e) {
			e.printStackTrace();
			return "0.00";
		}

	}

	/**
	 * http://blog.idrsolutions.com/2012/10/optimising-conversion-from-float-to-
	 * string-in-java/
	 * 
	 * @param val
	 * @return
	 */
	public static String toFloatStringFast(final float val) {

		return (val - (int) val == 0) ? "" + (int) val : "" + (int) val + '.'
				+ ((int) (val * 100) - (int) val * 100);

	}

	// 189****2292
	public static String hidePhone(final String phone) {
		final int length = phone.length();
		final StringBuilder sb = new StringBuilder();
		if (length > 3) {
			sb.append(phone.substring(0, 3));
		}
		sb.append("****");
		if (length > 7) {
			sb.append(phone.substring(7));
		}
		return sb.toString();
	}

	public static long getLong(final JSONObject jo, final String key) {
		final Object object = jo.get(key);
		if (object != null) {
			final String value = object.toString();
			if (!value.isEmpty()) {
				try {
					return Long.parseLong(value);
				} catch (final Exception e) {
					e.printStackTrace();
					return 0;
				}

			}
		}
		return 0;
	}

//	public static void main(final String[] args) {
//		System.out.println(checkJsonObject("13162640532"));
//		System.out.println(checkJsonObject("{"));
//		System.out.println(checkJsonObject("}"));
//		System.out.println(checkJsonObject("{sdfsdf}"));
//		System.out.println(checkJsonObject("{'sdfsdf'}"));
//		System.out.println(checkJsonObject("{}"));
//
//	}

	public static float formatNumberToFloat(final Number value) {
		final DecimalFormat df = new DecimalFormat();
		df.setDecimalSeparatorAlwaysShown(false);
		df.setGroupingUsed(false);
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		df.setNegativePrefix("");
		return Float.parseFloat(df.format(value));
	}

	public static String formatNumberToString(final Number value) {
		final DecimalFormat df = new DecimalFormat("###.#");
		return df.format(value);
	}

	public static String substringFirstCharFromString(final String str) {
		String result = "";
		if (!isNullOrEmpty(str)) {
			result = str.charAt(0) + "";
		}
		return result;
	}
}
