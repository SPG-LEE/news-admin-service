package sq.base;

import sq.util.JsonUtil;

/**
 * 接口数据实体类
 * 
 *
 * 
 * @param <T>
 */

public class AppListResult<T> extends AppResult<T> {
	private boolean hasNext;

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	@Override
	public String toJson() {
		return JsonUtil.toJson(this);
	}
}
