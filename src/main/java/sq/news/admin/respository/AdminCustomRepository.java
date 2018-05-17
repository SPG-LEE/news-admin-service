package sq.news.admin.respository;

import java.util.List;

import sq.news.admin.entity.Admin;

/**
 * Created by jim on 2017/6/28.
 */
public interface AdminCustomRepository {
	List<Admin> findAllAdmins(String pageIndex, String pageSize, String orderByField, String pageNoLimit,
                              String search);

}
