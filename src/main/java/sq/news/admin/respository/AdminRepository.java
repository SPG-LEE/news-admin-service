package sq.news.admin.respository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sq.enumeration.EntityStatus;
import sq.news.admin.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>,
		AdminCustomRepository {
	Admin findByLoginNameAndPassword(String loginName, String password);

	List<Admin> findByLoginName(String loginName);

	List<Admin> findByNameLikeAndEntityStatus(String name,
			EntityStatus entityStatus, Pageable pageable);

	long countByNameLikeOrLoginNameLike(String name, String loginName);

	@Query("select case when count(distinct entity.id)>0 then true else false end from Admin entity left join entity.roles role where role.id in (select permissionRole.id from Role permissionRole left join permissionRole.permissions permissionInRole where permissionInRole.permission=:permission)")
	boolean hasPermission(@Param("permission") String permission);

	@Query("select case when count(distinct entity.id)>0 then true else false end from Admin entity left join entity.roles role where entity.id=:adminId and role.id in (select permissionRole.id from Role permissionRole left join permissionRole.permissions permissionInRole where permissionInRole.permission=:permission)")
	boolean hasPermission(@Param("adminId") long adminId,
			@Param("permission") String permission);

	long countByName(String name);

	@Query("select case when count(distinct entity.id)>0 then true else false end from Admin entity left join entity.roles role where role.roleName='admin' and entity.id=:id")
	boolean isSuperAdmin(@Param("id") long id);
}
