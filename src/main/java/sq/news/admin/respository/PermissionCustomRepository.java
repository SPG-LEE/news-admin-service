package sq.news.admin.respository;

import java.util.List;

import sq.news.admin.entity.Permission;

/**
 * Created by jim on 2017/6/28.
 */
public interface PermissionCustomRepository {

	List<Permission> findAllPermissions(String pageIndex, String pageSize, String orderByField, String pageNoLimit,
                                        String search);

}
