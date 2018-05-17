package sq.util;

import sq.base.ServiceResult;

public class ServiceResultBuilder {

	public static <T> ServiceResult<T> buildSuccessResult(String code) {
		return buildSuccessResult(null, code, 1);
	}

	public static <T> ServiceResult<T> buildSuccessResult(T t, String code) {
		return buildSuccessResult(t, code, 1);
	}

	public static <T> ServiceResult<T> buildSuccessResult(T t, String code, long count) {
		ServiceResult<T> result = new ServiceResult<T>();
		result.setTotal(count);
		result.setCode(code);
		result.setData(t);
		result.setSuccess(true);
		return result;
	}

	public static <T> ServiceResult<T> buildFailedResult(T t, String code) {
		return buildFailedResult(t, code, 0);
	}

	public static <T> ServiceResult<T> buildFailedResult(String code) {
		return buildFailedResult(null, code, 0);
	}

	public static <T> ServiceResult<T> buildFailedResult(String code, int count) {
		return buildFailedResult(null, code, count);
	}

	public static <T> ServiceResult<T> buildFailedResult(T t, String code, int count) {
		ServiceResult<T> result = new ServiceResult<T>();
		result.setTotal(count);
		result.setCode(code);
		result.setData(t);
		return result;
	}

	public static <T> ServiceResult<T> buildSuccessMessageResult(String message) {
		return buildSuccessMessageResult(null, message, 1);
	}

	public static <T> ServiceResult<T> buildSuccessMessageResult(T t, String message) {
		return buildSuccessMessageResult(t, message, 1);
	}

	public static <T> ServiceResult<T> buildSuccessMessageResult(T t, String message, long count) {
		ServiceResult<T> result = new ServiceResult<T>();
		result.setTotal(count);
		result.setMessage(message);
		result.setData(t);
		result.setSuccess(true);
		return result;
	}

	public static <T> ServiceResult<T> buildFailedMessageResult(String message) {
		return buildFailedMessageResult(null, message, 0);
	}

	public static <T> ServiceResult<T> buildFailedMessageResult(T t, String message) {
		return buildFailedMessageResult(t, message, 0);
	}

	public static <T> ServiceResult<T> buildFailedMessageResult(String message, int count) {
		return buildFailedMessageResult(null, message, count);
	}

	public static <T> ServiceResult<T> buildFailedMessageResult(T t, String message, int count) {
		ServiceResult<T> result = new ServiceResult<T>();
		result.setTotal(count);
		result.setMessage(message);
		result.setData(t);
		return result;
	}

}
