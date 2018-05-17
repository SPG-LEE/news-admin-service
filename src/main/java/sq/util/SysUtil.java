package sq.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class SysUtil {

	private final static int ENCODE_XORMASK = 0x5A;

	private final static char ENCODE_DELIMETER = '\002';

	private final static char ENCODE_CHAR_OFFSET1 = 'A';

	private final static char ENCODE_CHAR_OFFSET2 = 'h';

	public static float formatNumberToFloat(final Number value) {
		final DecimalFormat df = new DecimalFormat("########");
		return Float.parseFloat(df.format(value));
	}

	public static float formatNumber(final Number value) {
		final DecimalFormat df = new DecimalFormat("########");
		return Float.parseFloat(df.format(value));
	}

	public static String formatNumberToString(final Number value) {
		final DecimalFormat df = new DecimalFormat("####");
		return df.format(value);
	}

	public static int parseInt(final float value) {
		return (int) Math.rint(value - 0.01);
	}

	public static String formatDate(final Date date) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	public static String abbreviate(final String value, final int count) {
		return StringUtils.abbreviate(value, count);
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

	/**
	 * Unrafels a cookie string containing a username and password.
	 * 
	 * @param value
	 *            The cookie value.
	 * @return String[] containing the username at index 0 and the password at
	 *         index 1, or <code>{ null, null }</code> if cookieVal equals
	 *         <code>null</code> or the empty string.
	 */
	public static String[] decodePasswordCookie(String cookieVal) {

		// check that the cookie value isn't null or zero-length
		if (cookieVal == null || cookieVal.trim().isEmpty()) {
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
	public static String encodeNameCookie(final String username) {
		final StringBuffer buf = new StringBuffer();
		if (username != null) {
			final byte[] bytes = username.getBytes();
			int b;

			for (int n = 0; n < bytes.length; n++) {
				b = bytes[n] ^ (ENCODE_XORMASK + n);
				buf.append((char) (ENCODE_CHAR_OFFSET1 + (b & 0x0F)));
				buf.append((char) (ENCODE_CHAR_OFFSET2 + ((b >> 4) & 0x0F)));
			}
		}
		return buf.toString();
	}

	/**
	 * Unrafels a cookie string containing a username and password.
	 * 
	 * @param value
	 *            The cookie value.
	 * @return String[] containing the username at index 0 and the password at
	 *         index 1, or <code>{ null, null }</code> if cookieVal equals
	 *         <code>null</code> or the empty string.
	 */
	public static String decodeNameCookie(String cookieVal) {

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

		return cookieVal;
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

	public static String retrieveUser(final String email) {
		final String[] parts = email.split("@");
		String user = null;
		if (parts != null && parts.length > 0) {
			user = parts[0];
		}
		if (user == null || user.trim().length() == 0) {
			user = email;
		}
		return user;
	}

	public static String encodeWithBase64(final String value,
			final String encoding) {
		String subject = value;
		try {
			subject = BASE64Encoder.encode(value.getBytes(encoding));
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return String.format("=?GB2312?B?%s?=", subject);
	}

	public static String digestWidthMD5(final String value) {
		final byte[] btKey = new byte[value.getBytes().length + 1];
		System.arraycopy(value.getBytes(), 0, btKey, 0, value.getBytes().length);
		final MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (final NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
		md.update(btKey);
		final byte[] btDigest = md.digest();
		return new String(btDigest);
	}

//	public static void main(final String[] args) {
//		final File file = new File("c:/test.txt");
//		final File frameFile = new File("c:/frame.png");
//		DateTime date = new DateTime();
//		date = date.withYear(2011);
//		date = date.withMonthOfYear(7);
//		date = date.withDayOfMonth(27);
//		date = date.withHourOfDay(1);
//		final long time = date.toDate().getTime();
//		System.out.println(time);
//		System.out.println(file.lastModified());
//		System.out.println(file.lastModified() > time);
//		System.out.println(frameFile.lastModified() > time);
//	}

	public static boolean isEmptyFile(final File file) {
		return file == null || !file.exists() || file.length() < 100;
	}

	public static Object getFirstResult(final List results) {
		if (results == null || results.isEmpty()) {
			return null;
		} else {
			return results.get(0);
		}
	}

	public static int getCount(final List results) {
		if (results == null || results.isEmpty()) {
			return 0;
		} else {
			final Object object = results.get(0);
			if (object == null) {
				return 0;
			}
			return ((Long) object).intValue();
		}
	}

}
