package edu.mondragon.webengl.domain.user.repository;


import edu.mondragon.webengl.domain.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class UserRepositorySQL implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositorySQL.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setUserId(rs.getInt("userId"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setSecondName(rs.getString("second_name"));
        user.setEmail(rs.getString("email"));
        return user;
    };

    @Override
    public User insertUser(User user) {
        String sql = "INSERT INTO user(username,password,first_name,second_name,email) VALUES(?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getSecondName());
            ps.setString(5, user.getEmail());
            return ps;
        }, keyHolder);
        
        //jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getFirstName(), user.getSecondName(), user.getEmail());
        Number key = keyHolder.getKey();
        if (key != null) {
            user.setUserId(key.intValue());
            System.out.println("User ID: " + user.getUserId());
            logger.info("User ID: " + user.getUserId());
            return user;
        } else {
            System.out.println("No se ha podido obtener el ID del usuario");
            return null;
        }
    }

    @Override
    public User loadUser(String username, String password) {
        String sql = "SELECT * FROM user WHERE username=? AND password=?";
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, username, password);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User loadUser(int userId) {
        String sql = "SELECT * FROM user WHERE userId=?";
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<User> loadUsers() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public User updateUser(User user) {
        String sql = "UPDATE user SET username=?, password=?, first_name=?, second_name=?, email=? WHERE userId=?";
        int rows = jdbcTemplate.update(sql,
                user.getUsername(), user.getPassword(),
                user.getFirstName(), user.getSecondName(),
                user.getEmail(), user.getUserId());

        return rows > 0 ? user : null;
    }

    @Override
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM user WHERE userId=?";
        return jdbcTemplate.update(sql, userId) > 0;
    }
}

