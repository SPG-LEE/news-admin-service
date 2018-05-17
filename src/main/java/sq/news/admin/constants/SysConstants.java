package sq.news.admin.constants;

import org.joda.time.DateTime;

import java.util.Date;

public class SysConstants {

	public static final String SUPERADMIN = "admin";
	public static final String[] FILE_EXTENSION = new String[] { "TIFF", "tif",
			"TIF", "tiff", "jpg", "JPG", "JPEG", "jpeg" };
	public static final String FILE_SEPERATOR = "/";
	public static final Object PICTURE_EXT_NAME = ".jpg";
	public static final String DIR_PATH = "res/";
	public static final String SMALL_EXT = "_s";
	public static final String MIDDLE_EXT = "_m";
	public static final String BIG_EXT = "_b";
	public static final String LOGO_IMG_EXT = "_logo";
	public static final String MAIN_IMG_EXT = "_main";
	public static final String SUB_IMG_EXT = "_sub";
	public static final int BIG_WIDTH = 640;
	public static final int DETAIL_WIDTH = 600;
	public static final int MIDDLE_WIDTH = 320;
	public static final int SMALL_WIDTH = 240;
	public static final int ZERO_WIDTH = 0;

	public static final String COOKIE_REMEMBER_NAME = "login.rememberName";
	public static final String COOKIE_REMEMBER_LOGIN = "login.rememberLogin";

	public static final String ID_PROPERTY = "id";
	public static final String NAME_PROPERTY = "name";
	public static final String STATUS_UNKOWN = "未知";
	public static final String STATUS_DELETE = "删除";
	public static final String STATUS_DISABLE = "停用";
	public static final String STATUS_ENALBE = "启用";
	public static final String SORT_NAME = "名称";
	public static final String SORT_DEFAULT = "默认";
	public static final String FILTER_ALL = "所有";
	public static final String FILTER_DISABLE = "停用";
	public static final String FILTER_ENABLE = "启用";
	public static final String DELETE_SUCCESS = "delete_success";
	public static final String DELETE_MULTI_SUCCESS = "delete_multi_success";
	public static final String ENABLE_MULTI_SUCCESS = "enable_multi_success";
	public static final String DISABLE_MULTI_SUCCESS = "disable_multi_success";
	public static final String COOKIE_REMEMBER_USER = "login.rememberUser";
	public static final int SKU_UPLOADIMAGE_DIALOGPAGING_SIZE = 10;
	public static final int PRODUCT_UPLOADIMAGE_DIALOGPAGING_SIZE = 12;
	public static final int ITEM_IMAGE_MINSIZE = 40;
	public static final int PRODUCT_IMAGE_MINSIZE = 122;
	public static final long SECOND = 1000;

	public static final long MINUTE = 60 * SECOND;

	public static final long HOUR = 60 * MINUTE;

	public static final long DAY = 24 * HOUR;

	public static final long WEEK = 7 * DAY;

	public static final int MAX_COOKIE_AGE = (int) (WEEK / 1000) * 8;

	public static final String TOKEN_KEY = "Sq888";

	public static final int MAX_USER_COOKIE_AGE = (int) (WEEK / 1000) * 365;

	public static final String QUERY_SUCCESS = "查询成功";
	public static final String QUERY_FAIL = "查询失败！";
	public static final String QUERY_EMPTY = "查询数据为空！";
	public static final String OPERATION_SUCCESS = "操作成功";
	public static final String OPERATION_FAIL = "操作失败！";
	public static final String ILLEGAL_REQUEST = "非法请求!";
	public static final String HAVEN_PUBLISH = "已认领!";
	public static final String UNDEFINED = "undefined";
	public static final String PARAM_ISNOTNULL = "参数不能为空";
	public static final String PARAM_UNLEGAL = "参数不合法！";
	public static final Date START_DATE = new DateTime().withYear(2015)
			.withMonthOfYear(12).toDate();

	public static final String URL_1688_PRODUCT = "https://detail.1688.com";
	public static final String URL_AMAZONE_PRODUCT = "https://www.amazon.com";
	public static final String URL_ALIEXPRESSS_PRODUCT = "https://www.aliexpress.com";
	public static final String URL_LEKENI_PRODUCT = "http://www.pfhoo.com";
	public static final String URL_AMAZON_API_CN = "https://developer.amazonservices.com.cn/";
	public static final String URL_AMAZON_API_US = "https://developer.amazonservices.com/";
	public static final String URL_AMAZON_API_GE = "https://developer.amazonservices.de/";
	public static final String URL_AMAZON_API_FR = "https://developer.amazonservices.fr/";
	public static final String URL_AMAZON_API_ES = "https://developer.amazonservices.es/";
	public static final String URL_AMAZON_API_CA = "https://developer.amazonservices.ca/";
	public static final String URL_AMAZON_API_IN = "https://developer.amazonservices.in/";
	public static final String URL_AMAZON_API_IT = "https://developer.amazonservices.it/";
	public static final String URL_AMAZON_API_UK = "https://developer.amazonservices.co.uk/";
	public static final String URL_AMAZON_API_JP = "https://developer.amazonservices.jp/";
	public static final String LKN_INTERFACEMIMA = "4346be563c694c76916afc257a53ee3c";
	public static final String PUBLIC_TOKEN_ID = "Sq888";
	public static final String TOKEN_DEADLINE = "600";
	public static final String ADMIN_TOKEN_DEADLINE = "30";
	public static final String USER_TOKEN_DEADLINE = "86400";
	public static final String USER_CODE_DEADLINE = "6000";
	public static final String USER_RESETTOKEN_DEADLINE = "30";
	public static final String PASSWORD_ZHENGZE = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z#$@_!]{6,20}$";
	public static final int CARTITEMS_MAX_COUNT = 100;
	public static final String NO_UPLOAD_IMAGES = "没有更新上传产品图片,请上传！";
	public static final int CATALOG_RECOMMAND_NUMBER = 4;


	public  static final String REDIS_HASHKEY_SHOPLIST = "PlatformShop";
}
