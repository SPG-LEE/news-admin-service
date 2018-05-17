package sq.news.admin.service;

import java.util.List;

import sq.news.admin.entity.Permission;

public interface PermissionService {

	Permission save(Permission permission);

	Permission merge(Permission permission);

	void delete(Permission permission);

	List<Permission> findByRoleId(Long id);

	List<Permission> findAllPermissions(String pageIndex, String pageSize, String orderByField, String pageNoLimit,
			String search);

	long countByNameLikeOrPermissionLikeAndEntityStatus(String search);

	Permission findByPermission(String permissionName);

	Permission findByName(String name);

	Permission findById(long id);

	long allCount();
}
