package by.javaguru.je.jdbc.dao;

import by.javaguru.je.jdbc.entity.Gender;
import by.javaguru.je.jdbc.entity.Role;
import by.javaguru.je.jdbc.entity.User;
import by.javaguru.je.jdbc.utils.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao implements Dao<Long, User> {
    private static final UserDao INSTANCE = new UserDao();

    private static final String SAVE_SQL = """
            INSERT INTO users (name, birthday, email, password, role, gender) VALUES (?,?,?,?,?,?)
            """;
    private static final String FIND_BY_EMAIL_AND_PASSWORD = """
            SELECT * FROM users WHERE email=? AND password=?;
            """;

    @Override
    public User save(User user) {
        try (Connection db = ConnectionManager.get();
             PreparedStatement statement = db.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setObject(2, user.getBirthday());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, String.valueOf(user.getRole()));
            statement.setString(6, String.valueOf(user.getGender()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getObject("id", Integer.class));
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }


    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean update(User user) {
        return false;
    }


    public static UserDao getInstance() {
        return INSTANCE;
    }

    public Optional<User> findByEmailAndPassword(String email, String pwd) {
        User user = null;
        try (Connection db = ConnectionManager.get();
             PreparedStatement statement = db.prepareStatement(FIND_BY_EMAIL_AND_PASSWORD)) {
            statement.setString(1,email);
            statement.setString(2,pwd);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                user = buildEntity(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User buildEntity(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getObject("id",Integer.class))
                .name(resultSet.getObject("name",String.class))
                .birthday(resultSet.getObject("birthday",Date.class).toLocalDate())
                .email(resultSet.getObject("email",String.class))
                .password(resultSet.getObject("password",String.class))
                .role(Role.valueOf(resultSet.getObject("role",String.class)))
                .gender(Gender.valueOf(resultSet.getObject("gender",String.class)))
                .build();
    }
}
