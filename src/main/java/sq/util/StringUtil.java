package sq.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

import sq.enumeration.ProductStatus;

public class StringUtil {
	public static boolean isPositiveInteger(final String orginal) {// 判断是否为正整数
		return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
	}

	public static boolean isPositiveInDecimal(final String orginal) {// 判断是否为数值
		return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*|\\+{0,1}[0-9]\\d*", orginal);
	}

	public static boolean isPlus(final String orginal) {// 判断是否为数值
		final String reg = "(-)(.*)";
		System.out.println("A:" + reg);
		final Pattern pattern = Pattern.compile(reg);
		final Matcher matcher = pattern.matcher(orginal);
		if (matcher.find()) {
			System.out.println("a2:" + matcher.group(2));
			final String str0 = matcher.group(1);
			System.out.println("a:" + str0);
			return true;
		} else {
			return false;
		}

	}

	public static boolean isMinus(final String orginal) {// 判断是否为数值
		final String reg = "(/+)(.*)";
		System.out.println("B:" + reg);
		final Pattern pattern = Pattern.compile(reg);
		final Matcher matcher = pattern.matcher(orginal);
		if (matcher.find()) {
			System.out.println("b1:" + matcher.group(2));
			final String str0 = matcher.group(1);
			System.out.println("b2:" + str0);
			return true;
		} else {
			return false;
		}
	}

	private static boolean isMatch(final String regex, final String orginal) {
		if (orginal == null || orginal.trim().equals("")) {
			return false;
		}
		final Pattern pattern = Pattern.compile(regex);
		final Matcher isNum = pattern.matcher(orginal);
		return isNum.matches();
	}

	public static Boolean isTrueOrFalse(final String param) {
		if (param.equals("true") || param.equals("false")) {
			return true;
		} else {
			return false;
		}
	}

	// public static void main(final String[] args) {
	// System.out.println(isPositiveInDecimal(""));
	// }
	//
	public static String subStringImageExtFromUrlString(String urlString) {
		if (FormatUtil.isNullOrEmpty(urlString)) {
			return "";
		}
		final int index = urlString.indexOf("?");
		if (index > 0) {
			urlString = urlString.substring(0, index);
		}
		final String ext = FilenameUtils.getExtension(urlString);
		final String formatExt = FormatUtil.isNullOrEmpty(ext) ? "" : ("." + ext);
		return formatExt;
	}

	public static void writeErroeMessage(final String message, final Exception e) {
		PrintStream stream = null;
		try {
			final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			final String logPath = "/user/lknDownImagelog/" + sf.format(new Date()) + ".log";
			final File file = new File(logPath);
			if (!file.exists()) {
				getFile(logPath);

			}
			// 创建文件的输出流
			stream = new PrintStream(new FileOutputStream(file, true));
			stream.append(new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss").format(new Date()) + "\r\n");
			stream.append(message + "\r\n");
			if (e != null) {
				e.printStackTrace(stream);
				stream.append("\r\n");
			}
		} catch (final FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (final Exception e2) {

		} finally {
			stream.flush();
			stream.close();
		}

	}

	private static File createFile(final String filePath) {
		final File file = new File(filePath);
		try {
			org.apache.commons.io.FileUtils.forceMkdir(file.getParentFile());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	public static File getFile(final String filePath) {
		final File file = createFile(filePath);
		return file;
	}

	public static boolean isEmail(String email) {
		// 正则表达式
		/*
		 * String regex = "^[A-Za-z]{1,40}@[A-Za-z0-9]{1,40}\\.[A-Za-z]{2,3}$";
		 * return email.matches(regex);
		 */

		// 不适用正则
		if (email == null || "".equals(email)) {
			return false;
		}

		if (!containsOneWord('@', email) || !email.contains(".")) {
			return false;
		}
		if (email.indexOf("@") + 1 == email.length()) {
			return false;
		}
		if (email.indexOf(".") + 1 == email.length()) {
			return false;
		}
		/*
		 * String prefix = email.substring(0, email.indexOf("@")); String middle
		 * = email.substring(email.indexOf("@") + 1, email.indexOf(".")); String
		 * subfix = email.substring(email.indexOf(".") + 1);
		 * System.out.println("prefix=" + prefix + "  middle=" + middle +
		 * "  subfix=" + subfix);
		 * 
		 * if (prefix == null || prefix.length() > 40 || prefix.length() == 0) {
		 * return false; }
		 * 
		 * 
		 * if (!isAllWords(prefix)) { return false; }
		 * 
		 * 
		 * if (middle == null || middle.length() > 40 || middle.length() == 0) {
		 * return false; } if (!isAllWordsAndNo(middle)) { return false; }
		 * 
		 * if (subfix == null || subfix.length() > 3 || subfix.length() < 2) {
		 * return false; }
		 * 
		 * if (!isAllWords(subfix)) { return false; }
		 */

		return true;
	}

	public static boolean checkParams(String pageIndex, String pageSize, String orderByField, String pageNoLimit) {

		try {
			if (!StringUtil.isTrueOrFalse(pageNoLimit)) {
				return false;
			}
			if (orderByField != null) {
				String subString = orderByField.substring(0, 1);
				if (subString.equals("-") || subString.equals("+")) {
				} else {
					return false;
				}
			}
			if (!Boolean.parseBoolean(pageNoLimit)) {
				if (!StringUtil.isPositiveInteger(pageIndex) || !StringUtil.isPositiveInteger(pageSize)) {
					if (!("0").equals(pageIndex)) {
						return false;
					}
				} else {
					if (orderByField != null) {
						if (orderByField.substring(0, 1).equals("-") || orderByField.substring(0, 1).equals("+")) {
						} else {
							return false;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean checkParams(String pageIndex, String pageSize, String orderByField, String pageNoLimit,
			String type, String catalogId) {
		try {
			if (!StringUtil.isTrueOrFalse(pageNoLimit)
					|| (!FormatUtil.isNullOrEmpty(catalogId) && !StringUtil.isPositiveInteger(catalogId))
					|| (!FormatUtil.isNullOrEmpty(type) && ProductStatus.valueOf(type) == null)) {
				return false;
			}
			if (orderByField != null) {
				String subString = orderByField.substring(0, 1);
				if (subString.equals("-") || subString.equals("+")) {
				} else {
					return false;
				}
			}
			if (!Boolean.parseBoolean(pageNoLimit)) {
				System.out.println(StringUtil.isPositiveInteger(pageIndex));
				if (!StringUtil.isPositiveInteger(pageIndex) || !StringUtil.isPositiveInteger(pageSize)) {
					if (!("0").equals(pageIndex)) {
						return false;
					}
				} else {
					if (orderByField != null) {
						if (orderByField.substring(0, 1).equals("-") || orderByField.substring(0, 1).equals("+")) {
						} else {
							return false;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 判断字符串只包含指定的一个字符c
	private static boolean containsOneWord(char c, String word) {
		char[] array = word.toCharArray();
		int count = 0;
		for (Character ch : array) {
			if (c == ch) {
				count++;
			}
		}
		return count == 1;
	}

	// 检查一个字符串是否全部是字母
	private static boolean isAllWords(String prefix) {
		char[] array = prefix.toCharArray();
		for (Character ch : array) {
			if (ch < 'A' || ch > 'z' || (ch < 'a' && ch > 'Z'))
				return false;
		}
		return true;
	}

	// 检查一个字符串是否包含字母和数字
	private static boolean isAllWordsAndNo(String middle) {
		char[] array = middle.toCharArray();
		for (Character ch : array) {
			if (ch < '0' || ch > 'z')
				return false;
			else if (ch > '9' && ch < 'A')
				return false;
			else if (ch > 'Z' && ch < 'a')
				return false;
		}
		return true;
	}

	public static boolean isLegalParam(String param) {// 只能包含 数字 字母 "." "_"
														// 且长度为5到20字符
		String regex = "^[A-Za-z0-9._]{5,20}+$";// ^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$

		if (FormatUtil.isNullOrEmpty(param)) {
			return false;
		}
		return param.matches(regex);
	}

	public static boolean compareFields(String stringNew, String stringOld) {
		if (FormatUtil.isEmpty(stringNew) && !FormatUtil.isNullOrEmpty(stringOld)) {
			return true;
		}
		if (!FormatUtil.isEmpty(stringNew) && FormatUtil.isNullOrEmpty(stringOld)) {
			return true;
		}
		if (!FormatUtil.isEmpty(stringNew) && !FormatUtil.isNullOrEmpty(stringOld) && !stringNew.equals(stringOld)) {
			return true;
		}
		return false;
	}
}
