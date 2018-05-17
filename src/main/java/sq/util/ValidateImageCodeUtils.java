package sq.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

public class ValidateImageCodeUtils {
	/**
	 * 楠岃瘉鐮侀毦搴︾骇鍒�Simple-鏁板瓧 Medium-鏁板瓧鍜屽皬鍐欏瓧姣�Hard-鏁板瓧鍜屽ぇ灏忓啓瀛楁瘝
	 */
	public enum SecurityCodeLevel {
		Simple, Medium, Hard
	};

	/**
	 * 浜х敓榛樿楠岃瘉鐮侊紝4浣嶄腑绛夐毦搴�
	 *
	 * @return
	 */
	public static String getSecurityCode() {
		return getSecurityCode(4, SecurityCodeLevel.Hard, false);
	}

	/**
	 * 浜х敓闀垮害鍜岄毦搴︿换鎰忕殑楠岃瘉鐮�
	 *
	 * @param length
	 * @param level
	 * @param isCanRepeat
	 * @return
	 */
	public static String getSecurityCode(int length, SecurityCodeLevel level, boolean isCanRepeat) {
		// 闅忔満鎶藉彇len涓瓧绗�
		int len = length;
		// 瀛楃闆嗗悎锛�-闄ゅ幓鏄撴贩娣嗙殑鏁板瓧0,1,瀛楁瘝l,o,O锛�
		char[] codes = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
				'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
				'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
				'Z' };
		// 鏍规嵁涓嶅悓闅惧害鎴彇瀛楃涓�
		if (level == SecurityCodeLevel.Simple) {
			codes = Arrays.copyOfRange(codes, 0, 10);
		} else if (level == SecurityCodeLevel.Medium) {
			codes = Arrays.copyOfRange(codes, 0, 36);
		}
		// 瀛楃闆嗗拰闀垮害
		int n = codes.length;
		// 鎶涘嚭杩愯鏃跺紓甯�
		if (len > n && isCanRepeat == false) {
			throw new RuntimeException(String.format(
					"调用SecurityCode.getSecurityCode(%1$s,%2$s,%3$s)出现异常，" + "当isCanRepeat为%3$s时，传入参数%1$s不能大于%4$s", len,
					level, isCanRepeat, n));
		}
		// 瀛樻斁鎶藉彇鍑烘潵鐨勫瓧绗�
		char[] result = new char[len];
		// 鍒ゆ柇鑳藉惁鍑虹幇閲嶅瀛楃
		if (isCanRepeat) {
			for (int i = 0; i < result.length; i++) {
				// 绱㈠紩0 and n-1
				int r = (int) (Math.random() * n);
				// 灏唕esult涓殑绗琲涓厓绱犺缃负code[r]瀛樻斁鐨勬暟鍊�
				result[i] = codes[r];
			}
		} else {
			for (int i = 0; i < result.length; i++) {
				// 绱㈠紩0 and n-1
				int r = (int) (Math.random() * n);
				// 灏唕esult涓殑绗琲涓厓绱犺缃负code[r]瀛樻斁鐨勬暟鍊�
				result[i] = codes[r];
				// 蹇呴』纭繚涓嶄細鍐嶆鎶藉彇鍒伴偅涓瓧绗︼紝杩欓噷鐢ㄦ暟缁勪腑鏈�悗涓�釜瀛楃鏀瑰啓code[r],骞跺皢n-1
				codes[r] = codes[n - 1];
				n--;
			}
		}
		return String.valueOf(result);
	}

	/**
	 * 鐢熸垚楠岃瘉鐮佸浘鐗�
	 * 
	 * @param securityCode
	 * 
	 * @return
	 * 
	 */
	public static BufferedImage createImage(String securityCode) {

		int codeLength = securityCode.length();// 楠岃瘉鐮侀暱搴�

		int fontSize = 18;// 瀛椾綋澶у皬

		int fontWidth = fontSize + 1;

		// 鍥剧墖瀹介珮

		int width = codeLength * fontWidth + 6;
		int height = fontSize * 2 + 1;
		// 鍥剧墖

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = image.createGraphics();

		g.setColor(Color.WHITE);// 璁剧疆鑳屾櫙鑹�

		g.fillRect(0, 0, width, height);// 濉厖鑳屾櫙

		// g.setColor(Color.LIGHT_GRAY);// 璁剧疆杈规棰滆壊
		g.setColor(Color.GREEN);

		g.setFont(new Font("Arial", Font.BOLD, height - 2));// 杈规瀛椾綋鏍峰紡

		g.drawRect(0, 0, width - 1, height - 1);// 缁樺埗杈规

		// 缁樺埗鍣偣

		Random rand = new Random();

		g.setColor(Color.LIGHT_GRAY);

		for (int i = 0; i < codeLength * 6; i++) {

			int x = rand.nextInt(width);

			int y = rand.nextInt(height);

			g.drawRect(x, y, 1, 1);// 缁樺埗1*1澶у皬鐨勭煩褰�

		}

		// 缁樺埗楠岃瘉鐮�

		int codeY = height - 10;

		// g.setColor(new Color(19, 148, 246));
		g.setColor(new Color(0, 195, 69));

		g.setFont(new Font("Georgia", Font.BOLD, fontSize));
		for (int i = 0; i < codeLength; i++) {
			double deg = new Random().nextDouble() * 20;
			g.rotate(Math.toRadians(deg), i * 16 + 13, codeY - 7.5);
			g.drawString(String.valueOf(securityCode.charAt(i)), i * 16 + 5, codeY);
			g.rotate(Math.toRadians(-deg), i * 16 + 13, codeY - 7.5);
		}

		g.dispose();// 鍏抽棴璧勬簮

		return image;

	}

	public static ByteArrayInputStream getImageAsInputStream(String securityCode) {

		BufferedImage image = createImage(securityCode);

		return convertImageToStream(image);

	}

	/**
	 * 
	 * 将BufferedImage转换成ByteArrayinputStream
	 * 
	 * @param image
	 * 
	 * @return
	 * 
	 */

	public static ByteArrayInputStream convertImageToStream(BufferedImage image) {

		ByteArrayInputStream inputStream = null;

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		try {
			ImageOutputStream imOut = ImageIO.createImageOutputStream(outStream);
			imOut = ImageIO.createImageOutputStream(outStream);
			ImageIO.write(image, "png", imOut);
			byte[] b = outStream.toByteArray();
			inputStream = new ByteArrayInputStream(b);

		} catch (Exception e) {

		}

		return inputStream;

	}

}
