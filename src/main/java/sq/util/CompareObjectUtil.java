package sq.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONArray;

import sq.bean.LogEntityPrimaryKey;
import io.swagger.annotations.ApiModelProperty;

public class CompareObjectUtil {
	private static String primarykey = "id";

	public static <T> String compareObject(T t2, T t1) throws Exception {
		// System.out.println("jsonMethod+++:" + JSON.toJSONString(t1) +
		// "jsonMethod++++" + JSON.toJSONString(t2));
		// System.out.println("start---id" + achieveFieldValue(t1, primarykey));
		String result = "";
		// 浦东数据字段比较
		List<Field> fieldList1 = achieveAllFields(t1);
		List<Field> fieldList2 = achieveAllFields(t2);
		Boolean isSameType = compareType(fieldList1, fieldList2);
		if (!isSameType) {
			return "数据类型不统一，无法比较";
		}
		List<Field> basicFieldList = new ArrayList<>();
		List<Field> complexFieldList = new ArrayList<>();
		List<Field> objectFieldList = new ArrayList<>();
		for (Field field : fieldList1) {
			if (ofBasicType(field.getType())) {
				basicFieldList.add(field);
			} else if (field.getType().equals(List.class) || field.getType().equals(Set.class)) {
				complexFieldList.add(field);
			} else {
				objectFieldList.add(field);
			}
		}
		// 基础类型比较
		for (Field field : basicFieldList) {
			String apiModelProperty = achieveSwiggerName(field);
			try {

				String basicComapreResult = compareBasicField(achieveFieldValue(t1, field.getName(),field),
						achieveFieldValue(t2, field.getName(),field), apiModelProperty);
				if (!FormatUtil.isNullOrEmpty(basicComapreResult)) {
					result += basicComapreResult;
				}
				continue;
			} catch (Exception e) {
				continue;
			}
		}
		// 复杂对象类型
		for (Field field : complexFieldList) {
			String apiModelProperty = achieveSwiggerName(field);
			try {
				String listResult = compareJSONArray(achieveFieldValue(t1, field.getName(),null),
						achieveFieldValue(t2, field.getName(),null), apiModelProperty);
				if (!FormatUtil.isNullOrEmpty(listResult)) {
					result += listResult;
				}
				continue;
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

		}
		// 比较基础对象
		for (Field field : objectFieldList) {
			String apiModelProperty = achieveSwiggerName(field);
			try {
				String compareObjectResult = compareBasicObject(achieveFieldValue(t1, field.getName(),null),
						achieveFieldValue(t2, field.getName(),null), apiModelProperty);
				if (!FormatUtil.isNullOrEmpty(compareObjectResult)) {
					result += compareObjectResult;
				}
				continue;
			} catch (Exception e) {
				continue;
			}
		}
		return result;

	}

	private static String compareBasicObject(Object objectNew, Object objectOld, String swiggerName) {
		if (objectNew == null && objectOld != null) {
			return "【" + swiggerName + "】由" + achieveFieldValue(objectOld, primarykey,null) + ",修改为" + null + "。";
		}
		if (objectNew != null && objectOld == null) {
			return "【" + swiggerName + "】由" + null + ",修改为" + achieveFieldValue(objectNew, primarykey,null) + "。";
		}
		if (objectNew == null && objectOld == null) {
			return "";
		}
		if (!FormatUtil.isNullOrEmpty(objectNew.toString()) && !FormatUtil.isNullOrEmpty(objectOld.toString())) {
			return "【" + swiggerName + "】由" + achieveFieldValue(objectOld, primarykey,null) + ",修改为"
					+ achieveFieldValue(objectNew, primarykey,null) + "。";
		}
		return "";

	}

	private static String achieveSwiggerName(Field field) {
		Annotation annotation = field.getAnnotation(ApiModelProperty.class);
		ApiModelProperty apiModelProperty = null;
		if (annotation != null) {
			apiModelProperty = (ApiModelProperty) annotation;
			return apiModelProperty.value();
		}
		return "";
	}

	private static <T> Method achieveFieldMethod(T t1, String fieldName) throws IntrospectionException {
		Method method;
		Class calss = t1.getClass();
		PropertyDescriptor pd = new PropertyDescriptor(fieldName, calss);
		method = pd.getReadMethod();
		if (method == null) {
			pd = new PropertyDescriptor(fieldName, calss.getSuperclass());
			method = pd.getReadMethod();
		}
		return method;
	}

	private static <T> List<Field> achieveAllFields(T t1) {
		List<Field> fieldList1 = new ArrayList<>();
		Class tempClass = t1.getClass();
		while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
			fieldList1.addAll(Arrays.asList(tempClass.getDeclaredFields()));
			tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
		}
		return fieldList1;
	}

	private static String compareBasicField(Object valueNew, Object valueOld, String apiModelProperty) {
		if (!FormatUtil.isNullOrEmpty(apiModelProperty)) {
			if (valueNew == null && valueOld != null) {
				return "【" + apiModelProperty + "】由" + null + ",修改为" + valueOld.toString() + "。";
			}
			if (valueNew != null && valueOld == null) {
				return "【" + apiModelProperty + "】由" + valueNew.toString() + ",修改为" + null + "。";

			}
			if (valueNew == null && valueOld == null) {
				return "";
			}
			if (!valueNew.toString().equals(valueOld.toString())) {
				if (!FormatUtil.isNullOrEmpty(valueNew.toString()) && !FormatUtil.isNullOrEmpty(valueOld.toString())) {
					if (apiModelProperty == null) {
						return "";
					}
					return "【" + apiModelProperty + "】由" + valueNew.toString() + ",修改为" + valueOld.toString() + "。";
				}

			}
		}

		return "";
	}

	private static Object achieveFieldValue(Object object, String fileName,Field field
	) {

		Method method = null;
		try {
			method = achieveFieldMethod(object, fileName);
			Object valueNew = method.invoke(object);
			if (field.getType().isEnum()) {
				try{
					Method methodEnum = achieveFieldMethod(valueNew, "name");
					valueNew = methodEnum.invoke(valueNew);
				}
				catch (Exception e){

				}

			}

			return valueNew;
		} catch (Exception e) {
			// e.printStackTrace();
			return "";
		}

	}

	private static String compareJSONArray(Object objectNew, Object objectOld, String apiModelProperty) {
		String myselfAnnion = "";
		String myselfField = "";
		if (FormatUtil.isNullOrEmpty(apiModelProperty)) {
			return "";
		}
		JSONArray jsonArrayNew = null;
		JSONArray jsonArrayOld = null;
		String message = "";
		if (objectNew != null || objectOld != null) {
			if (objectNew != null) {
				for (Object object : (Collection<Object>) objectNew) {
					List<Field> fieldList1 = achieveAllFields(object);
					for (Field field : fieldList1) {
						String hasMyselfAnnion = achieveMyselfAnnion(field);
						if (!FormatUtil.isNullOrEmpty(hasMyselfAnnion)) {
							myselfAnnion = hasMyselfAnnion;
							myselfField = field.getName();
						}
					}
				}
			}
			if (objectOld != null) {
				for (Object object : (Collection<Object>) objectOld) {
					List<Field> fieldList1 = achieveAllFields(object);
					for (Field field : fieldList1) {
						String hasMyselfAnnion = achieveMyselfAnnion(field);
						if (!FormatUtil.isNullOrEmpty(hasMyselfAnnion)) {
							myselfAnnion = hasMyselfAnnion;
							myselfField = field.getName();
						}
					}
				}
			}

		}
		if (objectNew == null && objectOld == null) {
			return "";
		}

		if (objectNew == null && objectOld != null) {
			jsonArrayOld = JSONArray.parseArray(JsonUtil.toJson(objectOld));
			int size = jsonArrayOld.size();
			if (!FormatUtil.isNullOrEmpty(myselfAnnion)) {
				message += "删除的" + "【" + apiModelProperty + "】" + myselfAnnion + "为";

				for (Object object : jsonArrayOld) {
					size--;
					if (size == 0) {
						message += achieveFieldValue(object, myselfField,null);

					} else {
						message += achieveFieldValue(object, myselfField,null) + "、";
					}

				}
			} else {
				message += "删除的" + "【" + apiModelProperty + "】" + primarykey + "为";
				for (Object object : jsonArrayOld) {
					size--;
					if (size == 0) {
						message += achieveFieldValue(object, primarykey,null);

					} else {
						message += achieveFieldValue(object, primarykey,null) + "、";

					}
				}
			}

			message += "。";
			return message;
		}
		if (objectNew != null && objectOld == null) {
			jsonArrayNew = JSONArray.parseArray(JsonUtil.toJson(objectNew));
			int size = jsonArrayNew.size();
			if (!FormatUtil.isNullOrEmpty(myselfAnnion)) {
				message += "新增的" + "【" + apiModelProperty + "】" + myselfAnnion + "为";
				for (Object object : jsonArrayNew) {
					size--;
					if (size == 0) {
						message += achieveFieldValue(object, myselfField,null);

					} else {
						message += achieveFieldValue(object, myselfField,null) + "、";

					}
				}
			} else {
				message += "新增的" + "【" + apiModelProperty + "】" + primarykey + "为";
				for (Object object : jsonArrayNew) {
					size--;
					if (size == 0) {
						message += achieveFieldValue(object, primarykey,null);

					} else {
						message += achieveFieldValue(object, primarykey,null) + "、";

					}
				}
			}
			message += "。";
			return message;
		}

		Map<String, Object> oldDataMap = new HashMap<String, Object>();
		for (Object object : (Collection<Object>) objectNew) {

			oldDataMap.put(achieveFieldValue(object, primarykey,null).toString(), object);
		}
		Map<String, Object> newDataMap = new HashMap<>();
		for (Object object : (Collection<Object>) objectOld) {
			newDataMap.put(achieveFieldValue(object, primarykey,null).toString(), object);
		}
		Set<String> middleDateMap = new HashSet<>(newDataMap.keySet());
		middleDateMap.retainAll(oldDataMap.keySet());
		Set<Object> dataChanged = newDataMap.entrySet().stream()
				.filter(filter -> middleDateMap.contains(filter.getKey())).map(Map.Entry::getValue)
				.collect(Collectors.toSet());
		if (dataChanged != null && !dataChanged.isEmpty()) {
			for (Object object : dataChanged) {
				try {
					String id = achieveFieldValue(object, primarykey,null).toString();
					Object objectOldDate = oldDataMap.get(id);
					String compareChange = compareObject(object, objectOldDate);
					if (!FormatUtil.isNullOrEmpty(compareChange)) {
						if (!FormatUtil.isNullOrEmpty(myselfAnnion)) {
							message += "【" + apiModelProperty + "】" + myselfAnnion + "为"
									+ achieveFieldValue(object, myselfField,null) + compareChange;
						} else {
							message += "【" + apiModelProperty + "】" + primarykey + "为"
									+ achieveFieldValue(object, primarykey,null) + compareChange;
						}

					}
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
		}

		Set<Object> colorAdd = newDataMap.entrySet().stream().filter(filter -> !middleDateMap.contains(filter.getKey()))
				.map(Map.Entry::getValue).collect(Collectors.toSet());
		if (colorAdd != null && !colorAdd.isEmpty()) {
			int size = colorAdd.size();
			if (!FormatUtil.isNullOrEmpty(myselfAnnion)) {
				message += "新增的" + "【" + apiModelProperty + "】" + myselfAnnion + "为";

				for (Object object : colorAdd) {
					size--;
					if (size == 0) {
						message += achieveFieldValue(object, myselfField,null);

					} else {
						message += achieveFieldValue(object, myselfField,null) + "、";

					}

				}
			} else {
				message += "新增的" + "【" + apiModelProperty + "】" + primarykey + "为";
				for (Object object : colorAdd) {
					size--;
					if (size == 0) {
						message += achieveFieldValue(object, primarykey,null);

					} else {
						message += achieveFieldValue(object, primarykey,null) + "、";

					}

				}
			}

			message += "。";
		}
		Set<Object> colorDelete = oldDataMap.entrySet().stream()
				.filter(filter -> !middleDateMap.contains(filter.getKey())).map(Map.Entry::getValue)
				.collect(Collectors.toSet());
		if (colorDelete != null) {
			int size = colorDelete.size();
			if (colorDelete != null && !colorDelete.isEmpty()) {
				if (!FormatUtil.isNullOrEmpty(myselfAnnion)) {
					message += "删除的" + "【" + apiModelProperty + "】" + myselfAnnion + "为";
					for (Object object : colorDelete) {
						size--;
						if (size == 0) {
							message += achieveFieldValue(object, myselfField,null);

						} else {
							message += achieveFieldValue(object, myselfField,null) + "、";

						}
					}
				} else {
					message += "删除的" + "【" + apiModelProperty + "】" + primarykey + "为";
					for (Object object : colorDelete) {
						size--;
						if (size == 0) {
							message += achieveFieldValue(object, primarykey,null);

						} else {
							message += achieveFieldValue(object, primarykey,null) + "、";

						}
					}
				}

				message += "。";
			}
		}
		return message;
	}

	private static boolean ofBasicType(Class<?> class1) {
		if (class1.equals(Integer.class)) {
			return true;
		}
		if (class1.equals(Long.class)) {
			return true;
		}
		if (class1.equals(Float.class)) {
			return true;
		}
		if (class1.equals(Double.class)) {
			return true;
		}
		if (class1.equals(Boolean.class)) {
			return true;
		}
		if (class1.equals(Byte.class)) {
			return true;
		}
		if (class1.equals(Short.class)) {
			return true;
		}
		if (class1.equals(Character.class)) {
			return true;
		}
		if (class1.isEnum()) {
			return true;
		}
		if (class1.equals(String.class)) {
			return true;
		}
		if (class1.equals(Date.class)) {
			return true;
		}
		if (class1.equals(Boolean.class)) {
			return true;
		}
		if (class1.equals(double.class)) {
			return true;
		}
		if (class1.equals(float.class)) {
			return true;
		}
		if (class1.equals(boolean.class)) {
			return true;
		}
		if (class1.equals(int.class)) {
			return true;
		}
		if (class1.equals(long.class)) {
			return true;
		}
		return false;
	}

	private static boolean ofListOrSet(Object object) {
		if (object instanceof Set) {
			return true;
		}
		if (object instanceof List) {
			return true;
		}
		return false;
	}

	private static boolean compareType(List<Field> field1s, List<Field> field2s) {
		boolean result = true;
		for (Field field : field1s) {
			boolean b = false;
			for (Field field2 : field2s) {
				if (field.getName().equals(field2.getName())) {
					b = true;
				}
			}
			if (b == false) {
				result = false;
			}

		}
		return result;

	}

	private static String achieveMyselfAnnion(Field field) {
		Annotation annotation = field.getAnnotation(LogEntityPrimaryKey.class);
		LogEntityPrimaryKey apiModelProperty = null;
		if (annotation != null) {
			apiModelProperty = (LogEntityPrimaryKey) annotation;
			return apiModelProperty.name();
		}
		return "";
	}
//	public static void main(String[] args) throws  Exception {
//
//		Method method = achieveFieldMethod(BankName.BCA, "wwww");
//		System.out.println(method.toString());
//		Object valueNew = method.invoke(BankName.BCA);
//		System.out.println(valueNew.toString());
//
//	}

}
