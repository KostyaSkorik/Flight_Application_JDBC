package by.javaguru.je.jdbc.service;

import by.javaguru.je.jdbc.dao.UserDao;
import by.javaguru.je.jdbc.dto.CreateUserDto;
import by.javaguru.je.jdbc.mapper.CreateUserMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserDao userDao = UserDao.getInstance();

    public Integer createUser(CreateUserDto createUserDto){
        var user = createUserMapper.mapFrom(createUserDto);
        var result = userDao.save(user);
        return result.getId();
    }


    public static UserService getInstance() {
        return INSTANCE;
    }

}


