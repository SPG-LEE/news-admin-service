package sq.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {
	private static final double EARTH_RADIUS = 6378.137;

	private static double rad(final double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 根据两个位置的经纬度，来计算两地的距离（单位为KM） 参数为String类型
	 * 
	 * @param lat1
	 *            用户经度
	 * @param lng1
	 *            用户纬度
	 * @param lat2
	 *            商家经度
	 * @param lng2
	 *            商家纬度
	 * @return
	 */
	public static String getDistance(final String lat1Str, final String lng1Str, final String lat2Str,
			final String lng2Str) {
		try {
			final Double lat1 = Double.parseDouble(lat1Str);
			final Double lng1 = Double.parseDouble(lng1Str);
			final Double lat2 = Double.parseDouble(lat2Str);
			final Double lng2 = Double.parseDouble(lng2Str);

			final double radLat1 = rad(lat1);
			final double radLat2 = rad(lat2);
			final double difference = radLat1 - radLat2;
			final double mdifference = rad(lng1) - rad(lng2);
			double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
					+ Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(mdifference / 2), 2)));
			distance = distance * EARTH_RADIUS;
			distance = Math.round(distance * 10000) / 10000;
			String distanceStr = distance + "";
			distanceStr = distanceStr.substring(0, distanceStr.indexOf("."));
			return distanceStr;
		} catch (final Exception e) {
			e.printStackTrace();
			return "未知";
		}

	}

	/**
	 * 获取当前用户一定距离以内的经纬度值 单位米 return minLat 最小经度 minLng 最小纬度 maxLat 最大经度 maxLng
	 * 最大纬度 minLat
	 */
	public static Map getAround(final String latStr, final String lngStr, final String raidus) {
		final Map map = new HashMap();

		final Double latitude = Double.parseDouble(latStr);// 传值给经度
		final Double longitude = Double.parseDouble(lngStr);// 传值给纬度

		final Double degree = (24901 * 1609) / 360.0; // 获取每度
		final double raidusMile = Double.parseDouble(raidus);

		final Double mpdLng = Double.parseDouble((degree * Math.cos(latitude * (Math.PI / 180)) + "").replace("-", ""));
		final Double dpmLng = 1 / mpdLng;
		final Double radiusLng = dpmLng * raidusMile;
		// 获取最小经度
		final Double minLat = longitude - radiusLng;
		// 获取最大经度
		final Double maxLat = longitude + radiusLng;

		final Double dpmLat = 1 / degree;
		final Double radiusLat = dpmLat * raidusMile;
		// 获取最小纬度
		final Double minLng = latitude - radiusLat;
		// 获取最大纬度
		final Double maxLng = latitude + radiusLat;

		map.put("minLat", minLat + "");
		map.put("maxLat", maxLat + "");
		map.put("minLng", minLng + "");
		map.put("maxLng", maxLng + "");

		return map;
	}

//	public static void main(final String[] args) {
//		// 济南国际会展中心经纬度：117.11811 36.68484
//		// 趵突泉：117.00999000000002 36.66123
//		System.out.println(getDistance("117.11811", "36.68484", "117.00999000000002", "36.66123"));
//
//		System.out.println(getAround("117.11811", "36.68484", "13000"));
//		// 117.01028712333508(Double), 117.22593287666493(Double),
//		// 36.44829619896034(Double), 36.92138380103966(Double)
//
//	}

}
