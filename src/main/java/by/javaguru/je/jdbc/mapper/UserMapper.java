package by.javaguru.je.jdbc.mapper;

import by.javaguru.je.jdbc.dto.UserDto;
import by.javaguru.je.jdbc.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper implements Mapper<UserDto,User> {
    private static final UserMapper INSTANCE = new UserMapper();

    @Override
    public UserDto mapFrom(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .role(user.getRole())
                .gender(user.getGender())
                .build();
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }


}
