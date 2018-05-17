package sq.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class SignatureUtil {
    private static String DEFAULT_ENCODING = "UTF-8";
    private static String SIGNATURE_METHOD = "HmacSHA256";
    private static String KEY = "Sq888";

    public static String urlEncode(String rawValue) {
        String value = rawValue == null ? "" : rawValue;
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, DEFAULT_ENCODING)
                    .replace("+", "%20").replace("*", "%2A")
                    .replace("%7E", "~");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
        return encoded;
    }

    public static String sign(Map<String, String> parameters) {
        try {
            return sign(calculateStringToSign(parameters), KEY,
                    SIGNATURE_METHOD);
        } catch (SignatureException e) {
            return null;
        }
    }

    public static String sign(String data, String key, String algorithm)
            throws SignatureException {
        byte[] signature;
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(key.getBytes(), algorithm));
            signature = Base64.encodeBase64(mac.doFinal(data
                    .getBytes(DEFAULT_ENCODING)));
        } catch (Exception e) {
            throw new SignatureException("Failed to generate signature: "
                    + e.getMessage(), e);
        }
        return new String(signature);
    }

    public static String calculateStringToSign(Map<String, String> parameters)
            throws SignatureException {
        StringBuffer data = new StringBuffer();
        Map<String, String> sorted = new TreeMap<String, String>();
        sorted.putAll(parameters);
        Iterator<Map.Entry<String, String>> pairs = sorted.entrySet()
                .iterator();
        while (pairs.hasNext()) {
            Map.Entry<String, String> pair = pairs.next();
            String key = pair.getKey();
            data.append(urlEncode(key));
            data.append("=");
            String value = pair.getValue();
            data.append(urlEncode(value));
            if (pairs.hasNext()) {
                data.append("&");
            }
        }
        return data.toString();
    }
}
