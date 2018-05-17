package sq.news.admin.service;

import java.util.List;

import sq.news.admin.entity.Role;

public interface RoleService {

	Role save(Role role);

	Role findByRoleName(String roleName);

	List<Role> findByAdminId(Long id);

	List<Role> findByPermissionId(Long id);

	List<Role> findAllRolesNotAdmin(String pageIndex, String pageSize, String orderByField, String pageNoLimit,
			String search);

	long countByNameLikeNotAdmin(String search);

	Role findByName(String name);

	Role findById(long id);

	void merge(Role roleInDb);

	void delete(Role roleInDb);

	long allCount();

	List<Role> findAll();
}
