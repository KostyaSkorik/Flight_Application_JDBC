package by.javaguru.je.jdbc.validator;

import by.javaguru.je.jdbc.dto.CreateUserDto;
import by.javaguru.je.jdbc.utils.LocalDateFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto>{

    private static final CreateUserValidator INSTANCE = new CreateUserValidator();



    public ValidationResult isValid(CreateUserDto userDto) {
        ValidationResult validationResult = new ValidationResult();
        if (!LocalDateFormatter.isValid(userDto.getBirthday())) {
            validationResult.add(Error.of("invalid.birthday","Birthday is invalid"));
        }
        if(userDto.getName().isEmpty()){
            validationResult.add(Error.of("invalid.name","Name is invalid"));
        }
        if(userDto.getEmail().isEmpty()){
            validationResult.add(Error.of("invalid.email","Email is invalid"));
        }
        if(userDto.getPassword().isEmpty()){
            validationResult.add(Error.of("invalid.password","Password is invalid"));
        }
        if(userDto.getRole().isEmpty()){
            validationResult.add(Error.of("invalid.role","Role is invalid"));
        }
        if(userDto.getGender().isEmpty()){
            validationResult.add(Error.of("invalid.gender","Gender is invalid"));
        }
        return validationResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
