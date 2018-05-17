package sq.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class RequestUtil {
	public static Map<String, String> parseXml(final HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		final Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流

		// 读取输入流

		final String stringFromRequest = getStringFromRequest(request);
		System.out.println("string from request " + stringFromRequest);
		final Document document = DocumentHelper.parseText(stringFromRequest);
		// 得到xml根元素
		final Element root = document.getRootElement();
		// 得到根元素的所有子节点

		final List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (final Element e : elementList) {
			System.out.println(e.getName() + "=" + e.getText());
			map.put(e.getName(), e.getText());
		}

		return map;
	}

	private static String getStringFromRequest(final HttpServletRequest request) {
		InputStream is = null;
		try {
			is = request.getInputStream();

			final InputStreamReader isr = new InputStreamReader(is);
			final BufferedReader br = new BufferedReader(isr);
			String s = "";
			final StringBuffer sb = new StringBuffer();
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			final String str = sb.toString();
			return str;
		} catch (final IOException e) {

			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}
		return null;
	}
}
