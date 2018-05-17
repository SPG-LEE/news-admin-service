package sq.util;

public class EncodeUtils {
	public static String encode(final String value) {
		return DESPlus.getDefault().encrypt(value);
	}

	public static String decode(final String value) {
		return DESPlus.getDefault().decrypt(value);
	}
}
