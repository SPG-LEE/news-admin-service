package sq.news.admin.service.hibernate;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sq.enumeration.EntityStatus;
import sq.news.admin.entity.Permission;
import sq.news.admin.entity.Role;
import sq.news.admin.respository.PermissionRepository;
import sq.news.admin.respository.RoleRepository;
import sq.news.admin.service.PermissionService;

@Service
public class PermissionHibernateService implements PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Permission save(Permission permission) {
		return permissionRepository.saveAndFlush(permission);
	}

	@Override
	public Permission merge(Permission permission) {
		return permissionRepository.saveAndFlush(permission);
	}

	@Override
	@Transactional
	public void delete(Permission permission) {
		for (Role role : roleRepository.findByPermissionId(permission.getId())) {
			role.getPermissions().remove(permission);
			roleRepository.save(role);
		}
		permissionRepository.delete(permission);
	}

	@Override
	public List<Permission> findByRoleId(Long id) {
		return permissionRepository.findByRoleId(id);
	}

	@Override
	public List<Permission> findAllPermissions(String pageIndex, String pageSize, String orderByField,
			String pageNoLimit, String search) {
		return permissionRepository.findAllPermissions(pageIndex, pageSize, orderByField, pageNoLimit, search);
	}

	@Override
	public long countByNameLikeOrPermissionLikeAndEntityStatus(String search) {
		return permissionRepository.countByNameLikeOrPermissionLikeAndEntityStatus("%" + search + "%",
				"%" + search + "%", EntityStatus.ENABLE);
	}

	public Permission findByPermission(String permissionName) {
		return permissionRepository.findByPermission(permissionName);
	}

	@Override
	public Permission findByName(String name) {
		return permissionRepository.findByName(name);
	}

	@Override
	public Permission findById(long id) {
		return permissionRepository.findById(id);
	}

	@Override
	public long allCount() {
		return permissionRepository.count();
	}
}
