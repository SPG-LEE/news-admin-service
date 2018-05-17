package sq.news.admin.respository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import sq.news.admin.entity.Permission;
import sq.news.admin.respository.PermissionCustomRepository;
import sq.util.FormatUtil;
import sq.util.OrderByFieldUtil;
import sq.util.StringUtil;

@Repository
public class PermissionRepositoryImpl implements PermissionCustomRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Permission> findAllPermissions(String pageIndex,
			String pageSize, String orderByField, String pageNoLimit,
			String search) {
		String hqlString = "from Permission entity where 1=1 ";
		if (!FormatUtil.isNullOrEmpty(search)) {
			hqlString += " and  (entity.name like:name or entity.permission like:permissions)";
		}
		if (!FormatUtil.isNullOrEmpty(orderByField)) {
			hqlString += "order by entity."
					+ OrderByFieldUtil.getOrder(orderByField);
		}
		Query query = entityManager.createQuery(hqlString);
		if (!FormatUtil.isNullOrEmpty(search)) {
			query.setParameter("name", "%" + search + "%");
			query.setParameter("permissions", "%" + search + "%");
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
