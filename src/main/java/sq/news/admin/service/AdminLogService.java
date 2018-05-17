package sq.news.admin.service;

import java.util.List;

import sq.news.admin.entity.AdminLog;

public interface AdminLogService {

	long getCountByLinkId(String linkId);

	List<AdminLog> findByLinkId(String linkId, int start, int pageSize);

	List<AdminLog> findAll(int i, int pageSize);

	long getAllCount();
}
