package com.afiona.center.user.domain.repo;

import com.afiona.center.user.domain.model.User;

import java.util.List;

/**
 * 用户repo
 *
 * @author dengweiyi
 * @date 2020-03-09
 */
public interface UserRepository {
    /**
     * 查询（根据用户名）
     *
     * @param name
     * @return : com.afiona.center.user.domain.model.User
     */
    User getByName(String name);

    /**
     * 列表查询
     *
     * @param userIds
     * @return : java.util.List<com.afiona.center.user.domain.model.User>
     */
    List<User> listByIds(List<Long> userIds);

    /**
     * 新增单个用户
     *
     * @param user 用户信息
     * @return 是否成功
     */
    Boolean insertOne(User user);
}
