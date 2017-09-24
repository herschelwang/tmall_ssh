package name.heshun.tmall_ssh.service.impl;

import name.heshun.tmall_ssh.pojo.User;
import name.heshun.tmall_ssh.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Aware on 2017/9/8.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {
    @Override
    public boolean isExist(String name) {
        List list = list("name", name);
        if (!list.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public User get(String name, String password) {
        List<User> list = list("name", name, "password", password);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
