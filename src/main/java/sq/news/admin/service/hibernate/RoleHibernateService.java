package sq.news.admin.service.hibernate;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sq.news.admin.entity.Role;
import sq.news.admin.respository.RoleRepository;
import sq.news.admin.service.RoleService;

@Service
public class RoleHibernateService implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        role.setUpdateDate(new Date());
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public List<Role> findByAdminId(Long id) {
        return roleRepository.findByAdminId(id);
    }

    @Override
    public List<Role> findByPermissionId(Long id) {
        return roleRepository.findByPermissionId(id);
    }

    @Override
    public List<Role> findAllRolesNotAdmin(String pageIndex, String pageSize, String orderByField, String pageNoLimit,
                                           String search) {

        return roleRepository.findAllRolesNotAdmin(pageIndex, pageSize, orderByField, pageNoLimit, search);
    }

    @Override
    public long countByNameLikeNotAdmin(String search) {
        return roleRepository.countByNameLikeNotAdmin(search);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role findById(long id) {
        if (roleRepository.findById(id).isPresent()){
            return roleRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public void merge(Role role) {
        role.setUpdateDate(new Date());
        roleRepository.saveAndFlush(role);
    }

    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    @Override
    public long allCount() {
        return roleRepository.count();
    }

    @Override
    public List<Role> findAll() {

        return roleRepository.findAll();
    }

}
