package sq.util;

import sq.base.AppListResult;

public class AppListResultBuilder {

	public static <T> AppListResult<T> buildSuccessResult(T t, String code,
			boolean hasNext) {
		return buildSuccessResult(t, code, 1, hasNext);
	}

	public static <T> AppListResult<T> buildSuccessResult(String code) {
		return buildSuccessResult(null, code, 1, false);
	}

	public static <T> AppListResult<T> buildSuccessResult(T t, String code) {
		return buildSuccessResult(t, code, 1, false);
	}

	public static <T> AppListResult<T> buildSuccessResult(T t, String code,
			long count, boolean hasNext) {
		AppListResult<T> result = new AppListResult<T>();
		result.setTotal(count);
		result.setCode(code);
		result.setData(t);
		result.setSuccess(true);
		result.setHasNext(hasNext);
		return result;
	}

	public static <T> AppListResult<T> buildFailedResult(T t, String code) {
		return buildFailedResult(t, code, 0);
	}

	public static <T> AppListResult<T> buildFailedResult(String code) {
		return buildFailedResult(null, code, 0);
	}

	public static <T> AppListResult<T> buildFailedResult(String code, int count) {
		return buildFailedResult(null, code, count);
	}

	public static <T> AppListResult<T> buildFailedResult(T t, String code,
			int count) {
		AppListResult<T> result = new AppListResult<T>();
		result.setTotal(count);
		result.setCode(code);
		result.setData(t);
		return result;
	}

	public static <T> AppListResult<T> buildSuccessMessageResult(String code) {
		return buildSuccessMessageResult(null, code, 1, false);
	}

	public static <T> AppListResult<T> buildSuccessMessageResult(T t,
			String code) {
		return buildSuccessMessageResult(t, code, 1, false);
	}

	public static <T> AppListResult<T> buildSuccessMessageResult(T t,
			String code, boolean hasNext) {
		return buildSuccessMessageResult(t, code, 1, hasNext);
	}

	public static <T> AppListResult<T> buildSuccessMessageResult(T t,
			String message, long count, boolean hasNext) {
		AppListResult<T> result = new AppListResult<T>();
		result.setTotal(count);
		result.setMessage(message);
		result.setData(t);
		result.setSuccess(true);
		return result;
	}

	public static <T> AppListResult<T> buildFailedMessageResult(T t, String code) {
		return buildFailedMessageResult(t, code, 0);
	}

	public static <T> AppListResult<T> buildFailedMessageResult(String code) {
		return buildFailedMessageResult(null, code, 0);
	}

	public static <T> AppListResult<T> buildFailedMessageResult(String code,
			int count) {
		return buildFailedMessageResult(null, code, count);
	}

	public static <T> AppListResult<T> buildFailedMessageResult(T t,
			String message, int count) {
		AppListResult<T> result = new AppListResult<T>();
		result.setTotal(count);
		result.setMessage(message);
		result.setData(t);
		return result;
	}
}
