package sq.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import sq.base.BaseEntity;
import sq.base.WithName;

public class AppUtil {

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
			return ((Long) results.get(0)).intValue();
		}
	}

	public static String join(final Collection<? extends WithName> values,
			final String seperator) {
		if (values == null || values.isEmpty()) {
			return "";
		}
		final Iterator<WithName> iter = (Iterator<WithName>) values.iterator();
		final StringBuilder sb = new StringBuilder(iter.next().getName());
		while (iter.hasNext()) {
			sb.append(seperator);
			sb.append(iter.next().getName());
		}
		return sb.toString();
	}

	public static String join(final String[] values, final String seperator) {
		if (values == null || values.length == 0) {
			return "";
		}

		final StringBuilder sb = new StringBuilder();
		for (final String value : values) {
			sb.append(value);
			sb.append(seperator);
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static String join(final Collection<? extends WithName> values,
			final String seperator, final String extra) {
		if (values == null || values.isEmpty()) {
			return "";
		}
		final Iterator<WithName> iter = (Iterator<WithName>) values.iterator();
		final StringBuilder sb = new StringBuilder(iter.next().getName());
		while (iter.hasNext()) {
			sb.append(seperator);
			sb.append(iter.next().getName());
			sb.append(extra);
		}
		return sb.toString();
	}

	public static List<Long> toIdList(
			final Collection<? extends BaseEntity> values) {
		final List<Long> ids = new ArrayList<Long>();
		if (values == null || values.isEmpty()) {
			return ids;
		}
		for (final BaseEntity baseEntity : values) {
			ids.add(baseEntity.getId());
		}
		return ids;
	}

	/** Read the object from Base64 string. */
	private static Object fromString(final String s) throws IOException,
			ClassNotFoundException {
		final byte[] data = DESPlus.getDefault().encrypt(s).getBytes();
		final ObjectInputStream ois = new ObjectInputStream(
				new ByteArrayInputStream(data));
		final Object o = ois.readObject();
		ois.close();
		return o;
	}

	/** Write the object to a Base64 string. */
	private static String toString(final Serializable o) throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(o);
		oos.close();
		return new String(DESPlus.getDefault().decrypt(baos.toByteArray()));
	}

	public static String getUrl(final HttpServletRequest request) {
		final StringBuffer requestURL = request.getRequestURL();
		final String queryString = request.getQueryString();
		if (StringUtils.isNotBlank(queryString)) {
			requestURL.append("?").append(queryString);
		}
		return requestURL.toString();
	}
	// public static String getPinYin(String inputString) {
	// if (inputString == null) {
	// return "";
	// }
	// HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
	// format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	// format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	// format.setVCharType(HanyuPinyinVCharType.WITH_V);
	//
	// char[] input = inputString.trim().toCharArray();
	// StringBuffer output = new StringBuffer("");
	//
	// try {
	// for (int i = 0; i < input.length; i++) {
	// if (input[i] == ElasticSearchConstants.SEPERATOR_CHAR) {
	// output.append(input[i]);
	// continue;
	// }
	// String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i],
	// format);
	// if (temp != null && temp.length >= 1) {
	// output.append(temp[0]);
	// }
	// }
	// } catch (BadHanyuPinyinOutputFormatCombination e) {
	// e.printStackTrace();
	// }
	// return output.toString();
	// }
	//
	// public static void main(String[] args) {
	// System.out.println(getPinYin("测试112dbd"));
	// System.out.println(getRecommenderNumber(23L));
	// System.out.println(getRecommenderId(getRecommenderNumber(2323L)));
	// }
	//
	// public static String getRecommenderNumber(Long id) {
	// return SysConstants.RECOMMENDER_PREFIX
	// + (SysConstants.RECOMMENDER_NUMBER + id.intValue());
	// }
	//
	// public static Long getRecommenderId(String number) {
	//
	// return new Long(Integer.parseInt(number
	// .substring(SysConstants.RECOMMENDER_PREFIX.length()))
	// - (SysConstants.RECOMMENDER_NUMBER));
	// }
}
