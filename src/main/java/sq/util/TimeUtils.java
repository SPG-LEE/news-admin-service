package sq.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {

	public static String transformTime(final String UtcTime) {

		final String newDateStr = string2Timezone("yyyy-MM-dd HH:mm:ss", UtcTime,
				TimeZone.getTimeZone("America/Los_Angeles").getID(), "yyyy-MM-dd HH:mm:ss",
				TimeZone.getTimeZone("China/Shang_Hai").getID());
		return newDateStr;
	}

	private static int getDiffTimeZoneRawOffset(final String fromTimeZoneId, final String toTimeZoneId) {
		return TimeZone.getTimeZone(fromTimeZoneId).getRawOffset() - TimeZone.getTimeZone(toTimeZoneId).getRawOffset();
	}

	private static String string2Timezone(final String srcFormater, final String srcDateTime,
			final String fromTimeZoneId, final String dstFormater, final String toTimeZoneId) {
		if (srcFormater == null || "".equals(srcFormater))
			return null;
		if (srcDateTime == null || "".equals(srcDateTime))
			return null;
		if (dstFormater == null || "".equals(dstFormater))
			return null;
		if (toTimeZoneId == null || "".equals(toTimeZoneId))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(srcFormater);
		try {
			final int diffTime = getDiffTimeZoneRawOffset(fromTimeZoneId, toTimeZoneId);
			Date d = sdf.parse(srcDateTime);
			final long nowTime = d.getTime();
			final long newNowTime = nowTime - diffTime;
			d = new Date(newNowTime);
			return date2String(dstFormater, d);
		} catch (final ParseException e) {
			e.printStackTrace();
			return null;
		} finally {
			sdf = null;
		}
	}

	private static String date2String(final String formater, final Date aDate) {
		if (formater == null || "".equals(formater))
			return null;
		if (aDate == null)
			return null;
		return (new SimpleDateFormat(formater)).format(aDate);
	}
}
