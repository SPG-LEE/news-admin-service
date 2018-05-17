package sq.news.admin.respository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import sq.news.admin.entity.Admin;
import sq.news.admin.respository.AdminCustomRepository;
import sq.util.FormatUtil;
import sq.util.OrderByFieldUtil;
import sq.util.StringUtil;

@Repository
public class AdminRepositoryImpl implements AdminCustomRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Admin> findAllAdmins(String pageIndex, String pageSize, String orderByField, String pageNoLimit,
                                     String search) {
		String hqlString = "from Admin entity where 1=1 ";
		if (!FormatUtil.isNullOrEmpty(search)) {
			hqlString += " and  (entity.name like:name or entity.loginName like:login)";
		}
		if (!FormatUtil.isNullOrEmpty(orderByField)) {
			hqlString += "  order by entity." + OrderByFieldUtil.getOrder(orderByField);
		}
		Query query = entityManager.createQuery(hqlString);
		if (!FormatUtil.isNullOrEmpty(search)) {
			query.setParameter("name", "%" + search + "%");
			query.setParameter("login", "%" + search + "%");
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
