package sq.news.admin.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sq.news.admin.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>,
		RoleCustomRepository {

	@Query("select entity from Role entity left join entity.permissions permission where permission.id=:permissionId")
	List<Role> findByPermissionId(@Param("permissionId") Long id);

	Role findByRoleName(String roleName);

	@Query("select entity from Role entity left join entity.admins admin where admin.id=:adminId")
	List<Role> findByAdminId(@Param("adminId") Long id);

	@Query("select count(entity) from Role entity where entity.name like :name and entity.roleName is not 'admin'")
	long countByNameLikeNotAdmin(@Param("name") String search);

	Role findByName(String name);

	long countByName(String search);
}
