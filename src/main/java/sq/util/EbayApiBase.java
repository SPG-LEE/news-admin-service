package sq.util;



import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EbayApiBase {


    private static class SingletonHolder{
        private static EbayApiBase instance=new EbayApiBase();
    }

    private EbayApiBase(){

    }

    public static EbayApiBase getInstance(){

        return SingletonHolder.instance;
    }
    /**
     *

     * @param soapRequestData
     *            请求参数
     * @param apiName
     *            接口名
     * @return
     * @throws Exception
     */
    public String outinputEbay(String urls, String soapRequestData, String apiName) throws Exception {

        InputStream input = null;
        OutputStream out = null;
        HttpURLConnection connection = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urls);

            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            connection.setRequestProperty("SOAPAction", "https://api.apacshipping.ebay.com/" + apiName);

            out = connection.getOutputStream();
            out.write(soapRequestData.getBytes("utf-8"));

            input = connection.getInputStream();
            byte[] b = new byte[1024];
            int len;

            while ((len = input.read(b)) != -1) {
                String str = new String(b, 0, len, "utf-8");
                sb.append(str);
            }

        } catch (Exception e) {

            throw e;
        } finally {
            if (input != null) {
                input.close();
            }
            if (out != null) {
                out.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return sb.toString();

    }

}