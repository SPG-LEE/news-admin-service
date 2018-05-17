package sq.news.admin.service;

import java.util.List;

import sq.news.admin.entity.Admin;

public interface AdminService {
	Admin findById(long id);

	void save(Admin admin);

	Admin merge(Admin admin);

	Admin findByLoginNameAndPassword(String loginName, String password);

	Admin findByLoginName(String loginName);

	List<Admin> findByNameLike(String name, int start, int pageSize);

	boolean hasPermission(String permission);

	boolean hasPermission(Admin admin, String permission);

	List<Admin> findAllAdmins(String pageIndex, String pageSize,
			String orderByField, String pageNoLimit, String search);

	long countByNameLike(String search);

	void delete(Admin findAdmin);

	long allCount();

	void saveAndRalation(Admin admin);

	void updateAndRoleRalation(Admin admin, Admin adminDb);

	boolean isSuperAdmin(Long id);
}
