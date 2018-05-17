package sq.news.admin.service.hibernate;

import sq.news.admin.constants.SysConstants;
import sq.news.admin.entity.Permission;
import sq.news.admin.entity.Role;
import sq.news.admin.respository.AdminRepository;
import sq.news.admin.respository.PermissionRepository;
import sq.news.admin.service.AdminService;
import sq.enumeration.EntityStatus;
import sq.news.admin.entity.Admin;
import sq.news.admin.respository.RoleRepository;
import sq.util.FormatUtil;
import sq.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminHibernateService implements AdminService {
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public Admin findById(long id) {
		if (adminRepository.findById(id).isPresent()) {
			return adminRepository.findById(id).get();
		}
		return null;
	}

	@Override
	@Transactional
	public void save(Admin admin) {
		admin.setUpdateDate(new Date());
		adminRepository.saveAndFlush(admin);
	}

	@Override
	@Transactional
	public Admin merge(Admin admin) {
		admin.setUpdateDate(new Date());
		return adminRepository.saveAndFlush(admin);
	}

	@Override
	@Transactional
	public void delete(Admin admin) {
		adminRepository.delete(admin);
	}

	@Override
	public Admin findByLoginNameAndPassword(String loginName, String password) {
		return adminRepository.findByLoginNameAndPassword(loginName, password);
	}

	@Override
	public Admin findByLoginName(String loginName) {
		if (adminRepository.findByLoginName(loginName) != null
				&& adminRepository.findByLoginName(loginName).size() > 0) {
			return adminRepository.findByLoginName(loginName).get(0);
		}
		return null;

	}

	@Override
	public List<Admin> findByNameLike(String name, int start, int pageSize) {
		return adminRepository.findByNameLikeAndEntityStatus(name, EntityStatus.ENABLE,
				new PageRequest(start, pageSize));
	}

	@Override
	public boolean hasPermission(String permission) {
		return adminRepository.hasPermission(permission);
	}

	@Override
	public boolean hasPermission(Admin admin, String permission) {
		if (admin.getRoles().stream().filter(filter -> filter.getRoleName().equals(
				SysConstants.SUPERADMIN)).count() > 0) {
			return true;
		}
		return adminRepository.hasPermission(admin.getId(), permission);
	}

	@Override
	public List<Admin> findAllAdmins(String pageIndex, String pageSize, String orderByField, String pageNoLimit,
			String search) {
		return adminRepository.findAllAdmins(pageIndex, pageSize, orderByField, pageNoLimit, search);
	}

	@Override
	public long countByNameLike(String search) {
		return adminRepository.countByNameLikeOrLoginNameLike("%" + search + "%", "%" + search + "%");
	}

	@Override
	public long allCount() {
		return adminRepository.count();
	}

	@Transactional
	@Override
	public void saveAndRalation(Admin admin) {
		Set<Role> roles = new HashSet<>();
		for (Role role : admin.getRoles()) {
			if (roleRepository.findById(role.getId()).isPresent()) {
				roles.add(roleRepository.findById(role.getId()).get());
			}

		}
		Admin adminDb = new Admin();
		adminRepository.save(adminDb);
		adminDb.setLoginName(admin.getLoginName());
		adminDb.setDescription(admin.getDescription());
		adminDb.setHeadImage(admin.getHeadImage());
		adminDb.setName(admin.getName());
		if (admin.getPassword() != null) {
			adminDb.setPassword(Md5Util.MD5(admin.getPassword().trim()));
		}

		adminDb.setRoles(roles);
		adminRepository.saveAndFlush(adminDb);

	}

	@Transactional
	@Override
	public void updateAndRoleRalation(Admin admin, Admin adminDb) {
		Set<Role> roles = new HashSet<>();
		Set<Permission> permissions = new HashSet<>();
		for (Role role : admin.getRoles()) {
			if (roleRepository.findById(role.getId()).isPresent()) {
				roles.add(roleRepository.findById(role.getId()).get());
			}
		}
		for (Permission permission : admin.getPermissions()) {
			if (permissionRepository.findById(permission.getId()).isPresent()) {
				permissions.add(permissionRepository.findById(permission.getId()).get());
			}

		}

		adminDb.setLoginName(admin.getLoginName());
		adminDb.setDescription(admin.getDescription());
		adminDb.setHeadImage(admin.getHeadImage());
		adminDb.setName(admin.getName());
		if (admin.getPassword() != null && !adminDb.getPassword().equals(admin.getPassword())
				&& !FormatUtil.isNullOrEmpty(admin.getPassword())) {
			adminDb.setPassword(Md5Util.MD5(admin.getPassword()));
		}
		adminDb.setEntityStatus(admin.getEntityStatus());
		if (roles != null && roles.size() > 0) {
			adminDb.setRoles(roles);
		}
		if (permissions != null && permissions.size() > 0) {
			adminDb.setPermissions(permissions);
		}

		adminRepository.saveAndFlush(adminDb);

	}

	@Override
	public boolean isSuperAdmin(Long id) {
		return adminRepository.isSuperAdmin(id);
	}

}
