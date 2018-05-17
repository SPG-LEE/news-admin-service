package sq.util;

import java.security.Key;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public class DESPlus {

	private static final String strDefaultKey = "xinyi.jim.jiang";
	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 * 
	 * @param arrB
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	public static String byteArr2HexStr(final byte[] arrB) throws Exception {
		if (arrB == null || arrB.length == 0) {
			return "";
		}
		final int iLen = arrB.length;

		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		final StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 * 
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>
	 */
	public static byte[] hexStr2ByteArr(final String strIn) {
		if (strIn == null || strIn.trim().isEmpty()) {
			return null;
		}
		final byte[] arrB = strIn.getBytes();
		final int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		final byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			final String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	private static DESPlus desPlus;

	public static synchronized DESPlus getDefault() {
		if (desPlus == null) {
			desPlus = new DESPlus();
		}
		return desPlus;
	}

	/**
	 * 默认构造方法，使用默认密钥
	 * 
	 * @throws Exception
	 */
	private DESPlus() {
		this(strDefaultKey);
	}

	/**
	 * 指定密钥构造方法
	 * 
	 * @param strKey
	 *            指定的密钥
	 * @throws Exception
	 */
	private DESPlus(final String strKey) {
		try {
			// Security.addProvider(new com.sun.crypto.provider.SunJCE());
			final Key key = getKey(strKey.getBytes());

			this.encryptCipher = Cipher.getInstance("DES");
			this.encryptCipher.init(Cipher.ENCRYPT_MODE, key);

			this.decryptCipher = Cipher.getInstance("DES");
			this.decryptCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 加密字节数组
	 * 
	 * @param arrB
	 *            需加密的字节数组
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	public byte[] encrypt(final byte[] arrB) {
		try {
			return this.encryptCipher.doFinal(arrB);
		} catch (final IllegalBlockSizeException ex) {
		} catch (final BadPaddingException ex) {
		}
		return null;
	}

	/**
	 * 加密字符串
	 * 
	 * @param strIn
	 *            需加密的字符串
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public String encrypt(final String strIn) {
		try {
			return byteArr2HexStr(encrypt(strIn.getBytes()));
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	/**
	 * 解密字节数组
	 * 
	 * @param arrB
	 *            需解密的字节数组
	 * @return 解密后的字节数组
	 * @throws Exception
	 */
	public byte[] decrypt(final byte[] arrB) {
		if (arrB == null) {
			return null;
		}
		try {
			return this.decryptCipher.doFinal(arrB);
		} catch (final IllegalBlockSizeException ex) {
		} catch (final BadPaddingException ex) {
		}
		return null;
	}

	/**
	 * 解密字符串
	 * 
	 * @param strIn
	 *            需解密的字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public String decrypt(final String strIn) {
		final byte[] decrypt = decrypt(hexStr2ByteArr(strIn));
		if (decrypt == null) {
			return null;
		}
		return new String(decrypt);
	}

	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * 
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws Exception
	 */
	private Key getKey(final byte[] arrBTmp) {
		// 创建一个空的8位字节数组（默认值为0）
		final byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		// 生成密钥
		final Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}

	/**
	 * @param args
	 */
//	public static void main(final String[] args) {
//		try {
//			final String test = "qweqwe1";
//			// DESPlus des = new DESPlus();//默认密钥
//			final DESPlus des = new DESPlus();// 自定义密钥
//			System.out.println("加密前的字符：" + test);
//			System.out.println("加密后的字符：" + des.encrypt(test));
//			System.out.println("解密后的字符：" + des.decrypt(des.encrypt(test)));
//		} catch (final Exception e) {
//			e.printStackTrace();
//		}
//	}
}