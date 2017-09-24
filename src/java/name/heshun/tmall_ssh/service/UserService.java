package name.heshun.tmall_ssh.service;

import name.heshun.tmall_ssh.pojo.User;

/**
 * Created by Aware on 2017/9/8.
 */
public interface UserService extends BaseService {
    /**
     * 判断用户是否存在(用于注册时, 用户名不能重复)
     *
     * @param name
     * @return
     */
    boolean isExist(String name);

    /**
     * 根据用户名和密码获取用户
     *
     * @param name
     * @param password
     * @return
     */
    User get(String name, String password);
}
