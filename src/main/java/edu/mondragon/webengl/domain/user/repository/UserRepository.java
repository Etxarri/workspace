package edu.mondragon.webengl.domain.user.repository;

import java.util.List;
import edu.mondragon.webengl.domain.user.model.User;

public interface UserRepository {
    User insertUser(User user);
    User loadUser(String username, String password);
    User loadUser(int userId);
    List<User> loadUsers();
    User updateUser(User user);
    boolean deleteUser(int userId);
    boolean existsByUsername(String username);

}

