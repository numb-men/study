package gordon.study.cache.springcache.multicache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import gordon.study.cache.ehcache3.basic.UserModel;

@Repository
public class MultiCacheUserRepository {

    private static final Map<String, UserModel> users = new ConcurrentHashMap<>();

    static {
        for (int i = 1; i <= 7; i++) {
            users.put("id" + i, new UserModel("id" + i, "phone" + i, "email" + i, "openid" + i, "info" + i));
        }
        users.put("id" + 8, new UserModel("id" + 8, "phone" + 8, null, "openid" + 8, "info" + 8));
        users.put("id" + 9, new UserModel("id" + 9, null, "email" + 9, "openid" + 9, "info" + 9));
    }

    public UserModel findUser(String id) {
        System.out.println("query user in database by id: " + id);
        return users.get(id);
    }

    public UserModel findUserByPhone(String phone) {
        System.out.println("query user in database by phone: " + phone);
        for (UserModel user : users.values()) {
            if (phone.equals(user.getPhone())) {
                return user;
            }
        }
        return null;
    }

    public UserModel findUserByEmail(String email) {
        System.out.println("query user in database by email: " + email);
        for (UserModel user : users.values()) {
            if (email.equals(user.getEmail())) {
                return user;
            }
        }
        return null;
    }

    public UserModel updateUser(String id, String info) {
        System.out.println("update user in database by id: " + id);
        UserModel user = users.get(id);
        if (user == null) {
            return null;
        }
        UserModel newUser = new UserModel(user.getId(), user.getPhone(), user.getEmail(), user.getOpenid(), info);
        users.put(id, newUser);
        return newUser;
    }

    public UserModel deleteUser(String id) {
        System.out.println("delete user in database by id: " + id);
        return users.remove(id);
    }

    public boolean changePhone(String id, String newPhone) {
        System.out.println("change user phone in database by id: " + id);
        UserModel user = users.get(id);
        if (user != null) {
            UserModel newUser = new UserModel(user.getId(), newPhone, user.getEmail(), user.getOpenid(), user.getInfo());
            users.put(id, newUser);
            return true;
        }
        return false;
    }

}
