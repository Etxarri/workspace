package edu.mondragon.webengl.domain.user.repository;


import edu.mondragon.webengl.domain.user.model.RecienLLegado;
import edu.mondragon.webengl.domain.user.model.User;
import edu.mondragon.webengl.domain.user.model.Voluntario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositorySQL implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositorySQL.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    private RowMapper<User> userRowMapper = (rs, rowNum) -> {
        String tipo = rs.getString("tipo");
        User user;
        if ("voluntario".equalsIgnoreCase(tipo)) {
            Voluntario vol = new Voluntario();
            vol.setComunidad_autonoma(rs.getString("comunidad_autonoma"));
            user = vol;
        } else if ("recienllegado".equalsIgnoreCase(tipo)) {
            RecienLLegado rec = new RecienLLegado();
            rec.setCiudad(rs.getString("ciudad"));
            rec.setPais(rs.getString("pais"));
            rec.setLang(rs.getString("idioma_principal") != null ? Locale.forLanguageTag(rs.getString("idioma_principal")) : null);
            user = rec;
        } else {
            user = new User();
        }
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
    String sql = "INSERT INTO user(username,password,first_name,second_name,email,tipo) VALUES(?,?,?,?,?,?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getFirstName());
        ps.setString(4, user.getSecondName());
        ps.setString(5, user.getEmail());
        ps.setString(6, user.getTipo());
        return ps;
    }, keyHolder);

    Number key = keyHolder.getKey();
    if (key != null) {
        user.setUserId(key.intValue());
        System.out.println("User ID: " + user.getUserId());
        logger.info("User ID: " + user.getUserId());

        if ("voluntario".equalsIgnoreCase(user.getTipo())) {
            String sql2 = "INSERT INTO voluntario(userId,comunidad_autonoma) VALUES(?,?)";
            jdbcTemplate.update(sql2, user.getUserId(), ((Voluntario) user).getComunidad_autonoma());
        } else if ("recienllegado".equalsIgnoreCase(user.getTipo())) {
            String sql3 = "INSERT INTO recienllegado(userId,ciudad,pais,idioma_principal) VALUES(?,?,?,?)";
            jdbcTemplate.update(sql3,
                    user.getUserId(),
                    ((RecienLLegado) user).getCiudad(),
                    ((RecienLLegado) user).getPais(),
                    ((RecienLLegado) user).getLang());
        }

        return user;
    } else {
        System.out.println("No se ha podido obtener el ID del usuario");
        return null;
    }
}


    @Override
    public User loadUser(String username, String password) {
        String sql = "SELECT u.*, v.comunidad_autonoma, r.ciudad, r.pais, r.idioma_principal " +
                     "FROM user u " +
                     "LEFT JOIN voluntario v ON u.userId = v.userId " +
                     "LEFT JOIN recienllegado r ON u.userId = r.userId " +
                     "WHERE u.username=? AND u.password=?";
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, username, password);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User loadUser(int userId) {
        String sql = "SELECT u.*, v.comunidad_autonoma, r.ciudad, r.pais, r.idioma_principal " +
                     "FROM user u " +
                     "LEFT JOIN voluntario v ON u.userId = v.userId " +
                     "LEFT JOIN recienllegado r ON u.userId = r.userId " +
                     "WHERE u.userId=?";
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<User> loadUsers() {
        String sql = "SELECT u.*, v.comunidad_autonoma, r.ciudad, r.pais, r.idioma_principal " +
                     "FROM user u " +
                     "LEFT JOIN voluntario v ON u.userId = v.userId " +
                     "LEFT JOIN recienllegado r ON u.userId = r.userId";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    public User updateUser(User user) {
        // Construir SQL dinámicamente según los datos proporcionados
        StringBuilder sqlBuilder = new StringBuilder("UPDATE user SET ");
        Map<String, Object> params = new HashMap<>();

        if (user.getFirstName() != null) {
                        System.out.println("Cambio first name");
            sqlBuilder.append("first_name=:first_name, ");
            params.put("first_name", user.getFirstName());
        }
        if (user.getUsername() != null) {
                        System.out.println("Cambio username");
            sqlBuilder.append("username=:username, ");
            params.put("username", user.getUsername());
        }
        if (user.getEmail() != null) {
            System.out.println("Cambio email");
            sqlBuilder.append("email=:email, ");
            params.put("email", user.getEmail());
        }
        if (user.getPassword() != null) {
            System.out.println("Cambio password");
            sqlBuilder.append("password=:password, ");
            params.put("password", user.getPassword());
        }

        // Remover la última coma y espacio
        sqlBuilder.setLength(sqlBuilder.length() - 2);

        sqlBuilder.append(" WHERE userId=:userId");
        params.put("userId", user.getUserId());
                    System.out.println(user.getUserId());

        String sql = sqlBuilder.toString();

        int rows = namedParameterJdbcTemplate.update(sql, params);

        return rows > 0 ? user : null;
    }

    @Override
    public boolean deleteUser(int userId) {
        // Eliminar primero de las tablas hijas para evitar errores de clave foránea
        String sql1 = "DELETE FROM voluntario WHERE userId=?";
        String sql2 = "DELETE FROM recienllegado WHERE userId=?";
        jdbcTemplate.update(sql1, userId);
        jdbcTemplate.update(sql2, userId);

        String sql = "DELETE FROM user WHERE userId=?";
        return jdbcTemplate.update(sql, userId) > 0;
    }


    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        
        try {
            User user = jdbcTemplate.queryForObject(
                sql,
                new Object[]{email},
                new UserRowMapper()  // Mapeador personalizado
            );
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();  // Si no se encuentra el usuario
        }
    }
        // Clase interna para mapear el resultado de la consulta a un objeto User
    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("userId"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            // Agrega más campos según tu tabla
            return user;
        }
    }
}

