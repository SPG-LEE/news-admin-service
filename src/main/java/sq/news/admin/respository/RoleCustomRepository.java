package sq.news.admin.respository;

import java.util.List;

import sq.news.admin.entity.Role;

/**
 * Created by jim on 2017/6/28.
 */
public interface RoleCustomRepository {

	List<Role> findAllRolesNotAdmin(String pageIndex, String pageSize,
                                    String orderByField, String pageNoLimit, String search);
}
