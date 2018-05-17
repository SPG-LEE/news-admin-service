package sq.util;

import java.math.BigDecimal;

public class MathUtil {

	/**
	 * 提供精确加法计算的add方法
	 * 
	 * @param value1
	 *            被加数
	 * @param value2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(final double value1, final double value2) {
		final BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
		final BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确减法运算的sub方法
	 * 
	 * @param value1
	 *            被减数
	 * @param value2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(final double value1, final double value2) {
		final BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
		final BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确乘法运算的mul方法
	 * 
	 * @param value1
	 *            被乘数
	 * @param value2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(final double value1, final double value2) {
		final BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
		final BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供精确的除法运算方法div
	 * 
	 * @param value1
	 *            被除数
	 * @param value2
	 *            除数
	 * @param scale
	 *            精确范围
	 * @return 两个参数的商
	 * @throws IllegalAccessException
	 */
	public static double div(final double value1, final double value2, final int scale) throws IllegalAccessException {
		// 如果精确范围小于0，抛出异常信息
		if (scale < 0) {
			throw new IllegalAccessException("精确度不能小于0");
		}
		final BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
		final BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
		return b1.divide(b2, scale).doubleValue();
	}
	/**
	 * @param x 区间min
	 * @param y 区间max
	 * */
	public static double getRandomDouble(int x, int y) {
		return x + Math.random() * y % (y - x + 1);
	}
}
