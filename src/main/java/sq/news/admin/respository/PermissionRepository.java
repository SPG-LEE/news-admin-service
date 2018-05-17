package sq.news.admin.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sq.enumeration.EntityStatus;
import sq.news.admin.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long>, PermissionCustomRepository {

	Permission findById(long id);

	@Query("select entity from Permission entity left join entity.roles role where role.id=:roleId ")
	List<Permission> findByRoleId(@Param("roleId") Long id);

	Permission findByPermission(String permission);

	Permission findByName(String name);

	long countByNameLikeOrPermissionLikeAndEntityStatus(String name, String permission, EntityStatus entityStatus);

	long countByName(String name);
}
