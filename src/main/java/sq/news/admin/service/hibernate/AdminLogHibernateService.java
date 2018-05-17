package sq.news.admin.service.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import sq.news.admin.entity.AdminLog;
import sq.news.admin.respository.AdminLogRepository;
import sq.news.admin.service.AdminLogService;

@Service
public class AdminLogHibernateService implements AdminLogService {
	@Autowired
	private AdminLogRepository adminLogRepository;

	@Override
	public long getCountByLinkId(String linkId) {
		return adminLogRepository.countByLinkId(linkId);
	}

	@Override
	public List<AdminLog> findByLinkId(String linkId, int start, int pageSize) {
		return adminLogRepository.findByLinkId(linkId, new PageRequest(start, pageSize));
	}

	@Override
	public List<AdminLog> findAll(int start, int pageSize) {
		return adminLogRepository.findAll(new PageRequest(start, pageSize)).getContent();
	}

	@Override
	public long getAllCount() {
		return adminLogRepository.count();
	}

}