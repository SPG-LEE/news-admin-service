package sq.news.admin.respository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import sq.news.admin.entity.Role;
import sq.news.admin.respository.RoleCustomRepository;
import sq.util.FormatUtil;
import sq.util.OrderByFieldUtil;
import sq.util.StringUtil;

@Repository
public class RoleRepositoryImpl implements RoleCustomRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Role> findAllRolesNotAdmin(String pageIndex, String pageSize,
			String orderByField, String pageNoLimit, String search) {
		String hqlString = "from Role entity where entity.roleName is not 'admin'";
		if (!FormatUtil.isNullOrEmpty(search)) {
			hqlString += " and  (entity.name like:name)";
		}
		if (!FormatUtil.isNullOrEmpty(orderByField)) {
			hqlString += " and entity.entityStatus='ENABLE' order by entity."
					+ OrderByFieldUtil.getOrder(orderByField);

		}
		Query query = entityManager.createQuery(hqlString);
		if (!FormatUtil.isNullOrEmpty(search)) {
			query.setParameter("name", "%" + search + "%");
		}
		if (!FormatUtil.isNullOrEmpty(pageNoLimit)) {
			if (StringUtil.isTrueOrFalse(pageNoLimit)) {
				if (pageNoLimit.equals("false")) {
					int index = Integer.parseInt(pageIndex);
					int size = Integer.parseInt(pageSize);
					int start = index * size;
					query.setFirstResult(start);
					if (size > 0) {
						query.setMaxResults(size);
					}
				}
			}
		}
		return query.getResultList();
	}
}
