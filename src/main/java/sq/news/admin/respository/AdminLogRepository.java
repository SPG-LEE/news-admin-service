package sq.news.admin.respository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import sq.news.admin.entity.AdminLog;

public interface AdminLogRepository extends JpaRepository<AdminLog, Long>, AdminLogCustomRepository {

	long countByLinkId(String linkId);

	List<AdminLog> findByLinkId(String linkId, Pageable pageable);
}
