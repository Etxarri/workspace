package edu.mondragon.webengl.domain.user.service;

import java.util.List;

import edu.mondragon.webengl.domain.user.model.User;
import edu.mondragon.webengl.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User login(String username, String password){
        return repository.loadUser(username, password);
    }

    public User loadUser(String username, String password) {
        return repository.loadUser(username, password);
    }

    public User loadUser(int userId) {
        return repository.loadUser(userId);
    }

    public List<User> loadUsers() {
        return repository.loadUsers();
    }

    public User saveUser(User user) {
        if (user.getUserId() == 0) {
            return repository.insertUser(user);
        } else {
            return repository.updateUser(user);
        }
    }

    public boolean deleteUser(int userId) {
        return repository.deleteUser(userId);
    }
}
